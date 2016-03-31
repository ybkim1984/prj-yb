/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim Eunyoung</a>
 * @since 2015. 4. 16.
 */
package namoo.board.dom2.pr.ws.service.logic;

import java.util.List;

import namoo.board.dom2.pr.ws.proxy.BoardUserWs;
import namoo.board.dom2.pr.ws.proxy.DcBoardUser;
import namoo.board.dom2.pr.ws.service.SpecialBoardUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpecialBoadUserServiceLogic implements SpecialBoardUserService {
	
	@Autowired
	@Qualifier("BoardUserWs")
	private BoardUserWs boardUserWs;
	//private BoardUserWs boardUserWs = new BoardUserWs_Service().getBoardUserWsLogicPort();

	@Override
	public List<DcBoardUser> findAllSpecialUsers() {
		return boardUserWs.findAllUsers();
	}

}
