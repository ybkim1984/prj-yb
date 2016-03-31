package util;

import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.da.lifecycle.BoardStoreMongoJavaLifecycler;

public class BoardServiceFactory {
	//
	private static BoardServiceFactory instance;
	
	private BoardStoreLifecycler BoardStoreLifecycler;
	private BoardServiceLifecycler boardServiceLifecycler;
	
	private BoardServiceFactory() {
		//
		BoardStoreLifecycler = BoardStoreMongoJavaLifecycler.getInstance();
		boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(BoardStoreLifecycler);
	}
	
	public static BoardServiceFactory getInstance() {
		//
		if(instance  == null) {
			instance = new BoardServiceFactory();
		}
		return instance;
	}
	//--------------------------------------------------------------------------
	public BoardUserService getBoardUserService() {
		//
		return boardServiceLifecycler.getBoardUserService();
	}
	
	public ExcelFileBatchLoader getExcelFileBatchLoader() {
		//
		return boardServiceLifecycler.getExcelFileBatchLoader();
	}
	
	public PostingService getPostingService() {
		//
		return boardServiceLifecycler.getPostingService();
	}
	
	public SocialBoardService getSocialBoardService() {
		//
		return boardServiceLifecycler.getSocialBoardService();
	}

}
