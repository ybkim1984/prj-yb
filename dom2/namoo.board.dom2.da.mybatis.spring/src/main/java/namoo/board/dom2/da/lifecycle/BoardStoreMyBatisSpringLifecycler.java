/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
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

import org.springframework.beans.factory.annotation.Autowired;


public class BoardStoreMyBatisSpringLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler lifecycler; 
    
    @Autowired
    private BoardUserMyBatisStore boardUserMyBatisStore;
    
    @Autowired
    private BoardTeamMyBatisStore boardTeamMyBatisStore;
    
    @Autowired
    private SocialBoardMyBatisStore socialBoardMyBatisStore;
    
    @Autowired
    private PostingMyBatisStore postingMyBatisStore;

    //--------------------------------------------------------------------------

    public static BoardStoreLifecycler getInstance() {
        //
        if(lifecycler == null) {
            lifecycler = new BoardStoreMyBatisSpringLifecycler();
        }
        
        return lifecycler;
    }

    @Override
    public BoardUserStore callBoardUserStore() {
        // 
        return boardUserMyBatisStore;
    }

    @Override
    public BoardTeamStore callBoardTeamStore() {
        // 
        return boardTeamMyBatisStore;
    }

    @Override
    public SocialBoardStore callSocialBoardStore() {
        // 
        return socialBoardMyBatisStore;
    }

    @Override
    public PostingStore callPostingStore() {
        // 
        return postingMyBatisStore;
    }

}
