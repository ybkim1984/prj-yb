/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.board.dom2.entity.board.DCPostingComment;

public class PostingCommentDoc {
	//
	private String oid; 
    private String postingUsid;
    private int sequence;         // in DCPosting
    private String comment;
    private String writerEmail;
    private Date writtenTime;
	
	public PostingCommentDoc() {}
	
	public PostingCommentDoc(DCPostingComment postingComment){
		//
		this.oid = postingComment.getOid();
		this.comment = postingComment.getComment();
		this.postingUsid = postingComment.getPostingUsid();
		this.sequence = postingComment.getSequence();
		this.writerEmail = postingComment.getWriterEmail();
		this.writtenTime = postingComment.getWrittenTime();
	}

	public static DCPostingComment createDomain(PostingCommentDoc commentDoc) {
		//
		if (commentDoc == null) {
			return null;
		}
		
		return commentDoc.createDomain();
	}
	
	public static List<DCPostingComment> createDomain(List<PostingCommentDoc> commentDocs) {
		//
		if (commentDocs == null) {
			return null;
		}
		
		List<DCPostingComment> comments = new ArrayList<>();
		for (PostingCommentDoc commentDoc : commentDocs) {
			comments.add(commentDoc.createDomain());
		}
		return comments;
	}
	
	private DCPostingComment createDomain(){
		//
		DCPostingComment comment = new DCPostingComment(this.comment, this.writerEmail);
		comment.setOid(this.oid);
		comment.setSequence(this.sequence);
		
		return comment;
	}
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPostingUsid() {
		return postingUsid;
	}

	public void setPostingUsid(String postingUsid) {
		this.postingUsid = postingUsid;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWriterEmail() {
		return writerEmail;
	}

	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}

	public Date getWrittenTime() {
		return writtenTime;
	}

	public void setWrittenTime(Date writtenTime) {
		this.writtenTime = writtenTime;
	}
}