/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.hibernate.BoardTeamHibernateStore;
import namoo.board.dom2.da.hibernate.BoardUserHibernateStore;
import namoo.board.dom2.da.hibernate.PostingHibernateStore;
import namoo.board.dom2.da.hibernate.SocialBoardHibernateStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

public class BoardStoreHibernateLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler lifecycler; 
    
    
    private BoardStoreHibernateLifecycler() {
        // 
    }

    //--------------------------------------------------------------------------    
    public static BoardStoreLifecycler getInstance() {
        //
        if(lifecycler == null) {
            lifecycler = new BoardStoreHibernateLifecycler();
        }
        
        return lifecycler;
    }

    public BoardUserStore callBoardUserStore() {
        // 
        return new BoardUserHibernateStore();
    }

    public BoardTeamStore callBoardTeamStore() {
        // 
        return new BoardTeamHibernateStore();
    }

    public SocialBoardStore callSocialBoardStore() {
        // 
        return new SocialBoardHibernateStore();
    }

    public PostingStore callPostingStore() {
        // 
        return new PostingHibernateStore();
    }

}
