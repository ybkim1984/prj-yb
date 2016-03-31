/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */

package namoo.board.dom2.da.mongojava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import namoo.board.dom2.da.mongojava.dbobject.BoardMemberDBObject;
import namoo.board.dom2.da.mongojava.dbobject.BoardTeamDBObject;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BoardTeamMongoJavaStore implements BoardTeamStore {
    //
    private static final String BoardPartyCollectionName = "boardteam";
    private static final String SequenceCollectionName = "team_seq";
    DB db = MongoClientFactory.getDB();
    DBCollection collection = db.getCollection(BoardPartyCollectionName);
    DBCollection sequenceCollection = db.getCollection(SequenceCollectionName);
    
    //--------------------------------------------------------------------------
    @Override
    public void create(DCBoardTeam team) {
        //
        BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject(team);
        collection.insert(boardTeamDBObject.document());
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        // 
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", usid));
        if (doc == null) {
        	throw new EmptyResultException("No such boardTeam -->" + usid);
        }
        
        BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject(doc);
        return boardTeamDBObject.createBoardTeamDomain();
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        // 
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("name", name));
        if (doc == null) {
        	throw new EmptyResultException("No such boardTeam -->" + name);
        }
        
        BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject(doc);
        return boardTeamDBObject.createBoardTeamDomain();
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        //
        DBCursor cursor = collection.find();
        if (cursor.count() <= 0) {
            return Collections.emptyList();
        }
        
        List<DCBoardTeam> boardTeams = new ArrayList<DCBoardTeam>();
        for (Iterator<DBObject> iter = cursor.iterator(); iter.hasNext(); ) {
            BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject((BasicDBObject)iter.next());
            boardTeams.add(boardTeamDBObject.createDomain());
        }
        
        return boardTeams;
    }

    @Override
    public void delete(String usid) {
        // 
        collection.remove(new BasicDBObject("usid", usid));
    }

    @Override
    public int nextSequence() {
        // 
    	BasicDBObject query = new BasicDBObject();
		query.put("name", "team");
		BasicDBObject update = new BasicDBObject();
        if(sequenceCollection.count() > 0) {
        	update.put("$inc", new BasicDBObject("team_id", 1));
    	} else {
    		update.put("$set", new BasicDBObject("team_id", 1));
    	}
        BasicDBObject result = (BasicDBObject) sequenceCollection.findAndModify(query, null, null, false, update, true, true);
        
        return result.getInt("team_id");
       
    }

    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        //
        BoardMemberDBObject boardMemberDBObject = new BoardMemberDBObject(member);

        DBObject query = new BasicDBObject("usid", teamUsid);
        DBObject updateDoc = new BasicDBObject(
                "$push", new BasicDBObject("boardMembers", boardMemberDBObject.document()));
        
        collection.update(query, updateDoc);
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        //
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
            obj.add(new BasicDBObject("usid", teamUsid));
            obj.add(new BasicDBObject("boardMembers.user.email", memberEmail));
            andQuery.put("$and", obj);
        BasicDBObject doc = (BasicDBObject) collection.findOne(andQuery);
        
        if (doc == null) {
        	throw new EmptyResultException("No such boardMember -->" + memberEmail);
        }    
        
        BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject(doc);            
        return boardTeamDBObject.createBoardMemberDomainByEmail(memberEmail);
        
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", teamUsid));
        
        if (doc == null) {
            return null;
        }    
        
        BoardTeamDBObject boardTeamDBObject = new BoardTeamDBObject(doc);        
        return boardTeamDBObject.createBoardMemberDomain();
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        //
        DBObject findQuery = new BasicDBObject("usid", teamUsid);
        DBObject updateQuery = new BasicDBObject("$pull", new BasicDBObject("boardMembers", new BasicDBObject("usid", teamUsid)));
        collection.update(findQuery, updateQuery);
    }

	@Override
	public boolean isExist(String usid) {
		//
		 BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", usid));
	        if (doc == null) {
	        	return false;
	        }
	        
		return true;
	}

	@Override
	public boolean isExistByName(String name) {
		// 
		 BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("name", name));
	        if (doc == null) {
	        	return false;
	        }
	        
		return true;
	}

	@Override
	public boolean isExistMember(String teamUsid, String memberEmail) {
		//
		BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
            obj.add(new BasicDBObject("usid", teamUsid));
            obj.add(new BasicDBObject("boardMembers.user.email", memberEmail));
            andQuery.put("$and", obj);
        BasicDBObject doc = (BasicDBObject) collection.findOne(andQuery);
        
        if (doc == null) {
        	return false;
        }
        return true;
	}

}
