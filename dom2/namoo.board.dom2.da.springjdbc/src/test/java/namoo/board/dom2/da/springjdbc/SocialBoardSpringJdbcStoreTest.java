/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.da.springjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import namoo.board.dom2.da.springjdbc.shared.BaseSpringJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;

@DatabaseSetup(value = "/dataset/social_boards.xml", type = DatabaseOperation.CLEAN_INSERT)
public class SocialBoardSpringJdbcStoreTest extends BaseSpringJdbcStoreDbUnitTest {
    //
    @Autowired
    private SocialBoardStore socialBoardStore;
    
    //--------------------------------------------------------------------------
    
    @Test
    public void testRetreive() throws EmptyResultException {
        //
        DCSocialBoard socialBoard = socialBoardStore.retrieve("001");
        
        assertNotNull(socialBoard);
        assertEquals("영화게시판", socialBoard.getName());
        assertEquals(true, socialBoard.isCommentable());
        assertEquals("001", socialBoard.getTeamUsid());
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(socialBoard));
    }
    
    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCSocialBoard socialBoard = socialBoardStore.retrieveByName("영화게시판");
        
        assertNotNull(socialBoard);
        assertEquals("001", socialBoard.getUsid());
        assertEquals(true,    socialBoard.isCommentable());
        System.out.println(socialBoard.getTeamUsid());
        assertEquals("001", socialBoard.getTeamUsid());
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(socialBoard));
    }
    
    @Test
    public void testRetreiveAll() {
        //
        List<DCSocialBoard> allBoards = socialBoardStore.retrieveAll();
        
        assertEquals(2, allBoards.size());
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(allBoards));
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCSocialBoard socialBoard = socialBoardStore.retrieve("001");
        assertNotNull(socialBoard);
        
        socialBoardStore.delete("001");
        
        try {
            socialBoardStore.retrieve("001");
            fail("Didn't throw exception");
        } catch (Exception e) {}
    }
    
    @Test
    public void testNextSequence() {
        //
        int nextSequence = socialBoardStore.nextSequence();
        assertEquals(3, nextSequence);
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
