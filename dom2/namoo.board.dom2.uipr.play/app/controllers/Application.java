package controllers;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.service.BoardUserService;
import play.cache.Cache;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.BoardServiceFactory;

public class Application extends Controller {
	
    private static BoardUserService boardUserService = BoardServiceFactory.getInstance().getBoardUserService();
    public static Result index() {
    	//
        List<DCBoardUser> users = new ArrayList<DCBoardUser>();
        
        users.add(new DCBoardUser("hkkang@nextree.co.kr", "강형구", "010-1234-5678"));
        users.add(new DCBoardUser("syhan@nextree.co.kr", "한성영", "010-0000-0000"));
        users.add(new DCBoardUser("eschoi@nextree.co.kr", "최은선", "010-2222-2222"));

        for (DCBoardUser user : users) {
            boardUserService.registerUser(user);
        }

        // 팀 데이터 생성
        DCBoardTeam team = new DCBoardTeam("컨설팅팀", new DCBoardUser("eschoi@nextree.co.kr"));
        team.setUsid("000");

        boardUserService.registerBoardTeam(team.getName(),"eschoi@nextree.co.kr");
       
        return loginForm();
    }
    
    public static Result loginForm() {    
    	//
    	return ok(views.html.login.render());
    }
	
    public static Result login() {
    	//
    	Form<DCBoardUser> accountForm = Form.form(DCBoardUser.class).bindFromRequest();
    	
    	if(accountForm.hasErrors()) {
    		return badRequest(views.html.login.render());
    	}
    	String email = accountForm.get().getEmail();
    	String password = accountForm.get().USER_PASSWORD;
    	
    	
    	DCBoardUser boardUser = boardUserService.findUserWithEmail(email);
    	if (boardUser == null) {
    		String message = "로그인에 실패하였습니다. 이메일을 확인하세요";
			String linkURL = "/login";
    		return ok(views.html.message.error.render(message, linkURL));
    	}
    	
    	if (!"1234".equals(password)) {
    		String message = "로그인에 실패하였습니다. 비밀번호를 확인하세요";
			String linkURL = "/towner/login";
    		return ok(views.html.message.error.render(message, linkURL));
    	}
    	
    	session().put("name", boardUser.getName());
    	session().put("email", boardUser.getEmail());
    	Cache.set(boardUser.getEmail(), boardUser);
    	
    	return redirect(routes.Postings.home());
    }
    
    public static Result logout() {
    	//
    	Cache.remove(session().get("email"));
    	session().clear();
    	
    	flash().put("success", "로그아웃되었습니다.");
    	
    	return redirect(routes.Postings.home());
    }

}
