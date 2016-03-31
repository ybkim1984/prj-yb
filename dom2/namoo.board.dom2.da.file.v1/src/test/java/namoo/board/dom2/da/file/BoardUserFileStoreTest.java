/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.da.file.BoardUserFileStore;
import namoo.board.dom2.da.file.shared.BaseFileStoreTest;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;

public class BoardUserFileStoreTest extends BaseFileStoreTest {
    //
    private BoardUserStore userStore = new BoardUserFileStore();
    
    /** 초기데이터로 insert 된 데이터셋 userEmail */
    private final String userEmail = "hkkang@nextree.co.kr";
    
    
    @Override
    public void setUp() {
        // 
        List<DCBoardUser> users = new ArrayList<DCBoardUser>();
        users.add(new DCBoardUser("hkkang@nextree.co.kr", "강형구", "010-1234-5678"));
        users.add(new DCBoardUser("tsong@nextree.co.kr", "송태국", "010-1111-2222"));
        
        for (DCBoardUser user : users) {
            userStore.create(user);
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
