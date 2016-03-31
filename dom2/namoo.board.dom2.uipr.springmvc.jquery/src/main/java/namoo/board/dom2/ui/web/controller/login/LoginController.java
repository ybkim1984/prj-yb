/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 6.
 */

package namoo.board.dom2.ui.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.util.exception.NamooBoardException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {
	//
	@Autowired
	private BoardUserService boardUser;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestParam("inputEmail") String email, @RequestParam("inputPassword") String password,

	HttpServletRequest req) throws NamooBoardException {
		//
		SessionManager sessionManager = SessionManager.getInstance(req);

		if (sessionManager.login(email, password)) {
			//
			return Boolean.TRUE.toString();

		} else {
			//
			return Boolean.FALSE.toString();

		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		//
		SessionManager.getInstance(req).logout();

		return "redirect:/home";
	}
}
