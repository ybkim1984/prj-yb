/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.user;

import java.util.ArrayList;
import java.util.List;

public class DCBoardTeam {
    //
    private String oid;
    private String usid; 
    private String name;        
    private DCBoardUser admin;
    
    private List<DCBoardMember> members;

    //--------------------------------------------------------------------------
    
    @SuppressWarnings("unused")
    private DCBoardTeam() {}
    
    public DCBoardTeam(String usid) {
        //
        this.usid = usid;
    }
    
    public DCBoardTeam(String name, DCBoardUser admin){
        //
        this.name = name;
        this.admin = admin; 
        this.members = new ArrayList<DCBoardMember>(); 
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("usid: " + this.usid); 
        buffer.append(", name: " + this.name); 
        buffer.append(", admin " + this.admin.toString()); 
        
        return buffer.toString(); 
    }
    
    public void addMember(DCBoardMember member) {
        //
        members.add(member);
    }
    
    public DCBoardMember getMember(String memberEmail) {
        //
        for (DCBoardMember member : members) {
            if (member.getUser().getEmail().equals(memberEmail)) {
                return member;
            }
        }
        return null;
    }
    
    public void removeMember(String memberEmail) {
        //
        DCBoardMember targetMember = null;
        for (DCBoardMember member : members) {
            if (member.getUser().getEmail().equals(memberEmail)) {
                targetMember = member;
                break;
            }
        }
        
        members.remove(targetMember);
    }
    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DCBoardUser getAdmin() {
        return admin;
    }

    public void setAdmin(DCBoardUser admin) {
        this.admin = admin;
    }

    public List<DCBoardMember> getMembers() {
        return members;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }
}