/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BoardTeamDBObject {
    //
    private BasicDBObject doc;
    
    //--------------------------------------------------------------------------
    private BoardTeamDBObject() {
        //
        this.doc = new BasicDBObject();
    }
        
    public BoardTeamDBObject(DCBoardTeam boardTeam) {
        //
        this();        
        this.doc.put("name", boardTeam.getName());
        this.doc.put("usid", boardTeam.getUsid());
        this.doc.put("admin", createDocument(boardTeam.getAdmin()));
    }
    
    private DBObject createDocument(DCBoardUser boardUser) {
        //
        DBObject doc = new BasicDBObject();
        doc.put("name", boardUser.getName());
        doc.put("email", boardUser.getEmail());
        doc.put("phonenumber", boardUser.getPhoneNumber());
        
        return doc;
    }
    
    public BoardTeamDBObject(BasicDBObject doc) {
        //
        this.doc = doc;
    }
    
    //--------------------------------------------------------------------------
    public BasicDBObject document() {
        //
        return this.doc;
    }
    
    public DCBoardTeam createDomain() {
        //
        DCBoardTeam boardTeam = createBoardTeamDomain();
        boardTeam = fillBoardMemberDomain(boardTeam);
        return boardTeam;
    }

    private DCBoardTeam fillBoardMemberDomain(DCBoardTeam boardTeam) {
        //
        BasicDBList boardMemberDocs = (BasicDBList) this.doc.get("boardMembers");
        if (boardMemberDocs != null && boardMemberDocs.size() > 0) {
            for (Object boardMemberDoc : boardMemberDocs) {
                BoardMemberDBObject boardMemebrDBObject = new BoardMemberDBObject((BasicDBObject) boardMemberDoc);
                boardTeam.addMember(boardMemebrDBObject.createDomain());
            }
        }
        return boardTeam;
    }
    
    public DCBoardTeam createBoardTeamDomain() {
        //
        String usid = (String)doc.get("usid");
        String name = (String)doc.get("name");
        DBObject adminDoc = (DBObject)doc.get("admin");
        
        DCBoardTeam boardTeam = new DCBoardTeam(name, mapToUser(adminDoc));
        boardTeam.setUsid(usid);
                
        return boardTeam;
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
    //--------------------------------------------------------------------------
    
    public DCBoardMember createBoardMemberDomain(String boardMemberUsid) {
        //
        DCBoardTeam boardTeam = createBoardTeamDomain();
        BasicDBObject boardMemberDoc = findBoardMemberDocument(boardMemberUsid);
        
        BoardMemberDBObject boardMemberDBObject = new BoardMemberDBObject(boardMemberDoc);
        DCBoardMember boardMember = boardMemberDBObject.createDomain();
        boardTeam.addMember(boardMember);
        
        return boardMember;
    }
    
    private BasicDBObject findBoardMemberDocument(String boardMemberUsid) {
        //
        BasicDBList boardMemberDocs = (BasicDBList) this.doc.get("boardMembers");
        if (boardMemberDocs == null || boardMemberDocs.size() <= 0) {
            return null;
        }
        
        for (Object boardMemberDocObj : boardMemberDocs) {
            BasicDBObject boardMemberDoc = (BasicDBObject) boardMemberDocObj;
            if (boardMemberUsid.equals(boardMemberDoc.get("usid"))) {
                return boardMemberDoc;
            }
        }
        return null;
    }
    
    public DCBoardMember createBoardMemberDomainByEmail(String memberEmail) {
        //
        DCBoardTeam boardTeam = createBoardTeamDomain();
        BasicDBObject boardMemberDoc = findBoardMemberDocumentByEmail(memberEmail);
        
        BoardMemberDBObject boardMemberDBObject = new BoardMemberDBObject(boardMemberDoc);
        DCBoardMember boardMemeber = boardMemberDBObject.createDomain();
        boardTeam.addMember(boardMemeber);
        
        return boardMemeber;
    }
    
    private BasicDBObject findBoardMemberDocumentByEmail(String boardMemberEmail) {
        //
        BasicDBList boardMemberDocs = (BasicDBList) this.doc.get("boardMembers");
        if (boardMemberDocs == null || boardMemberDocs.size() <= 0) {
            return null;
        }
        
        for (Object boardMemberDocObj : boardMemberDocs) {
            BasicDBObject boardMemberDoc = (BasicDBObject) boardMemberDocObj;
            
            BasicDBObject userDoc = (BasicDBObject) boardMemberDoc.get("user");
            if(userDoc == null) continue;
            
            if (userDoc.getString("email").equals(boardMemberEmail)) {
                return boardMemberDoc;
            }
        }
        return null;
    }
    
    public List<DCBoardMember> createBoardMemberDomain() {
        //
        DCBoardTeam boardTeam = createBoardTeamDomain();        
        BasicDBList boardMemberDocs = (BasicDBList) this.doc.get("boardMembers");
        List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();        
        for (Object boardMemberDocObj : boardMemberDocs) {
            BasicDBObject boardMemberDoc = (BasicDBObject) boardMemberDocObj;
            BoardMemberDBObject boardMemberDBObject = new BoardMemberDBObject(boardMemberDoc);
            DCBoardMember boardMember = boardMemberDBObject.createDomain();
            boardTeam.addMember(boardMember);
            boardMembers.add(boardMember);            
        }
        return boardMembers;
    }
    
}