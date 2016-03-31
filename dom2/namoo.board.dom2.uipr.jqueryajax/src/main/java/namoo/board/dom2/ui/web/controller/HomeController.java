/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;

@WebServlet("/home")
public class HomeController extends HttpServlet {
	//
	private static final long serialVersionUID = -5861495189713689207L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		if (SessionManager.getInstance(req).isLogin()) {
			PageTransfer.getInstance(req, resp).redirectTo("/post");
		}else {
			PageTransfer.getInstance(req, resp).redirectTo("/login");
		}
		
	}
}
