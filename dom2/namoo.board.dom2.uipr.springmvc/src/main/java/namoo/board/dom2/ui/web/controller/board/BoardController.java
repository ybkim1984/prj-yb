/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package namoo.board.dom2.ui.web.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.session.LoginRequired;
import namoo.board.dom2.ui.web.util.MessagePage;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.namevalue.NameValueList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@LoginRequired
public class BoardController {
	//
	@Autowired
	private SocialBoardService soicalBoardService;
	
	@Autowired
	private BoardUserService boardUserService;
	
	//--------------------------------------------------------------------------
	// 보드리스트
	
	@RequestMapping(value = "boards", method = RequestMethod.GET)
	public String main(HttpServletRequest req) throws NamooBoardException {
		// 
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		req.setAttribute("boardList", socialBoards);
		
		return "/board/list";
	}
	
	//--------------------------------------------------------------------------
	// 보드개설
	
	@RequestMapping(value = "board/create", method = RequestMethod.GET)
	public String create(HttpServletRequest req) throws NamooBoardException {
		//
		List<DCBoardTeam> teams = boardUserService.findAllBoardTeams();
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("teams", teams);
		
		return "board/create";
	}
	
	@RequestMapping(value = "board/create", method = RequestMethod.POST)
	public ModelAndView create(
			@RequestParam("teamUsid") String teamUsid, 
			@RequestParam("boardName") String boardName, 
			@RequestParam(value="commentable",required=false) String commentable 
			) throws NamooBoardException {
		// 
		Boolean comment = false;
		if("on".equals(commentable)) {
			comment = true;
		}
		soicalBoardService.registerSocialBoard(teamUsid, boardName, comment);
		
		String message = "게시판 생성이 완료되었습니다.";
		String linkURL = "boards";

		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 보드 수정
	
	@RequestMapping(value = "board/{boardUsid}", method = RequestMethod.GET)
	public String update(
			@PathVariable("boardUsid") String boardUsid, 
			HttpServletRequest req) throws NamooBoardException {
		// 
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);
		
		return "board/update";
		
	}
	
	@RequestMapping(value = "board/{boardUsid}", method = RequestMethod.POST)
	public ModelAndView update(
			@PathVariable("boardUsid") String boardUsid, 
			@RequestParam("boardName") String boardName) throws NamooBoardException {
		// 
		NameValueList nameValues = NameValueList.getInstance();			
		nameValues.add("name", boardName);
	
		soicalBoardService.modifySocialBoard(boardUsid, nameValues);
		
		String message = "게시판이 수정되었습니다.";
		String linkURL = "boards";

		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 보드 삭제
	
	
	@RequestMapping(value = "board/{boardUsid}/delete")
	public ModelAndView delete(
			@PathVariable("boardUsid") String boardUsid) throws NamooBoardException {
		//
		soicalBoardService.removeSocialBoard(boardUsid);
		
		String message = "게시판이 삭제되었습니다.";
		String linkURL = "boards";
		return MessagePage.information(message, linkURL);
	}
}
