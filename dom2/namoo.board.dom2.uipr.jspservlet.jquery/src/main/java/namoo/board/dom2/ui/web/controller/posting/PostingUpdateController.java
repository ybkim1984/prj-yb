/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 */
package namoo.board.dom2.ui.web.controller.posting;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/posting")
public class PostingUpdateController extends HttpServlet {
    //
    private static final long serialVersionUID = -5560368585200241068L;
    
    private final SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
    private final PostingService postingService = BoardServiceFactory.getInstance().createPostingService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        // 로그인 체크
        if(!SessionManager.getInstance(req).isLogin()) {
            
            String message = SessionManager.LOGIN_REQUIRED_MESSAGE;
            String linkUrl = "/login";
            
            PageTransfer.getInstance(req, resp).error(message, linkUrl);
            return;
        }
        
        String boardUsid = req.getParameter("boardUsid");
        String postingUsid = req.getParameter("postingUsid");
        
        DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
        DCPosting posting = postingService.findPostingWithContents(postingUsid);
        List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
        
        req.setAttribute("posting", posting);
        req.setAttribute("boardUsid", boardUsid);
        req.setAttribute("boardList", socialBoards);
        req.setAttribute("socialBoard", socialBoard);

        // request dispatch
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/posting/update.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        String contents = req.getParameter("contents");
        String postingUsid = req.getParameter("postingUsid");
        
        postingService.modifyPostingContents(postingUsid, contents);
        
        resp.getWriter().write(Boolean.TRUE.toString());
        resp.getWriter().close();
    }
}
