/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.sp.ws.server.Server;
import namoo.board.dom2.util.namevalue.NameValueList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SocialBoardWsPresenterTest {
    //
    private SocialBoardWsPresenter socialBoardWsPresenter;
    private BoardUserWsPresenter boardUserWsPresenter;
    
    //--------------------------------------------------------------------------
    
    @BeforeClass
    public static void setUpBefore() {
        //
        new Server();
    }
    
    @Before
    public void setUp() throws Exception {
        //
        socialBoardWsPresenter = new SocialBoardWsPresenter();
        boardUserWsPresenter = new BoardUserWsPresenter();
        
        String email = "test1@nextree.co.kr";
        String name = "테스트1";
        String phoneNumber = "010-9999-9999";
        DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserWsPresenter.registerUser(boardUser);
        
        email = "test2@nextree.co.kr";
        name = "테스트2";
        phoneNumber = "010-8888-8888";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserWsPresenter.registerUser(boardUser);
        
        email = "test3@nextree.co.kr";
        name = "테스트3";
        phoneNumber = "010-7777-7777";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserWsPresenter.registerUser(boardUser);
        
        email = "test4@nextree.co.kr";
        name = "테스트4";
        phoneNumber = "010-6666-6666";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserWsPresenter.registerUser(boardUser);
        
        String teamName = "Board team 1";
        String adminEmail = "test1@nextree.co.kr";
        boardUserWsPresenter.registerBoardTeam(teamName, adminEmail);
        
        teamName = "Board team 2";
        adminEmail = "test2@nextree.co.kr";
        boardUserWsPresenter.registerBoardTeam(teamName, adminEmail);
        
        String teamUsid = "001";
        String boardName = "게시판1";
        boolean commentable = true;
        socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "001";
        boardName = "게시판2";
        commentable = true;
        socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "002";
        boardName = "게시판3";
        commentable = true;
        socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "002";
        boardName = "게시판4";
        commentable = true;
        socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
    }
    
    @Test
    public void testRegisterSocialBoard() {
        //
        String teamUsid = "001";
        String boardName = "자유게시판";
        boolean commentable = true;
        
        String boardUsid = socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
        
        assertNotNull(boardUsid);
        assertEquals("005", boardUsid);
    }
    
    @Test
    public void testFindSocialBoard() {
        //
        String boardUsid = "001";
        DCSocialBoard socialBoard = socialBoardWsPresenter.findSocialBoard(boardUsid);
        
        assertNotNull(socialBoard);
        assertEquals("001", socialBoard.getUsid());
        assertEquals("게시판1", socialBoard.getName());
    }
    
    @Test
    public void testFindAllSocialBoards() {
        //
        List<DCSocialBoard> allSocialBoards = socialBoardWsPresenter.findAllSocialBoards();
        
        assertNotNull(allSocialBoards);
        assertEquals(4, allSocialBoards.size());
    }
    
    @Test
    public void testModifySocialBoard() {
        //
        String boardUsid = "001";
        
        // 
        DCSocialBoard socialBoard = socialBoardWsPresenter.findSocialBoard(boardUsid);
        assertNotNull(socialBoard);
        assertEquals("001", socialBoard.getUsid());
        assertEquals("게시판1", socialBoard.getName());
        
        //
        NameValueList nameValues = NameValueList.getInstance();
        nameValues.add("name", "알림판");
        nameValues.add("commentable", false);
        socialBoardWsPresenter.modifySocialBoard(boardUsid, nameValues);
        
        DCSocialBoard modifiedSocialBoard = socialBoardWsPresenter.findSocialBoard(boardUsid);
        
        assertNotNull(socialBoard);
        assertEquals("알림판", modifiedSocialBoard.getName());
        assertEquals(false, modifiedSocialBoard.isCommentable());
    }
    
    @Test
    public void testRemoveSocialBoard() {
        //
        String boardUsid = "001";
        socialBoardWsPresenter.removeSocialBoard(boardUsid);
        
        DCSocialBoard socialBoard = socialBoardWsPresenter.findSocialBoard(boardUsid);
        
        assertNull(socialBoard);
    }
    
}
