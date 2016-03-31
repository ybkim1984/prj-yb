/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim Eunyoung</a>
 * @since 2015. 4. 16.
 */
package namoo.board.dom2.pr.ws.service;

import java.util.List;

import namoo.board.dom2.pr.ws.proxy.DcBoardUser;

public interface SpecialBoardUserService {

	public List<DcBoardUser> findAllSpecialUsers();

}
