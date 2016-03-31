/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 6.
 */

package namoo.board.dom2.ui.web.controller.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.ui.web.session.LoginRequired;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

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
public class PostController {
	//
	@Autowired
	private SocialBoardService soicalBoardService;

	@Autowired
	private PostingService postingService;

	// --------------------------------------------------------------------------
	// 게시판 메인

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String main(HttpServletRequest req) {
		//
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		req.setAttribute("boardList", socialBoards);

		return "post/main";
	}

	// --------------------------------------------------------------------------
	// 게시판 상세보기

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}", method = RequestMethod.GET)
	public String detail(@PathVariable("boardUsid") String boardUsid, @PathVariable("postingUsid") String postingUsid, HttpServletRequest req) {
		//
		DCPosting posting = postingService.findPostingWithContents(postingUsid);
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		postingService.increasePostingReadCount(postingUsid);

		req.setAttribute("posting", posting);
		req.setAttribute("boardUsid", boardUsid);
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);

		return "post/detail";
	}

	// --------------------------------------------------------------------------
	// 게시판 리스트

	@RequestMapping(value = "board/{boardUsid}/posts", method = RequestMethod.GET)
	public String list(@PathVariable("boardUsid") String boardUsid, @RequestParam("page") String page, HttpServletRequest req) {
		//
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);

		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();

		int itemLimitOfPage = 3;
		int paging = Integer.parseInt(page);
		PageCriteria pageCriteria = new PageCriteria(paging, itemLimitOfPage);
		Page<DCPosting> postings = postingService.findPostingPage(boardUsid, pageCriteria);

		req.setAttribute("boardUsid", boardUsid);
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);
		req.setAttribute("postings", postings);

		return "post/list";
	}

	// --------------------------------------------------------------------------
	// 포스트 생성

	@RequestMapping(value = "board/{boardUsid}/post/create", method = RequestMethod.GET)
	public String create(@PathVariable("boardUsid") String boardUsid, HttpServletRequest req) {
		//
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		Boolean commentable = socialBoard.isCommentable();

		req.setAttribute("commentable", commentable);
		req.setAttribute("boardUsid", boardUsid);
		req.setAttribute("boardList", socialBoards);

		return "post/create";
	}

	@RequestMapping(value = "board/{boardUsid}/post/create", method = RequestMethod.POST)
	public @ResponseBody DCPosting create(@PathVariable("boardUsid") String boardUsid, @RequestParam("title") String title, @RequestParam("contents") String contents, @RequestParam("writerEmail") String writerEmail,
			@RequestParam(value = "anonymousComment") Boolean anonymousComment, @RequestParam(value = "commentable") Boolean commentable, HttpServletRequest req) {
		//

		PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
		postingCdo.setAnonymousComment(anonymousComment);
		postingCdo.setCommentable(commentable);

		String usid = postingService.registerPosting(boardUsid, postingCdo);
		DCPosting posting = postingService.findPosting(usid);
		return posting;
	}

	// --------------------------------------------------------------------------
	// 포스트 수정

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/update", method = RequestMethod.GET)
	public String update(@PathVariable("boardUsid") String boardUsid, @PathVariable("postingUsid") String postingUsid, HttpServletRequest req) {
		//
		DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
		DCPosting posting = postingService.findPostingWithContents(postingUsid);
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();

		req.setAttribute("posting", posting);
		req.setAttribute("boardUsid", boardUsid);
		req.setAttribute("boardList", socialBoards);
		req.setAttribute("socialBoard", socialBoard);

		return "post/update";

	}

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}", method = RequestMethod.POST)
	public @ResponseBody DCPosting update(@RequestParam("contents") String contents, @RequestParam("postingUsid") String postingUsid, @RequestParam("boardUsid") String boardUsid) {
		//
		postingService.modifyPostingContents(postingUsid, contents);

		DCPosting posting = postingService.findPosting(postingUsid);

		return posting;
	}

	// --------------------------------------------------------------------------
	// 포스트 삭제

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/delete")
	public @ResponseBody Boolean delete(@PathVariable("boardUsid") String boardUsid, @PathVariable("postingUsid") String postingUsid) {
		//
		postingService.removePosting(postingUsid);

		return Boolean.TRUE;
	}

	// --------------------------------------------------------------------------
	// 댓글 쓰기

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/comment", method = RequestMethod.POST)
	public @ResponseBody Boolean comment(@PathVariable("boardUsid") String boardUsid, @PathVariable("postingUsid") String postingUsid, @RequestParam("comment") String comment, @RequestParam("email") String email) {
		//
		CommentCdo commentCdo = new CommentCdo(comment, email);
		postingService.attachComment(postingUsid, commentCdo);

		return Boolean.TRUE;
	}

	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/comment/{sequence}/delete")
	public @ResponseBody Boolean comment(@PathVariable("boardUsid") String boardUsid, @PathVariable("postingUsid") String postingUsid, @PathVariable("sequence") String sequence) {
		//
		postingService.detachComment(postingUsid, Integer.parseInt(sequence));

		return Boolean.TRUE;
	}
}
