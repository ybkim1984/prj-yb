/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */
package namoo.board.dom2.da.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mybatis.mapper.SocialBoardMapper;
//import namoo.board.dom2.da.mybatis.annotationmapper.SocialBoardMapper; // FIXME Annotation 방식 Mapper
import namoo.board.dom2.da.mybatis.sessionfactory.MyBatisSessionFactory;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.apache.ibatis.session.SqlSession;

public class SocialBoardMyBatisStore implements SocialBoardStore {

    @Override
    public String create(DCSocialBoard board) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            mapper.insert(board);
        
            return board.getUsid();
            
        } finally {
            session.close();
        }
    }

    @Override
    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            
            DCSocialBoard board = mapper.select(usid); 
            if (board == null) {
                throw new EmptyResultException("No such a board --> " + usid);
            }
            return board;
            
        } finally {
            session.close();
        }
    }

    @Override
    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            
            DCSocialBoard board = mapper.selectByName(name);
            if (board == null) {
                throw new EmptyResultException("No such a board --> " + name);
            }
            return board;
            
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<DCSocialBoard> retrieveAll() {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            return mapper.selectAll();
            
        } finally {
            session.close();
        }
    }
    
    @Override
    public void update(DCSocialBoard socialBoard) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            mapper.update(socialBoard);
            
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(String usid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            mapper.delete(usid);
            
        } finally {
            session.close();
        }
    }
    
    @Override
    public int nextSequence() {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            Map<String, Integer> resultParam = new HashMap<String, Integer>();
            
            mapper.nextSequence(resultParam);

            return resultParam.get("nextSeq");
            
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExist(String usid) {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            return mapper.select(usid) != null;
            
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExistByName(String name) {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            SocialBoardMapper mapper = session.getMapper(SocialBoardMapper.class);
            return mapper.selectByName(name) != null;
            
        } finally {
            session.close();
        }
    }

}