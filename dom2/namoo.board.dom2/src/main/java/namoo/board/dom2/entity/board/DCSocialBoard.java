/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.util.namevalue.NameValue;
import namoo.board.dom2.util.namevalue.NameValueList;

public class DCSocialBoard {
    //
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_COMMENTABLE = "commentable";
            
    private String oid;
    private String usid; 
    private String name;
    private Date createDate;
    private String teamUsid; 
    
    private boolean commentable;

    private List<DCPosting> postings;

    //--------------------------------------------------------------------------
    
    public DCSocialBoard() {
        //
        this.postings = new ArrayList<DCPosting>();
    }
    
    public DCSocialBoard(DCBoardTeam team, String name) {
        //
        this();
        this.name = name; 
        this.teamUsid = team.getUsid(); 
        this.createDate = new Date(System.currentTimeMillis());
        
        this.commentable = true; 
    }

    
    // da.jdbc
    public DCSocialBoard(String usid, String name, Date createDate, String teamUsid, boolean commentable) {
        this();
        this.usid = usid;
        this.name = name;
        this.createDate = createDate;
        this.teamUsid = teamUsid;
        this.commentable = commentable;
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer(); 
        buffer.append("usid: " + this.usid); 
        buffer.append(", name: " + this.name); 
        buffer.append(", createDate: " + this.createDate);
        buffer.append(", teamUsid: " + this.teamUsid);
        buffer.append(", commentable: " + this.commentable);
        
        return buffer.toString(); 
    }
    
    public void setValues(NameValueList nameValues) {
        // 
        for (NameValue nameValue : nameValues.getNameValues()) {
            // 
            if (nameValue.equalsName(PROPERTY_NAME)) {
                setName(nameValue.getValue()); 
            } else if (nameValue.equalsName(PROPERTY_COMMENTABLE)) {
                setCommentable(nameValue.getValueAsBoolean());
            } 
        }
    }
    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public String getTeamUsid() {
        return teamUsid;
    }

    public void setTeamUsid(String teamUsid) {
        this.teamUsid = teamUsid;
    }
    
    public List<DCPosting> getPostings() {
        return postings;
    }
}