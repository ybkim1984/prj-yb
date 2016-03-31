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

public class BoardMemberDoc {

	private String oid;
	private String usid;
	private String teamName;
	private BoardUserDoc user;

	public BoardMemberDoc() {}

	public BoardMemberDoc(DCBoardMember member) {
		this.oid = member.getOid();
		this.usid = member.getUsid();
		this.teamName = member.getTeamName();
		this.user = new BoardUserDoc(member.getUser());
	}

	public static DCBoardMember createDomain(BoardMemberDoc boardMemberDoc) {
		//
		if (boardMemberDoc == null) {
			return null;
		}

		return boardMemberDoc.createDomain();
	}

	public static List<DCBoardMember> createDomain(List<BoardMemberDoc> boardMemberDocs) {
		//
		if (boardMemberDocs == null) {
			return null;
		}

		List<DCBoardMember> dcBoardMembers = new ArrayList<>();
		for (BoardMemberDoc memberDoc : boardMemberDocs) {
			dcBoardMembers.add(memberDoc.createDomain());
		}
		return dcBoardMembers;
	}

	private DCBoardMember createDomain(){
		//
		DCBoardMember dcBoardMember = new DCBoardMember(this.usid);
		dcBoardMember.setOid(this.oid);
		dcBoardMember.setTeamName(this.teamName);
		dcBoardMember.setUser(BoardUserDoc.createDomain(this.user));

		return dcBoardMember;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public BoardUserDoc getUser() {
		return user;
	}

	public void setUser(BoardUserDoc user) {
		this.user = user;
	}

}
