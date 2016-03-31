/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mybatis.mapper;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardUser;

public interface BoardUserMapper {
    //
    void insert(DCBoardUser user);
    DCBoardUser select(String email);
    List<DCBoardUser> selectAll();
    List<DCBoardUser> selectByName(String name);
    void update(DCBoardUser user);
    void delete(String email);
    
}
