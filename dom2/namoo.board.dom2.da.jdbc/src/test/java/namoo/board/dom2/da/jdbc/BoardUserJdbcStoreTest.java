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

import namoo.board.dom2.da.jdbc.BoardUserJdbcStore;
import namoo.board.dom2.da.jdbc.shared.BaseJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Before;
import org.junit.Test;

public class BoardUserJdbcStoreTest extends BaseJdbcStoreDbUnitTest {
    //
    private BoardUserJdbcStore boardUserStore;
    
    private final String userEmail = "hkkang@nextree.co.kr";
    
    @Override
    protected String getDatasetXml() {
        // 
        return "/dataset/board_users.xml";
    }
    
    @Before
    public void setUp() throws Exception {
        //
        super.setUp();
        this.boardUserStore = new BoardUserJdbcStore(getDataSource());
    }
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String email = "hjyeom@nextree.co.kr";    

        try {
            boardUserStore.retrieveByEmail(email);
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
        DCBoardUser newUser = new DCBoardUser(email, "염효준", "010-0000-0000");
        boardUserStore.create(newUser);
        
        DCBoardUser created = boardUserStore.retrieveByEmail(email);
        
        assertNotNull(created);
        assertEquals(newUser.getName(), created.getName());
        assertEquals(newUser.getPhoneNumber(), created.getPhoneNumber());
    }

    @Test
    public void testRetrieveByEmail() throws EmptyResultException {
        //
        DCBoardUser user = boardUserStore.retrieveByEmail(this.userEmail);
        
        assertNotNull(user);
        assertEquals("강형구", user.getName());
        assertEquals("010-1234-5678", user.getPhoneNumber());
    }

    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardUser> allUsers = boardUserStore.retrieveAll();
        
        assertEquals(allUsers.size(), 2);
    }

    @Test
    public void testRetrieveByName() {
        //
        List<DCBoardUser> users = boardUserStore.retrieveByName("강형구");
        
        assertTrue(users.size() >= 1);
    }

    @Test
    public void testUpdate() throws EmptyResultException {
        //
        String newPhoneNumber = "010-9876-5432";
        
        DCBoardUser saved = boardUserStore.retrieveByEmail(this.userEmail);
        
        assertNotNull(saved);
        assertNotEquals(newPhoneNumber, saved.getPhoneNumber());

        saved.setPhoneNumber(newPhoneNumber);
        
        boardUserStore.update(saved);
        
        DCBoardUser updated = boardUserStore.retrieveByEmail(this.userEmail);
        
        assertEquals(newPhoneNumber, updated.getPhoneNumber());
    }

    @Test
    public void testDeleteByEmail() throws EmptyResultException {
        //
        DCBoardUser saved = boardUserStore.retrieveByEmail(this.userEmail);
        
        assertNotNull(saved);
        
        boardUserStore.deleteByEmail(this.userEmail);
        
        try {
            boardUserStore.retrieveByEmail(this.userEmail);
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
    }

    @Test
    public void testIsExistByEmail() {
        //
        assertTrue(boardUserStore.isExistByEmail(this.userEmail));
    }
    
    @Test
    public void testIsExistEmail() {
        //
        boolean isExistEmail =  boardUserStore.isExistByEmail("hkkang@nextree.co.kr");
        
        assertTrue(isExistEmail);
    }
}
