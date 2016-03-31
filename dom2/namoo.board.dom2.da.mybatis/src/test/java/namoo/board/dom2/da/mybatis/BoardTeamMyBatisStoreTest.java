/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mybatis;

import static org.junit.Assert.*;

import java.util.List;

import namoo.board.dom2.da.mybatis.BoardTeamMyBatisStore;
import namoo.board.dom2.da.mybatis.BoardUserMyBatisStore;
import namoo.board.dom2.da.mybatis.shared.BaseMyBatisStoreDbUnitTest;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.Test;

public class BoardTeamMyBatisStoreTest extends BaseMyBatisStoreDbUnitTest {
    //
    private BoardTeamStore teamStore = new BoardTeamMyBatisStore();
    private BoardUserStore userStore = new BoardUserMyBatisStore();
    
    /** 초기데이터로 insert 된 데이터셋 teamUsid */
    private final String teamUsid = "001";
    
    @Override
    protected String getDatasetXml() {
        // 
        return "/dataset/board_teams.xml";
    }

    //--------------------------------------------------------------------------
    // board team
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        String adminEmail = "hkkang@nextree.co.kr";
        
        DCBoardUser user = userStore.retrieveByEmail(adminEmail);
        DCBoardTeam team = new DCBoardTeam("개발팀", user); 
        
        team.setUsid("002");
        
        teamStore.create(team);
        
        DCBoardTeam created = teamStore.retrieveByName("개발팀");
        assertNotNull(created);
        assertEquals("002", created.getUsid());
        assertEquals(team.getAdmin().getEmail(), created.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCBoardTeam created = teamStore.retrieve(this.teamUsid);
        
        assertNotNull(created);
        assertEquals("컨설팅팀", created.getName());
        assertEquals("tsong@nextree.co.kr", created.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCBoardTeam saved = teamStore.retrieveByName("컨설팅팀");
        
        assertNotNull(saved);
        assertEquals(this.teamUsid, saved.getUsid());
        assertEquals("tsong@nextree.co.kr", saved.getAdmin().getEmail());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardTeam> allTeams = teamStore.retrieveAll();
        
        assertTrue(allTeams.size() >= 1);
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCBoardTeam saved = teamStore.retrieve(this.teamUsid);
        assertNotNull(saved);
        
        List<DCBoardMember> teamMembers = teamStore.retrieveMembers(this.teamUsid);
        assertFalse(teamMembers.isEmpty());
        
        
        teamStore.delete(this.teamUsid);
        
        try {
            teamStore.retrieve(this.teamUsid);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
        List<DCBoardMember> deletedMembers = teamStore.retrieveMembers(this.teamUsid);
        assertTrue(deletedMembers.isEmpty());
    }
    
    @Test
    public void testNextSequence() {
        //
        int sequence = teamStore.nextSequence();
        
        assertEquals(2, sequence);
    }
    
    // board member
    @Test
    public void testCreateMember() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieve(this.teamUsid);
        DCBoardUser user = userStore.retrieveByEmail("eschoi@nextree.co.kr");
        
        DCBoardMember member = new DCBoardMember(team, user);
        teamStore.createMember(team.getUsid(), member);
        
        DCBoardMember created = teamStore.retrieveMember(team.getUsid(), user.getEmail());
        assertNotNull(created);
        assertEquals(member.getUsid(), created.getUsid());
        assertEquals(team.getName(), created.getTeamName());
    }
    
    @Test
    public void testRetrieveMember() throws EmptyResultException {
        //
        DCBoardMember member = teamStore.retrieveMember(this.teamUsid, "hkkang@nextree.co.kr");
        
        assertNotNull(member);
        assertEquals("001-hkkang@nextree.co.kr", member.getUsid());
        assertEquals("컨설팅팀", member.getTeamName());
        assertEquals("hkkang@nextree.co.kr", member.getUser().getEmail());
    }
    
    @Test
    public void testRetrieveMembers() {
        //
        List<DCBoardMember> members = teamStore.retrieveMembers(this.teamUsid);
        assertTrue(members.size() == 2);
    }
    
    @Test
    public void testDeleteMember() {
        //
        teamStore.deleteMember(this.teamUsid, "hkkang@nextree.co.kr");
        
        try {
            teamStore.retrieveMember(this.teamUsid, "hkkang@nextree.co.kr");
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
        List<DCBoardMember> members = teamStore.retrieveMembers(this.teamUsid);
        assertTrue(members.size() == 1);
    }

}
