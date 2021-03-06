/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.controller.post;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.common.LoginManager;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

@WebServlet("/post/list")
public class PostListController extends HttpServlet {
	//
	private static final long serialVersionUID = -6013512684261508160L;
	
	private static SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
	private static PostingService postingService = BoardServiceFactory.getInstance().createPostingService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if(!LoginManager.getInstance(req, resp).isLogin()) {
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

		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/post/postList.jsp");
	}
}
