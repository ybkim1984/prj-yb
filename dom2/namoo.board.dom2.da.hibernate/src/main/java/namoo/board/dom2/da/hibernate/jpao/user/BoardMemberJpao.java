/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 30.
 */
package namoo.board.dom2.da.hibernate.jpao.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;

@Entity
@Table(name="TB_BOARD_MEMBER")
public class BoardMemberJpao implements Serializable {

    private static final long serialVersionUID = -1572478979864062456L;

    @Id
    @Column(name = "id")
    private String memberId; 
    
    @ManyToOne(optional=false)
    private BoardTeamJpao team;
    
    @OneToOne
    @JoinColumn(name="user_email")
    private BoardUserJpao user;
    
    @Column(name = "reg_date")
    private Date regDate;
    
    public BoardMemberJpao() {
        this.regDate = new Date();
    }
    
    public BoardMemberJpao(String memberId,DCBoardTeam team, DCBoardUser user) {
        //
        this.memberId = memberId;
        this.team = new BoardTeamJpao(team);
        this.user = new BoardUserJpao(user);
    }
    
    public DCBoardMember createBoardMember() {
        //
        return new DCBoardMember(this.team.createBoardTeam(),this.user.createBoardUser());
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public BoardTeamJpao getTeam() {
        return team;
    }

    public void setTeam(BoardTeamJpao team) {
        this.team = team;
    }

    public BoardUserJpao getUser() {
        return user;
    }

    public void setUser(BoardUserJpao user) {
        this.user = user;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
    
}
