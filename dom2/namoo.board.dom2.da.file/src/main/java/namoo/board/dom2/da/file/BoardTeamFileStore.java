/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.da.file.repository.BoardMemberRepository;
import namoo.board.dom2.da.file.repository.BoardTeamRepository;
import namoo.board.dom2.da.file.repository.BoardUserRepository;
import namoo.board.dom2.da.file.repository.SequenceRepository;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

public class BoardTeamFileStore implements BoardTeamStore {
    //
    private SequenceRepository seqStore = new SequenceRepository();
    private BoardTeamRepository teamStore = new BoardTeamRepository();
    private BoardUserRepository userStore = new BoardUserRepository();
    private BoardMemberRepository memberStore = new BoardMemberRepository();
    
    //--------------------------------------------------------------------------
    // DCBoardTeam
    
    @Override
    public void create(DCBoardTeam team) {
        // 
        teamStore.insert(team);
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        // 
        DCBoardTeam team = teamStore.select(usid);
        if (team == null) {
            throw new EmptyResultException("No such a team --> " + usid);
        }
        return setTeamAdmin(team);
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.selectOneByName(name);
        if (team == null) {
            throw new EmptyResultException("No such a team --> " + name);
        }
        return setTeamAdmin(team);
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        // 
        return setTeamAdmin(teamStore.selectAll());
    }

    @Override
    public void delete(String usid) {
        // 
        memberStore.deleteByTeam(usid);
        teamStore.delete(usid);
    }

    @Override
    public int nextSequence() {
        // 
        return seqStore.selectTeamNextSeq();
    }
    
    @Override
    public boolean isExist(String usid) {
        // 
        DCBoardTeam team = teamStore.select(usid);
        return team != null;
    }

    @Override
    public boolean isExistByName(String name) {
        // 
        DCBoardTeam team = teamStore.selectOneByName(name);
        return team != null;
    }

    
    private List<DCBoardTeam> setTeamAdmin(List<DCBoardTeam> teams) {
        //
        List<DCBoardTeam> results = new ArrayList<DCBoardTeam>();
        for (DCBoardTeam team : teams) {
            results.add(setTeamAdmin(team));
        }
        return results;
    }
    
    private DCBoardTeam setTeamAdmin(DCBoardTeam team) {
        //
        DCBoardUser adminUser = userStore.select(team.getAdmin().getEmail());
        team.setAdmin(adminUser);
        
        return team;
    }
    
    //--------------------------------------------------------------------------
    // BoardMemeber
    
    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        // 
        memberStore.insert(teamUsid, member);
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        //
        DCBoardMember member = memberStore.select(teamUsid, memberEmail);
        if (member == null) {
            throw new EmptyResultException("No such a member --> teamUsid: " + teamUsid + ", email: " + memberEmail);
        }
        return setMemberTeamName(teamUsid, member);
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        //
        return setMemberTeamName(teamUsid, memberStore.selectByTeam(teamUsid));
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        // 
        memberStore.delete(teamUsid, memberEmail);
    }
    
    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
        // 
        DCBoardMember member = memberStore.select(teamUsid, memberEmail);
        return member != null;
    }

    private List<DCBoardMember> setMemberTeamName(String teamUsid, List<DCBoardMember> members) {
        //
        List<DCBoardMember> results = new ArrayList<DCBoardMember>();
        
        for (DCBoardMember member : members) {
            results.add(setMemberTeamName(teamUsid, member));
        }
        return results;
    }
    
    private DCBoardMember setMemberTeamName(String teamUsid, DCBoardMember member) {
        //
        if (member == null) {
            return null;
        }
        
        DCBoardTeam team = teamStore.select(teamUsid);
        member.setTeamName(team.getName());
        
        return member;
    }
    
}
