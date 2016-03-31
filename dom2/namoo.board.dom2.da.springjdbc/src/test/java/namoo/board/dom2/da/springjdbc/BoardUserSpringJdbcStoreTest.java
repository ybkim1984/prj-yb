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
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "/dataset/board_users.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BoardUserSpringJdbcStoreTest extends BaseSpringJdbcStoreDbUnitTest {
    //
    @Autowired
    private BoardUserStore userStore;
    
    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String email = "eschoi@nextree.co.kr";
        
        try {
            userStore.retrieveByEmail(email);
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
        DCBoardUser newBoardUser = new DCBoardUser(email, "최은선", "010-0000-0000");
        userStore.create(newBoardUser);
        
        DCBoardUser created = userStore.retrieveByEmail(email);
        assertNotNull(created);
        assertEquals(newBoardUser.getName(), created.getName());
        assertEquals(newBoardUser.getPhoneNumber(), created.getPhoneNumber());
    }
    
    @Test 
    public void testRetrieveByEmail() throws EmptyResultException {
        //
        String email = "hkkang@nextree.co.kr";
        DCBoardUser boardUser = userStore.retrieveByEmail(email);
        
        assertNotNull(boardUser);
        assertEquals("강형구", boardUser.getName());
        assertEquals("010-1234-5678", boardUser.getPhoneNumber());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardUser> boardUsers = userStore.retrieveAll();
        assertEquals(2, boardUsers.size());
    }
    
    @Test
    public void testRetrieveByName() {
        //
        String name = "강형구";
        List<DCBoardUser> boardUsers = userStore.retrieveByName(name);
        
        assertTrue(boardUsers.size() > 0);
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        String email = "hkkang@nextree.co.kr";
        DCBoardUser boardUser = userStore.retrieveByEmail(email);
        
        assertNotNull(boardUser);
        assertEquals("010-1234-5678", boardUser.getPhoneNumber());
        
        boardUser.setPhoneNumber("010-5678-1234");
        userStore.update(boardUser);
        
        DCBoardUser changedBoardUser = userStore.retrieveByEmail(email);
        assertNotNull(changedBoardUser);
        assertEquals("강형구", changedBoardUser.getName());
        assertEquals("010-5678-1234", changedBoardUser.getPhoneNumber());
    }
    
    @Test
    public void testDeleteByEmail() throws EmptyResultException {
        //
        String userEmail = "hkkang@nextree.co.kr";
        
        DCBoardUser boardUser = userStore.retrieveByEmail(userEmail);
        assertNotNull(boardUser);
        
        userStore.deleteByEmail(userEmail);
        
        try {
            userStore.retrieveByEmail(userEmail);
            fail("Didn't throw exception");
        } catch (Exception e) {}
    }
    
    @Test
    public void testIsExistEmail() {
        //
        boolean isExistEmail =  userStore.isExistByEmail("hkkang@nextree.co.kr");
        
        assertTrue(isExistEmail);
    }
}
