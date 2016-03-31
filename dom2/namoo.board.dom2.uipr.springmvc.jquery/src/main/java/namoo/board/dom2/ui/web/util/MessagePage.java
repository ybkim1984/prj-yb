/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kizellee@nextree.co.kr">kizellee</a>
 * @since 2015. 2. 6.
 */

package namoo.board.dom2.ui.web.util;

import org.springframework.web.servlet.ModelAndView;

public class MessagePage {
	//
	public static ModelAndView information(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("message/info");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView error(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("message/error");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView confirm(String message, String confirmURL) {
		//
		ModelAndView mav = new ModelAndView("message/confirm");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		
		return mav;
	}
	
	public static ModelAndView confirm(String message, String confirmURL, String cancelURL) {
		//
		ModelAndView mav = new ModelAndView("message/confirm");
		mav.addObject("message", message);
		mav.addObject("confirmURL", confirmURL);
		mav.addObject("cancelURL", cancelURL);
		
		return mav;
	}
}
