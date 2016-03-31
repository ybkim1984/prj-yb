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

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.ui.web.common.LoginManager;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/post/write")
public class PostWriteController extends HttpServlet {
	//
	private static final long serialVersionUID = -3930852193813271610L;
	
	private static SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
	private static PostingService postingService = BoardServiceFactory.getInstance().createPostingService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if(!LoginManager.getInstance(req, resp).isLogin()) {
			return;
		}
	 
		String boardUsid = req.getParameter("boardUsid");

		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		Boolean commentable = socialBoard.isCommentable();
		
		req.setAttribute("commentable", commentable);
		req.setAttribute("boardUsid", boardUsid);
		req.setAttribute("boardList", socialBoards);
		
		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/post/postWrite.jsp");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
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
		
		postingService.registerPosting(boardUsid, postingCdo);
		
		String message = "게시판 생성이 완료되었습니다.";
		String confirmUrl = "/post/list?boardUsid="+boardUsid+"&page=1";

		PageTransfer.getInstance(req, resp).information(message, confirmUrl);
	}
}
