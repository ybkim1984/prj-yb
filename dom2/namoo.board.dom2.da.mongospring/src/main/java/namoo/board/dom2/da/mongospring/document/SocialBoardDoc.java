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

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = SocialBoardDoc.CollectionName)
public class SocialBoardDoc {
	//
	public static final String CollectionName = "SocialBoard";
	
	@Id
	private String oid;
	@Indexed
	private String name;
	private String usid;
	private String teamUsid;
	private boolean commentable;
	private Date createDate;
	
	
	public SocialBoardDoc() {}
	
	public SocialBoardDoc(DCSocialBoard board){
		//
		this.oid = board.getOid();
		this.name = board.getName(); 
		this.usid = board.getUsid(); 
		this.teamUsid = board.getTeamUsid();
		this.createDate = board.getCreateDate(); 
		this.commentable = board.isCommentable(); 
	}
	
	public static DCSocialBoard createDomain(SocialBoardDoc socialBoardDoc) {
		//
		if (socialBoardDoc == null) {
			return null;
		}
		
		return socialBoardDoc.createDomain();
	}

	public static List<DCSocialBoard> createDomain(List<SocialBoardDoc> socialBoardDocs) {
		//
		if (socialBoardDocs == null) {
			return null;
		}
		
		List<DCSocialBoard> boardDocs = new ArrayList<>();
		for (SocialBoardDoc socialBoardDoc : socialBoardDocs) {
			boardDocs.add(socialBoardDoc.createDomain());
		}
		return boardDocs;
	}
	
	private DCSocialBoard createDomain(){
		//
		DCSocialBoard socialBoard = new DCSocialBoard(this.usid, this.name, this.createDate, this.teamUsid, this.commentable);
		socialBoard.setOid(this.oid);
		
		return socialBoard;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}