 /*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis.mapper;

import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.board.DCSocialBoard;

public interface SocialBoardMapper {
    //
    void insert(DCSocialBoard board);
    DCSocialBoard select(String usid);
    DCSocialBoard selectByName(String boardName);
    List<DCSocialBoard> selectAll();
    void update(DCSocialBoard board);
    void delete(String usid);
    
    void nextSequence(Map<String, Integer> resultParam);
}
