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

import namoo.board.dom2.ui.web.session.SessionManager;

@WebServlet("/main")
public class HomeController extends HttpServlet {
    //
    private static final long serialVersionUID = -5861495189713689207L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        String redirectUrl = null;
        if (SessionManager.getInstance(req).isLogin()) {
            redirectUrl = getServletContext().getContextPath() + "/home";
        }else {
            redirectUrl = getServletContext().getContextPath() + "/login";
        }
        
        resp.sendRedirect(redirectUrl);
    }
}
