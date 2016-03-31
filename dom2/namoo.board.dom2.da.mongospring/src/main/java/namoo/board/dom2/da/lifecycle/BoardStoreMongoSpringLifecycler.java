/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jipark@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.lifecycle;

import namoo.board.dom2.da.mongospring.BoardTeamMongoSpringStore;
import namoo.board.dom2.da.mongospring.BoardUserMongoSpringStore;
import namoo.board.dom2.da.mongospring.PostingMongoSpringStore;
import namoo.board.dom2.da.mongospring.SocialBoardMongoSpringStore;
import namoo.board.dom2.da.mongospring.repository.BoardSeqRepository;
import namoo.board.dom2.da.mongospring.repository.BoardTeamRepository;
import namoo.board.dom2.da.mongospring.repository.BoardUserRepository;
import namoo.board.dom2.da.mongospring.repository.PostingRepository;
import namoo.board.dom2.da.mongospring.repository.SocialBoardRepository;
import namoo.board.dom2.da.mongospring.repository.custom.PostingCustomRepository;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardStoreMongoSpringLifecycler implements BoardStoreLifecycler {
    //
    private static BoardStoreLifecycler lifecycler; 
    
    
    private BoardStoreMongoSpringLifecycler() {
        // 
    }

    //--------------------------------------------------------------------------
    
    public static BoardStoreLifecycler getInstance() {
        //
        if(lifecycler == null) {
            lifecycler = new BoardStoreMongoSpringLifecycler();
        }
        
        return lifecycler;
    }
    
    @Autowired
	private BoardUserRepository boardUserRepository;

    @Autowired
    private BoardTeamRepository boardTeamRepository;
    
    @Autowired
    private BoardSeqRepository boardSeqRepository;
    
    @Autowired
    private SocialBoardRepository socialBoardRepository;
    
    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private PostingCustomRepository postingCustomRepository;
    
	@Override
	public BoardUserStore callBoardUserStore() {
		// 
		return new BoardUserMongoSpringStore(boardUserRepository);
	}

	@Override
	public BoardTeamStore callBoardTeamStore() {
		// 
		return new BoardTeamMongoSpringStore(boardTeamRepository, boardSeqRepository);
	}

	@Override
	public SocialBoardStore callSocialBoardStore() {
		// 
		return new SocialBoardMongoSpringStore(socialBoardRepository, boardSeqRepository);
	}

	@Override
	public PostingStore callPostingStore() {
		// 
		return new PostingMongoSpringStore(postingRepository, postingCustomRepository, boardSeqRepository);
	}

}
