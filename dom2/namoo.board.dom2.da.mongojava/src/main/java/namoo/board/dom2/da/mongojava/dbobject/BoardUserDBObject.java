/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.dbobject;

import namoo.board.dom2.entity.user.DCBoardUser;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class BoardUserDBObject {
	//
	private BasicDBObject doc;
	
	//--------------------------------------------------------------------------
	private BoardUserDBObject() {
		//
		this.doc = new BasicDBObject();
	}
		
	public BoardUserDBObject(DCBoardUser boardUser) {
		//
		this();		
		this.doc.put("name", boardUser.getName());
		this.doc.put("email", boardUser.getEmail());
		this.doc.put("phonenumber", boardUser.getPhoneNumber());
	}
	
	public BoardUserDBObject(BasicDBObject doc) {
		//
		this.doc = doc;
	}
	
	//--------------------------------------------------------------------------
	public BasicDBObject document() {
		//
		return this.doc;
	}

	public DCBoardUser createDomain() {
		//
		DCBoardUser boardUser = createBoardUserDomain();
		return boardUser;
	}

	public DCBoardUser createBoardUserDomain() {
		//
		ObjectId oid = (ObjectId) this.doc.get("_id");
		String email = (String)doc.get("email");
		String name = (String)doc.get("name");
		String phoneNumber = (String)doc.get("phonenumber");
		DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUser.setOid(oid.toString());
				
		return boardUser;
	}
}