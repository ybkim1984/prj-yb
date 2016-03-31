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

import namoo.board.dom2.da.mongojava.dbobject.BoardUserDBObject;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BoardUserMongoJavaStore implements BoardUserStore {
    //
    private static final String BoardUserCollectionName = "boardusers";
    DB db = MongoClientFactory.getDB();
    DBCollection collection = db.getCollection(BoardUserCollectionName);
    
    //--------------------------------------------------------------------------
    @Override
    public void create(DCBoardUser boardUser) {
        //
        BoardUserDBObject boardUserDBObject = new BoardUserDBObject(boardUser);
        collection.insert(boardUserDBObject.document());
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("email", email));
        if (doc == null) {
        	throw new EmptyResultException("No such boardUser -->" + email);
        }
        
        BoardUserDBObject boardUserDBObject = new BoardUserDBObject(doc);
        return boardUserDBObject.createDomain();
    }

    @Override
    public List<DCBoardUser> retrieveAll() {
        //
        DBCursor cursor = collection.find();
        if (cursor.count() <= 0) {
            return Collections.emptyList();
        }
        
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();
        for (Iterator<DBObject> iter = cursor.iterator(); iter.hasNext(); ) {
            BoardUserDBObject boardUserDBObject = new BoardUserDBObject((BasicDBObject)iter.next());
            boardUsers.add(boardUserDBObject.createDomain());
        }
        
        return boardUsers;
    }

    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        //
        DBCursor cursor = collection.find(new BasicDBObject("name", name));
        if (cursor.count() <= 0) {
            return Collections.emptyList();
        }
        
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();
        for (Iterator<DBObject> iter = cursor.iterator(); iter.hasNext(); ) {
            BoardUserDBObject boardUserDBObject = new BoardUserDBObject((BasicDBObject)iter.next());
            boardUsers.add(boardUserDBObject.createDomain());
        }
        
        return boardUsers;
    }

    @Override
    public void update(DCBoardUser boardUser) {
        //
        BoardUserDBObject boardUserDBObject = new BoardUserDBObject(boardUser);
        collection.update(new BasicDBObject("_id", new ObjectId(boardUser.getOid())), boardUserDBObject.document());
        
    }

    @Override
    public void deleteByEmail(String email) {
        //
        collection.remove(new BasicDBObject("email", email));
    }

    @Override
    public boolean isExistByEmail(String email) {
    	//
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("email", email));
        if (doc == null) {
            return false;
        }
        return true;
    }
}
