/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:bckim@nextree.co.kr">Kim, Byoungchul</a>
 * @since 2015. 1. 20.
 */

package namoo.board.dom2.da.mybatis.annotationmapper;

import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;


public interface BoardTeamMapper {
    //
    // board team
    @Insert("INSERT INTO board_team (team_id, team_name, admin_email, reg_date) "
            + "VALUES (#{usid}, #{name}, #{admin.email}, SYSDATE())")
    void insert(DCBoardTeam team);
    
    @Select("SELECT team.team_id, team.team_name, team.admin_email, user.user_name, user.phone_number "
            + "FROM board_team team"
            + " JOIN board_user user ON team.admin_email = user.user_email "
            + "WHERE team.team_id = #{usid}")
    @Results(value = {@Result(property="usid", column="team_id"),
            @Result(property="name", column="team_name"),
            @Result(property="admin.email", column="admin_email"),
            @Result(property="admin.name", column="user_name"),
            @Result(property="admin.phoneNumber", column="phone_number")})
    DCBoardTeam select(String usid);
    
    @Select("SELECT team.team_id, team.team_name, team.admin_email, user.user_name, user.phone_number"
            + " FROM board_team team"
            + " JOIN board_user user ON team.admin_email = user.user_email "
            + " WHERE team.team_name = #{name}")
    @Results(value = {@Result(property="usid", column="team_id"),
            @Result(property="name", column="team_name"),
            @Result(property="admin.email", column="admin_email"),
            @Result(property="admin.name", column="user_name"),
            @Result(property="admin.phoneNumber", column="phone_number")})
    DCBoardTeam selectByName(String name);
    
    @Select("SELECT team.team_id, team.team_name, team.admin_email, user.user_name, user.phone_number "
            + "FROM board_team team"
            + " JOIN board_user user ON team.admin_email = user.user_email")
    @Results(value = {@Result(property="usid", column="team_id"),
            @Result(property="name", column="team_name"),
            @Result(property="admin.email", column="admin_email"),
            @Result(property="admin.name", column="user_name"),
            @Result(property="admin.phoneNumber", column="phone_number")})
    List<DCBoardTeam> selectAll();
    
    @Delete("DELETE FROM board_team WHERE team_id = #{usid}")
    void delete(String usid);
    
    @Update("UPDATE board_seq "
            + "SET next_seq = (#{nextSeq} + 1) "
            + "WHERE seq_name = 'team_id'")
    @SelectKey(
            keyProperty = "nextSeq",
            before = true,
            resultType = int.class,
            statement = { "SELECT next_seq FROM board_seq WHERE seq_name = 'team_id'" })
    void nextSequence(Map<String, Integer> resultParam);
    

    // team member
    @Insert("INSERT INTO board_member (member_id, team_id, member_email, reg_date) "
            + "VALUES (#{member.usid}, #{teamUsid}, #{member.user.email}, SYSDATE())")
    void insertMember(@Param("teamUsid") String teamUsid, @Param("member") DCBoardMember member);
    
    @Select("SELECT member.member_id,team.team_name,member.member_email, user.user_name, user.phone_number "
            + "FROM board_member member"
            + " JOIN board_team team ON member.team_id = team.team_id"
            + " JOIN board_user user ON member.member_email = user.user_email "
            + "WHERE member.team_id = #{teamUsid} AND member.member_email = #{memberEmail}")
    @Results(value = {@Result(property="usid", column="member_id"),
            @Result(property="teamName", column="team_name"),
            @Result(property="user.email", column="member_email"),
            @Result(property="user.name", column="user_name"),
            @Result(property="user.phoneNumber", column="phone_number")})
    DCBoardMember selectMember(@Param("teamUsid") String teamUsid, @Param("memberEmail") String memberEmail);
    
    @Select("SELECT member.member_id,team.team_name,member.member_email, user.user_name, user.phone_number "
            + "FROM board_member member"
            + " JOIN board_team team ON member.team_id = team.team_id"
            + " JOIN board_user user ON member.member_email = user.user_email "
            + "WHERE member.team_id = #{teamUsid}")
    @Results(value = {@Result(property="usid", column="member_id"),
            @Result(property="teamName", column="team_name"),
            @Result(property="user.email", column="member_email"),
            @Result(property="user.name", column="user_name"),
            @Result(property="user.phoneNumber", column="phone_number")})
    List<DCBoardMember> selectMembers(String teamUsid);
    
    @Delete("DELETE FROM board_member WHERE team_id = #{teamUsid} AND member_email = #{memberEmail}")
    void deleteMember(@Param("teamUsid") String teamUsid, @Param("memberEmail") String memberEmail);
    
    @Delete("DELETE FROM board_member WHERE team_id = #{teamUsid}")
    void deleteMembersByBoard(String boardUsid);
    
}
