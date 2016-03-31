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
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;
import namoo.board.dom2.util.json.JsonUtil;

@WebServlet("/posting/create")
public class PostingCreateController extends HttpServlet {
    //
    private static final long serialVersionUID = -3930852193813271610L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
        
        // 로그인 체크
        if(!SessionManager.getInstance(req).isLogin()) {
            
            String message = SessionManager.LOGIN_REQUIRED_MESSAGE;
            String linkUrl = "/login";
            
            PageTransfer.getInstance(req, resp).error(message, linkUrl);
            return;
        }
     
        String boardUsid = req.getParameter("boardUsid");

        List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
        DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
        Boolean commentable = socialBoard.isCommentable();
        
        req.setAttribute("commentable", commentable);
        req.setAttribute("boardUsid", boardUsid);
        req.setAttribute("boardList", socialBoards);
        
        // request dispatch
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/posting/create.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        PostingService postingService = BoardServiceFactory.getInstance().createPostingService();
        
        String boardUsid = req.getParameter("boardUsid");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        String writerEmail = req.getParameter("writerEmail");
        
        String commentable = req.getParameter("commentable");
        String anonymousComment = req.getParameter("anonymousComment");
        
        Boolean comment = true;
        if("on".equals(commentable)) {
            comment = false;
        }
        
        Boolean anonymous = false;
        if("on".equals(anonymousComment)) {
            anonymous = true;
        }
    
        PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
        postingCdo.setAnonymousComment(anonymous);
        postingCdo.setCommentable(comment);
        
        String usid = postingService.registerPosting(boardUsid, postingCdo);
        DCPosting posting = postingService.findPosting(usid);
        
        resp.getWriter().write(JsonUtil.toJson(posting));
        resp.getWriter().close();
    }
}
