/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mybatis;

import java.util.List;

import namoo.board.dom2.da.mybatis.mapper.BoardUserMapper;
//import namoo.board.dom2.s.mybatis.annotationmapper.BoardUserMapper; //FIXME Annotation 방식 Mapper
import namoo.board.dom2.da.mybatis.sessionfactory.MyBatisSessionFactory;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.apache.ibatis.session.SqlSession;

public class BoardUserMyBatisStore implements BoardUserStore {

    @Override
    public void create(DCBoardUser user) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            mapper.insert(user);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            
            DCBoardUser user = mapper.select(email); 
            if (user == null) {
                throw new EmptyResultException("No such a user --> " + email);
            }
            return user;
        
        } finally {
            session.close();
        }
    }

    @Override
    public List<DCBoardUser> retrieveAll() {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            return mapper.selectAll();
        
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            return mapper.selectByName(name);
        
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DCBoardUser user) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            mapper.update(user);
        
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteByEmail(String email) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            mapper.delete(email);
        
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExistByEmail(String email) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardUserMapper mapper = session.getMapper(BoardUserMapper.class);
            return mapper.select(email) != null;
        
        } finally {
            session.close();
        }
    }

    

}
