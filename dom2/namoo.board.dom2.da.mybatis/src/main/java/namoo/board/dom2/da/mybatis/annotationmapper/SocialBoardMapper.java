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

import namoo.board.dom2.entity.board.DCSocialBoard;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface SocialBoardMapper {
    
    @Insert("INSERT INTO social_board (board_id, board_name, commentable_yn, team_id, create_date)"
            + "VALUES (#{usid}, #{name}, #{commentable,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler}, #{teamUsid}, SYSDATE())")
    void insert(DCSocialBoard board);
    
    @Select("SELECT  board_id, board_name, commentable_yn, team_id, create_date"
            + " FROM social_board"
            + " WHERE board_id = #{usid}")
    @Results(value = {@Result(property="usid", column="board_id"),
            @Result(property="name", column="board_name"),
            @Result(property="commentable", column="commentable_yn"),
            @Result(property="teamUsid", column="team_id"),
            @Result(property="createDate", column="create_date")})
    DCSocialBoard select(String usid);
    
    @Select("SELECT  board_id, board_name, commentable_yn, team_id, create_date"
            + " FROM social_board"
            + " WHERE board_name = #{boardName}")
    @Results(value = {@Result(property="usid", column="board_id"),
            @Result(property="name", column="board_name"),
            @Result(property="commentable", column="commentable_yn"),
            @Result(property="teamUsid", column="team_id"),
            @Result(property="createDate", column="create_date")})
    DCSocialBoard selectByName(String boardName);
    
    @Select("SELECT  board_id, board_name, commentable_yn, team_id, create_date FROM social_board")
    @Results(value = {@Result(property="usid", column="board_id"),
            @Result(property="name", column="board_name"),
            @Result(property="commentable", column="commentable_yn"),
            @Result(property="teamUsid", column="team_id"),
            @Result(property="createDate", column="create_date")})
    List<DCSocialBoard> selectAll();
    
    @Update("UPDATE social_board"
            + " SET board_name = #{name}, commentable_yn = #{commentable,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler}"
            + " WHERE board_id = #{usid}")
    void update(DCSocialBoard board);
    
    @Delete("DELETE FROM social_board WHERE board_id = #{usid}")
    void delete(String usid);

    @Update("UPDATE board_seq"
            + " SET next_seq = (#{nextSeq} + 1)"
            + " WHERE seq_name = 'board_id'")
    @SelectKey(
            keyProperty = "nextSeq",
            before = true,
            resultType = int.class,
            statement = { "SELECT next_seq FROM board_seq WHERE seq_name = 'board_id'" })
    void nextSequence(Map<String, Integer> resultParam);
    
    
}
