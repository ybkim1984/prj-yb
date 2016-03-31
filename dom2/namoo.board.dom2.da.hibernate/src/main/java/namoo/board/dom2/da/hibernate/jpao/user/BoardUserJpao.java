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
import javax.persistence.Table;

import namoo.board.dom2.entity.user.DCBoardUser;

@Entity
@Table(name="TB_BOARD_USER")
public class BoardUserJpao implements Serializable {

    private static final long serialVersionUID = 2441353495732598807L;
    
    @Id
    @Column(name="email")
    private String email;
    
    @Column(name="name")
    private String name;
    
    @Column(name="phone_number")
    private String phoneNumber;
    
    @Column(name = "reg_date")
    private Date regDate;
    
    public BoardUserJpao() {
        //
        this.regDate = new Date();
    }
    
    public BoardUserJpao(DCBoardUser user) {
        //
        this();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();        
    }
    
    public DCBoardUser createBoardUser() {
        //
        return new DCBoardUser(email,name,phoneNumber);
    }
    
    public void copyAttrs(DCBoardUser user) {
        //
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
    }    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
