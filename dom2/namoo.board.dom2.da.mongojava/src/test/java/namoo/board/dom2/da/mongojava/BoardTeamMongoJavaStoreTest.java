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

import namoo.board.dom2.da.mongojava.BoardTeamMongoJavaStore;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.da.mongojava.shared.BaseMongoTestCase;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTeamMongoJavaStoreTest extends BaseMongoTestCase {
    //
    private DCBoardTeam teamOne;
    
    private BoardTeamStore boardTeamStore;
    
    @Before
    public void setUp() throws Exception {
        //
    	MongoClientFactory.getDB().getCollection("boardteam").drop();
        this.boardTeamStore = new BoardTeamMongoJavaStore();
       
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        this.teamOne = new DCBoardTeam("교육팀", userPark);
        teamOne.setUsid("0001");
        this.boardTeamStore.create(teamOne);
    }
    
    @After
    public void tearDown() throws Exception {
        //
        //MongoClientFactory.getDB().getCollection("boardusers").drop();
    }
    
    //--------------------------------------------------------------------------

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
    
    @Test
    public void delete() throws EmptyResultException {
        //
        boardTeamStore.delete("0001");
   
    }
    
    @Test
    public void createMember() throws EmptyResultException {
        //
        DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userChoi = new DCBoardUser("ihchoi@nextree.co.kr","최인혜","010-3035-XXXX");
        DCBoardMember member = new DCBoardMember(retrievedTeam, userChoi);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), member);
        
        DCBoardMember retrievedMember = this.boardTeamStore.retrieveMember("0001", "ihchoi@nextree.co.kr");
        assertThat("최인혜", is(retrievedMember.getUser().getName()));
    }
    
    @Test
    public void retrieveMembers() throws EmptyResultException {
        //
        DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userChoi = new DCBoardUser("ihchoi@nextree.co.kr","최인혜","010-3035-XXXX");
        DCBoardMember member = new DCBoardMember(retrievedTeam, userChoi);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), member);
        
        List<DCBoardMember> members = this.boardTeamStore.retrieveMembers(retrievedTeam.getUsid());
        assertThat(1, is(members.size()));
    }
    
    @Test
    public void deleteMember() throws EmptyResultException {
        //
        DCBoardTeam retrievedTeam = this.boardTeamStore.retrieveByName("교육팀");
        DCBoardUser userChoi = new DCBoardUser("ihchoi@nextree.co.kr","최인혜","010-3035-XXXX");
        DCBoardMember member = new DCBoardMember(retrievedTeam, userChoi);
        this.boardTeamStore.createMember(retrievedTeam.getUsid(), member);
        
        this.boardTeamStore.deleteMember(retrievedTeam.getUsid(), "ihchoi@nextree.co.kr");
    
    }
    @Test
    public void testNextSequence() {
        //
        boardTeamStore.nextSequence();
        
    }
}
