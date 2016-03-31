/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import namoo.board.dom2.entity.user.DCBoardUser;

@WebService(targetNamespace="http://proxy.ws.pr.dom2.board.namoo/")
public interface BoardUserWs {
	//
	public void registerUser(@WebParam(name = "boardUserJson") String boardUser);
	public DCBoardUser findUserWithEmail(@WebParam(name = "userEmail") String userEmail);
	public List<DCBoardUser> findAllUsers();
	public void removeUserWithEmail(@WebParam(name = "userEmail") String userEmail);
}
