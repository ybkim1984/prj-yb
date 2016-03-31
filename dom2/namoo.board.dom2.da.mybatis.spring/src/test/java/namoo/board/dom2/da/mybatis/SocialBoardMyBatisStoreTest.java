/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis;

import static org.junit.Assert.*;

import java.util.List;

import namoo.board.dom2.da.mybatis.shared.BaseMyBatisStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "/dataset/social_boards.xml", type = DatabaseOperation.CLEAN_INSERT)
public class SocialBoardMyBatisStoreTest extends BaseMyBatisStoreDbUnitTest {
    //
    @Autowired
    private SocialBoardStore boardStore;
    @Autowired
    private BoardTeamStore teamStore;
    
    private final String boardUsid = "001";

    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieveByName("컨설팅팀");
        DCSocialBoard board = new DCSocialBoard(team, "스터디게시판");
        
        board.setUsid("003");
        
        String createdId = boardStore.create(board);
        
        DCSocialBoard created = boardStore.retrieve(createdId);
        assertNotNull(created);
        assertEquals(board.getName(), created.getName());
        assertEquals(true, board.isCommentable());
        assertEquals(board.getTeamUsid(), created.getTeamUsid());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        assertNotNull(board);
        assertEquals("영화게시판", board.getName());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }
    
    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieveByName("영화게시판");
        assertNotNull(board);
        assertEquals(this.boardUsid, board.getUsid());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCSocialBoard> allBoards = boardStore.retrieveAll();
        
        assertEquals(allBoards.size(), 2);
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        board.setName("팁게시판");
        board.setCommentable(false);
        
        boardStore.update(board);
        
        
        DCSocialBoard updated = boardStore.retrieve(this.boardUsid);
        assertEquals(board.getName(), updated.getName());
        assertEquals(board.isCommentable(), updated.isCommentable());
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
    	DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        assertNotNull(board);
        
        boardStore.delete(this.boardUsid);
        
        try {
        	boardStore.retrieve(this.boardUsid);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }
    
    @Test
    public void testNextSequence() {
        //
        int nextSequence = boardStore.nextSequence();
        assertEquals(nextSequence, 3);
    }
    
}
