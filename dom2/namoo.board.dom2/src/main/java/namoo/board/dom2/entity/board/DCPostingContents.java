/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.board;



public class DCPostingContents {
    //
    private String oid; 
    private String postingUsid; 
    private String contents;
    

    private DCCommentBag commentBag;

    //--------------------------------------------------------------------------
    
    private DCPostingContents() {
        //
        this.commentBag = new DCCommentBag();
    }
    
    public DCPostingContents(DCPosting posting, String contents) {
        //
        this();
        
        this.postingUsid = posting.getUsid(); 
        this.contents = contents; 
    }

    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("pstingUsid: " + this.postingUsid); 
        buffer.append(", contents: " + this.contents);
        return buffer.toString(); 
    }
    
    public void addComment(DCPostingComment comment) {
        // 
        commentBag.addComment(comment);
    }
    
    public void removeComment(int sequence) {
        // 
        commentBag.removeComment(sequence); 
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

    public DCCommentBag getCommentBag() {
        return commentBag;
    }

    public void setCommentBag(DCCommentBag commentBag) {
        this.commentBag = commentBag;
    }
}