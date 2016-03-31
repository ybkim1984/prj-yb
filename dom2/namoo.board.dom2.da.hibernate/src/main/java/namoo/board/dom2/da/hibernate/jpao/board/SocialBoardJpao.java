/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate.jpao.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import namoo.board.dom2.entity.board.DCSocialBoard;

@Entity
@Table(name="TB_SOCIAL_BOARD")
public class SocialBoardJpao implements Serializable {

    private static final long serialVersionUID = -2512929786480260471L;
    
    //
    @Id
    @Column(name="board_id")
    private String usid; 
    
    @Column(name="board_name")
    private String name;
    
    @Column(name="create_date")
    private Date createDate;
    
    @Column(name="team_id")
    private String teamUsid; 
    
    @Column(name="commentable_yn")
    private boolean commentable;
    
    @OneToMany
    private List<PostingJpao> postings;
    
    public SocialBoardJpao() {
        //
        this.postings = new ArrayList<PostingJpao>();
    }
    
    public SocialBoardJpao(DCSocialBoard board) {
        //TODO Constructor 패턴을 통해 도메인 객체를 생성해야 하나? Dozer를 사용할까?
    }

    public String getUsid() {
        return usid;
    }


    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTeamUsid() {
        return teamUsid;
    }

    public void setTeamUsid(String teamUsid) {
        this.teamUsid = teamUsid;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public List<PostingJpao> getPostings() {
        return postings;
    }

    public void setPostings(List<PostingJpao> postings) {
        this.postings = postings;
    }
}
