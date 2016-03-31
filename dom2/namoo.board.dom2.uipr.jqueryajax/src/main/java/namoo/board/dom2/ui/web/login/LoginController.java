/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:yongjunlee@nextree.co.kr">Lee, Yongjun</a>
 * @since 2015. 1. 22.
 */
package namoo.board.dom2.ui.web.login;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;

import com.google.gson.JsonObject;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	//
	private static final long serialVersionUID = -7268145103091648288L;
	
	private ExcelFileBatchLoader excelLoader;
	
	public void init() throws ServletException
    {
		BoardStoreLifecycler storeLifecycler = BoardStoreMyBatisLifecycler.getInstance();
		BoardUserService boardUserService = BoardServicePojoLifecycler.getInstance(storeLifecycler).getBoardUserService();
		List<DCBoardUser> allUsers = boardUserService.findAllUsers();
		if (allUsers == null || allUsers.isEmpty()) {
			BoardServiceLifecycler serviceLifecycler = BoardServicePojoLifecycler.getInstance(storeLifecycler);
			excelLoader = serviceLifecycler.getExcelFileBatchLoader();
			
			URL path = this.getClass().getResource("/BoardUsers.xlsx");
			excelLoader.registerServiceUsers(new File(path.getPath()));
		}
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		PageTransfer.getInstance(req, resp).forwardTo("/WEB-INF/views/user/login.jsp");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//
		String email = req.getParameter("inputEmail");
		String password = req.getParameter("inputPassword");
		
		SessionManager sessionManager = SessionManager.getInstance(req);
		boolean isLogin = sessionManager.login(email, password);
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("isLogin", isLogin);
		
		resp.setContentType("text/html;charset=UTF-8");		
		PrintWriter pw = resp.getWriter();
		pw.write(jsonObj.toString());
		pw.close();
	}
	
}
