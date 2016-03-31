/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:bckim@nextree.co.kr">Kim, Byoungchul</a>
 * @since 2015. 1. 20.
 */

package namoo.board.dom2.da.mybatis.annotationmapper;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardUser;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BoardUserMapper {
    
    @Insert("INSERT INTO board_user (user_email, user_name, phone_number, reg_date) "
            + "VALUES (#{email}, #{name}, #{phoneNumber}, SYSDATE())")
    void insert(DCBoardUser user);
    
    @Select("SELECT user_email email , user_name name, phone_number phoneNumber"
            + " FROM board_user"
            + " WHERE user_email = #{email}")
    DCBoardUser select(String email);
    
    @Select("SELECT user_email email, user_name name, phone_number phoneNumber FROM board_user")
    List<DCBoardUser> selectAll();
    
    @Select("SELECT user_email email, user_name name, phone_number phoneNumber"
            + " FROM board_user"
            + " WHERE user_name = #{name}")
    List<DCBoardUser> selectByName(String name);
    
    @Update(" UPDATE board_user"
            + " SET phone_number = #{phoneNumber}"
            + " WHERE user_email = #{email}")
    void update(DCBoardUser user);
    
    @Delete("DELETE FROM board_user WHERE user_email = #{email}")
    void delete(String email);
    
}
