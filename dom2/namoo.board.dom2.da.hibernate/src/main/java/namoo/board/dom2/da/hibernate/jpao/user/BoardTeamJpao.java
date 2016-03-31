/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 30.
 */
package namoo.board.dom2.da.hibernate.jpao.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import namoo.board.dom2.entity.user.DCBoardTeam;

@Entity
@Table(name="TB_BOARD_TEAM")
public class BoardTeamJpao implements Serializable {
    //    
    private static final long serialVersionUID = 8799803231465833962L;
    
    @Id
    @Column(name="id")    
    private String id; 
    
    @Column(name="name", unique=true)
    private String name;    
    
    @OneToOne
    @JoinColumn(name="admin_email")
    private BoardUserJpao admin;
    
    @Column(name = "reg_date")
    private Date regDate;
    
    @OneToMany(mappedBy="team",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    private List<BoardMemberJpao> boardMembers;
    
    public BoardTeamJpao() {
        //
        this.regDate = new Date();
    }

    public BoardTeamJpao(DCBoardTeam team) {
        // 
        this();
        this.id = team.getUsid();
        this.name = team.getName();
        this.admin = new BoardUserJpao(team.getAdmin());
    }    

    public DCBoardTeam createBoardTeam() {
        //
        DCBoardTeam boardTeam = new DCBoardTeam(name,admin.createBoardUser());
        boardTeam.setUsid(this.id);
        return boardTeam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardUserJpao getAdmin() {
        return admin;
    }

    public void setAdmin(BoardUserJpao admin) {
        this.admin = admin;
    }

    public List<BoardMemberJpao> getBoardMembers() {
        return boardMembers;
    }

    public void setBoardMembers(List<BoardMemberJpao> boardMembers) {
        this.boardMembers = boardMembers;
    }
    
    public void addBoardMembers(BoardMemberJpao boardMemberJpao) {
        if(boardMembers == null) {
            boardMembers = new ArrayList<BoardMemberJpao>();            
        }
        boardMembers.add(boardMemberJpao);
    }
    
    public Date getRegDate() {
        return regDate;
    }
    
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
