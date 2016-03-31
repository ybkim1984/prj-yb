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

import namoo.board.dom2.da.jdbc.shared.BaseJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Before;
import org.junit.Test;

public class BoardTeamJdbcStoreTest extends BaseJdbcStoreDbUnitTest {
    //
    private BoardUserJdbcStore boardUserStore;
    private BoardTeamJdbcStore boardTeamStore;
    
    private final String teamUsid = "001";

    @Override
    protected String getDatasetXml() {
        //
        return "/dataset/board_teams.xml";
    }
    
    @Before
    public void setUp() throws Exception {
        //
        super.setUp();
        this.boardUserStore = new BoardUserJdbcStore(getDataSource());
        this.boardTeamStore = new BoardTeamJdbcStore(getDataSource());
    }

    @Test
    public void testCreate() throws EmptyResultException {
        //
        String adminEmail = "tsong@nextree.co.kr";
        
        DCBoardUser user = boardUserStore.retrieveByEmail(adminEmail);
        
        DCBoardTeam team = new DCBoardTeam("개발팀", user); 
        team.setUsid("002");
        boardTeamStore.create(team);
        
        DCBoardTeam created = boardTeamStore.retrieveByName("개발팀");
        
        assertNotNull(created);
        assertEquals("002", created.getUsid());
        assertEquals(team.getAdmin().getEmail(), created.getAdmin().getEmail());
    }

    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCBoardTeam created = boardTeamStore.retrieve(this.teamUsid);
        
        assertNotNull(created);
        assertEquals("컨설팅팀", created.getName());
        assertEquals("tsong@nextree.co.kr", created.getAdmin().getEmail());
    }

    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCBoardTeam saved = boardTeamStore.retrieveByName("컨설팅팀");
        
        assertNotNull(saved);
        assertEquals(this.teamUsid, saved.getUsid());
        assertEquals("tsong@nextree.co.kr", saved.getAdmin().getEmail());
    }

    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardTeam> allTeams = boardTeamStore.retrieveAll();
        
        assertTrue(allTeams.size() >= 1);
    }

    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCBoardTeam saved = boardTeamStore.retrieve(this.teamUsid);
        
        assertNotNull(saved);
        
        List<DCBoardMember> teamMembers = boardTeamStore.retrieveMembers(this.teamUsid);
        
        assertFalse(teamMembers.isEmpty());
        
        
        boardTeamStore.delete(this.teamUsid);
        
        try {
            boardTeamStore.retrieve(this.teamUsid);
            fail("Didn't throw exception");
        } catch (Exception e) {}
        
        List<DCBoardMember> deletedMembers = boardTeamStore.retrieveMembers(this.teamUsid);
        
        assertTrue(deletedMembers.isEmpty());
    }

    @Test
    public void testNextSequence() {
        //
        int sequence = boardTeamStore.nextSequence();
        
        assertEquals(2, sequence);
    }

    @Test
    public void testCreateMember() throws EmptyResultException {
        //
        DCBoardTeam team = boardTeamStore.retrieve(this.teamUsid);
        DCBoardUser user = boardUserStore.retrieveByEmail("tsong@nextree.co.kr");
        
        DCBoardMember member = new DCBoardMember(team, user);
        boardTeamStore.createMember(team.getUsid(), member);
        
        DCBoardMember created = boardTeamStore.retrieveMember(team.getUsid(), user.getEmail());
        
        assertNotNull(created);
        assertEquals(member.getUsid(), created.getUsid());
        
        assertEquals(team.getName(), created.getTeamName());
    }

    @Test
    public void testRetrieveMember() throws EmptyResultException {
        //
        DCBoardMember member = boardTeamStore.retrieveMember(this.teamUsid, "hkkang@nextree.co.kr");
        
        assertNotNull(member);
        assertEquals("001-hkkang@nextree.co.kr", member.getUsid());
        assertEquals("컨설팅팀", member.getTeamName());
        assertEquals("hkkang@nextree.co.kr", member.getUser().getEmail());
    }

    @Test
    public void testRetrieveMembers() {
        //
        List<DCBoardMember> members = boardTeamStore.retrieveMembers(this.teamUsid);
        
        assertTrue(members.size() == 2);
    }

    @Test
    public void testDeleteMember() {
        //
        boardTeamStore.deleteMember(this.teamUsid, "hkkang@nextree.co.kr");
        
        List<DCBoardMember> members = boardTeamStore.retrieveMembers(this.teamUsid);
        
        assertTrue(members.size() == 1);
        
        try {
            boardTeamStore.retrieveMember(this.teamUsid, "hkkang@nextree.co.kr");
            fail("Didn't throw exception");
        } catch (Exception e) {}
    }
    
    @Test
    public void testIsExist() {
    	//
    	boolean isExist = boardTeamStore.isExist("001");
    	
    	assertTrue(isExist);
    }
    
    @Test
    public void testIsExistByName() {
    	//
    	boolean isExist = boardTeamStore.isExistByName("컨설팅팀");
    	
    	assertTrue(isExist);
    }
    
    @Test
    public void testIsExistMember() {
    	//
    	boolean isExist = boardTeamStore.isExistMember("001", "hkkang@nextree.co.kr");
    	
    	assertTrue(isExist);
    }
    
    

}
