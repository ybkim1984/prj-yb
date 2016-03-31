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

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SocialBoardSpringJdbcStore implements SocialBoardStore {
    //
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    // -------------------------------------------------------------------------
    
    @Override
    public String create(DCSocialBoard board) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO social_board (board_id, board_name, commentable_yn, team_id, create_date) ")
               .append("     VALUES (:boardId, :boardName, :commentableYn, :teamId, SYSDATE())");
        
        String commentableYn = (board.isCommentable()) ? "Y" : "N";
        
        Map<String, String> parameterMap = new HashMap<String, String>(4);
        parameterMap.put("boardId", board.getUsid());
        parameterMap.put("boardName", board.getName());
        parameterMap.put("commentableYn", commentableYn);
        parameterMap.put("teamId", board.getTeamUsid());
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
        
        return board.getUsid();
    }

    @Override
    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT board.board_id, board.board_name, board.commentable_yn, ")
               .append("       board.team_id, board.create_date, team.team_name, ")
               .append("       admin.user_email, admin.user_name, admin.phone_number ")
               .append("  FROM social_board board ")
               .append("  JOIN board_team team ON board.team_id = team.team_id ")
               .append("  JOIN board_user admin ON team.admin_email = admin.user_email ")
               .append(" WHERE board_id = :boardId");
        
        try {
            Map<String, String> parameterMap = new HashMap<String, String>(1);
            parameterMap.put("boardId", usid);
            return namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, new SocialBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a socialBoard --> " + usid);
        }
    }

    @Override
    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT board.board_id, board.board_name, board.commentable_yn, ")
               .append("       board.team_id, board.create_date, team.team_name, ")
               .append("       admin.user_email, admin.user_name, admin.phone_number ")
               .append("  FROM social_board board ")
               .append("  JOIN board_team team ON board.team_id = team.team_id ")
               .append("  JOIN board_user admin ON team.admin_email = admin.user_email ")
               .append(" WHERE board_name = :boardName");
        
        try {
            Map<String, String> parameterMap = new HashMap<String,String>(1);
            parameterMap.put("boardName", name);
            
            return namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, new SocialBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a socialBoard --> " + name);
        }
    }

    @Override
    public List<DCSocialBoard> retrieveAll() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT board.board_id, board.board_name, board.commentable_yn, ")
               .append("       board.team_id, board.create_date, team.team_name, ")
               .append("       admin.user_email, admin.user_name, admin.phone_number ")
               .append("  FROM social_board board ")
               .append("  JOIN board_team team ON board.team_id = team.team_id ")
               .append("  JOIN board_user admin ON team.admin_email = admin.user_email ");

        return namedParameterJdbcTemplate.query(builder.toString(), new SocialBoardRowMapper());
    }

    @Override
    public void update(DCSocialBoard board) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE social_board ")
               .append("   SET board_name = :boardName, commentable_yn = :commentableYn ")
               .append(" WHERE board_id = :boardId");
        
        String commentableYn = board.isCommentable() ? "Y" : "N";
        
        Map<String, String> parameterMap = new HashMap<String, String>(3);
        parameterMap.put("boardName", board.getName());
        parameterMap.put("commentableYn", commentableYn);
        parameterMap.put("boardId", board.getUsid());
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public void delete(String usid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM social_board ")
               .append(" WHERE board_id = :boardId");
        
        Map<String, String> parameterMap = new HashMap<String, String>(1);
        parameterMap.put("boardId", usid);
        
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
    }

    @Override
    public int nextSequence() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = 'board_id'");
        
        Map<String, Integer> parameterMap = new HashMap<String, Integer>(1);
        
        int nextSequence = namedParameterJdbcTemplate.queryForObject(builder.toString(), parameterMap, Integer.class);
        
        builder = new StringBuilder();
        builder.append("UPDATE board_seq ")
               .append("   SET next_seq = :nextSequence")
               .append(" WHERE seq_name = 'board_id'");
        
        parameterMap.put("nextSequence", (nextSequence + 1));
        namedParameterJdbcTemplate.update(builder.toString(), parameterMap);
        
        return nextSequence;
    }
    
    @Override
    public boolean isExist(String usid) {
    	// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM social_board")
    	       .append(" WHERE board_id = :boardUsid");
    	
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("boardUsid", usid);
    	
    	int count = namedParameterJdbcTemplate.queryForObject(builder.toString(), paramMap, Integer.class);
    	
    	return (count > 0) ? true : false;
    }
    
    @Override
    public boolean isExistByName(String name) {
    	// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM social_board")
    	       .append(" WHERE board_name = :boardName");
    	
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("boardName", name);
    	
    	int count = namedParameterJdbcTemplate.queryForObject(builder.toString(), paramMap, Integer.class);
    	
    	return (count > 0) ? true : false;
    }
    
    // -------------------------------------------------------------------------

	private static class SocialBoardRowMapper implements RowMapper<DCSocialBoard> {
        //
        @Override
        public DCSocialBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser admin = new DCBoardUser(rs.getString("user_email"),
                                            rs.getString("user_name"), 
                                            rs.getString("phone_number"));

            DCSocialBoard socialBoard = new DCSocialBoard(new DCBoardTeam(rs.getString("team_name"), admin), 
                                                      rs.getString("board_name"));
            
            boolean commentable = ("Y".equals(rs.getString("commentable_yn"))) ? true : false;
            socialBoard.setCommentable(commentable);
            socialBoard.setUsid(rs.getString("board_id"));
            socialBoard.setTeamUsid(rs.getString("team_id"));
            socialBoard.setCreateDate(rs.getDate("create_date"));
            
            return socialBoard;
        }
    }
}
