/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 2. 2.
 */
package namoo.board.dom2.da.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import namoo.board.dom2.da.hibernate.BoardUserHibernateStore;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

import org.dbunit.dataset.DataSetException;
import org.junit.Test;

public class BoardUserHibernateStoreTest extends BoardInitHibernateStoreTest {
    //
    private BoardUserHibernateStore userStore = new BoardUserHibernateStore();
    
    @Override
    protected String getDatasetXml() {
        //
        return "./dataset/board_users.xml";
    }    
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String email = "demonpark@nextree.co.kr";
        DCBoardUser insertUser = new DCBoardUser(email,"박석재","010-0000-0000");
        
        assertEquals(2,userStore.retrieveAll().size());
        
        userStore.create(insertUser);
        
        assertEquals(3,userStore.retrieveAll().size());
        DCBoardUser user = userStore.retrieveByEmail(email);
        assertEquals("박석재",user.getName());
    }

    @Test
    public void testRetrieveByEmail() throws EmptyResultException {
        //
        DCBoardUser user = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        
        assertNotNull(user);
        assertEquals("강형구", user.getName());
        assertEquals("010-1234-5678", user.getPhoneNumber());
    }

    @Test
    public void testRetrieveAll() throws DataSetException {
        //
        List<DCBoardUser> users = userStore.retrieveAll();
        
        assertEquals(2,users.size());
    }

    @Test
    public void testRetrieveByName() {
        //
        List<DCBoardUser> users = userStore.retrieveByName("강형구");
        assertEquals("hkkang@nextree.co.kr",users.get(0).getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        DCBoardUser user = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        assertEquals("강형구",user.getName());
        
        user.setName("수정 강형구");
        userStore.update(user);
        
        DCBoardUser updateUser = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        assertEquals("수정 강형구",updateUser.getName());        
    }

    @Test
    public void testDeleteByEmail() throws Exception {
        DCBoardUser user = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        assertNotNull(user);
        
        userStore.deleteByEmail(user.getEmail());
        
        DCBoardUser deleteUser = null;
        
        try {        
            deleteUser = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        }catch(NamooBoardException e) {
            assertTrue(true);
        }
        assertNull(deleteUser);
    }

    @Test
    public void testIsExistByEmail() {
        assertTrue(userStore.isExistByEmail("hkkang@nextree.co.kr"));
        assertFalse(!userStore.isExistByEmail("demonpark@nextree.co.kr"));
    }

}
