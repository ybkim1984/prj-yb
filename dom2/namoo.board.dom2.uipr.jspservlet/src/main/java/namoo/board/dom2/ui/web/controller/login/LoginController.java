/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.util.StringUtils;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    //
    private static final long serialVersionUID = -7268145103091648288L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        // request dispatch
        RequestDispatcher dispatcher = 
                req.getRequestDispatcher("/WEB-INF/views/common/login.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        String email = req.getParameter("inputEmail");
        String password = req.getParameter("inputPassword");
        String redirectUrl = req.getParameter("redirectUrl");
        
        SessionManager sessionManager = SessionManager.getInstance(req);
        if (sessionManager.login(email, password)) {
            //
            if (!StringUtils.isEmpty(redirectUrl)) {
                resp.sendRedirect(redirectUrl);
                return;
            }
            
            // redirect
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
            
        } else {
            //
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            
            String message = "로그인에 실패하였습니다. 회원정보를 확인하세요";
            String confirmUrl = "/login";
            
            PageTransfer.getInstance(req, resp).error(message, confirmUrl);
        }
    }
}
