/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 6.
 */

package namoo.board.dom2.ui.web.controller;

import javax.servlet.http.HttpServletRequest;

import namoo.board.dom2.ui.web.session.SessionManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String init(HttpServletRequest req) {
		//
		if (SessionManager.getInstance(req).isLogin()) {
			return "redirect:/post";
		} else {
			return "redirect:/login";
		}
	}
}
