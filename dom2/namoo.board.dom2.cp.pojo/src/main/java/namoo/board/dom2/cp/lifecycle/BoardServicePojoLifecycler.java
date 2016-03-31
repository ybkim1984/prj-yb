/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2014. 12. 11.
 */

package namoo.board.dom2.cp.lifecycle;

import namoo.board.dom2.cp.pojo.ExcelFileBatchPojoLoaderLogic;
import namoo.board.dom2.cp.pojo.PostingPojoServiceLogic;
import namoo.board.dom2.cp.pojo.SocialBoardPojoServiceLogic;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.logic.BoardUserServiceLogic;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;

public class BoardServicePojoLifecycler implements BoardServiceLifecycler {

    private static BoardServiceLifecycler serviceLifecycler;
    
    private BoardStoreLifecycler storeLifecycler; 
    
    //--------------------------------------------------------------------------
    
    private BoardServicePojoLifecycler(BoardStoreLifecycler storeLifecycler) {
        // 
        this.storeLifecycler = storeLifecycler; 
    }
    
    public static BoardServiceLifecycler getInstance(BoardStoreLifecycler storeLifecycler) {
        //
        if(serviceLifecycler == null) {
            serviceLifecycler = new BoardServicePojoLifecycler(storeLifecycler);
        }
        
        return serviceLifecycler; 
    }

    // -------------------------------------------------------------------------

    
    @Override
    public SocialBoardService getSocialBoardService() {
        // 
        return new SocialBoardPojoServiceLogic(storeLifecycler);
    }

    @Override
    public PostingService getPostingService() {
        //
        return new PostingPojoServiceLogic(storeLifecycler);
    }

    @Override
    public BoardUserService getBoardUserService() {
        // 
        return new BoardUserServiceLogic(storeLifecycler);
    }
    
    @Override
    public ExcelFileBatchLoader getExcelFileBatchLoader() {
        //
        return new ExcelFileBatchPojoLoaderLogic(serviceLifecycler);
    }
    
}
