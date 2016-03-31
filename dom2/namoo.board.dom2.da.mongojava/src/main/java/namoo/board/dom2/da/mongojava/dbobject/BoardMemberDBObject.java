/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BoardMemberDBObject {
    //
    private BasicDBObject doc;
    
    //--------------------------------------------------------------------------
    private BoardMemberDBObject() {
        //
        this.doc = new BasicDBObject();
    }
        
    public BoardMemberDBObject(DCBoardMember boardMember) {
        //
        this();        
        this.doc.put("usid", boardMember.getUsid());
        this.doc.put("teamname", boardMember.getTeamName());
        this.doc.put("user", createDocument(boardMember.getUser()));
    }
    
    private DBObject createDocument(DCBoardUser boardUser) {
        //
        DBObject doc = new BasicDBObject();
        doc.put("name", boardUser.getName());
        doc.put("email", boardUser.getEmail());
        doc.put("phonenumber", boardUser.getPhoneNumber());
        
        return doc;
    }
    
    public BoardMemberDBObject(BasicDBObject doc) {
        //
        this.doc = doc;
    }
    
    //--------------------------------------------------------------------------
    public BasicDBObject document() {
        //
        return this.doc;
    }

    public DCBoardMember createDomain() {
        //
        DCBoardMember boardMember = createBoardMemberDomain();
        return boardMember;
    }

    public DCBoardMember createBoardMemberDomain() {
        //
        String usid = (String) doc.get("usid");
        String teamName = (String) doc.get("teamname");
        
        DCBoardTeam team = new DCBoardTeam(teamName, null);
        team.setUsid(usid);
        
        if(doc.get("user") == null) return null;

        return new DCBoardMember(team, mapToUser((DBObject) doc.get("user")));
    }
    
    private DCBoardUser mapToUser(DBObject doc) {
        //
        if (doc == null) { 
            return null;
        }
        
        String email = (String)doc.get("email");
        String name = (String)doc.get("name");
        String phoneNumber = (String)doc.get("phonenumber");
        DCBoardUser user = new DCBoardUser(email, name, phoneNumber);
        
        return user;
    }
}