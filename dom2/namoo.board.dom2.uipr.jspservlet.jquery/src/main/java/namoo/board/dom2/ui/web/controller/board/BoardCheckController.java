/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kwlee@nextree.co.kr">Lee, Kiwang</a>
 * @since 2015. 3. 10.
 */
package namoo.board.dom2.ui.web.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/board/check")
public class BoardCheckController extends HttpServlet {
    //
    private static final long serialVersionUID = 495098317447302613L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
    	SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
        
    	List<DCSocialBoard> boards = soicalBoardService.findAllSocialBoards();
		Boolean nameCheckOk = true;
		String boardName = req.getParameter("boardName");
		
		for (DCSocialBoard socialBoard : boards) {
			if (boardName.equals(socialBoard.getName())) {
				nameCheckOk = false;
				break;
			}
		}

		resp.getWriter().write(nameCheckOk.toString());
		resp.getWriter().close();
    }
}
