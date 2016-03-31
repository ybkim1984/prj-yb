/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 15.
 */
package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.mem.BoardTeamMemStore;
import namoo.board.dom2.da.mem.BoardUserMemStore;
import namoo.board.dom2.da.mem.PostingMemStore;
import namoo.board.dom2.da.mem.SocialBoardMemStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

public class BoardStoreMemLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreMemLifecycler instance;
    
    
    private BoardStoreMemLifecycler() {
        // nothing to do..
    }
    
    public static BoardStoreMemLifecycler getInstance() {
        //
        if (instance == null) {
            instance = new BoardStoreMemLifecycler();
        }
        return instance;
    }
    
    //--------------------------------------------------------------------------

    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return new BoardUserMemStore();
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return new BoardTeamMemStore();
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return new SocialBoardMemStore();
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return new PostingMemStore();
    }
    
    
}
