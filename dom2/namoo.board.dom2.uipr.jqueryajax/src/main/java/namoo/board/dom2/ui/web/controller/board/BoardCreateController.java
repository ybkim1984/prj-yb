/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.common.LoginManager;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/board/create")
public class BoardCreateController extends HttpServlet {
	//
	private static final long serialVersionUID = 495098317447302613L;
	
	private static SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
	private static BoardUserService boardUserService = BoardServiceFactory.getInstance().createBoardUserService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if(!LoginManager.getInstance(req, resp).isLogin()) {
			return;
		}
		
		List<DCBoardTeam> teams = boardUserService.findAllBoardTeams();
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("teams", teams);
	
		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/board/boardCreate.jsp");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		String teamUsid = req.getParameter("teamUsid");
		String boardName = req.getParameter("boardName");
		String commentable = req.getParameter("commentable");
		
		Boolean comment = false;
		if("on".equals(commentable)) {
			comment = true;
		}
		
		soicalBoardService.registerSocialBoard(teamUsid, boardName, comment);
		
		String message = "게시판 생성이 완료되었습니다.";
		String confirmUrl = "/board";

		PageTransfer.getInstance(req, resp).information(message, confirmUrl);
	}
}
