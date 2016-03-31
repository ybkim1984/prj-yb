package namoo.board.dom2.da.mongospring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import namoo.board.dom2.da.lifecycle.BoardStoreMongoSpringLifecycler;
import namoo.board.dom2.da.mongospring.document.SocialBoardDoc;
import namoo.board.dom2.da.mongospring.shared.BaseMongoSpringStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class SocialBoardMongoSpringStoreTest extends BaseMongoSpringStoreDbUnitTest{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private BoardStoreMongoSpringLifecycler storeLifecycler;
	
	private SocialBoardStore socialBoardStore;
	private Date createDate = new Date();
	
	@Before
	public void setUp() throws Exception {
		//
		mongoTemplate.dropCollection(SocialBoardDoc.CollectionName);
		socialBoardStore = createSocialBoardMongoSpringStore();
		
		DCSocialBoard freeBoard = new DCSocialBoard("free", "자유게시판", createDate , "플라워팀", false);
		socialBoardStore.create(freeBoard);

		DCSocialBoard inquiryBoard = new DCSocialBoard("inquiry", "문의사항", createDate , "플라워팀", false);
		socialBoardStore.create(inquiryBoard);

	}
	
	private SocialBoardStore createSocialBoardMongoSpringStore() {
		//
		return storeLifecycler.callSocialBoardStore();
	}
	
	@After
	public void tearDown() throws Exception {
		//
		//mongoTemplate.dropCollection(SocialBoardDoc.CollectionName);
	}
	@Test
	public void create() throws EmptyResultException {
		//
		
		DCSocialBoard notiBoard = new DCSocialBoard("noti", "공지사항", createDate , "플라워팀", false);
		socialBoardStore.create(notiBoard);
		
		DCSocialBoard retrievedBoard = socialBoardStore.retrieve("noti");
		assertThat("공지사항", is(retrievedBoard.getName()));
	}

	@Test
	public void retrieve() throws EmptyResultException {
		//
		String usid ="free";
				
		DCSocialBoard retrievedBoard = socialBoardStore.retrieve(usid);
		assertThat("자유게시판", is(retrievedBoard.getName()));
	}

	@Test
	public void retrieveByName() throws EmptyResultException {
		//
		String name ="자유게시판";
		
		DCSocialBoard retrievedBoard = socialBoardStore.retrieveByName(name);
		assertThat("자유게시판", is(retrievedBoard.getName()));
	}
	
	@Test
	public void retrieveAll() throws EmptyResultException {
		//
		List<DCSocialBoard> boards = socialBoardStore.retrieveAll();
		assertThat(2, is(boards.size()));
	}
	
	@Test
	public void update() throws EmptyResultException {
		//
		String usid ="free";
		
		DCSocialBoard retrievedBoard = socialBoardStore.retrieve(usid);
		retrievedBoard.setName("자유게시판2");
		socialBoardStore.update(retrievedBoard);
		
		DCSocialBoard retrievedBoard2 = socialBoardStore.retrieve(usid);
		assertThat("자유게시판2", is(retrievedBoard2.getName()));
	}
	
	@Test
	public void delete() throws EmptyResultException {
		//
		String usid ="free";
		socialBoardStore.delete(usid);
		
		DCSocialBoard retrievedBoard = socialBoardStore.retrieve(usid);
		assertThat(null, is(retrievedBoard));
	}
	
	@Test
	public void isExist() {
		//
		String usid ="free";
		boolean isExist = socialBoardStore.isExist(usid);
		assertThat(true, is(isExist));
	}
	
	@Test
	public void isExistByName() {
		//
		boolean isExistByName = socialBoardStore.isExistByName("자유게시판");
		assertThat(true, is(isExistByName));
	}
	
	/*


    @Override
    public boolean isExist(String usid) {
    	Long count = this.socialBoardRepository.countByUsid(usid);
    	return count > 0;
    }

    @Override
    public boolean isExistByName(String name) {
    	Long count = this.socialBoardRepository.countByName(name);
		return count > 0;
    }*/
}
