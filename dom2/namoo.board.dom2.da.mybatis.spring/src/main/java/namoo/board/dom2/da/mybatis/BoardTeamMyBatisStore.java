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

import namoo.board.dom2.da.mybatis.mapper.BoardTeamMapper;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardTeamMyBatisStore implements BoardTeamStore {
    //
    @Autowired
    private BoardTeamMapper boardTeamMapper;

    @Override
    public void create(DCBoardTeam team) {
        // 
        boardTeamMapper.insert(team);
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        // 
        DCBoardTeam boardTeam = boardTeamMapper.select(usid);
        if(boardTeam == null) {
            throw new EmptyResultException("No such boardTeam -->" + usid);
        }
        return boardTeam;
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        //
        DCBoardTeam boardTeam = boardTeamMapper.selectByName(name);
        if(boardTeam == null) {
            throw new EmptyResultException("No such boardName -->" + name);
        }
        return boardTeam;
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        //
        return boardTeamMapper.selectAll();
    }

    @Override
    public void delete(String usid) {
        // 
        boardTeamMapper.delete(usid);
        boardTeamMapper.deleteMembersByBoard(usid);
    }

    @Override
    public int nextSequence() {
        // 
        Map<String, Integer> resultParam = new HashMap<String, Integer>();
        boardTeamMapper.nextSequence(resultParam);
        
        return resultParam.get("nextSeq");
    }

    @Override
    public boolean isExist(String usid) {
        // 
        return boardTeamMapper.select(usid) != null;
    }

    @Override
    public boolean isExistByName(String name) {
        // 
        return boardTeamMapper.selectByName(name) != null;
    }
    
    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        // 
        boardTeamMapper.insertMember(teamUsid, member);
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        // 
        DCBoardMember boardMember = boardTeamMapper.selectMember(teamUsid, memberEmail);
        if(boardMember == null) {
            throw new EmptyResultException("No such boardMember -->" + memberEmail);
        }
        return boardMember;
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        // 
        return boardTeamMapper.selectMembers(teamUsid);
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        // 
        boardTeamMapper.deleteMember(teamUsid, memberEmail);
    }


    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
        // 
        return boardTeamMapper.selectMember(teamUsid, memberEmail) != null;
    }
}
