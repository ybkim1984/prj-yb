/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import namoo.board.dom2.da.mongojava.BoardUserMongoJavaStore;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.da.mongojava.shared.BaseMongoTestCase;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardUserMongoJavaStoreTest extends BaseMongoTestCase {
    //
    private DCBoardUser userPark;
    private DCBoardUser userChoi;
    
    private BoardUserStore boardUserStore;
    
    @Before
    public void setUp() throws Exception {
        //
        MongoClientFactory.getDB().getCollection("boardusers").drop();
        this.boardUserStore = new BoardUserMongoJavaStore();
        
        userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        boardUserStore.create(userPark);
        
        userChoi = new DCBoardUser("ihchoi@nextree.co.kr","최인혜","010-3035-XXXX");
        boardUserStore.create(userChoi);
    }
    
    @After
    public void tearDown() throws Exception {
        //
        MongoClientFactory.getDB().getCollection("boardusers").drop();
    }
    
    //--------------------------------------------------------------------------

    @Test
    public void create() throws EmptyResultException {
        //
        DCBoardUser newUserPark = new DCBoardUser("jwpark@nextree.co.kr","박주원","010-8580-XXXX");
        boardUserStore.create(newUserPark);
        
        DCBoardUser retrievedBoardUser = boardUserStore.retrieveByEmail("jwpark@nextree.co.kr");
        assertThat("박주원", is(retrievedBoardUser.getName()));
    }
    
    @Test
    public void retrieveAll() {
        //
        List<DCBoardUser> users = boardUserStore.retrieveAll();
        assertThat(2, is(users.size()));
    }
    
    @Test
    public void retrieveByName() {
        //
        List<DCBoardUser> users = boardUserStore.retrieveByName("최인혜");
        assertThat("최인혜", is(users.get(0).getName()));
    }
    
    @Test
    public void update() throws EmptyResultException {
        //
        DCBoardUser retrievedBoardUser = boardUserStore.retrieveByEmail("ihchoi@nextree.co.kr");
        retrievedBoardUser.setName("최인혜2");
        boardUserStore.update(retrievedBoardUser);
        
        DCBoardUser retrievedBoardUser2 = boardUserStore.retrieveByEmail("ihchoi@nextree.co.kr");
        assertThat("최인혜2", is(retrievedBoardUser2.getName()));
    }
    
    @Test
    public void deleteByEmail() throws EmptyResultException {
        //
        boardUserStore.deleteByEmail("ihchoi@nextree.co.kr");
 
    }
    
    @Test
    public void isExistByEmail() {
        //
        boolean isExistByEmail = boardUserStore.isExistByEmail("ihchoi@nextree.co.kr");
        
        assertThat(true, is(isExistByEmail));
    }
}
