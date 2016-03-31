/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import java.util.Date;

import namoo.board.dom2.entity.board.DCSocialBoard;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class SocialBoardDBObject {
    //
    private BasicDBObject doc;
    
    //--------------------------------------------------------------------------
    private SocialBoardDBObject() {
        //
        this.doc = new BasicDBObject();
    }
        
    public SocialBoardDBObject(DCSocialBoard socialBoard) {
        //
        this();        
        this.doc.put("usid", socialBoard.getUsid());
        this.doc.put("name", socialBoard.getName());
        this.doc.put("commentable", socialBoard.isCommentable());
        this.doc.put("createdate", socialBoard.getCreateDate());
        this.doc.put("teamusid", socialBoard.getTeamUsid());
    }
    
    public SocialBoardDBObject(BasicDBObject doc) {
        //
        this.doc = doc;
    }
    
    //--------------------------------------------------------------------------
    public BasicDBObject document() {
        //
        return this.doc;
    }

    public DCSocialBoard createDomain() {
        //
        DCSocialBoard socialBoard = createSocialBoardDomain();
        return socialBoard;
    }

    public DCSocialBoard createSocialBoardDomain() {
        //
        ObjectId oid = (ObjectId) this.doc.get("_id");
        String usid = (String)doc.get("usid");
        String name = (String)doc.get("name");
        Boolean commentable = (Boolean)doc.get("commentable");
        Date createDate = (Date)doc.get("createdate");
        String teamUsid = (String)doc.get("teamusid");
        
        DCSocialBoard socialBoard = new DCSocialBoard(usid, name, createDate, teamUsid, commentable);
        socialBoard.setOid(oid.toString());
                
        return socialBoard;
    }
}