/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.board.sp.jersey.common;

import java.io.File;
import java.net.URL;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;
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
		
		ExcelFileBatchLoader excelLoader = boardServiceLifecycler.getExcelFileBatchLoader();
		
    	URL path = this.getClass().getResource("/BoardUsers.xlsx");
		boolean result = excelLoader.registerServiceUsers(new File(path.getPath()));
		
		System.out.println("Excel Loader was executed. --> " + result);
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
