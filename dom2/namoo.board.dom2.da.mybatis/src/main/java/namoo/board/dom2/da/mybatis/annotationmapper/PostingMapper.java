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

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.util.page.PageCriteria;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface PostingMapper {
    //
    // posting
    @Insert("INSERT INTO posting (posting_id, board_id, title, writer_email, read_count, reg_date, commentable_yn, anonymous_comment_yn) "
            + "VALUES (#{usid}, #{boardUsid}, #{title}, #{writerEmail}, #{readCount}, #{registerDate}, "
            + "#{option.commentable,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler}, "
            + "#{option.anonymousComment,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler})")
    void insert(DCPosting posting);
    
    @Select(" SELECT posting.posting_id, posting.title, posting.read_count, posting.board_id, posting.reg_date, posting.writer_email, "
            + "user.user_name, posting.commentable_yn, posting.anonymous_comment_yn "
            + "FROM posting JOIN board_user user ON posting.writer_email = user.user_email "
            + "WHERE posting_id = #{usid}")
    @Results(value = {@Result(property="usid", column="posting_id"),
            @Result(property="title", column="title"),
            @Result(property="readCount", column="read_count"),
            @Result(property="boardUsid", column="board_id"),
            @Result(property="registerDate", column="reg_date"),
            @Result(property="writerEmail", column="writer_email"),
            @Result(property="writerName", column="user_name"),
            @Result(property="option.commentable", column="commentable_yn"),
            @Result(property="option.anonymousComment", column="anonymous_comment_yn")})
    DCPosting select(String usid);
    
    @Select("SELECT #{pageCriteria.page} AS page,  #{pageCriteria.itemLimitOfPage} AS itemLimitOfPage, COUNT(posting_id) AS totalItemCount"
            + " FROM posting WHERE board_id = #{boardUsid}")
    PageCriteria selectPageCriteria(@Param("boardUsid")String boardUsid, @Param("pageCriteria")PageCriteria pageCriteria);
    
    @Select("SELECT posting.posting_id, posting.title, posting.read_count, posting.board_id, posting.reg_date,"
            + " posting.writer_email, user.user_name, posting.commentable_yn, posting.anonymous_comment_yn"
            + " FROM posting JOIN board_user user ON posting.writer_email = user.user_email"
            + " WHERE board_id = #{boardUsid}"
            + " ORDER BY posting.reg_date DESC"
            + " LIMIT #{pageCriteria.start}, #{pageCriteria.itemLimitOfPage}")
    @Results(value = {@Result(property="usid", column="posting_id"),
            @Result(property="title", column="title"),
            @Result(property="readCount", column="read_count"),
            @Result(property="boardUsid", column="board_id"),
            @Result(property="registerDate", column="reg_date"),
            @Result(property="writerEmail", column="writer_email"),
            @Result(property="writerName", column="user_name"),
            @Result(property="option.commentable", column="commentable_yn"),
            @Result(property="option.anonymousComment", column="anonymous_comment_yn")})
    List<DCPosting> selectPage(@Param("boardUsid")String boardUsid, @Param("pageCriteria")PageCriteria pageCriteria);
    

    @Select("SELECT posting_id"
            + " FROM posting"
            + " WHERE board_id = #{boardUsid}")
    List<String> selectUsidByBoard(String boardUsid);
    
    @Update("UPDATE posting"
            + " SET title = #{title}, "
            + " commentable_yn = #{option.commentable,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler},"
            + " anonymous_comment_yn = #{option.anonymousComment,typeHandler=namoo.board.dom2.da.mybatis.typehandler.BooleanToCharYnTypeHandler}"
            + " WHERE posting_id = #{usid}")
    void update(DCPosting posting);
    
    @Update("UPDATE posting"
            + " SET read_count = (SELECT read_count + 1 FROM posting WHERE posting_id = #{usid})"
            + " WHERE posting_id = #{usid}")
    void updateReadCount(String usid);
    
    @Delete("DELETE FROM posting WHERE posting_id = #{usid}")
    void delete(String usid);
    
    @Delete("DELETE FROM posting WHERE board_id = #{boardUsid}")
    void deleteByBoard(String boardUsid);
    
    @Insert("INSERT INTO board_seq (seq_name, next_seq) VALUES (#{seqName}, 1)")
    void insertSequence(String seqName);
    
    @Select("SELECT next_seq FROM board_seq WHERE seq_name = #{seqName}")
    Integer selectSequence(String seqName);
    
    @Update(" UPDATE board_seq"
            + " SET next_seq = #{nextSeq} + 1"
            + " WHERE seq_name = #{seqName} ")
    @SelectKey(
            keyProperty = "nextSeq",
            before = true,
            resultType = int.class,
            statement = { "SELECT next_seq FROM board_seq WHERE seq_name = #{seqName}" })
    void nextSequence(Map<String, Object> resultParam);

    // contents
    @Select("SELECT posting_id, contents"
            + " FROM posting "
            + " WHERE posting_id = #{postingUsid}")
    @Results(value = {@Result(property="postingUsid", column="posting_id"),
            @Result(property="contents", column="contents"),
            @Result(property="commentBag.comments", column="posting_id", many=@Many(select="namoo.board.dom2.da.mybatis.annotationmapper.PostingMapper.selectComments"))})
    DCPostingContents selectContents(String postingUsid);
    
    @Select(" SELECT posting_id, sequence, comment, writer_email, written_time"
            + " FROM posting_comment "
            + " WHERE posting_id = #{postingUsid} ")
    @Results(value = {@Result(property="postingUsid", column="posting_id"),
            @Result(property="sequence", column="sequence"),
            @Result(property="comment", column="comment"),
            @Result(property="writerEmail", column="writer_email"),
            @Result(property="writtenTime", column="written_time")})
    List<DCPostingComment> selectComments(@Param("postingUsid") String postingUsid);
    
    @Update(" UPDATE posting SET contents = #{contents} WHERE posting_id = #{postingUsid}")
    void updateContents(DCPostingContents contents);
    
    //
    @Insert("INSERT INTO posting_comment (posting_id, sequence, comment, writer_email, written_time)"
            + " VALUES (#{postingUsid}, #{comment.sequence}, #{comment.comment}, #{comment.writerEmail}, #{comment.writtenTime})")
    void insertComment(@Param("postingUsid") String postingUsid, @Param("comment") DCPostingComment comment);
    
    @Select(" SELECT comment.comment_id, comment.posting_id, comment.sequence, comment.comment, written_time, comment.writer_email, user.user_name"
            + " FROM posting_comment comment JOIN board_user user ON comment.writer_email = user.user_email"
            + " WHERE posting_id = #{postingUsid} AND sequence = #{sequence} ")
    DCPostingComment selectComment(@Param("postingUsid") String postingUsid, @Param("sequence") int sequence);
    
    @Delete("DELETE FROM posting_comment WHERE posting_id = #{postingUsid} AND sequence = #{sequence}")
    void deleteComment(@Param("postingUsid") String postingUsid, @Param("sequence") int sequence);
    
    @Delete("DELETE FROM posting_comment WHERE posting_id = #{postingUsid}")
    void deleteCommentInPosting(String postingUsid);
    
    @Delete("<script>DELETE FROM posting_comment"
            + " WHERE posting_id IN "
            + "     <foreach item='postingUsid' collection='list' open='(' separator=',' close=')'>"
            + "         #{postingUsid}"
            + "     </foreach></script>")
    void deleteCommentByUsids(@Param("list")List<String> postingUsids);
    
    @Update(" UPDATE board_seq"
            + " SET next_seq = ((SELECT next_seq FROM board_seq WHERE seq_name = 'comment_id') + 1)"
            + " WHERE seq_name = 'comment_id' ")
    void nextCommentSequence(Map<String, Integer> resultParam);

}
