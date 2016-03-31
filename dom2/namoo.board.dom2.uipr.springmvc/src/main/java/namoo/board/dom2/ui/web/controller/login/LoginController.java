/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package namoo.board.dom2.ui.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.ui.web.util.MessagePage;
import namoo.board.dom2.util.exception.NamooBoardException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class LoginController {
	//
	@Autowired
	private BoardUserService boardUser;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		//
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("inputEmail") String email, @RequestParam("inputPassword") String password,

	HttpServletRequest req) throws NamooBoardException {
		//
		SessionManager sessionManager = SessionManager.getInstance(req);

		if (sessionManager.login(email, password)) {
			//

			return new ModelAndView(new RedirectView("/home", true));
		} else {
			//
			req.setAttribute("email", email);
			req.setAttribute("password", password);

			String message = "로그인에 실패하였습니다. 회원정보를 확인하세요";
			String linkURL = "/login";

			return MessagePage.error(message, linkURL);
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		//
		SessionManager.getInstance(req).logout();

		return "redirect:/home";
	}
}
