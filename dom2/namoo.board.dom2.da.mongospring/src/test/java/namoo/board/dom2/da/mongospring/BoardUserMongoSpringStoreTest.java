/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.mongospring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import namoo.board.dom2.da.lifecycle.BoardStoreMongoSpringLifecycler;
import namoo.board.dom2.da.mongospring.document.BoardUserDoc;
import namoo.board.dom2.da.mongospring.shared.BaseMongoSpringStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BoardUserMongoSpringStoreTest extends BaseMongoSpringStoreDbUnitTest {
	//
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private BoardStoreMongoSpringLifecycler storeLifecycler;
	
	private DCBoardUser userPark;
	private DCBoardUser userChoi;
	
	private BoardUserStore boardUserStore;
	
	@Before
	public void setUp() throws Exception {
		//
		mongoTemplate.dropCollection(BoardUserDoc.CollectionName);
		boardUserStore = createBoardUserMongoSpringStore();
		
		userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
		boardUserStore.create(userPark);
		
		userChoi = new DCBoardUser("ihchoi@nextree.co.kr","최인혜","010-3035-XXXX");
		boardUserStore.create(userChoi);
	}
	
	private BoardUserStore createBoardUserMongoSpringStore() {
		//
		return storeLifecycler.callBoardUserStore();
	}
	
	@After
	public void tearDown() throws Exception {
		//
		//mongoTemplate.dropCollection(BoardUserDoc.CollectionName);
	}
	
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
		
		DCBoardUser retrievedBoardUser = boardUserStore.retrieveByEmail("ihchoi@nextree.co.kr");
		assertThat(null, is(retrievedBoardUser));
	}
	
	@Test
	public void isExistByEmail() {
		//
		boolean isExistByEmail = boardUserStore.isExistByEmail("ihchoi@nextree.co.kr");
		assertThat(true, is(isExistByEmail));
	}
}
