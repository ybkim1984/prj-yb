/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.user;

public class DCBoardMember {
    //
    private String oid; 
    private String usid; 
    private String teamName; 
    private DCBoardUser user; 
    
    //--------------------------------------------------------------------------
    
    @SuppressWarnings("unused")
    private DCBoardMember() {}
    
    public DCBoardMember(String usid) {
        //
        this.usid = usid;
    }
    
    public DCBoardMember(DCBoardTeam team, DCBoardUser user) {
        //
        this.usid = team.getUsid() + "-" + user.getEmail(); 
        this.teamName = team.getName(); 
        this.user = user; 
    }

    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("usid: " + this.usid); 
        buffer.append(", teamName: " + this.teamName); 
        buffer.append(", user " + this.user.toString()); 
        
        return buffer.toString(); 
    }
    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
    
    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public DCBoardUser getUser() {
        return user;
    }

    public void setUser(DCBoardUser user) {
        this.user = user;
    }

    
}
