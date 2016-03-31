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

@WebServlet("/post/modify")
public class PostUpdateController extends HttpServlet {
	//
	private static final long serialVersionUID = -5560368585200241068L;
	
	private static SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
	private static PostingService postingService = BoardServiceFactory.getInstance().createPostingService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if(!LoginManager.getInstance(req, resp).isLogin()) {
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

		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/post/postModify.jsp");
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		String contents = req.getParameter("contents");
		String postingUsid = req.getParameter("postingUsid");
		String boardUsid = req.getParameter("boardUsid");
		
		postingService.modifyPostingContents(postingUsid, contents);
		
		PageTransfer.getInstance(req, resp).redirectTo("/post/detail?boardUsid="+boardUsid+"&postingUsid="+postingUsid);
	}
}
