/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.ui.web.session.SessionManager;

public class LoginManager {
	//
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public LoginManager(HttpServletRequest req, HttpServletResponse resp) {
		//
		this.req = req;
		this.resp = resp;
	}
	
	public static LoginManager getInstance(HttpServletRequest req, HttpServletResponse resp) {
		//
		return new LoginManager(req, resp);
	}
	
	public boolean isLogin() throws ServletException, IOException {
		//
		if (!SessionManager.getInstance(req).isLogin()) {
			//
			String message = "해당 기능을 사용하기 위해서 로그인이 필요합니다.";
			String linkURL = "/login";
			PageTransfer.getInstance(req, resp).error(message, linkURL);
			return false;
		}
		
		return true;
	}
}
