/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hjyeom@nextree.co.kr">Yeom, Hyojun</a>
 * @since 2015. 1. 27.
 */

package namoo.board.dom2.da.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import namoo.board.dom2.da.jdbc.BoardTeamJdbcStore;
import namoo.board.dom2.da.jdbc.SocialBoardJdbcStore;
import namoo.board.dom2.da.jdbc.shared.BaseJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Before;
import org.junit.Test;

public class SocialBoardJdbcStoreTest extends BaseJdbcStoreDbUnitTest {
    //
    private BoardTeamJdbcStore boardTeamStore;
    private SocialBoardJdbcStore socialBoardStore;
    
    private final String boardUsid = "001";
    
    @Override
    protected String getDatasetXml() {
        //
        return "/dataset/social_boards.xml";
    }
    
    @Before
    public void setUp() throws Exception {
        //
        super.setUp();
        this.boardTeamStore = new BoardTeamJdbcStore(getDataSource());
        this.socialBoardStore = new SocialBoardJdbcStore(getDataSource());
    }
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardTeam team = boardTeamStore.retrieveByName("컨설팅팀");
        DCSocialBoard board = new DCSocialBoard(team, "스터디게시판");
        
        board.setUsid("003");
        
        String createdId = socialBoardStore.create(board);
        
        DCSocialBoard created = socialBoardStore.retrieve(createdId);
        
        assertNotNull(created);
        assertEquals(board.getName(), created.getName());
        assertEquals(true, board.isCommentable());
        assertEquals(board.getTeamUsid(), created.getTeamUsid());
    }

    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCSocialBoard board = socialBoardStore.retrieve(this.boardUsid);
        
        assertNotNull(board);
        assertEquals("영화게시판", board.getName());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }

    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCSocialBoard board = socialBoardStore.retrieveByName("영화게시판");
        
        assertNotNull(board);
        assertEquals(this.boardUsid, board.getUsid());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }

    @Test
    public void testRetrieveAll() {
        //
        List<DCSocialBoard> allBoards = socialBoardStore.retrieveAll();
        
        assertEquals(allBoards.size(), 2);
    }

    @Test
    public void testUpdate() throws EmptyResultException {
        //
        DCSocialBoard board = socialBoardStore.retrieve(this.boardUsid);
        board.setName("팁게시판");
        board.setCommentable(false);
        
        socialBoardStore.update(board);
        
        DCSocialBoard updated = socialBoardStore.retrieve(this.boardUsid);
        
        assertEquals(board.getName(), updated.getName());
        assertEquals(board.isCommentable(), updated.isCommentable());
    }

    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCSocialBoard board = socialBoardStore.retrieve(this.boardUsid);
        
        assertNotNull(board);
        
        socialBoardStore.delete(this.boardUsid);
        
        try {
            socialBoardStore.retrieve(this.boardUsid);
            fail("Didn't throw exception");
        } catch (Exception e) {}
    }

    @Test
    public void testNextSequence() {
        //
        int nextSequence = socialBoardStore.nextSequence();
        
        assertEquals(nextSequence, 3);
    }
    
    @Test
    public void testIsExist() {
    	//
    	boolean isExist = socialBoardStore.isExist("001");
    	
    	assertTrue(isExist);
    }
    
    @Test
    public void testIsExistByName() {
    	//
    	boolean isExist = socialBoardStore.isExistByName("영화게시판");
    	
    	assertTrue(isExist);
    }
}
