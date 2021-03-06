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
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.common.LoginManager;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;
import namoo.board.dom2.util.namevalue.NameValueList;

@WebServlet("/board/detail")
public class BoardUpdateController extends HttpServlet {
	//
	private static final long serialVersionUID = 495098317447302613L;
	
	private static SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if(!LoginManager.getInstance(req, resp).isLogin()) {
			return;
		}
		
		String boardUsid = req.getParameter("boardUsid");
		
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);
		
		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/board/boardModify.jsp");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		String boardUsid = req.getParameter("boardUsid");
		String boardName = req.getParameter("boardName");
		
		NameValueList nameValues = NameValueList.getInstance();			
		nameValues.add("name", boardName);
	
		soicalBoardService.modifySocialBoard(boardUsid, nameValues);
		
		String message = "게시판이 수정되었습니다.";
		String confirmUrl = "/board";

		PageTransfer.getInstance(req, resp).information(message, confirmUrl);
	}
}
