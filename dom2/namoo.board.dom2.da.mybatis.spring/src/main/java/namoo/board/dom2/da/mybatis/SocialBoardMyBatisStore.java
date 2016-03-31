/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mybatis.mapper.SocialBoardMapper;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SocialBoardMyBatisStore implements SocialBoardStore {
    //
    @Autowired
    private SocialBoardMapper socialBoardMapper;

    @Override
    public String create(DCSocialBoard board) {
        // 
        socialBoardMapper.insert(board);
        return board.getUsid();
    }

    @Override
    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        // 
        DCSocialBoard socialBoard = socialBoardMapper.select(usid);
        if(socialBoard == null) {
            throw new EmptyResultException("No such socialBoard -->" + usid);
        }
        return socialBoard;
    }

    @Override
    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        //
        DCSocialBoard socialBoard = socialBoardMapper.selectByName(name);
        if(socialBoard == null) {
            throw new EmptyResultException("No such socialBoard -->" + name);
        }
        return socialBoard;
    }
    
    @Override
    public List<DCSocialBoard> retrieveAll() {
        // 
        return socialBoardMapper.selectAll();
    }
    
    @Override
    public void update(DCSocialBoard socialBoard) {
        // 
        socialBoardMapper.update(socialBoard);
    }

    @Override
    public void delete(String usid) {
        // 
        socialBoardMapper.delete(usid);
    }
    
    @Override
    public int nextSequence() {
        //
        Map<String, Integer> resultParam = new HashMap<String, Integer>();
        socialBoardMapper.nextSequence(resultParam);
        return resultParam.get("nextSeq");
    }

    @Override
    public boolean isExist(String usid) {
        // 
        return socialBoardMapper.select(usid) != null;
    }

    @Override
    public boolean isExistByName(String name) {
        // 
        return socialBoardMapper.selectByName(name) != null;
    }

}
