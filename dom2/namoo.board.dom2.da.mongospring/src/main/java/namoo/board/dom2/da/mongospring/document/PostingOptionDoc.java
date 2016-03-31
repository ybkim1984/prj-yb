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

import namoo.board.dom2.entity.board.DCPostingOption;

public class PostingOptionDoc {
	//
    public static final String PROPERTY_COMMENTABLE = "commentable";
    public static final String PROPERTY_ANONYMOUS_COMMENT = "anonymousComment";
    
    private boolean commentable;
    private boolean anonymousComment; 
    
    
    public PostingOptionDoc() {
        //
        this.commentable = true; 
        this.anonymousComment = false; 
    }
    
    public PostingOptionDoc commentable(boolean commentable) {
        //
        this.commentable = commentable; 
        return this; 
    }
    
    public PostingOptionDoc anonymousComment(boolean anonymousComment) {
        //
        this.anonymousComment = anonymousComment; 
        return this; 
    }
    
	public PostingOptionDoc(DCPostingOption postingOption){
		//
		this.anonymousComment = postingOption.isAnonymousComment();
		this.commentable = postingOption.isCommentable();
	}

	public static DCPostingOption createDomain(PostingOptionDoc optionDoc) {
		//
		if (optionDoc == null) {
			return null;
		}

		return optionDoc.createDomain();
	}

	public static List<DCPostingOption> createDomain(List<PostingOptionDoc> optionDocs) {
		//
		if (optionDocs == null) {
			return null;
		}

		List<DCPostingOption> dcPostingOptions = new ArrayList<>();
		for (PostingOptionDoc optionDoc : optionDocs) {
			dcPostingOptions.add(optionDoc.createDomain());
		}
		return dcPostingOptions;
	}

	private DCPostingOption createDomain(){
		//        
		DCPostingOption dcPostingOption = new DCPostingOption();
		dcPostingOption.setAnonymousComment(this.anonymousComment);
		dcPostingOption.setCommentable(this.commentable);
		
		return dcPostingOption;
	}
	
	public boolean isCommentable() {
		return commentable;
	}

	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}

	public boolean isAnonymousComment() {
		return anonymousComment;
	}

	public void setAnonymousComment(boolean anonymousComment) {
		this.anonymousComment = anonymousComment;
	}
}