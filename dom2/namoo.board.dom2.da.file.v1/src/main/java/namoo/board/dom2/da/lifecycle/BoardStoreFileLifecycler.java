/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 6.
 */
package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.file.BoardTeamFileStore;
import namoo.board.dom2.da.file.BoardUserFileStore;
import namoo.board.dom2.da.file.PostingFileStore;
import namoo.board.dom2.da.file.SocialBoardFileStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

public class BoardStoreFileLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler instance;
    
    private BoardStoreFileLifecycler() {
        //
    }
    
    public static BoardStoreLifecycler getInstance() {
        //
        if (instance == null) {
            instance = new BoardStoreFileLifecycler();
        }
        return instance;
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return new BoardUserFileStore();
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return new BoardTeamFileStore();
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return new SocialBoardFileStore();
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return new PostingFileStore();
    }

}
