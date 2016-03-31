/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.controller.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;

@WebServlet("/comment/delete")
public class CommentDeleteController extends HttpServlet {
	//
	private static final long serialVersionUID = -3265187027618151077L;

	private static PostingService postingService = BoardServiceFactory.getInstance().createPostingService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		String boardUsid = req.getParameter("boardUsid");
		String postingUsid = req.getParameter("postingUsid");
		String sequence = req.getParameter("sequence");
		
		postingService.detachComment(postingUsid, Integer.parseInt(sequence));
		
		PageTransfer.getInstance(req, resp).redirectTo("/post/detail?boardUsid="+boardUsid+"&postingUsid="+postingUsid);
	}
}
