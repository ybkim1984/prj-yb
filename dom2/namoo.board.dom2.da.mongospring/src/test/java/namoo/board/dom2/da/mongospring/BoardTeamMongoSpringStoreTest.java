/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.mongospring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import namoo.board.dom2.da.lifecycle.BoardStoreMongoSpringLifecycler;
import namoo.board.dom2.da.mongospring.document.BoardTeamDoc;
import namoo.board.dom2.da.mongospring.shared.BaseMongoSpringStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BoardTeamMongoSpringStoreTest extends BaseMongoSpringStoreDbUnitTest {
	//
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private BoardStoreMongoSpringLifecycler storeLifecycler;

	private BoardTeamStore boardTeamStore;

	@Before
	public void setUp() throws Exception {
		//
		mongoTemplate.dropCollection(BoardTeamDoc.CollectionName);
		boardTeamStore = createBoardTeamMongoSpringStore();

		DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
		DCBoardTeam teamOne = new DCBoardTeam("교육팀", userPark);
		teamOne.setUsid("0001");
		this.boardTeamStore.create(teamOne);
	}

	private BoardTeamStore createBoardTeamMongoSpringStore() {
		//
		return storeLifecycler.callBoardTeamStore();
	}

	@After
	public void tearDown() throws Exception {
		//
//		mongoTemplate.dropCollection(BoardTeamDoc.CollectionName);
//		mongoTemplate.dropCollection(BoardSeqDoc.CollectionName);
	}

	@Test
	public void create() throws EmptyResultException {
		//
		DCBoardUser newUserPark = new DCBoardUser("jwpark@nextree.co.kr","박주원","010-8580-XXXX");
		DCBoardTeam teamOne = new DCBoardTeam("개발팀", newUserPark);
		teamOne.setUsid("0002");
		boardTeamStore.create(teamOne);

		DCBoardTeam retrievedTeam = this.boardTeamStore.retrieve("0002");
		assertThat("개발팀", is(retrievedTeam.getName()));
	}

    @Test
    public void retrieveByName() throws EmptyResultException {
        //
        DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        assertThat("교육팀", is(retrievedTeam.getName()));
    }


    @Test
    public void retrieveAll() {
        //
        List<DCBoardTeam> retrievedTeamList = this.boardTeamStore.retrieveAll();
        assertThat(1, is(retrievedTeamList.size()));
    }

    @Test (expected = EmptyResultException.class)
    public void delete() throws EmptyResultException {
        //
    	this.boardTeamStore.delete("0001");

    	DCBoardTeam retrievedBoardTeam = this.boardTeamStore.retrieve("0001");
    	assertThat(null, is(retrievedBoardTeam));
    }

    @Test
    public void createMember() throws EmptyResultException {
        //
    	DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userKim = new DCBoardUser("yjkim2@nextree.co.kr","김영진","010-7229-XXXX");
        DCBoardMember memberKim = new DCBoardMember(retrievedTeam, userKim);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberKim);

        DCBoardMember retrievedMember = this.boardTeamStore.retrieveMember("0001", "yjkim2@nextree.co.kr");
        assertThat("김영진", is(retrievedMember.getUser().getName()));
    }

    @Test
    public void retrieveMembers() throws EmptyResultException {
        //
        DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userKim = new DCBoardUser("yjkim2@nextree.co.kr","김영진","010-7229-XXXX");
        DCBoardMember memberKim = new DCBoardMember(retrievedTeam, userKim);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberKim);

		DCBoardUser userSong = new DCBoardUser("kssong@nextree.co.kr","송경선","010-7229-XXXX");
        DCBoardMember memberSong = new DCBoardMember(retrievedTeam, userSong);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberSong);

        List<DCBoardMember> members = this.boardTeamStore.retrieveMembers(retrievedTeam.getUsid());
        assertThat(2, is(members.size()));
    }

    @Test  (expected = EmptyResultException.class)
    public void deleteMember() throws EmptyResultException {
    	//
    	DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userKim = new DCBoardUser("yjkim2@nextree.co.kr","김영진","010-7229-XXXX");
        DCBoardMember memberKim = new DCBoardMember(retrievedTeam, userKim);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberKim);

		DCBoardUser userSong = new DCBoardUser("kssong@nextree.co.kr","송경선","010-7229-XXXX");
        DCBoardMember memberSong = new DCBoardMember(retrievedTeam, userSong);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberSong);

        this.boardTeamStore.deleteMember(retrievedTeam.getUsid(), "yjkim2@nextree.co.kr");
        
        DCBoardMember retrieveMember = this.boardTeamStore.retrieveMember(retrievedTeam.getUsid(), "yjkim2@nextree.co.kr");
        assertThat(null, is(retrieveMember));

    }
    
    @Test
    public void testNextSequence() {
        // 
        int nextSequence = this.boardTeamStore.nextSequence();
        assertEquals(2,nextSequence);
    }
    
    @Test
    public void testIsExist(){
    	//
    	boolean isExist = this.boardTeamStore.isExist("0001");
    	assertEquals(true, isExist);
    }
    
    @Test
    public void testIsExistByName(){
    	//
    	boolean isExist = this.boardTeamStore.isExistByName("교육팀");
    	assertEquals(true, isExist);
    }
    
    @Test
    public void testIsExistMember() throws EmptyResultException{
    	//
    	DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userKim = new DCBoardUser("yjkim2@nextree.co.kr","김영진","010-7229-XXXX");
        DCBoardMember memberKim = new DCBoardMember(retrievedTeam, userKim);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), memberKim);
        
    	boolean isExist = this.boardTeamStore.isExistMember("0001", "yjkim2@nextree.co.kr");
    	assertEquals(true, isExist);
    }
}
