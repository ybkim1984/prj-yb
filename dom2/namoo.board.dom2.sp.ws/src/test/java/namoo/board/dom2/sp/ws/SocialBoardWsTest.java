package namoo.board.dom2.sp.ws;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.sp.ws.logic.BoardUserWsLogic;
import namoo.board.dom2.sp.ws.logic.SocialBoardWsLogic;
import namoo.board.dom2.sp.ws.server.Server;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SocialBoardWsTest {
	//
	private SocialBoardWs socialBoardWs;
    private BoardUserWs boardUserWs;
    
    //--------------------------------------------------------------------------
    
    @BeforeClass
    public static void setUpBefore() {
        //
        new Server();
    }
    
    @Before
    public void setUp() throws Exception {
        //
        socialBoardWs = new SocialBoardWsLogic();
        boardUserWs = new BoardUserWsLogic();
        
        String email = "test1@nextree.co.kr";
        String name = "테스트1";
        String phoneNumber = "010-9999-9999";
        DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
        String boardUserJson = JsonUtil.toJson(boardUser);
        boardUserWs.registerUser(boardUserJson);
        
        email = "test2@nextree.co.kr";
        name = "테스트2";
        phoneNumber = "010-8888-8888";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserJson = JsonUtil.toJson(boardUser);
        boardUserWs.registerUser(boardUserJson);
        
        email = "test3@nextree.co.kr";
        name = "테스트3";
        phoneNumber = "010-7777-7777";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserJson = JsonUtil.toJson(boardUser);
        boardUserWs.registerUser(boardUserJson);
        
        email = "test4@nextree.co.kr";
        name = "테스트4";
        phoneNumber = "010-6666-6666";
        boardUser = new DCBoardUser(email, name, phoneNumber);
        boardUserJson = JsonUtil.toJson(boardUser);
        boardUserWs.registerUser(boardUserJson);
        
        String teamName = "Board team 1";
        String adminEmail = "test1@nextree.co.kr";
        boardUserWs.registerBoardTeam(teamName, adminEmail);
        
        teamName = "Board team 2";
        adminEmail = "test2@nextree.co.kr";
        boardUserWs.registerBoardTeam(teamName, adminEmail);
        
        String teamUsid = "001";
        String boardName = "게시판1";
        boolean commentable = true;
        socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "001";
        boardName = "게시판2";
        commentable = true;
        socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "002";
        boardName = "게시판3";
        commentable = true;
        socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
        
        teamUsid = "002";
        boardName = "게시판4";
        commentable = true;
        socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
    }
    
    @Test
    public void testRegisterSocialBoard() {
        //
        String teamUsid = "001";
        String boardName = "자유게시판";
        boolean commentable = true;
        
        String boardUsid = socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
        
        assertNotNull(boardUsid);
        assertEquals("005", boardUsid);
    }
    
    @Test
    public void testFindSocialBoard() {
        //
        String boardUsid = "001";
        String socialBoardJson = socialBoardWs.findSocialBoard(boardUsid);
        DCSocialBoard socialBoard = (DCSocialBoard)JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class);
        
        assertNotNull(socialBoard);
        assertEquals("001", socialBoard.getUsid());
        assertEquals("게시판1", socialBoard.getName());
    }
    
    @Test
    public void testFindAllSocialBoards() {
        //
        List<String> allSocialBoardsJson = socialBoardWs.findAllSocialBoards();
        
        List<DCSocialBoard> allSocialBoards = new ArrayList<DCSocialBoard>();
        for(String socialBoardJson : allSocialBoardsJson) {
        	allSocialBoards.add((DCSocialBoard)JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class));
        }
        
        assertNotNull(allSocialBoards);
        assertEquals(4, allSocialBoards.size());
    }
    
    @Test
    public void testModifySocialBoard() {
        //
        String boardUsid = "001";
        
        // 
        String socialBoardJson = socialBoardWs.findSocialBoard(boardUsid);
        DCSocialBoard socialBoard = (DCSocialBoard)JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class);
        assertNotNull(socialBoard);
        assertEquals("001", socialBoard.getUsid());
        assertEquals("게시판1", socialBoard.getName());
        
        //
        NameValueList nameValues = NameValueList.getInstance();
        nameValues.add("name", "알림판");
        nameValues.add("commentable", false);
        String nameValuesJson = JsonUtil.toJson(nameValues);
        socialBoardWs.modifySocialBoard(boardUsid, nameValuesJson);
        
        String modifiedSocialBoardJson = socialBoardWs.findSocialBoard(boardUsid);
        DCSocialBoard modifiedSocialBoard = (DCSocialBoard)JsonUtil.fromJson(modifiedSocialBoardJson, DCSocialBoard.class);
        
        assertNotNull(socialBoard);
        assertEquals("알림판", modifiedSocialBoard.getName());
        assertEquals(false, modifiedSocialBoard.isCommentable());
    }
    
    @Test
    public void testRemoveSocialBoard() {
        //
        String boardUsid = "001";
        socialBoardWs.removeSocialBoard(boardUsid);
        
        String socialBoardJson = socialBoardWs.findSocialBoard(boardUsid);
        DCSocialBoard socialBoard = (DCSocialBoard)JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class);
        
        assertNull(socialBoard);
    }
}