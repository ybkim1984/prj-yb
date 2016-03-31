/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring.document;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingContents;

public class PostingContentsDoc {
	//
    private String oid; 
    private String postingUsid; 
    private String contents;

    private CommentBagDoc commentBag;
    
    public PostingContentsDoc() {}
	
	public PostingContentsDoc(DCPostingContents postingContents){
		//
		this.oid = postingContents.getOid();
		this.contents = postingContents.getContents();
		this.postingUsid = postingContents.getPostingUsid();
		
		if(postingContents.getCommentBag() != null){
			this.commentBag = new CommentBagDoc(postingContents.getCommentBag());
		}
	}
	
	public static DCPostingContents createDomain(PostingContentsDoc postingContents) {
		//
		if (postingContents == null) {
			return null;
		}

		return postingContents.createDomain();
	}

	public static List<DCPostingContents> createDomain(List<PostingContentsDoc> postingContentsDocs) {
		//
		if (postingContentsDocs == null) {
			return null;
		}

		List<DCPostingContents> postingContents = new ArrayList<>();
		for (PostingContentsDoc contents : postingContentsDocs) {
			postingContents.add(contents.createDomain());
		}
		return postingContents;
	}
	
	
	private DCPostingContents createDomain() {
		//
		DCPostingContents postingContents = new DCPostingContents(new DCPosting(this.postingUsid), this.contents);
		postingContents.setOid(this.oid);
		
		postingContents.setCommentBag(CommentBagDoc.createDomain(this.commentBag));
		
		return postingContents;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public CommentBagDoc getCommentBag() {
		return commentBag;
	}

	public void setCommentBag(CommentBagDoc commentBag) {
		this.commentBag = commentBag;
	}
}