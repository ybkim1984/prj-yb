/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2014. 12. 11.
 */

package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.mybatis.BoardTeamMyBatisStore;
import namoo.board.dom2.da.mybatis.BoardUserMyBatisStore;
import namoo.board.dom2.da.mybatis.PostingMyBatisStore;
import namoo.board.dom2.da.mybatis.SocialBoardMyBatisStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

public class BoardStoreMyBatisLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler lifecycler; 
    
    
    private BoardStoreMyBatisLifecycler() {
        // 
    }

    //--------------------------------------------------------------------------
    
    public static BoardStoreLifecycler getInstance() {
        //
        if(lifecycler == null) {
            lifecycler = new BoardStoreMyBatisLifecycler();
        }
        
        return lifecycler;
    }

    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return new BoardUserMyBatisStore();
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return new BoardTeamMyBatisStore();
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return new SocialBoardMyBatisStore();
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return new PostingMyBatisStore();
    }

}
