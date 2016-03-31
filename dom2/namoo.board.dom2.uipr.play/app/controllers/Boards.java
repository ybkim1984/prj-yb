package controllers;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.namevalue.NameValueList;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.BoardServiceFactory;

public class Boards extends Controller{
	//
	private static SocialBoardService socialBoardService = BoardServiceFactory.getInstance().getSocialBoardService();
	private static BoardUserService boardUserService = BoardServiceFactory.getInstance().getBoardUserService();

	public static Result create() {
		//
		Form<DCSocialBoard> socialBoard = Form.form(DCSocialBoard.class);
		DCSocialBoard dto = socialBoard.bindFromRequest().get();
		socialBoardService.registerSocialBoard(dto.getTeamUsid(), dto.getName(), dto.isCommentable());

		return redirect("/boards");
	}

	public static Result list() {
		//
		List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
		List<DCBoardTeam> teams = boardUserService.findAllBoardTeams();


		return ok(views.html.board.render(socialBoards, teams));
	}
	public static Result update(String usid) {
		//
		Form<DCSocialBoard> socialBoardForm = Form.form(DCSocialBoard.class);
		DCSocialBoard socialBoard = socialBoardForm.bindFromRequest().get();		
		NameValueList nameValues = NameValueList.getInstance();	
		nameValues.add("name", socialBoard.getName());
		
		socialBoardService.modifySocialBoard(usid, nameValues);
		return redirect("/boards");
	}
	
	public static Result delete(String usid) {
		//
		socialBoardService.removeSocialBoard(usid);
		return redirect("/boards");
	}
	
	
}
