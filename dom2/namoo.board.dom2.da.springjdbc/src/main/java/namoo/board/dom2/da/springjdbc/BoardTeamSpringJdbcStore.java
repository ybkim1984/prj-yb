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

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardTeamSpringJdbcStore implements BoardTeamStore {
    //
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // -------------------------------------------------------------------------
    
    @Override
    public void create(DCBoardTeam team) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO board_team (team_id, team_name, admin_email, reg_date)")
               .append("     VALUES (?, ?, ?, SYSDATE())");
        
        jdbcTemplate.update(builder.toString(),
                                team.getUsid(), team.getName(), team.getAdmin().getEmail());
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT team.team_id, team.team_name, team.admin_email, ")
               .append("       user.user_name, user.phone_number ")
               .append("  FROM board_team team ")
               .append("  JOIN board_user user ON team.admin_email = user.user_email ")
               .append(" WHERE team.team_id = ?");
        
        try {
            return jdbcTemplate.queryForObject(builder.toString(), new BoardTeamRowMapper(), usid);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a boardTeam --> " + usid);
        }
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT team.team_id, team.team_name, team.admin_email, ")
               .append("       user.user_name, user.phone_number ")
               .append("  FROM board_team team ")
               .append("  JOIN board_user user ON team.admin_email = user.user_email ")
               .append(" WHERE team.team_name = ?");

        try {
            return jdbcTemplate.queryForObject(builder.toString(), new BoardTeamRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a boardTeam --> " + name);
        }
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT team.team_id, team.team_name, team.admin_email, ")
               .append("       user.user_name, user.phone_number ")
               .append("  FROM board_team team ")
               .append("  JOIN board_user user ON team.admin_email = user.user_email");
        
        return jdbcTemplate.query(builder.toString(), new BoardTeamRowMapper());
    }

    @Override
    public void delete(String usid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM board_member")
               .append("      WHERE team_id = ?");
        
        jdbcTemplate.update(builder.toString(), usid);
        
        builder = new StringBuilder();
        builder.append("DELETE FROM board_team ")
               .append("      WHERE team_id = ?");
        
        jdbcTemplate.update(builder.toString(), usid);
    }

    @Override
    public int nextSequence() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = 'team_id'");
        
        int nextSequence = jdbcTemplate.queryForObject(builder.toString(), Integer.class);
        
        builder = new StringBuilder();
        builder.append("UPDATE board_seq ")
               .append("   SET next_seq = ? ")
               .append(" WHERE seq_name = 'team_id'");
        
        jdbcTemplate.update(builder.toString(), (nextSequence + 1));
        
        return nextSequence;
    }

    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO board_member (member_id, team_id, member_email, reg_date) ")
               .append("     VALUES (?, ?, ?, SYSDATE())");
        
        jdbcTemplate.update(builder.toString(), member.getUsid(), teamUsid, member.getUser().getEmail());
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT member.member_id, team.team_id, team.team_name, ")
               .append("       member.member_email, user.user_name, user.phone_number, ")
               .append("       admin.user_email AS admin_email, admin.user_name AS admin_name, ")
               .append("       admin.phone_number AS admin_phone_number ")
               .append("  FROM board_member member ")
               .append("  JOIN board_team team ON member.team_id = team.team_id ")
               .append("  JOIN board_user user ON member.member_email = user.user_email ")
               .append("  JOIN board_user admin ON member.member_email = admin.user_email ")
               .append(" WHERE member.team_id = ? AND member.member_email = ?");
        
        try {
            return jdbcTemplate.queryForObject(builder.toString(), new BoardMemberRowMapper(), teamUsid, memberEmail);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultException("No such a boardMember --> teamUsid : " + teamUsid + ", memberEmail : " + memberEmail);
        }
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT member.member_id, team.team_id, team.team_name, ")
               .append("       member.member_email, user.user_name, user.phone_number, ")
               .append("       admin.user_email AS admin_email, admin.user_name AS admin_name, ")
               .append("       admin.phone_number AS admin_phone_number ")
               .append("  FROM board_member member ")
               .append("  JOIN board_team team ON member.team_id = team.team_id ")
               .append("  JOIN board_user user ON member.member_email = user.user_email ")
               .append("  JOIN board_user admin ON member.member_email = admin.user_email ")
               .append(" WHERE member.team_id = ?");
        
        return jdbcTemplate.query(builder.toString(), new BoardMemberRowMapper(), teamUsid);
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM board_member ")
               .append("      WHERE team_id = ? AND member_email = ?");
        
        jdbcTemplate.update(builder.toString(), teamUsid, memberEmail);
    }
    
    @Override
    public boolean isExist(String usid) {
    	// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM board_team")
    	       .append(" WHERE team_id = ?");
    	
    	int count =  jdbcTemplate.queryForObject(builder.toString(), Integer.class, usid);
    	
    	return (count > 0) ? true : false;
    }
    
    @Override
    public boolean isExistByName(String name) {
    	// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM board_team")
    	       .append(" WHERE team_name = ?");
    	
    	int count = jdbcTemplate.queryForObject(builder.toString(), Integer.class, name);
    	
    	return (count > 0) ? true : false;
    }
    
    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
    	// 
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT COUNT(*)")
    	       .append("  FROM board_member member")
    	       .append("  JOIN board_team team ON member.team_id = team.team_id")
    	       .append("  JOIN board_user user ON member.member_email = user.user_email")
    	       .append(" WHERE member.team_id = ? AND member.member_email = ?");
    	
    	int count = jdbcTemplate.queryForObject(builder.toString(), Integer.class, teamUsid, memberEmail);
    	
    	return (count > 0) ? true : false;
    }
    
    // -------------------------------------------------------------------------

	private static class BoardTeamRowMapper implements RowMapper<DCBoardTeam> {
        //
        @Override
        public DCBoardTeam mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser admin = new DCBoardUser(rs.getString("admin_email"),
                                            rs.getString("user_name"),
                                            rs.getString("phone_number"));

            DCBoardTeam boardTeam = new DCBoardTeam(rs.getString("team_name"), admin);
            boardTeam.setUsid(rs.getString("team_id"));
            
            return boardTeam;
        }
    }
    
    private static class BoardMemberRowMapper implements RowMapper<DCBoardMember> {
        //
        @Override
        public DCBoardMember mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 
            DCBoardUser boardUser = new DCBoardUser(rs.getString("member_email"),
                                                rs.getString("user_name"),
                                                rs.getString("phone_number"));
            
            DCBoardUser admin = new DCBoardUser(rs.getString("admin_email"), 
                                            rs.getString("admin_name"), 
                                            rs.getString("admin_phone_number"));
            
            DCBoardTeam boardTeam = new DCBoardTeam(rs.getString("team_name"), admin);
            boardTeam.setUsid(rs.getString("team_id"));
            
            return new DCBoardMember(boardTeam, boardUser);
        }
    }
}
