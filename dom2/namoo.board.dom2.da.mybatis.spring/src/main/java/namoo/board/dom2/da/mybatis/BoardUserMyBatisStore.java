/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis;

import java.util.List;

import namoo.board.dom2.da.mybatis.mapper.BoardUserMapper;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardUserMyBatisStore implements BoardUserStore {
    //
    @Autowired
    private BoardUserMapper boardUserMapper;

    @Override
    public void create(DCBoardUser user) {
        // 
        boardUserMapper.insert(user);
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        //
        DCBoardUser boardUser = boardUserMapper.select(email);
        if(boardUser == null) {
            throw new EmptyResultException("No such boardUser -->" + email);
        }
        return boardUser;
    }

    @Override
    public List<DCBoardUser> retrieveAll() {
        // 
        return boardUserMapper.selectAll();
    }
    
    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        // 
        return boardUserMapper.selectByName(name);
    }

    @Override
    public void update(DCBoardUser user) {
        // 
        boardUserMapper.update(user);
    }

    @Override
    public void deleteByEmail(String email) {
        // 
        boardUserMapper.delete(email);
    }

    @Override
    public boolean isExistByEmail(String email) {
        // 
        return boardUserMapper.select(email) != null;
    }
}
