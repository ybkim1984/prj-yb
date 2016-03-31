/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 6.
 */

package namoo.board.dom2.ui.web.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.session.LoginRequired;
import namoo.board.dom2.util.namevalue.NameValueList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@LoginRequired
public class BoardController {
	//
	@Autowired
	private SocialBoardService soicalBoardService;

	@Autowired
	private BoardUserService boardUserService;

	// --------------------------------------------------------------------------
	// 보드리스트

	@RequestMapping(value = "boards", method = RequestMethod.GET)
	public String main(HttpServletRequest req) {
		//
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		req.setAttribute("boardList", socialBoards);

		return "/board/list";
	}

	// --------------------------------------------------------------------------
	// 보드개설

	@RequestMapping(value = "board/create", method = RequestMethod.GET)
	public String create(HttpServletRequest req) {
		//
		List<DCBoardTeam> teams = boardUserService.findAllBoardTeams();
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();

		req.setAttribute("boardList", socialBoards);
		req.setAttribute("teams", teams);

		return "board/create";
	}

	// --------------------------------------------------------------------------
	// 보드개설및 수정시 게시판 이름 체크

	@RequestMapping(value = "board/check", method = RequestMethod.POST)
	public @ResponseBody String check(@RequestParam("boardName") String boardName) {
		//
		List<DCSocialBoard> boards = soicalBoardService.findAllSocialBoards();
		Boolean nameCheckOk = true;
		for (DCSocialBoard socialBoard : boards) {
			if (boardName.equals(socialBoard.getName())) {
				nameCheckOk = false;
				break;
			}
		}

		return nameCheckOk.toString();
	}

	@RequestMapping(value = "board/create", method = RequestMethod.POST)
	public @ResponseBody DCSocialBoard create(@RequestParam("teamUsid") String teamUsid, @RequestParam("boardName") String boardName, @RequestParam("commentable") boolean comment) {
		//
		String boardUsid = soicalBoardService.registerSocialBoard(teamUsid, boardName, comment);
		DCSocialBoard board = soicalBoardService.findSocialBoard(boardUsid);
		return board;
	}

	// --------------------------------------------------------------------------
	// 보드 수정

	@RequestMapping(value = "board/{boardUsid}", method = RequestMethod.GET)
	public String update(@PathVariable("boardUsid") String boardUsid, HttpServletRequest req) {
		//
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();

		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);

		return "board/update";

	}

	@RequestMapping(value = "board/{boardUsid}", method = RequestMethod.POST)
	public @ResponseBody String update(@PathVariable("boardUsid") String boardUsid, @RequestParam("boardName") String boardName) {
		//
		NameValueList nameValues = NameValueList.getInstance();
		nameValues.add("name", boardName);

		soicalBoardService.modifySocialBoard(boardUsid, nameValues);
		return Boolean.TRUE.toString();

	}

	// --------------------------------------------------------------------------
	// 보드 삭제

	@RequestMapping(value = "board/{boardUsid}/delete")
	public @ResponseBody String delete(@PathVariable("boardUsid") String boardUsid) {
		//
		soicalBoardService.removeSocialBoard(boardUsid);
		return Boolean.TRUE.toString();
	}
}
