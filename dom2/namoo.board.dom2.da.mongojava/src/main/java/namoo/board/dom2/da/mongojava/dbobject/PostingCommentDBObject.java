/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import java.util.Date;

import namoo.board.dom2.entity.board.DCPostingComment;

import com.mongodb.BasicDBObject;

public class PostingCommentDBObject {
    //
    private BasicDBObject doc;
    
    //--------------------------------------------------------------------------
    private PostingCommentDBObject() {
        //
        this.doc = new BasicDBObject();
        
    }
        
    public PostingCommentDBObject(DCPostingComment postingComment) {
        //
        this();        
        this.doc.put("sequence", postingComment.getSequence());
        this.doc.put("comment", postingComment.getComment());
        this.doc.put("writerEmail", postingComment.getWriterEmail());
        this.doc.put("writtenTime", postingComment.getWrittenTime());
    }
    
    
    public PostingCommentDBObject(BasicDBObject doc) {
        //
        this.doc = doc;
    }
    
    //--------------------------------------------------------------------------
    public BasicDBObject document() {
        //
        return this.doc;
    }

    public DCPostingComment createDomain() {
        //
        DCPostingComment postingComment = createPostingCommentDomain();
        return postingComment;
    }

    public DCPostingComment createPostingCommentDomain() {
        //
        int sequence = (Integer) doc.get("sequence");
        String comment = (String) doc.get("comment");
        String writerEmail = (String) doc.get("writerEmail");
        Date writtenTime = (Date) doc.get("writtenTime");
        
        DCPostingComment postingComment = new DCPostingComment(comment, writerEmail);
        postingComment.setSequence(sequence);
        postingComment.setWrittenTime(writtenTime);
        
        return postingComment;
    }
    
}
