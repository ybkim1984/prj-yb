/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws.logic;

import java.util.List;

import javax.jws.WebService;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.sp.ws.BoardUserWs;
import namoo.board.dom2.util.json.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;

@WebService(endpointInterface = "namoo.board.dom2.sp.ws.BoardUserWs", serviceName = "BoardUserWs", targetNamespace="http://proxy.ws.pr.dom2.board.namoo/")
public class BoardUserWsLogic implements BoardUserWs {
	//
	private BoardUserService boardUserService;

	@Autowired
	public BoardUserWsLogic(BoardStoreLifecycler boardStoreLifecycler) {
		// 생성자의 매개변수는 컨테이너에서 주입
		BoardServiceLifecycler boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(boardStoreLifecycler);
		this.boardUserService = boardServiceLifecycler.getBoardUserService();
	}

	@Override
	public void registerUser(String boardUserJson) {
		DCBoardUser boardUser = (DCBoardUser) JsonUtil.fromJson(boardUserJson, DCBoardUser.class);
		boardUserService.registerUser(boardUser);
	}

	@Override
	public DCBoardUser findUserWithEmail(String userEmail) {
		DCBoardUser boardUser = boardUserService.findUserWithEmail(userEmail);
		return boardUser;
	}

	@Override
	public List<DCBoardUser> findAllUsers() {
		return boardUserService.findAllUsers();
	}

	@Override
	public void removeUserWithEmail(String userEmail) {
		boardUserService.removeUserWithEmail(userEmail);
	}

}
