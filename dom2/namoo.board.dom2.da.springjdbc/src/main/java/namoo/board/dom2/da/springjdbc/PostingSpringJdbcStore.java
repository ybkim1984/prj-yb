/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.da.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class PostingSpringJdbcStore implements PostingStore {
    //
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    //--------------------------------------------------------------------------

    @Override
    public void create(DCPosting posting) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO posting (posting_id, board_id, title, writer_email, read_count, ")
               .append("                     reg_date, commentable_yn, anonymous_comment_yn) ")
               .append("     VALUES (:postingId, :boardId, :title, :writerEmail, :readCount, ")
               .append("             SYSDATE(), :commentableYn, :annoymousCommentYn)");
        
        DCPostingOption postingOption = posting.getOption();
        String commentable = postingOption.isCommentable() ? "Y" : "N";
        String annonymousComment = postingOption.isAnonymousComment() ? "Y" : "N";
        
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("postingId", posting.getUsid());
        parameterMap.put("boardId", posting.getBoardUsid());
        parameterMap.put("title", posting.getTitle());
        parameterMap.put("writerEmail", posting.getWriterEmail());
        parameterMap.put("readCount", posting.getReadCount());
        parameterMap.put("commentableYn", commentable);
        parameterMap.put("annoymousCommentYn", annonymousComment);
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT user.user_name, user.phone_number,posting.posting_id, ")
               .append("       posting.title, posting.read_count,posting.board_id, posting.reg_date, ")
               .append("       posting.writer_email,posting.commentable_yn, posting.anonymous_comment_yn ")
               .append("  FROM posting posting ")
               .append("  JOIN board_user user ON posting.writer_email = user.user_email ")
               .append(" WHERE posting.posting_id = :postingId ");
    
        try {
            Map<String, String> parameterMap = new HashMap<String, String>();
            parameterMap.put("postingId", usid);
            
            return namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, new PostingRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a posting --> " + usid);
        }
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT user.user_name, user.phone_number,posting.posting_id, ")
               .append("       posting.title, posting.read_count,posting.board_id, posting.reg_date, ")
               .append("       posting.writer_email,posting.commentable_yn, posting.anonymous_comment_yn ")
               .append("  FROM posting ")
               .append("  JOIN board_user user ON posting.writer_email = user.user_email ")
               .append(" WHERE board_id = :boardId ")
               .append(" ORDER BY posting.reg_date DESC ")
               .append(" LIMIT :start, :itemLimitOfPage");
        
        Map<String, Object> parameterMap = new HashMap<String, Object>(3);
        parameterMap.put("boardId", boardUsid);
        parameterMap.put("start", pageCriteria.getStart());
        parameterMap.put("itemLimitOfPage", pageCriteria.getItemLimitOfPage());
        
        List<DCPosting> postings = namedParameterJdbcTemplate.query(builder.toString(), parameterMap, 
                                                                    new PostingRowMapper());
        
        builder = new StringBuilder(); 
        builder.append("SELECT :page AS page, :itemLimitOfPage AS item_limit_of_page, ")
               .append("       COUNT(posting_id) AS total_item_count ")
               .append("  FROM posting ")
               .append(" WHERE board_id = :boardUsid");
        
        parameterMap = new HashMap<String, Object>(3);
        parameterMap.put("page", pageCriteria.getPage());
        parameterMap.put("itemLimitOfPage", pageCriteria.getItemLimitOfPage());
        parameterMap.put("boardUsid", boardUsid);
        
        PageCriteria criteria = namedParameterJdbcTemplate.queryForObject(builder.toString(), 
                                                            parameterMap, new PageCriteriaRowMapper());
        
        return new Page<DCPosting>(postings, criteria);
    }
    

    @Override
    public void update(DCPosting posting) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE posting ")
               .append("   SET title = :title, commentable_yn = :commentableYn,  anonymous_comment_yn = :anonymousCommentYn ")
               .append(" WHERE posting_id = :postingId");

        DCPostingOption postingOption = posting.getOption();
        String commentbaleYn = postingOption.isCommentable() ? "Y" : "N";
        String anonymousCommentYn = postingOption.isAnonymousComment() ? "Y" : "N";
        
        Map<String, String> parameterMap = new HashMap<String, String>(4);
        parameterMap.put("title", posting.getTitle());
        parameterMap.put("commentableYn", commentbaleYn);
        parameterMap.put("anonymousCommentYn", anonymousCommentYn);
        parameterMap.put("postingId", posting.getUsid());
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public void delete(String usid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM posting_comment ")
               .append("      WHERE posting_id = :postingId");
        
        Map<String, String> parameterMap = new HashMap<String, String>(1);
        parameterMap.put("postingId", usid);
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
        
        builder = new StringBuilder();
        builder.append("DELETE FROM posting ")
               .append("      WHERE posting_id = :postingId");
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public void deleteByBoard(String boardUsid) {
        // 
         Map<String, String> parameterMap = new HashMap<String, String>(1);
         parameterMap.put("boardId", boardUsid);
        
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM posting_comment")
               .append("      WHERE posting_id IN (SELECT posting_id")
               .append("                             FROM posting")
               .append("                            WHERE board_id = :boardId)");
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
        
        builder = new StringBuilder();
        builder.append("DELETE FROM posting ")
               .append("      WHERE board_id = :boardId");
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public int nextSequence(String boardUsid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = :sequenceName");
        
        StringBuilder seqNameBuilder = new StringBuilder();
        seqNameBuilder.append(boardUsid).append("-posting_id");
        
        int nextSequence = 0;
        
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("sequenceName", seqNameBuilder.toString());
        
        try {
            nextSequence = namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, Integer.class);
        } catch(EmptyResultDataAccessException e) {
            builder = new StringBuilder();
            builder.append("INSERT INTO board_seq (next_seq, seq_name) ")
                   .append("     VALUES (:nextSequence, :sequenceName)");
            
            nextSequence = 1;
            parameterMap.put("nextSequence", (nextSequence + 1));
            namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
            
            return nextSequence;
        }
        
        builder = new StringBuilder();
        builder.append("UPDATE board_seq ")
               .append("   SET next_seq = :nextSequence ")
               .append(" WHERE seq_name = :sequenceName");
        
        parameterMap.put("nextSequence", (nextSequence + 1));
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
        
        return nextSequence;
    }

    @Override
    public void increaseReadCount(String usid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE posting ")
               .append("   SET read_count = (SELECT read_count + 1 FROM posting WHERE posting_id = :postingId) ")
               .append(" WHERE posting_id = :postingId");
        
        Map<String, String> parameterMap = new HashMap<String, String>(1);
        parameterMap.put("postingId", usid);
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public void createContents(DCPostingContents contents) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE posting ")
               .append("   SET contents = :contents ")
               .append(" WHERE posting_id = :postingUsid");
        
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(contents);
        
        namedParameterJdbcTemplate.update(builder.toString(), sqlParameterSource);
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT posting.posting_id, posting.board_id, ")
               .append("       posting.contents, posting.title, user.user_name, ")
               .append("       user.user_email, user.phone_number ")
               .append("  FROM posting posting ")
               .append("  JOIN board_user user ON posting.writer_email = user.user_email ")
               .append(" WHERE posting.posting_id = :postingId");
        
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("postingId", postingUsid);
        
        DCPostingContents postingContents =  null;
        
        try {
            postingContents = namedParameterJdbcTemplate.queryForObject(builder.toString(), 
                    parameterMap, new PostingContentsRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a postingContents --> " + postingUsid);
        }
        
        builder = new StringBuilder();
        builder.append("SELECT comment.comment_id, comment.posting_id, comment.sequence, ")
               .append("       comment.comment, comment.writer_email, comment.written_time ")
               .append("  FROM posting_comment comment ")
               .append(" WHERE comment.posting_id = :postingId");
        
        List<DCPostingComment> comments = namedParameterJdbcTemplate.query(builder.toString(), parameterMap, new PostingCommentRowMapper());
        
        DCCommentBag commentBag = new DCCommentBag();
        if(comments != null) {
            for(DCPostingComment comment : comments) {
                commentBag.addComment(comment);
            }
        }
        
        postingContents.setCommentBag(commentBag);
        
        return postingContents;
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE posting ")
               .append("   SET contents = :contents ")
               .append(" WHERE posting_id = :postingUsid");
        
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(contents);
        
        namedParameterJdbcTemplate.update(builder.toString(), sqlParameterSource);
    }

    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO posting_comment (posting_id, sequence, comment, writer_email, written_time)")
               .append("     VALUES (:postingId, :sequence, :comment, :writerEmail, :writtenTime)");
        
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("postingId", postingUsid);
        parameterMap.put("sequence", comment.getSequence());
        parameterMap.put("comment", comment.getComment());
        parameterMap.put("writerEmail", comment.getWriterEmail());
        parameterMap.put("writtenTime", comment.getWrittenTime());
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT comment.comment_id, comment.posting_id, comment.sequence, ")
               .append("       comment.comment, written_time, comment.writer_email, user.user_name ")
               .append("  FROM posting_comment comment ")
               .append("  JOIN board_user user ON comment.writer_email = user.user_email ")
               .append(" WHERE posting_id = :postingId AND sequence = :sequence");
        
        Map<String, Object> parameterMap = new HashMap<String, Object>(2);
        parameterMap.put("postingId", postingUsid);
        parameterMap.put("sequence", sequence);
        
        try {
            return namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, 
                                                                    new PostingCommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a postingComment --> postingUsid : " + postingUsid + ", sequence : " + sequence);
        }
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM posting_comment ")
               .append("      WHERE posting_id = :postingId AND sequence = :sequence");
        
        Map<String, Object> parameterMap = new HashMap<String, Object>(2);
        parameterMap.put("postingId", postingUsid);
        parameterMap.put("sequence", sequence);
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
	public boolean isExist(String usid) {
		// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM posting")
    	       .append(" WHERE posting_id = :postingUsid");
    	
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("postingUsid", usid);
    	
    	int count = namedParameterJdbcTemplate.queryForObject(builder.toString(), paramMap, Integer.class);
    	
		return (count > 0) ? true : false;
	}

	@Override
	public int nextCommentSequence(String postingUsid) {
		// 
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT next_seq")
		       .append("  FROM board_seq")
		       .append(" WHERE seq_name = :sequenceName");
		
		
		StringBuilder sequenceName = new StringBuilder();
		sequenceName.append(postingUsid).append("-comment");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sequenceName", sequenceName.toString());
		
		int commentSequence = 0;

		try {
			commentSequence = namedParameterJdbcTemplate.queryForObject(builder.toString(), paramMap, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			builder = new StringBuilder();
			builder.append("INSERT INTO board_seq (seq_name, next_seq)")
			       .append(" VALUES (:sequenceName, 1)");
			
			namedParameterJdbcTemplate.update(builder.toString(), paramMap);
			return (commentSequence + 1);
		}
		
		builder = new StringBuilder();
		builder.append("UPDATE board_seq")
		       .append("   SET next_seq = (:nextSequence)")
		       .append(" WHERE seq_name = :sequenceName");
		
		paramMap.put("nextSequence", commentSequence + 1);
		namedParameterJdbcTemplate.update(builder.toString(), paramMap);
		
		return commentSequence;
	}

	@Override
	public boolean isExistComment(String postingUsid, int sequence) {
		// 
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*)")
		       .append("  FROM posting_comment")
		       .append(" WHERE posting_id = :postingUsid")
		       .append("   AND sequence = :sequence");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		int count = namedParameterJdbcTemplate.queryForObject(builder.toString(), paramMap, Integer.class);
		
		return (count > 0) ? true : false;
	}

	//--------------------------------------------------------------------------

	private static class PostingRowMapper implements RowMapper<DCPosting> {

        @Override
        public DCPosting mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser boardUser = new DCBoardUser(rs.getString("writer_email"),
                                                rs.getString("user_name"), 
                                                rs.getString("phone_number"));
            
            boolean commentable = (rs.getString("commentable_yn").equals("Y")) ? true : false;
            boolean annoymousComment = (rs.getString("anonymous_comment_yn").equals("Y")) ? true : false;
            
            DCPostingOption postionOption = new DCPostingOption();
            postionOption.setCommentable(commentable);
            postionOption.setAnonymousComment(annoymousComment);
            
            DCPosting posting = new DCPosting(rs.getString("board_id"), rs.getString("title"), boardUser);
            posting.setUsid(rs.getString("posting_id"));
            posting.setTitle(rs.getString("title"));
            posting.setReadCount(rs.getInt("read_count"));
            posting.setRegisterDate(rs.getDate("reg_date"));
            posting.setOption(postionOption);
            
            return posting;
        }
    }
    
    private static class PageCriteriaRowMapper implements RowMapper<PageCriteria> {
        //
        @Override
        public PageCriteria mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            PageCriteria pageCriteria = new PageCriteria(rs.getInt("page"), 
                                                         rs.getInt("item_limit_of_page"));
            
            pageCriteria.setTotalItemCount(rs.getInt("total_item_count"));
            
            return pageCriteria;
        }
    }
    
    private static class PostingContentsRowMapper implements RowMapper<DCPostingContents> {
        //
        @Override
        public DCPostingContents mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser boardUser = new DCBoardUser(rs.getString("user_email"),
                                                rs.getString("user_name"), 
                                                rs.getString("phone_number"));
            
            DCPosting posting = new DCPosting(rs.getString("board_id"),
                                          rs.getString("title"), boardUser);
            
            DCPostingContents postingContents = new DCPostingContents(posting, rs.getString("contents"));
            
            return postingContents;
        }
    }
    
    private static class PostingCommentRowMapper implements RowMapper<DCPostingComment> {
        //
        @Override
        public DCPostingComment mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCPostingComment postingComment = new DCPostingComment(rs.getString("comment"),
                                                               rs.getString("writer_email"));
            postingComment.setPostingUsid(rs.getString("posting_id"));
            postingComment.setSequence(rs.getInt("sequence"));
            postingComment.setWrittenTime(rs.getDate("written_time"));
            
            return postingComment;
        }
    }
}
