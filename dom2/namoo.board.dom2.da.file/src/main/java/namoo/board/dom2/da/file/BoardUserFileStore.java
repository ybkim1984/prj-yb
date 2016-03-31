/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 6.
 */
package namoo.board.dom2.da.file;

import java.util.List;

import namoo.board.dom2.da.file.repository.BoardUserRepository;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

public class BoardUserFileStore implements BoardUserStore {
    //
    private BoardUserRepository store = new BoardUserRepository();
    
    //--------------------------------------------------------------------------
    
    @Override
    public void create(DCBoardUser user) {
        //
        store.insert(user);
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        // 
        DCBoardUser user = store.select(email);
        if (user == null) {
            throw new EmptyResultException("No such a user --> " + email);
        }
        return user;
    }

    @Override
    public List<DCBoardUser> retrieveAll() {
        // 
        return store.selectAll();
    }

    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        // 
        return store.selectListByName(name);
    }

    @Override
    public void update(DCBoardUser user) {
        // 
        store.update(user);
    }

    @Override
    public void deleteByEmail(String email) {
        // 
        store.delete(email);
    }

    @Override
    public boolean isExistByEmail(String email) {
        // 
        DCBoardUser user = store.select(email);
        return user != null;
    }
    
}
