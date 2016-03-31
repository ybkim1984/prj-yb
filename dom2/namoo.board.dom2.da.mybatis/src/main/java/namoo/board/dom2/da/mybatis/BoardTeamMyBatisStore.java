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

import namoo.board.dom2.da.mybatis.mapper.BoardTeamMapper;
// import namoo.board.dom2.da.mybatis.annotationmapper.BoardTeamMapper; // FIXME Annotation 방식 Mapper
import namoo.board.dom2.da.mybatis.sessionfactory.MyBatisSessionFactory;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.apache.ibatis.session.SqlSession;

public class BoardTeamMyBatisStore implements BoardTeamStore {
    //
    
    //--------------------------------------------------------------------------
    // team
    
    @Override
    public void create(DCBoardTeam team) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            mapper.insert(team);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            
            DCBoardTeam team = mapper.select(usid); 
            if (team == null) {
                throw new EmptyResultException("No such a team --> " + usid);
            }
            return team;
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            
            DCBoardTeam team = mapper.selectByName(name); 
            if (team == null) {
                throw new EmptyResultException("No such a team --> " + name);
            }
            return team;
        
        } finally {
            session.close();
        }
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        //
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            return mapper.selectAll();
        
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(String usid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            mapper.delete(usid);
            mapper.deleteMembersByBoard(usid);
        
        } finally {
            session.close();
        }
    }

    @Override
    public int nextSequence() {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            
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
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
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
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            return mapper.selectByName(name) != null;
        
        } finally {
            session.close();
        }
    }
    
    
    //--------------------------------------------------------------------------
    // member
    
    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            mapper.insertMember(teamUsid, member);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            
            DCBoardMember member = mapper.selectMember(teamUsid, memberEmail); 
            if (member == null) {
                throw new EmptyResultException("No such a member --> " + member);
            }
            return member;
        
        } finally {
            session.close();
        }
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            return mapper.selectMembers(teamUsid);
        
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            mapper.deleteMember(teamUsid, memberEmail);
        
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            BoardTeamMapper mapper = session.getMapper(BoardTeamMapper.class);
            return mapper.selectMember(teamUsid, memberEmail) != null;
        
        } finally {
            session.close();
        }
    }
    
    
}
