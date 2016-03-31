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
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardUserSpringJdbcStore implements BoardUserStore {
    //
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // -------------------------------------------------------------------------
    
    @Override
    public void create(DCBoardUser user) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO board_user (user_email, user_name, phone_number, reg_date) ")
               .append("     VALUES (?, ?, ?, SYSDATE())");
        
        jdbcTemplate.update(builder.toString(), user.getEmail(), user.getName(), user.getPhoneNumber());
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ")
               .append(" WHERE user_email = ?");
        
        try {
            return jdbcTemplate.queryForObject(builder.toString(), new BoardUserRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a boardUser --> " + email);
        }
    }

    @Override
    public List<DCBoardUser> retrieveAll() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user");
        
        return jdbcTemplate.query(builder.toString(), new BoardUserRowMapper());
    }

    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ")
               .append(" WHERE user_name = ?");
        
        return jdbcTemplate.query(builder.toString(), new BoardUserRowMapper(), name);
    }

    @Override
    public void update(DCBoardUser user) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE board_user ")
               .append("   SET phone_number = ? ")
               .append(" WHERE user_email = ? ");
        
        jdbcTemplate.update(builder.toString(), user.getPhoneNumber(), user.getEmail());
    }

    @Override
    public void deleteByEmail(String email) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM board_user ")
               .append("      WHERE user_email = ?");
        
        jdbcTemplate.update(builder.toString(), email);
    }

    @Override
    public boolean isExistByEmail(String email) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT count(*) ")
               .append("  FROM board_user ")
               .append(" WHERE user_email = ?");
        
        int count = jdbcTemplate.queryForObject(builder.toString(), Integer.class, email);
        
        return (count > 0) ? true : false;
    }
    
    // -------------------------------------------------------------------------
    
    private static class BoardUserRowMapper implements RowMapper<DCBoardUser> {
        //
        @Override
        public DCBoardUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser boardUser = new DCBoardUser(rs.getString("user_email"),
                                                rs.getString("user_name"),
                                                rs.getString("phone_number"));

            return boardUser;
        }
    }

}
