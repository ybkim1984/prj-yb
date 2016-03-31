/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.board;

import namoo.board.dom2.util.namevalue.NameValue;
import namoo.board.dom2.util.namevalue.NameValueList;

public class DCPostingOption {
    //
    public static final String PROPERTY_COMMENTABLE = "commentable";
    public static final String PROPERTY_ANONYMOUS_COMMENT = "anonymousComment";
    
    private boolean commentable;
    private boolean anonymousComment; 

    //--------------------------------------------------------------------------
    
    public DCPostingOption() {
        //
        this.commentable = true; 
        this.anonymousComment = false; 
    }
    
    public DCPostingOption commentable(boolean commentable) {
        //
        this.commentable = commentable; 
        return this; 
    }
    
    public DCPostingOption anonymousComment(boolean anonymousComment) {
        //
        this.anonymousComment = anonymousComment; 
        return this; 
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer();
        buffer.append("commentable: " + this.commentable); 
        buffer.append(", anonymousComment: " + this.anonymousComment); 
        return buffer.toString();
    }
    
    public void setValues(NameValueList nameValues) {
        // 
        for (NameValue nameValue : nameValues.getNameValues()) {
            // 
            if (nameValue.equalsName(PROPERTY_COMMENTABLE)) {
                setCommentable(nameValue.getValueAsBoolean()); 
            } else if (nameValue.equalsName(PROPERTY_ANONYMOUS_COMMENT)) {
                setAnonymousComment(nameValue.getValueAsBoolean());
            }
        }
    }
    
    public void validation() {
        //
        if (!this.commentable) {
            this.anonymousComment = false;
        }
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