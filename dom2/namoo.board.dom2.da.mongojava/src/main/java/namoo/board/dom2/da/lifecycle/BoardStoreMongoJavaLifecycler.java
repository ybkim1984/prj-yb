/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */

package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.mongojava.BoardTeamMongoJavaStore;
import namoo.board.dom2.da.mongojava.BoardUserMongoJavaStore;
import namoo.board.dom2.da.mongojava.PostingMongoJavaStore;
import namoo.board.dom2.da.mongojava.SocialBoardMongoJavaStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

public class BoardStoreMongoJavaLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler lifecycler; 
    
    
    private BoardStoreMongoJavaLifecycler() {
        // 
    }

    //--------------------------------------------------------------------------
    
    public static BoardStoreLifecycler getInstance() {
        //
        if(lifecycler == null) {
            lifecycler = new BoardStoreMongoJavaLifecycler();
        }
        
        return lifecycler;
    }

    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return new BoardUserMongoJavaStore();
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return new BoardTeamMongoJavaStore();
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return new SocialBoardMongoJavaStore();
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return new PostingMongoJavaStore();
    }

}
