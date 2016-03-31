/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate.jpao.board;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_POSTING_COMMENT")
public class PostingCommentJpao implements Serializable {
    //
    private static final long serialVersionUID = 1787712645385531047L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private int sequence;
    
    private String comment;
    
    @ManyToOne
    private PostingContentsJpao postingContents;
    
    @Column(name="writer_Email")
    private String writerEmail;
    
    @Column(name="written_Time")
    private Date writtenTime;
    
    public PostingCommentJpao() {
        
    }
    
//    public PostingCommentJpao(DCPostingComment comment) {
//        //
//        this.postingId = comment.getPostingUsid();
//        this.sequence = comment.getSequence();
//        this.comment = comment.getComment();
//        this.writerEmail = comment.getWriterEmail();
//    }
//    
//    public DCPostingComment createPostingComment() {
//        //
//        DCPostingComment postingComment = new DCPostingComment(comment,writerEmail);
//        postingComment.setPostingUsid(postingId);
//        postingComment.setSequence(sequence);
//        return    postingComment; 
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
