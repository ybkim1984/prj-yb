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

import namoo.board.dom2.entity.user.DCBoardUser;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = BoardUserDoc.CollectionName)
public class BoardUserDoc {
	//
	public static final String CollectionName = "boardusers";
	
	@Id
	private String oid;
	@Indexed
	private String email;
	private String name;
	private String phoneNumber;
	
	public BoardUserDoc() {}
	
	public BoardUserDoc(DCBoardUser user){
		//
		this.oid = user.getOid();
		this.email = user.getEmail(); 
		this.name = user.getName(); 
		this.phoneNumber = user.getPhoneNumber(); 
	}
	
	public static DCBoardUser createDomain(BoardUserDoc userDoc) {
		//
		if (userDoc == null) {
			return null;
		}
		
		return userDoc.createDomain();
	}
	
	public static List<DCBoardUser> createDomain(List<BoardUserDoc> userDocs) {
		//
		if (userDocs == null) {
			return null;
		}
		
		List<DCBoardUser> users = new ArrayList<>();
		for (BoardUserDoc userDoc : userDocs) {
			users.add(userDoc.createDomain());
		}
		return users;
	}
	
	private DCBoardUser createDomain(){
		//
		DCBoardUser user = new DCBoardUser(this.email, this.name, this.phoneNumber);
		user.setOid(this.oid);
		
		return user;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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