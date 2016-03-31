/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 15.
 */
package namoo.board.dom2.da.mem.mapstore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mem.util.ObjectCopyUtil;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;

public class BoardTeamRepository {
    //
    private static BoardTeamRepository instance = new BoardTeamRepository();
    private Map<String, DCBoardTeam> teamMap = new HashMap<String, DCBoardTeam>();

    
    private BoardTeamRepository() {}
    
    public static BoardTeamRepository getInstance() {
        //
        return instance;
    }
    
    //--------------------------------------------------------------------------
    // board team
    
    public void put(DCBoardTeam team) {
        //
        DCBoardTeam copied = ObjectCopyUtil.copyObject(team, DCBoardTeam.class); 
        this.teamMap.put(copied.getUsid(), copied);
    }
    
    public DCBoardTeam get(String usid) {
        //
        DCBoardTeam team = this.teamMap.get(usid); 
        return ObjectCopyUtil.copyObject(team, DCBoardTeam.class);
    }
    
    public DCBoardTeam getByName(String name) {
        //
        for (DCBoardTeam team : this.teamMap.values()) {
            if (team.getName().equals(name)) {
                return ObjectCopyUtil.copyObject(team, DCBoardTeam.class);
            }
        }
        return null;
    }
    
    public List<DCBoardTeam> getAll() {
        //
        return ObjectCopyUtil.copyObjects(this.teamMap.values(), DCBoardTeam.class);
    }
    
    public void remove(String usid) {
        //
        this.teamMap.remove(usid);
    }
    
    
    //--------------------------------------------------------------------------
    // team member

    public void putMember(String teamUsid, DCBoardMember member) {
        //
        DCBoardTeam team = this.teamMap.get(teamUsid);
        team.addMember(ObjectCopyUtil.copyObject(member, DCBoardMember.class));
    }
    
    public DCBoardMember getMember(String teamUsid, String memberEmail) {
        //
        DCBoardTeam team = this.teamMap.get(teamUsid);
        return ObjectCopyUtil.copyObject(team.getMember(memberEmail), DCBoardMember.class);
    }
    
    public List<DCBoardMember> getMembers(String teamUsid) {
        //
        DCBoardTeam team = this.teamMap.get(teamUsid);
        if (team == null) {
            return Collections.emptyList();
        }
        return ObjectCopyUtil.copyObjects(team.getMembers(), DCBoardMember.class);
    }
    
    public void removeMember(String teamUsid, String memberEmail) {
        //
        DCBoardTeam team = this.teamMap.get(teamUsid);
        team.removeMember(memberEmail);
    }
    
}
