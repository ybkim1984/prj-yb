/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */

package namoo.board.dom2.ui.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreJdbcLifecycler;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.util.exception.NamooBoardException;

public class SessionManager {
    //
    private static final String LOGIN_USER = "loginUser";
    public static final String LOGIN_REQUIRED_MESSAGE = "해당 기능을 수행하기 위하여 로그인이 필요합니다.";
    
    private HttpSession session;
    
    private SessionManager(HttpServletRequest req) {
        //
        this.session = req.getSession(false);
    }
    
    //--------------------------------------------------------------------------
    
    public static SessionManager getInstance(HttpServletRequest req) {
        //
        return new SessionManager(req);
    }

    public boolean isLogin() {
        //
        if (session != null && session.getAttribute(LOGIN_USER) != null) {
            return true;
        }
        
        return false;
    }
    
    public boolean login(String email, String password) throws NamooBoardException {
        //
        BoardStoreLifecycler persistLifecycler = BoardStoreJdbcLifecycler.getInstance();
        BoardServiceLifecycler serviceLifecycler = BoardServicePojoLifecycler.getInstance(persistLifecycler);
        
        BoardUserService boardUserService = serviceLifecycler.getBoardUserService();
        
        if (boardUserService.loginAsUser(email, password)) {
            DCBoardUser boardUser = boardUserService.findUserWithEmail(email);
            session.setAttribute(LOGIN_USER, boardUser);
            return true;
            
        } else {
            
            if (session != null) {
                session.invalidate();
            }
            return false;
        }
    }
    
    public void logout() {
        //
        if (session != null) {
            session.invalidate();
        }
    }
}
