/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 */
package namoo.board.dom2.ui.web.controller.posting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/posting/delete")
public class PostingDeleteController extends HttpServlet {
    //
    private static final long serialVersionUID = 604095350211980740L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        PostingService postingService = BoardServiceFactory.getInstance().createPostingService();
        
        String postingUsid = req.getParameter("postingUsid");
        
        postingService.removePosting(postingUsid);
        
        resp.getWriter().write(Boolean.TRUE.toString());
        resp.getWriter().close();
    }
}
