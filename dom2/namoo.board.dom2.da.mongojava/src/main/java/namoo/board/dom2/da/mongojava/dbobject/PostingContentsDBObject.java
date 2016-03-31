/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import java.util.Iterator;

import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.user.DCBoardUser;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PostingContentsDBObject {
	//
	private BasicDBObject doc;
	
	//--------------------------------------------------------------------------
	private PostingContentsDBObject() {
		//
		this.doc = new BasicDBObject();
	}
		
	public PostingContentsDBObject(DCPostingContents postingContents) {
		//
		this();		
		this.doc.put("postingUsid", postingContents.getPostingUsid());
		this.doc.put("contents", postingContents.getContents());
		if(postingContents.getCommentBag() != null){
			this.doc.put("commentBag", createDocument(postingContents.getCommentBag()));
		}
	}
	
	private BasicDBObject createDocument(DCCommentBag commentBag) {
		//
		BasicDBObject doc = new BasicDBObject();
		if(commentBag.getComments() == null || commentBag.getComments().isEmpty()) return doc;
		
		BasicDBList comments = new BasicDBList();
		for(DCPostingComment comment : commentBag.getComments()){
			if(comment == null) continue;
			comments.add(createDocument(comment));
		}
		doc.put("comments", comments);
		
		return doc;
	}
	
	private BasicDBObject createDocument(DCPostingComment comment) {
		//
		BasicDBObject doc = new BasicDBObject();
		doc.put("sequence", comment.getSequence());
		doc.put("comment", comment.getComment());
		doc.put("writerEmail", comment.getWriterEmail());
		doc.put("writtenTime", comment.getWrittenTime());
		
		return doc;
	}
	
	public PostingContentsDBObject(BasicDBObject doc) {
		//
		this.doc = doc;
	}
	
	//--------------------------------------------------------------------------
	public BasicDBObject document() {
		//
		return this.doc;
	}

	public DCPostingContents createDomain() {
		//
		DCPostingContents postingContents = createPostingContentsDomain();
		return postingContents;
	}

	public DCPostingContents createPostingContentsDomain() {
		//
		String postingUsid = (String) doc.get("postingUsid");
		String contents = (String) doc.get("contents");
		
		DCBoardUser user = new DCBoardUser(null, null, null);
		DCPosting posting = new DCPosting(null, null, user);
		posting.setUsid(postingUsid);
		DCPostingContents postingContents = new DCPostingContents(posting, contents);
		
		postingContents.setCommentBag(mapToCommentBag((DBObject) doc.get("commentBag")));
				
		return postingContents;
	}
	
	private DCCommentBag mapToCommentBag(DBObject doc) {
		//
		if (doc == null) { 
			return null;
		}
		
		BasicDBList commentDocs = (BasicDBList) doc.get("comments");
		
		//
		if (commentDocs == null || commentDocs.isEmpty() ) {
			return null;
		}
		
		DCCommentBag commentBag = new DCCommentBag();
		for (Iterator<?> iter = commentDocs.iterator(); iter.hasNext(); ) {
			BasicDBObject commmentDoc = (BasicDBObject) iter.next();
			PostingCommentDBObject postingCommentDBObject = new PostingCommentDBObject((BasicDBObject)commmentDoc);
			commentBag.addComment(postingCommentDBObject.createDomain());
		}
		
		return commentBag;
	}
	public DCPostingComment createPostingCommentDomain(int sequence) {
		//
		DCPostingContents postingContents = createPostingContentsDomain();
		BasicDBObject doc = findPostingCommentDocument(sequence);
		
		PostingCommentDBObject postingCommentDBObject = new PostingCommentDBObject(doc);
		DCPostingComment postingComment = postingCommentDBObject.createDomain();
		postingContents.addComment(postingComment);
		
		return postingComment;
	}

	private BasicDBObject findPostingCommentDocument(int sequence) {
		// 
		BasicDBList docs = (BasicDBList) this.doc.get("comments");
		if (docs == null || docs.size() <= 0) {
			return null;
		}
		
		for (Object docObj : docs) {
			BasicDBObject doc = (BasicDBObject) docObj;
			if (String.valueOf(sequence).equals(doc.get("sequence"))) {
				return doc;
			}
		}
		return null;
	}
}