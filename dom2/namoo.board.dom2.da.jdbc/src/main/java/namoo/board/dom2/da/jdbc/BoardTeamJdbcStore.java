/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hjyeom@nextree.co.kr">Yeom, Hyojun</a>
 * @since 2015. 1. 27.
 */

package namoo.board.dom2.da.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

public class BoardTeamJdbcStore implements BoardTeamStore {
    //
    private DataSource dataSource;
    
    //--------------------------------------------------------------------------

    public BoardTeamJdbcStore(DataSource dataSource) {
        //
        this.dataSource = dataSource;
    }

    @Override
    public void create(DCBoardTeam team) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO board_team (team_id, team_name, admin_email, reg_date) ")
               .append("VALUES (?, ?, ?, sysdate())");

            pstmt = conn.prepareStatement(sql.toString()); // ,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, team.getUsid());
            pstmt.setString(2, team.getName());
            pstmt.setString(3, team.getAdmin().getEmail());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                team.setUsid(rs.getString(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to create");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to create");
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to create");
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to create");
                }
            }
        }
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardTeam boardTeam = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT team_id, team_name, admin_email, user_name, phone_number ")
               .append("  FROM board_team ")
               .append("  JOIN board_user ON admin_email = board_user.user_email ")
               .append(" WHERE team_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardTeam = createBoardTeamDomain(rs);
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieve");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieve");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieve");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieve");
                }
            }
        }
        
        if(boardTeam == null) {
            throw new EmptyResultException("No such a boardTeam --> " + usid);
        }
        
        return boardTeam;
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardTeam boardTeam = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT team_id, team_name, admin_email, user_name, phone_number ")
               .append("  FROM board_team ")
               .append("  JOIN board_user ON admin_email = board_user.user_email ")
               .append(" WHERE team_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardTeam = createBoardTeamDomain(rs);
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveByName");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByName");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByName");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByName");
                }
            }
        }
        
        if(boardTeam == null) {
            throw new EmptyResultException("No such a boardTeam --> " + name);
        }
        
        return boardTeam;
    }

    @Override
    public List<DCBoardTeam> retrieveAll() {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DCBoardTeam> teams = new ArrayList<DCBoardTeam>();
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT team_id, team_name, admin_email, user_name, phone_number ")
               .append("  FROM board_team ")
               .append("  JOIN board_user ON admin_email = board_user.user_email ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                teams.add(createBoardTeamDomain(rs));
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveAll");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveAll");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveAll");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveAll");
                }
            }
        }
        
        return teams;
    }

    @Override
    public void delete(String usid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM board_team WHERE team_id = ?");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);
            pstmt.executeUpdate();
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("DELETE FROM board_member WHERE team_id = ? ");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to delete");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to delete");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to delete");
                }
            }
        }
    }

    @Override
    public int nextSequence() {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int seq = 0;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = 'team_id' ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                seq = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("UPDATE board_seq ")
               .append("   SET next_seq = ? + 1 ")
               .append(" WHERE seq_name = 'team_id' ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, seq);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to nextSequence");
        } finally {
        	if(rs != null) {
        		try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new NamooBoardException("error to nextSequence");
				}
        	}
        	
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to nextSequence");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to nextSequence");
                }
            }
        }
        
        return seq;
    }

    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO board_member (member_id, team_id, member_email, reg_date) ")
               .append("VALUES (?, ?, ?, sysdate()) ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, member.getUsid());
            pstmt.setString(2, teamUsid);
            pstmt.setString(3, member.getUser().getEmail());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to createMember");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
        }
    }

    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardMember boardMember = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT member_id, team_name, member_email, user_name, phone_number ")
               .append("  FROM board_member ")
               .append("  JOIN board_team ON board_member.team_id = board_team.team_id ")
               .append("  JOIN board_user ON member_email = board_user.user_email ")
               .append(" WHERE board_member.team_id = ? ")
               .append("   AND board_member.member_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, teamUsid);
            pstmt.setString(2, memberEmail);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardMember = createboardTeamMemberDomain(rs, teamUsid);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("error to retrieveMember");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
        }
        
        if(boardMember == null) {
            throw new EmptyResultException("No such a boardMember -- > teamUsid : " + teamUsid + ", email : " + memberEmail);
        }
        
        return boardMember;
    }

    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        //  
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT member_id, team_name, member_email, user_name, phone_number ")
               .append("  FROM board_member ")
               .append("  JOIN board_team ON board_member.team_id = board_team.team_id ")
               .append("  JOIN board_user ON member_email = board_user.user_email ")
               .append(" WHERE board_member.team_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, teamUsid);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                boardMembers.add(createboardTeamMemberDomain(rs, teamUsid));
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveMembers");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveMembers");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveMembers");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveMembers");
                }
            }
        }
        
        return boardMembers;
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM board_member WHERE team_id = ? AND member_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, teamUsid);
            pstmt.setString(2, memberEmail);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to deleteMember");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteMember");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteMember");
                }
            }
        }

    }
    
    @Override
	public boolean isExist(String usid) {
		// 
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	DCBoardTeam boardTeam = null;
    	
    	try {
			conn = dataSource.getConnection();
			
			StringBuilder sql = new StringBuilder();
            sql.append("SELECT team_id, team_name, admin_email, user_name, phone_number ")
               .append("  FROM board_team ")
               .append("  JOIN board_user ON admin_email = board_user.user_email ")
               .append(" WHERE team_id = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, usid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardTeam = createBoardTeamDomain(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new NamooBoardException("error to isExist");
		} finally {
			 if (rs != null) {
                 try {
                     rs.close();
                 } catch(Exception e) {
                     e.printStackTrace();
                     throw new NamooBoardException("error to isExist");
                 }
             }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExist");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExist");
                }
            }
		}

		return boardTeam != null;
	}

	@Override
	public boolean isExistByName(String name) {
		// 
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardTeam boardTeam = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT team_id, team_name, admin_email, user_name, phone_number ")
               .append("  FROM board_team ")
               .append("  JOIN board_user ON admin_email = board_user.user_email ")
               .append(" WHERE team_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardTeam = createBoardTeamDomain(rs);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to isExistByName");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByName");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByName");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByName");
                }
            }
        }
        
        return boardTeam != null;
	}

	@Override
	public boolean isExistMember(String teamUsid, String memberEmail) {
		// 
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardMember boardMember = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT member_id, team_name, member_email, user_name, phone_number ")
               .append("  FROM board_member ")
               .append("  JOIN board_team ON board_member.team_id = board_team.team_id ")
               .append("  JOIN board_user ON member_email = board_user.user_email ")
               .append(" WHERE board_member.team_id = ? ")
               .append("   AND board_member.member_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, teamUsid);
            pstmt.setString(2, memberEmail);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardMember = createboardTeamMemberDomain(rs, teamUsid);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("error to retrieveMember");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createMember");
                }
            }
        }
        
        return boardMember != null;
	}

	private DCBoardTeam createBoardTeamDomain(ResultSet rs) throws SQLException {
        //
        DCBoardUser boardUser = new DCBoardUser(rs.getString("admin_email"), rs.getString("user_name"),
        		rs.getString("phone_number"));
        
        DCBoardTeam boardTeam = new DCBoardTeam(rs.getString("team_id"), boardUser);
        boardTeam.setName(rs.getString("team_name"));
        boardTeam.setUsid(rs.getString("team_id"));
        boardTeam.setAdmin(boardUser);
        
        return boardTeam;
    }
    
    private DCBoardMember createboardTeamMemberDomain(ResultSet rs, String teamUsid) throws SQLException {
        //
        DCBoardUser boardUser = new DCBoardUser(rs.getString("member_email"), rs.getString("user_name"),
        		rs.getString("phone_number"));
        
        DCBoardTeam boardTeam = new DCBoardTeam(rs.getString("team_name"), boardUser);
        boardTeam.setUsid(teamUsid);
        
        return new DCBoardMember(boardTeam, boardUser);
    }
}
