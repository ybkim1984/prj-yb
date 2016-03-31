package controllers;

import java.util.List;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.BoardServiceFactory;

public class Postings extends Controller{
	//
	private static SocialBoardService socialBoardService = BoardServiceFactory.getInstance().getSocialBoardService();
	private static PostingService postingService = BoardServiceFactory.getInstance().getPostingService();
	
	public static Result createView(String boardUsid) {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
		return ok(views.html.posting.create.render(socialBoards,socialBoard));
	}
	public static Result detail(String boardUsid,String postingUsid) {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
		DCPosting posting = postingService.findPostingWithContents(postingUsid);
	
		return ok(views.html.posting.detail.render(socialBoards, posting, socialBoard));
	}
	public static Result create(String boardUsid) {
		//
		Form<PostingCdo> postingCdo = Form.form(PostingCdo.class);
		PostingCdo dto = postingCdo.bindFromRequest().get();
		System.out.println(dto.isAnonymousComment());
		System.out.println(dto.isCommentable());
		postingService.registerPosting(boardUsid, dto);
		return redirect("/board/"+boardUsid +"/postings?page=1");
	}
	
	public static Result home() {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		return ok(views.html.home.render(socialBoards));
	}

	public static Result list(String boardUsid, String page) {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		 int itemLimitOfPage = 3;
	        int paging = Integer.parseInt(page);
	        PageCriteria pageCriteria = new PageCriteria(paging ,itemLimitOfPage);
	        Page<DCPosting> postings = postingService.findPostingPage(boardUsid, pageCriteria);
	        DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
		return ok(views.html.posting.list.render(socialBoards, postings, socialBoard));
	}
	public static Result updateView(String boardUsid, String postingUsid) {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
		DCPosting posting = postingService.findPostingWithContents(postingUsid);
		return ok(views.html.posting.update.render(socialBoards, posting, socialBoard));
	}
	public static Result update(String boardUsid, String postingUsid) {
		//
		Form<PostingCdo> postingCdo = Form.form(PostingCdo.class);
		PostingCdo dto = postingCdo.bindFromRequest().get();	
		
		postingService.modifyPostingContents(postingUsid, dto.getContents());
		return redirect("/board/"+boardUsid +"/posting/"+postingUsid);
	}
	
	public static Result delete(String boardUsid, String postingUsid) {
		//
		postingService.removePosting(postingUsid);
		return redirect("/board/"+boardUsid+"/postings?page=1");
	}
	public static Result commentCreate(String boardUsid, String postingUsid) {
		//
		Form<CommentCdo> commentCdo = Form.form(CommentCdo.class);
		CommentCdo dto = commentCdo.bindFromRequest().get();
		postingService.attachComment(postingUsid, dto);
		return redirect("/board/"+boardUsid +"/posting/"+postingUsid);
	}
	
	public static Result commentDelete(String boardUsid, String postingUsid, int sequence) {
		//
		postingService.detachComment(postingUsid, sequence);
		return redirect("/board/"+boardUsid +"/posting/"+postingUsid);
	}
	
}
