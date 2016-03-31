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

import namoo.board.dom2.da.mongojava.dbobject.SocialBoardDBObject;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class SocialBoardMongoJavaStore implements SocialBoardStore {
    //
    private static final String SocialBoardCollectionName = "socialBoard";
    private static final String SequenceCollectionName = "board_seq";
    DB db = MongoClientFactory.getDB();
    DBCollection collection = db.getCollection(SocialBoardCollectionName);
    DBCollection sequenceCollection = db.getCollection(SequenceCollectionName);
    
    //--------------------------------------------------------------------------
    @Override
    public String create(DCSocialBoard socialBoard) {
        //
        SocialBoardDBObject socialBoardDBObject = new SocialBoardDBObject(socialBoard);
        collection.insert(socialBoardDBObject.document());
        return socialBoard.getUsid();
    }

    @Override
    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", usid));
        if (doc == null) {
        	throw new EmptyResultException("No such socialBoard -->" + usid);
        }
        
        SocialBoardDBObject socialBoardDBObject = new SocialBoardDBObject(doc);;
        return socialBoardDBObject.createDomain();
    }

    @Override
    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        // 
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("name", name));
        if (doc == null) {
        	throw new EmptyResultException("No such socialBoard -->" + name);
        }
        
        SocialBoardDBObject socialBoardDBObject = new SocialBoardDBObject(doc);;
        return socialBoardDBObject.createDomain();
    }

    @Override
    public List<DCSocialBoard> retrieveAll() {
        // 
        DBCursor cursor = collection.find();
        if (cursor.count() <= 0) {
            return Collections.emptyList();
        }
        
        List<DCSocialBoard> socialBoards = new ArrayList<DCSocialBoard>();
        for (Iterator<DBObject> iter = cursor.iterator(); iter.hasNext(); ) {
            SocialBoardDBObject socialBoardDBObject = new SocialBoardDBObject((BasicDBObject)iter.next());
            socialBoards.add(socialBoardDBObject.createDomain());
        }
        
        return socialBoards;
    }

    @Override
    public void update(DCSocialBoard socialBoard) {
        //
        SocialBoardDBObject socialBoardDBObject = new SocialBoardDBObject(socialBoard);
        collection.update(new BasicDBObject("_id", new ObjectId(socialBoard.getOid())), socialBoardDBObject.document());
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
		query.put("name", "board");
		BasicDBObject update = new BasicDBObject();
        if(sequenceCollection.count() > 0) {
        	update.put("$inc", new BasicDBObject("board_id", 1));
    	} else {
    		update.put("$set", new BasicDBObject("board_id", 1));
    	}
        BasicDBObject result = (BasicDBObject) sequenceCollection.findAndModify(query, null, null, false, update, true, true);
        
        return result.getInt("board_id");
    }
    
    //--------------------------------------------------------------------------
   

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
 
}
