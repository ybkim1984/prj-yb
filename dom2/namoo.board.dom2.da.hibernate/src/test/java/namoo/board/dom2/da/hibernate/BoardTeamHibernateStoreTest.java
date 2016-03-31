/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 2. 3.
 */

package namoo.board.dom2.da.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import namoo.board.dom2.da.hibernate.BoardTeamHibernateStore;
import namoo.board.dom2.da.hibernate.BoardUserHibernateStore;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

import org.junit.Test;

public class BoardTeamHibernateStoreTest extends BoardInitHibernateStoreTest {
    //
    private BoardTeamHibernateStore teamStore = new BoardTeamHibernateStore();
    private BoardUserStore userStore = new BoardUserHibernateStore();
    
    @Override
    protected String getDatasetXml() {
        //
        return "./dataset/board_teams.xml";
    }

    @Test
    public void testCreate() throws EmptyResultException {
        //
        String adminEmail = "hkkang@nextree.co.kr";
        
        DCBoardUser user = userStore.retrieveByEmail(adminEmail);
        DCBoardTeam team = new DCBoardTeam("개발팀", user);         
        team.setUsid("003");
        
        teamStore.create(team);
        
        DCBoardTeam insertTeam = teamStore.retrieve("003");
        assertEquals("개발팀", insertTeam.getName());

        assertThat(insertTeam.getAdmin().getName(),is("강형구"));
    }

    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieve("001");
        assertEquals("컨설팅팀", team.getName());
        assertEquals("송태국",team.getAdmin().getName());
    }

    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieveByName("컨설팅팀");
        assertThat(team.getAdmin().getEmail(),is("tsong@nextree.co.kr"));        
    }

    @Test
    public void testRetrieveAll() {
        //
        List<DCBoardTeam> teams = teamStore.retrieveAll();
        assertThat(teams.size(),is(2));
    }

    @Test
    public void testDelete() throws Exception {
        //
        List<DCBoardTeam> teams = teamStore.retrieveAll();
        assertThat(2,is(teams.size()));
        teamStore.delete("002");
        List<DCBoardTeam> afterTeams = teamStore.retrieveAll();
        assertThat(afterTeams.size(),is(1));    
        
        DCBoardTeam deleteTeam = null;
        try{
            deleteTeam = teamStore.retrieveByName("경영지원팀");
        }catch(NamooBoardException nb) {
            assertTrue(deleteTeam == null);
        }        
        DCBoardUser user = userStore.retrieveByEmail("demonpark@nextree.co.kr");
        assertThat(user.getName(),is("박석재"));//멤버는 삭제가 되어라도 admin의 user정보는 삭제되면 안됨        
    }

    @Test
    public void testNextSequence() {
        //
        int nextSeq = teamStore.nextSequence(); 
        assertEquals(4,nextSeq);
    }

    @Test
    public void testCreateMember() throws Exception {
        DCBoardTeam team = teamStore.retrieveByName("경영지원팀");
        DCBoardUser user = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        DCBoardMember member = new DCBoardMember(team, user);
        
        teamStore.createMember(team.getUsid(), member);
        
        DCBoardMember insertMember = teamStore.retrieveMember(team.getUsid(), "hkkang@nextree.co.kr");
        
        assertThat(insertMember.getUser().getName(), is("강형구"));    
        
    }

    @Test
    public void testRetrieveMember() {
        //
        DCBoardMember member = teamStore.retrieveMember("001","hkkang@nextree.co.kr");
        
        assertThat(member.getUser().getName(), is("강형구"));    
    }

    @Test
    public void testRetrieveMembers() {
        //
        List<DCBoardMember> members = teamStore.retrieveMembers("002");
        
        assertThat(members.size(), is(3));    
    }

    @Test
    public void testDeleteMember() throws Exception {
        //
        List<DCBoardMember> members = teamStore.retrieveMembers("002");        
        assertThat(members.size(), is(3));    
        
        teamStore.deleteMember("002", "demonpark@nextree.co.kr");
        
        List<DCBoardMember> afterMembers = teamStore.retrieveMembers("002");
        
        assertThat(afterMembers.size(), is(2));    
        
        DCBoardUser user = userStore.retrieveByEmail("demonpark@nextree.co.kr");
        assertThat(user.getName(),is("박석재"));// member를 지워도 연관관계의 user를 삭제되면 안됨
    }

}
