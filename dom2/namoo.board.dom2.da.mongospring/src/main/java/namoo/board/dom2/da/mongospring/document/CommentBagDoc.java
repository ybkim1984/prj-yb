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

import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPostingComment;

public class CommentBagDoc {
	//
	private List<PostingCommentDoc> comments;
	
	public CommentBagDoc() {}
	
	public CommentBagDoc(DCCommentBag commentBag){
		//
		this.comments = new ArrayList<PostingCommentDoc>();
		for (DCPostingComment dcPostingComment : commentBag.getComments()) {
			comments.add(new PostingCommentDoc(dcPostingComment));
		}
	}
	
	public static DCCommentBag createDomain(CommentBagDoc commentBagDoc) {
		//
		if (commentBagDoc == null) {
			return null;
		}

		return commentBagDoc.createDomain();
	}

	public static List<DCCommentBag> createDomain(List<CommentBagDoc> commentBagDocs) {
		//
		if (commentBagDocs == null) {
			return null;
		}

		List<DCCommentBag> commentBags = new ArrayList<>();
		for (CommentBagDoc commentBagDoc : commentBagDocs) {
			commentBags.add(commentBagDoc.createDomain());
		}
		return commentBags;
	}

	private DCCommentBag createDomain(){
		//
		DCCommentBag dcCommentBag = new DCCommentBag();
		
		for(DCPostingComment comment  : PostingCommentDoc.createDomain(this.comments)){
			dcCommentBag.addComment(comment);
		}
		return dcCommentBag;
	}

	public List<PostingCommentDoc> getComments() {
		return comments;
	}

	public void setComments(List<PostingCommentDoc> comments) {
		this.comments = comments;
	}
}