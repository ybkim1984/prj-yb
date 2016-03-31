/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mem;

import static org.junit.Assert.*;

import java.util.List;

import namoo.board.dom2.da.mem.BoardUserMemStore;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardUserMemStoreTest {
    //
    private BoardUserStore userStore = new BoardUserMemStore();
    
    /** 초기데이터로 insert 된 데이터셋 userEmail */
    private final String userEmail = "hkkang@nextree.co.kr";
    
    @Before
    public void setUp() {
        // 
        DCBoardUser user1 = new DCBoardUser("hkkang@nextree.co.kr", "강형구", "010-1234-5678");
        DCBoardUser user2 = new DCBoardUser("tsong@nextree.co.kr", "송태국", "010-1111-2222");
        
        userStore.create(user1);
        userStore.create(user2);
    }
    
    @After
    public void tearDown() {
        //
        List<DCBoardUser> allUsers = userStore.retrieveAll();
        
        for (DCBoardUser user : allUsers) {
            userStore.deleteByEmail(user.getEmail());
        }
    }
    
    
    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String email = "eschoi@nextree.co.kr";
        
        try {
            userStore.retrieveByEmail(email);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
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
