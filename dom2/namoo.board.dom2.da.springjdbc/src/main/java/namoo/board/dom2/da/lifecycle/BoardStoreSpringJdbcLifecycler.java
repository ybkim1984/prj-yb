/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardStoreSpringJdbcLifecycler implements BoardStoreLifecycler {
    //
    @Autowired
    private BoardUserStore boardUserStore;
    
    @Autowired
    private BoardTeamStore boardTeamStore;
    
    @Autowired
    private SocialBoardStore socialBoardStore;
    
    @Autowired
    private PostingStore postingStore;
    
    //--------------------------------------------------------------------------
    
    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return boardUserStore;
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return boardTeamStore;
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return socialBoardStore;
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return postingStore;
    }

}
