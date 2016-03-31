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
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

@WebServlet("/postings")
public class PostingListController extends HttpServlet {
    //
    private static final long serialVersionUID = -6013512684261508160L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
        PostingService postingService = BoardServiceFactory.getInstance().createPostingService();
        
        // 로그인 체크
        if(!SessionManager.getInstance(req).isLogin()) {
            
            String message = SessionManager.LOGIN_REQUIRED_MESSAGE;
            String linkUrl = "/login";
            
            PageTransfer.getInstance(req, resp).error(message, linkUrl);
            return;
        }
    
        String boardUsid = req.getParameter("boardUsid");
        String page = req.getParameter("page");
        
        DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
        
        List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
        
        int itemLimitOfPage = 3;
        int paging = Integer.parseInt(page);
        PageCriteria pageCriteria = new PageCriteria(paging ,itemLimitOfPage);
        Page<DCPosting> postings = postingService.findPostingPage(boardUsid, pageCriteria);
        
        req.setAttribute("boardUsid", boardUsid);
        req.setAttribute("boardList", socialBoards);
        req.setAttribute("socialBoard", socialBoard);
        req.setAttribute("postings", postings);

        // request dispatch
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/posting/list.jsp");
        dispatcher.forward(req, resp);
    }
}
