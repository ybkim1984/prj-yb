/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.entity.board;

import java.util.Date;

import namoo.board.dom2.entity.user.DCBoardUser;

public class DCPosting {
    //
    private String oid; 
    private String usid; 
    private String title;
    private String writerEmail;
    private String writerName;
    private int readCount;
    private Date registerDate;
    
    private String boardUsid; 
    
    private DCPostingOption option;
    private DCPostingContents contents;
    
    //--------------------------------------------------------------------------

    @SuppressWarnings("unused")
    private DCPosting() {}
    
    public DCPosting(String boardUsid, String title, DCBoardUser user){
        //
        this.title = title; 
        this.writerEmail = user.getEmail(); 
        this.writerName = user.getName();
        this.readCount = 0;
        this.registerDate = new Date(System.currentTimeMillis());
        this.boardUsid = boardUsid; 
        this.option = new DCPostingOption(); 
    }

    // da.jdbc
    public DCPosting(String usid) {
        this.usid = usid;
        this.registerDate = new Date(System.currentTimeMillis());
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        // 
        StringBuffer buffer = new StringBuffer();
        buffer.append("usid: " + this.usid); 
        buffer.append(", title: " + this.title); 
        buffer.append(", writerEmail: " + this.writerEmail); 
        buffer.append(", writerName: " + this.writerName); 
        buffer.append(", readCount: " + this.readCount); 
        buffer.append(", registerDate: " + this.registerDate); 
        buffer.append(", option " + this.option.toString()); 
        return buffer.toString();
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
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }
    
    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getBoardUsid() {
        return boardUsid;
    }

    public void setBoardUsid(String boardUsid) {
        this.boardUsid = boardUsid;
    }
    
    public DCPostingOption getOption() {
        return option;
    }

    public void setOption(DCPostingOption option) {
        this.option = option;
    }

    public DCPostingContents getContents() {
        return contents;
    }

    public void setContents(DCPostingContents contents) {
        this.contents = contents;
    }

    
}