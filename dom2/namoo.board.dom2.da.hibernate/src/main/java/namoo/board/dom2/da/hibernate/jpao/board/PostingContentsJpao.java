/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate.jpao.board;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_POSTING_CONTENTS")
public class PostingContentsJpao implements Serializable {
    //
    private static final long serialVersionUID = 3136168669353417140L;
    
    @Id
    @Column(name="posting_id")
    private String postingUsid;
    
    private String contents;
    
    @OneToMany(mappedBy="postingContents",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    private List<PostingCommentJpao> comments;
    
    public PostingContentsJpao() {}
    
    public PostingContentsJpao(PostingJpao postingJpao) {
        //
        this();
        this.postingUsid = postingJpao.getId();
        this.contents = postingJpao.getContents().getContents();
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
    
//    public DCPostingContents createPostingContents(PostingJpao postingJpao) {
//        return new DCPostingContents();
//    }

}
