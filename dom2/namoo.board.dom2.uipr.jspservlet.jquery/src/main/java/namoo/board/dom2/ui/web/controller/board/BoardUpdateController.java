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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.ui.web.common.PageTransfer;
import namoo.board.dom2.ui.web.session.SessionManager;
import namoo.board.dom2.ui.web.util.BoardServiceFactory;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;

@WebServlet("/board")
public class BoardUpdateController extends HttpServlet {
    //
    private static final long serialVersionUID = 495098317447302613L;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();

        // 로그인 체크
        if(!SessionManager.getInstance(req).isLogin()) {
            
            String message = SessionManager.LOGIN_REQUIRED_MESSAGE;
            String linkUrl = "/login";
            
            PageTransfer.getInstance(req, resp).error(message, linkUrl);
            return;
        }
        
        String boardUsid = req.getParameter("boardUsid");
        
        DCSocialBoard socialBoard = soicalBoardService.findSocialBoard(boardUsid);
        List<DCSocialBoard> socialBoards = soicalBoardService.findAllSocialBoards();
        
        req.setAttribute("boardList", socialBoards);
        req.setAttribute("socialBoard", socialBoard);
        
        // request dispatch
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/board/update.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //
        SocialBoardService soicalBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
        
        String boardUsid = req.getParameter("boardUsid");
        String boardName = req.getParameter("boardName");
        
        NameValueList nameValues = NameValueList.getInstance();            
        nameValues.add("name", boardName);
    
        soicalBoardService.modifySocialBoard(boardUsid, nameValues);
        DCSocialBoard board = soicalBoardService.findSocialBoard(boardUsid);

        resp.getWriter().write(JsonUtil.toJson(board));
        resp.getWriter().close();
    }
}
