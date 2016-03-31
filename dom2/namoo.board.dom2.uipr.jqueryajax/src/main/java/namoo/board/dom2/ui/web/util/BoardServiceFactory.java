/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:yongjunlee@nextree.co.kr">Lee, Yongjun</a>
 * @since 2015. 1. 23.
 */
package namoo.board.dom2.ui.web.util;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;

public class BoardServiceFactory {
	//
	private static BoardServiceFactory instance;
	
	private BoardStoreLifecycler boardStoreLifecycler;
	private BoardServiceLifecycler boardServiceLifecycler;

	private BoardServiceFactory() {
		//	
		boardStoreLifecycler = BoardStoreMyBatisLifecycler.getInstance();
		boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(boardStoreLifecycler);
	}
	
	public static BoardServiceFactory getInstance() {
		//
		if (instance == null) {
			instance = new BoardServiceFactory();
		}
		return instance;
	}
	
	//--------------------------------------------------------------------------
		
	public SocialBoardService createSocialBoardService() {
		//
		return boardServiceLifecycler.getSocialBoardService();
	}
	
	public BoardUserService createBoardUserService() {
		//
		return boardServiceLifecycler.getBoardUserService();
	}
	
	public PostingService createPostingService() {
		//
		return boardServiceLifecycler.getPostingService();
	}
	
}
