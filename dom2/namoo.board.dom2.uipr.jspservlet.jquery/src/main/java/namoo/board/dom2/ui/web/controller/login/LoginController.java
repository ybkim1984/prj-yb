/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 */
package namoo.board.dom2.ui.web.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.ui.web.session.SessionManager;

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
        
        SessionManager sessionManager = SessionManager.getInstance(req);
        
        resp.setContentType("text/html;charset=UTF-8");
        
        if (sessionManager.login(email, password)) {
            //
            resp.getWriter().write(Boolean.TRUE.toString());
        } else {
            //
        	resp.getWriter().write(Boolean.FALSE.toString());
        }
        resp.getWriter().close();
    }
}
