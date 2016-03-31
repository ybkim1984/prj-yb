/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */

package namoo.board.dom2.da.mongospring.document;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = BoardTeamDoc.CollectionName)
public class BoardTeamDoc {
	//
	public static final String CollectionName = "boardteam";

	@Id
	private String oid;
	@Indexed
	private String usid;
	private String name;
	private BoardUserDoc admin;

	private List<BoardMemberDoc> members;

	public BoardTeamDoc() {}

	public BoardTeamDoc(DCBoardTeam boardTeam) {
		//
		this.oid = boardTeam.getOid();
		this.usid = boardTeam.getUsid();
		this.name = boardTeam.getName();
		this.admin = new BoardUserDoc(boardTeam.getAdmin());

		this.members = new ArrayList<BoardMemberDoc>();
		for (DCBoardMember dcBoardMember : boardTeam.getMembers()) {
			members.add(new BoardMemberDoc(dcBoardMember));
		}
	}

	public static DCBoardTeam createDomain(BoardTeamDoc boardTeamDoc){
		//
		if(boardTeamDoc == null){
			return null;
		}

		return boardTeamDoc.createDomain();
	}

	private DCBoardTeam createDomain(){
		//
		DCBoardTeam boardTeam = new DCBoardTeam(this.name, BoardUserDoc.createDomain(this.admin));
		boardTeam.setOid(this.oid);
		boardTeam.setUsid(this.usid);
		return boardTeam;
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

	public BoardUserDoc getAdmin() {
		return admin;
	}

	public void setAdmin(BoardUserDoc admin) {
		this.admin = admin;
	}

	public List<BoardMemberDoc> getMembers() {
		return members;
	}

	public void setMembers(List<BoardMemberDoc> members) {
		this.members = members;
	}

	public void addMemberDoc(BoardMemberDoc boardMemberDoc) {
		//
		this.members.add(boardMemberDoc);
	}

	public DCBoardMember createBoardMemberDomainByEmail(String memberEmail) {
		//
		if(this.members.isEmpty() || this.members.size() <= 0){
			return null;
		}

		for (BoardMemberDoc boardMemberDoc : this.members) {
			BoardUserDoc boardUserDoc = boardMemberDoc.getUser();
			if(boardUserDoc == null) continue;

			if(memberEmail.equals(boardUserDoc.getEmail())){
				return BoardMemberDoc.createDomain(boardMemberDoc);
			}
		}

		return null;
	}
}