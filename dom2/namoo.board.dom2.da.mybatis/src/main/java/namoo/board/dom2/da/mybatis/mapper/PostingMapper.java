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

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.util.page.PageCriteria;

import org.apache.ibatis.annotations.Param;

public interface PostingMapper {
    //
    // posting
    void insert(DCPosting posting);
    DCPosting select(String usid);
    PageCriteria selectPageCriteria(@Param("boardUsid")String boardUsid, @Param("pageCriteria")PageCriteria pageCriteria);
    List<DCPosting> selectPage(@Param("boardUsid")String boardUsid, @Param("pageCriteria")PageCriteria pageCriteria);
    List<String> selectUsidByBoard(String boardUsid);
    void update(DCPosting posting);
    void updateReadCount(String usid);
    void delete(String usid);
    void deleteByBoard(String boardUsid);
    
    void insertSequence(String seqName);
    Integer selectSequence(String seqName);
    void nextSequence(Map<String, Object> resultParam);
    
    // contents
    DCPostingContents selectContents(String postingUsid);
    void updateContents(DCPostingContents contents);
    
    //
    void insertComment(@Param("postingUsid") String postingUsid, @Param("comment") DCPostingComment comment);
    DCPostingComment selectComment(@Param("postingUsid") String postingUsid, @Param("sequence") int sequence);
    void deleteComment(@Param("postingUsid") String postingUsid, @Param("sequence") int sequence);
    void deleteCommentInPosting(String postingUsid);
    void deleteCommentByUsids(List<String> postingUsids);
    
    void nextCommentSequence(Map<String, Integer> resultParam);
}
