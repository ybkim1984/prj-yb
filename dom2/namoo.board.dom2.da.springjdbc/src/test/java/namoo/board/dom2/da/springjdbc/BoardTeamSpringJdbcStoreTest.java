/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.da.springjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import namoo.board.dom2.da.springjdbc.shared.BaseSpringJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;

@DatabaseSetup(value = "/dataset/board_teams.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BoardTeamSpringJdbcStoreTest extends BaseSpringJdbcStoreDbUnitTest {
    //
    @Autowired
    private BoardTeamStore teamStore;
    @Autowired
    private BoardUserStore userStore;
    
    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String adminEmail = "syhan@nextree.co.kr";
        
        DCBoardUser admin = userStore.retrieveByEmail(adminEmail);
        DCBoardTeam team = new DCBoardTeam("개발팀", admin);
        
        team.setUsid("002");
        
        teamStore.create(team);
        
        DCBoardTeam createdTeam = teamStore.retrieveByName("개발팀");
        
        assertNotNull(createdTeam);
        assertEquals("002", createdTeam.getUsid());
        assertEquals(team.getAdmin().getEmail(), createdTeam.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCBoardTeam boardTeam = teamStore.retrieve("001");
        
        assertNotNull(boardTeam);
        assertEquals("컨설팅팀", boardTeam.getName());
        assertEquals("tsong@nextree.co.kr", boardTeam.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCBoardTeam boardTeam = teamStore.retrieveByName("컨설팅팀");
        
        assertNotNull(boardTeam);
        assertEquals("컨설팅팀", boardTeam.getName());
        assertEquals("tsong@nextree.co.kr", boardTeam.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardTeam> boardTeams = teamStore.retrieveAll();
        
        assertTrue(boardTeams.size() > 0);
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCBoardTeam existTeam = teamStore.retrieve("001");
        Gson gson = new Gson();
        System.out.println(gson.toJson(existTeam));
        assertNotNull(existTeam);
        
        teamStore.delete(existTeam.getUsid());
        
        try {
            teamStore.retrieve(existTeam.getUsid());
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
    }
    
    @Test
    public void testNextSequence() {
        //
        int nextSequence = teamStore.nextSequence();

        assertEquals(2, nextSequence);
    }
    
    @Test
    public void testCreateMember() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieve("001");
        DCBoardUser user = userStore.retrieveByEmail("eschoi@nextree.co.kr");
        
        DCBoardMember member = new DCBoardMember(team, user);
        teamStore.createMember(team.getUsid(), member);
        
        DCBoardMember created = teamStore.retrieveMember(team.getUsid(), user.getEmail());
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(created));
        
        assertNotNull(created);
        assertEquals(member.getUsid(), created.getUsid());
        assertEquals(team.getName(), created.getTeamName());
    }
    
    @Test 
    public void testRetrieveMember() throws EmptyResultException {
        //
        DCBoardMember member = teamStore.retrieveMember("001", "hkkang@nextree.co.kr");
        
        assertNotNull(member);
        assertEquals("001-hkkang@nextree.co.kr", member.getUsid());
        assertEquals("컨설팅팀", member.getTeamName());
        assertEquals("hkkang@nextree.co.kr", member.getUser().getEmail());
    }
    
    @Test
    public void testRetrieveMembers() {
        //
        List<DCBoardMember> members = teamStore.retrieveMembers("001");
        assertTrue(members.size() == 2);
    }
    
    @Test
    public void testDeleteMember() {
        //
        teamStore.deleteMember("001", "hkkang@nextree.co.kr");
        
        try {
            teamStore.retrieveMember("001", "hkkang@nextree.co.kr");
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
        List<DCBoardMember> members = teamStore.retrieveMembers("001");
        assertTrue(members.size() == 1);
    }
    
    @Test
    public void testIsExist() {
    	//
    	boolean isExist = teamStore.isExist("001");
    	
    	assertTrue(isExist);
    }
    
    @Test
    public void testIsExistByName() {
    	//
    	boolean isExist = teamStore.isExistByName("컨설팅팀");
    	
    	assertTrue(isExist);
    }
    
    @Test
    public void testIsExistMember() {
    	//
    	boolean isExist = teamStore.isExistMember("001", "hkkang@nextree.co.kr");
    	
    	assertTrue(isExist);
    }
}
