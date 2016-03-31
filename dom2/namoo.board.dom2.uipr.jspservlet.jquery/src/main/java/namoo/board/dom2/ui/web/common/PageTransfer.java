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

public class PageTransfer {
    //
    private static final String INFO_PAGE = "/WEB-INF/views/common/info_msg.jsp";
    private static final String ERROR_PAGE = "/WEB-INF/views/common/error_msg.jsp";
    private static final String CONFIRM_PAGE = "/WEB-INF/views/common/confirm_msg.jsp";
    
    private HttpServletRequest req;
    private HttpServletResponse resp;
    
    private PageTransfer(HttpServletRequest req, HttpServletResponse resp) {
        //
        this.req = req;
        this.resp = resp;
    }
    
    public static PageTransfer getInstance(HttpServletRequest req, HttpServletResponse resp) {
        //
        return new PageTransfer(req, resp);
    }
    
    public void information(String message, String confirmUrl) throws ServletException, IOException {
        //
        req.setAttribute("message", message);
        req.setAttribute("confirmUrl", req.getContextPath() + confirmUrl);
        
        req.getRequestDispatcher(INFO_PAGE).forward(req, resp);
    }
    
    public void error(String message, String confirmUrl) throws ServletException, IOException {
        //
        req.setAttribute("message", message);
        req.setAttribute("confirmUrl", req.getContextPath() + confirmUrl);

        req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
    }
    
    public void confirm(String message, String confirmUrl) throws ServletException, IOException {
        //
        req.setAttribute("message", message);
        req.setAttribute("confirmUrl", req.getContextPath() + confirmUrl);

        req.getRequestDispatcher(CONFIRM_PAGE).forward(req, resp);
    }
    
    public void confirm(String message, String confirmUrl, String cancelUrl) throws ServletException, IOException {
        //
        req.setAttribute("message", message);
        req.setAttribute("confirmUrl", req.getContextPath() + confirmUrl);
        req.setAttribute("cancelUrl", req.getContextPath() + cancelUrl);
        
        req.getRequestDispatcher(CONFIRM_PAGE).forward(req, resp);
    }

}
