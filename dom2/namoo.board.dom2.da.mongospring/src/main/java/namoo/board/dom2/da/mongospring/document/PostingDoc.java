/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.user.DCBoardUser;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = PostingDoc.CollectionName)
public class PostingDoc {
	//
	public static final String CollectionName = "Posting";
	
	@Id
	private String oid;
	@Indexed
	private String usid;
    private String title;
    private String writerEmail;
    private String writerName;
    private int readCount;
    private Date registerDate;
	
    private String boardUsid;
    
    private PostingOptionDoc optionDoc;
    
    private PostingContentsDoc contentsDoc;
    
	public PostingDoc() {}
	
	public PostingDoc(DCPosting posting){
		//
		this.oid = posting.getOid();
        this.title = posting.getTitle();
        this.usid = posting.getUsid();
        this.boardUsid = posting.getBoardUsid();
        this.writerEmail = posting.getWriterEmail();
        this.writerName =  posting.getWriterName();
        this.readCount = posting.getReadCount();
        this.registerDate = posting.getRegisterDate();
        
        if(posting.getOption() != null){
            this.optionDoc = new PostingOptionDoc(posting.getOption());
        }
        if(posting.getContents() != null){
            this.contentsDoc = new PostingContentsDoc(posting.getContents());
        }

	}
	
	public static DCPosting createDomain(PostingDoc posting) {
		//
		if (posting == null) {
			return null;
		}

		return posting.createDomain();
	}

	public static List<DCPosting> createDomain(List<PostingDoc> postingDocs) {
		//
		if (postingDocs == null) {
			return null;
		}

		List<DCPosting> posting = new ArrayList<>();
		for (PostingDoc contents : postingDocs) {
			posting.add(contents.createDomain());
		}
		return posting;
	}
	
	private DCPosting createDomain() {
		//
		DCPosting posting = new DCPosting(this.boardUsid, this.title, new DCBoardUser(this.writerName, this.writerEmail) );
		posting.setOid(this.oid);
		posting.setUsid(this.usid);
		posting.setTitle(this.title);
		posting.setBoardUsid(this.boardUsid);
		posting.setReadCount(this.readCount);
		posting.setRegisterDate(this.registerDate);
		posting.setWriterName(this.writerName);
		posting.setWriterEmail(this.writerEmail);
		
		posting.setOption(PostingOptionDoc.createDomain(this.optionDoc));
		posting.setContents(PostingContentsDoc.createDomain(this.contentsDoc));
		
		return posting;
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

	public String getBoardusid() {
		return boardUsid;
	}

	public void setBoardusid(String boardusid) {
		this.boardUsid = boardusid;
	}

	public PostingOptionDoc getOptionDoc() {
		return optionDoc;
	}

	public void setOptionDoc(PostingOptionDoc optionDoc) {
		this.optionDoc = optionDoc;
	}

	public PostingContentsDoc getContentsDoc() {
		return contentsDoc;
	}

	public void setContentsDoc(PostingContentsDoc contentsDoc) {
		this.contentsDoc = contentsDoc;
	}
}