/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */

package namoo.board.dom2.da.mongojava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import namoo.board.dom2.da.mongojava.dbobject.PostingCommentDBObject;
import namoo.board.dom2.da.mongojava.dbobject.PostingDBObject;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PostingMongoJavaStore implements PostingStore {
    //
    private static final String BoardPostingCollectionName = "boardposting";
    private static final String SequenceCollectionName = "board_seq";
    
    DB db = MongoClientFactory.getDB();
    DBCollection collection = db.getCollection(BoardPostingCollectionName);
    DBCollection sequenceCollection = db.getCollection(SequenceCollectionName);

    //--------------------------------------------------------------------------
    @Override
    public void create(DCPosting posting) {
        //
        PostingDBObject postingDBObject = new PostingDBObject(posting);
        collection.insert(postingDBObject.document());
    }

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", usid));
        if (doc == null) {
        	throw new EmptyResultException("No such posting -->" + usid);
        }
        PostingDBObject postingDBObject = new PostingDBObject(doc);
        return postingDBObject.createDomain();
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        //
        BasicDBObject query = new BasicDBObject("boardusid", boardUsid);
        
        int numToSkip = pageCriteria.getItemLimitOfPage() * (pageCriteria.getPage() - 1);
        int limitSize = pageCriteria.getItemLimitOfPage();
        
        DBCursor cursor = collection.find(query).skip(numToSkip).limit(limitSize);
        if(cursor == null || cursor.size() == 0) return null;
        
        List<DCPosting> postingList = new ArrayList<DCPosting>(cursor.size());
        for (Iterator<DBObject> iter = cursor.iterator(); iter.hasNext(); ) {
            PostingDBObject postingDBObject = new PostingDBObject((BasicDBObject)iter.next());
            postingList.add(postingDBObject.createDomain());
        }
    
        Page<DCPosting> paging = new Page<DCPosting>(postingList, pageCriteria);
        
        return paging;
    }

    @Override
    public void update(DCPosting posting) {
        //
        PostingDBObject postingDBObject = new PostingDBObject(posting);
        collection.update(new BasicDBObject("boardusid", posting.getBoardUsid()), postingDBObject.document());
    }

    @Override
    public void delete(String usid) {
        //
        collection.remove(new BasicDBObject("usid", usid));
    }

    @Override
    public void deleteByBoard(String boardUsid) {
        //
        collection.remove(new BasicDBObject("boardusid", boardUsid));
    }

    @Override
    public int nextSequence(String boardUsid) {
        //
    	BasicDBObject query = new BasicDBObject();
		query.put("name", boardUsid);
		BasicDBObject update = new BasicDBObject();
        if(sequenceCollection.count() > 0) {
        	update.put("$inc", new BasicDBObject("board_id", 1));
    	} else {
    		update.put("$set", new BasicDBObject("board_id", 1));
    	}
        BasicDBObject result = (BasicDBObject) sequenceCollection.findAndModify(query, null, null, false, update, true, true);
        
        return result.getInt("board_id");
    }

    @Override
    public void increaseReadCount(String usid) {
        //
        BasicDBObject query = new BasicDBObject();
        query.put("usid", usid);
        
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("readCount", 1));
        
        collection.findAndModify(query, null, null, false, update, true, true);
    }

    @Override
    public void createContents(DCPostingContents contents) {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", contents.getPostingUsid()));
        PostingDBObject postingDBObject = new PostingDBObject(doc);
        DCPosting posting = postingDBObject.createDomain();
        posting.setContents(contents);
        PostingDBObject postingContentsDBObject = new PostingDBObject(posting);
        collection.update(new BasicDBObject("usid", contents.getPostingUsid()), postingContentsDBObject.document());
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        // 
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", postingUsid));
        
        if (doc == null) {
        	throw new EmptyResultException("No such postingContents -->" + postingUsid);
        }  
        
        PostingDBObject postingDBObject = new PostingDBObject(doc);
        DCPosting posting = postingDBObject.createDomain();
        return posting.getContents();
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", contents.getPostingUsid()));
        PostingDBObject postingDBObject = new PostingDBObject(doc);
        DCPosting posting = postingDBObject.createDomain();
        posting.setContents(contents);
        PostingDBObject postingContentsDBObject = new PostingDBObject(posting);
        collection.update(new BasicDBObject("usid", contents.getPostingUsid()), postingContentsDBObject.document());
    }

    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        //
        PostingCommentDBObject postingCommentDBObject = new PostingCommentDBObject(comment);    
        
        DBObject findQuery = new BasicDBObject("usid", postingUsid);
        DBObject updateQuery = new BasicDBObject("$push", new BasicDBObject("contents.commentBag.comments", postingCommentDBObject.document()));
        collection.update(findQuery, updateQuery);
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        //
        BasicDBObject doc = (BasicDBObject) collection.findOne(new BasicDBObject("usid", postingUsid));
        
        if (doc == null) {
        	throw new EmptyResultException("No such postingComment -->" + postingUsid);
        }    
        
        PostingDBObject postingCommentDBObject = new PostingDBObject(doc);
        return postingCommentDBObject.createPostingCommentDomain(sequence);
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        //
        DBObject findQuery = new BasicDBObject("usid", postingUsid);
        DBObject updateQuery = new BasicDBObject("$pull", new BasicDBObject("contents.commentBag.comments", new BasicDBObject("sequence", sequence)));
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
	public int nextCommentSequence(String postingUsid) {
		//
		BasicDBObject query = new BasicDBObject();
		query.put("name", postingUsid);
		BasicDBObject update = new BasicDBObject();
        if(sequenceCollection.count() > 0) {
        	update.put("$inc", new BasicDBObject("board_id", 1));
    	} else {
    		update.put("$set", new BasicDBObject("board_id", 1));
    	}
        BasicDBObject result = (BasicDBObject) sequenceCollection.findAndModify(query, null, null, false, update, true, true);
        
        return result.getInt("board_id");
	}

	@Override
	public boolean isExistComment(String postingUsid, int sequence) {
		// 
		BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
            obj.add(new BasicDBObject("usid", postingUsid));
            obj.add(new BasicDBObject("contents.commentBag.comments.sequence", sequence));
            andQuery.put("$and", obj);
        BasicDBObject doc = (BasicDBObject) collection.findOne(andQuery);
        
        if (doc == null) {
        	return false;
        }
        return true;
	}
    
    
}
