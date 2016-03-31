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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingOption;

@Entity
@Table(name="TB_POSTING")
public class PostingJpao implements Serializable {
    //
    private static final long serialVersionUID = -421761262239203414L;
    
    @Id
    private String id;
    
    private String title;
    
    @Column(name="writer_Email")
    private String writerEmail;
    
    @Column(name="writer_Name")
    private String writerName;
    
    @Column(name="read_Count")
    private int readCount;
    
    @Column(name="reg_date")
    private Date regDate;
    
    @ManyToOne
    private SocialBoardJpao board; 
    
    private DCPostingOption option;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="contents_posting_id")
    private PostingContentsJpao contents;
    
    public PostingJpao(){}
    
    public PostingJpao(DCPosting posting) {
        //TODO
        this.title = posting.getTitle();
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

	public SocialBoardJpao getBoard() {
		return board;
	}

	public void setBoard(SocialBoardJpao board) {
		this.board = board;
	}

	public DCPostingOption getOption() {
		return option;
	}

	public void setOption(DCPostingOption option) {
		this.option = option;
	}

	public PostingContentsJpao getContents() {
		return contents;
	}

	public void setContents(PostingContentsJpao contents) {
		this.contents = contents;
	}


}
