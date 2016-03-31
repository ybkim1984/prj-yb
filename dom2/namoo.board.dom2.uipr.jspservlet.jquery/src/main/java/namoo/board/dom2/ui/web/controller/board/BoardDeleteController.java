/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 */
package namoo.board.dom2.ui.web.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/board/delete")
public class BoardDeleteController extends HttpServlet {
    //
    private static final long serialVersionUID = -3216928294490480848L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
        
        String boardUsid = req.getParameter("boardUsid");

        soicalBoardService.removeSocialBoard(boardUsid);
        
        resp.getWriter().write(Boolean.TRUE.toString());
        resp.getWriter().close();
    }
}
