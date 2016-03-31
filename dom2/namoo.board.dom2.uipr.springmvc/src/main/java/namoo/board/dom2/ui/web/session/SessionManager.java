/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package namoo.board.dom2.ui.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.util.exception.NamooBoardException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionManager {
	//
	private static final String LOGIN_USER = "loginUser";
	
	private HttpSession session;
	
	private SessionManager(HttpServletRequest req) {
		//
		this.session = req.getSession();
	}
	
	//--------------------------------------------------------------------------
	
	public static SessionManager getInstance(HttpServletRequest req) {
		//
		return new SessionManager(req);
	}

	public boolean isLogin() {
		//
		return (session.getAttribute(LOGIN_USER) != null) ? true : false;
	}
	
	public boolean login(String email, String password) throws NamooBoardException {
		//
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		BoardUserService boardUserService = context.getBean(BoardUserService.class);
		
		if (boardUserService.loginAsUser(email, password)) {
			DCBoardUser boardUser = boardUserService.findUserWithEmail(email);
			session.setAttribute(LOGIN_USER, boardUser);
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}
	
	public void logout() {
		//
		session.invalidate();
	}

}
