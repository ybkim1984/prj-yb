/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
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
import namoo.board.dom2.ui.web.util.MessagePage;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
@LoginRequired
public class PostController {
	//
	@Autowired
	private SocialBoardService soicalBoardService;
	
	@Autowired
	private PostingService postingService;
	
	//--------------------------------------------------------------------------
	// 게시판 메인
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String main(HttpServletRequest req) throws NamooBoardException {
		// 
		List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
		req.setAttribute("boardList", socialBoards);
		
		return "post/main";
	}
	
	//--------------------------------------------------------------------------
	// 게시판 상세보기
		
	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}", method = RequestMethod.GET)
	public String detail(@PathVariable("boardUsid") String boardUsid,
			@PathVariable("postingUsid") String postingUsid,
			HttpServletRequest req) throws NamooBoardException {
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
	//--------------------------------------------------------------------------
	// 게시판 리스트
			
	@RequestMapping(value = "board/{boardUsid}/posts", method = RequestMethod.GET)
	public String list(@PathVariable("boardUsid") String boardUsid,
			@RequestParam("page") String page, 
			HttpServletRequest req) throws NamooBoardException {
		// 
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
		
		return "post/list";
	}
	//--------------------------------------------------------------------------
	// 포스트 생성
	
	@RequestMapping(value = "board/{boardUsid}/post/create", method = RequestMethod.GET)
	public String create(@PathVariable("boardUsid") String boardUsid,
			HttpServletRequest req) throws NamooBoardException {
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
	public ModelAndView create(
			@PathVariable("boardUsid") String boardUsid, 
			@RequestParam("title") String title, 
			@RequestParam("contents") String contents, 
			@RequestParam("writerEmail") String writerEmail, 
			@RequestParam(value="anonymousComment",required=false) String anonymousComment, 
			@RequestParam(value="commentable",required=false) String commentable, 
			HttpServletRequest req) throws NamooBoardException {
		// 
		System.out.println("ss");
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
		
		String message = "작성한 글이 저장되었습니다.";
		String linkURL = "board/"+boardUsid+"/posts?page=1";

		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 포스트 수정
	
	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/update", method = RequestMethod.GET)
	public String update(
			@PathVariable("boardUsid") String boardUsid, 
			@PathVariable("postingUsid") String postingUsid, 
			HttpServletRequest req) throws NamooBoardException {
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
	public ModelAndView update(
			@RequestParam("contents") String contents, 
			@RequestParam("postingUsid") String postingUsid, 
			@RequestParam("boardUsid") String boardUsid) throws NamooBoardException {
		// 
		postingService.modifyPostingContents(postingUsid, contents);
		
		String message = "게시판이 수정되었습니다.";
		String linkURL = "board/"+boardUsid+"/post/"+ postingUsid;

		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 포스트 삭제
	
	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/delete")
	public ModelAndView delete(
			@PathVariable("boardUsid") String boardUsid,
			@PathVariable("postingUsid") String postingUsid) throws NamooBoardException {
		//
		postingService.removePosting(postingUsid);
		
		String message = "게시판이 삭제되었습니다.";
		String linkURL = "/board/"+boardUsid + "/posts?page=1";
		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 댓글 쓰기
	
	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/comment", method = RequestMethod.POST)
	public ModelAndView comment(
			@PathVariable("boardUsid") String boardUsid,
			@PathVariable("postingUsid") String postingUsid,
			@RequestParam("comment") String comment, 
			@RequestParam("email") String email) throws NamooBoardException {
		//
		CommentCdo commentCdo = new CommentCdo(comment, email);
		postingService.attachComment(postingUsid, commentCdo);
		
		String linkURL = "/board/"+boardUsid + "/post/"+ postingUsid;
		return new ModelAndView(new RedirectView(linkURL, true));
	}
	
	@RequestMapping(value = "board/{boardUsid}/post/{postingUsid}/comment/{sequence}/delete")
	public String comment(
			@PathVariable("boardUsid") String boardUsid,
			@PathVariable("postingUsid") String postingUsid,
			@PathVariable("sequence") String sequence) throws NamooBoardException {
		//
		postingService.detachComment(postingUsid, Integer.parseInt(sequence));
		
		return "redirect:"+ "/board/"+boardUsid + "/post/"+ postingUsid;
	}
}
