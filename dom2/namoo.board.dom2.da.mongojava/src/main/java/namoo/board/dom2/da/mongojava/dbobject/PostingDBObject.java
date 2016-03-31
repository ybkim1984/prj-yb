/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import java.util.Date;
import java.util.Iterator;

import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.entity.user.DCBoardUser;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PostingDBObject {
    //
    private BasicDBObject doc;
    
    //--------------------------------------------------------------------------
    private PostingDBObject() {
        //
        this.doc = new BasicDBObject();
    }
        
    public PostingDBObject(DCPosting posting) {
        //
        this();        
        this.doc.put("title", posting.getTitle());
        this.doc.put("usid", posting.getUsid());
        this.doc.put("boardusid", posting.getBoardUsid());
        this.doc.put("writerEmail", posting.getWriterEmail());
        this.doc.put("writerName", posting.getWriterName());
        this.doc.put("readCount", posting.getReadCount());
        this.doc.put("registerDate", posting.getRegisterDate());
        
        if(posting.getOption() != null){
            this.doc.put("option", createDocument(posting.getOption()));
        }
        
        if(posting.getContents() != null){
            this.doc.put("contents", createDocument(posting.getContents()));
        }
    }
    
    private BasicDBObject createDocument(DCPostingOption option) {
        //
        BasicDBObject doc = new BasicDBObject();
        doc.put("commentable", option.isCommentable());
        doc.put("anonymousComment", option.isAnonymousComment());
        return doc;
    }
    
    private BasicDBObject createDocument(DCPostingContents contents) {
        //
        BasicDBObject doc = new BasicDBObject();
        doc.put("postingUsid", contents.getPostingUsid());
        doc.put("contents", contents.getContents());
        if(contents.getCommentBag() != null){
            doc.put("commentBag", createDocument(contents.getCommentBag()));
        }
        
        return doc;
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
    
    public PostingDBObject(BasicDBObject doc) {
        //
        this.doc = doc;
    }
    
    //--------------------------------------------------------------------------
    public BasicDBObject document() {
        //
        return this.doc;
    }

    public DCPosting createDomain() {
        //
        DCPosting posting = createPostingDomain();
        return posting;
    }

    public DCPosting createPostingDomain() {
        //
        ObjectId oid = (ObjectId) this.doc.get("_id");
        String usid = (String)doc.get("usid");
        String boardUsid = (String)doc.get("boardusid");
        String title = (String)doc.get("title");
        String writerEmail = (String)doc.get("writerEmail");
        String writerName = (String)doc.get("writerName");
        int readCount = (Integer)doc.get("readCount");
        Date registerDate = (Date) doc.get("registerDate");
        
        DCBoardUser user = new DCBoardUser(writerEmail, writerName, null);
        
        DCPosting posting = new DCPosting(boardUsid, title, user);
        posting.setOid(oid.toString());
        posting.setUsid(usid);
        posting.setReadCount(readCount);
        posting.setRegisterDate(registerDate);
        
        posting.setOption(mapToOption((DBObject) doc.get("option")));
        posting.setContents(mapToContents((DBObject) doc.get("contents")));        
        return posting;
    }
    
    private DCPostingOption mapToOption(DBObject doc) {
        //
        if (doc == null) { 
            return null;
        }
        
        boolean commentable = (Boolean) doc.get("commentable");
        boolean anonymousComment = (Boolean) doc.get("anonymousComment");
        
        DCPostingOption option = new DCPostingOption();
        option.setCommentable(commentable);
        option.setAnonymousComment(anonymousComment);
        
        return option;
    }
    
    private DCPostingContents mapToContents(DBObject doc) {
        //
        if (doc == null) { 
            return null;
        }
        
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
            commentBag.addComment(mapToComment(commmentDoc));
        }
        
        return commentBag;
    }
    
    private DCPostingComment mapToComment(DBObject doc) {
        //
        if (doc == null) { 
            return null;
        }
        
        int sequence = (Integer) doc.get("sequence");
        String comment = (String) doc.get("comment");
        String writerEmail = (String) doc.get("writerEmail");
        Date writtenTime = (Date) doc.get("writtenTime");
        
        DCPostingComment postingComment = new DCPostingComment(comment, writerEmail);
        postingComment.setSequence(sequence);
        postingComment.setWrittenTime(writtenTime);
        
        return postingComment;
    }

    public DCPostingComment createPostingCommentDomain(int sequence) {
        //
        DCPosting posting = createPostingDomain();
        BasicDBObject doc = findPostingCommentDocument(sequence);
        
        PostingCommentDBObject postingCommentDBObject = new PostingCommentDBObject(doc);
        DCPostingComment postingComment = postingCommentDBObject.createDomain();
        posting.getContents().addComment(postingComment);
        
        return postingComment;
    }

    private BasicDBObject findPostingCommentDocument(int sequence) {
        // 
        BasicDBObject contentsDoc = (BasicDBObject) this.doc.get("contents");
        BasicDBObject commentBagDoc = (BasicDBObject) contentsDoc.get("commentBag");
        BasicDBList commentDocs = (BasicDBList) commentBagDoc.get("comments");
        if (commentDocs == null || commentDocs.size() <= 0) {
            return null;
        }
        
        for (Object docObj : commentDocs) {
            BasicDBObject doc = (BasicDBObject) docObj;
            if (sequence ==  (int) doc.get("sequence")) {
                return doc;
            }
        }
        return null;
    }
}