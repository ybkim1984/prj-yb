/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mybatis.mapper;

import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;

import org.apache.ibatis.annotations.Param;


public interface BoardTeamMapper {
    //
    // board team
    void insert(DCBoardTeam team);
    DCBoardTeam select(String usid);
    DCBoardTeam selectByName(String name);
    List<DCBoardTeam> selectAll();
    void delete(String usid);

    void nextSequence(Map<String, Integer> resultParam);

    
    // team member
    void insertMember(@Param("teamUsid") String teamUsid, @Param("member") DCBoardMember member);
    DCBoardMember selectMember(@Param("teamUsid") String teamUsid, @Param("memberEmail") String memberEmail);
    List<DCBoardMember> selectMembers(String teamUsid);
    void deleteMember(@Param("teamUsid") String teamUsid, @Param("memberEmail") String memberEmail);
    void deleteMembersByBoard(String boardUsid);
    
}
