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
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "/dataset/board_users.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BoardUserMyBatisStoreTest extends BaseMyBatisStoreDbUnitTest{
    //
    @Autowired
    private BoardUserStore userStore;
    
    /** 초기데이터로 insert 된 데이터셋 userEmail */
    private final String userEmail = "hkkang@nextree.co.kr";

    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String email = "eschoi@nextree.co.kr";
   
        DCBoardUser newUser = new DCBoardUser(email, "최은선", "010-0000-0000");
        userStore.create(newUser);
        
        DCBoardUser created = userStore.retrieveByEmail(email);
        assertNotNull(created);
        assertEquals(newUser.getName(), created.getName());
        assertEquals(newUser.getPhoneNumber(), created.getPhoneNumber());
    }
    
    @Test
    public void testRetrieveByEmail() throws EmptyResultException {
        //
        DCBoardUser user = userStore.retrieveByEmail(this.userEmail);
        
        assertNotNull(user);
        assertEquals("강형구", user.getName());
        assertEquals("010-1234-5678", user.getPhoneNumber());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardUser> allUsers = userStore.retrieveAll();
        assertEquals(allUsers.size(), 2);
    }
    
    @Test
    public void testRetrieveByName() {
        //
        List<DCBoardUser> users = userStore.retrieveByName("강형구");
        
        assertTrue(users.size() >= 1);
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        String newPhoneNumber = "010-9876-5432";
        
        DCBoardUser saved = userStore.retrieveByEmail(this.userEmail);
        assertNotNull(saved);
        assertNotEquals(newPhoneNumber, saved.getPhoneNumber());

        saved.setPhoneNumber(newPhoneNumber);
        
        userStore.update(saved);
        
        DCBoardUser updated = userStore.retrieveByEmail(this.userEmail);
        assertEquals(newPhoneNumber, updated.getPhoneNumber());
    }
    
    @Test
    public void testDeleteByEmail() throws EmptyResultException {
        //
    	DCBoardUser saved = userStore.retrieveByEmail(this.userEmail);
        assertNotNull(saved);
        
        userStore.deleteByEmail(this.userEmail);
        
        try {
            userStore.retrieveByEmail(this.userEmail);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }
    
    @Test
    public void testIsExistByEmail() {
        //
        assertTrue(userStore.isExistByEmail(this.userEmail));
    }
}
