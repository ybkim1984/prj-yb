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
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

public class SocialBoardJdbcStore implements SocialBoardStore {
    //
    private DataSource dataSource;
    
    //--------------------------------------------------------------------------
    
    public SocialBoardJdbcStore(DataSource dataSource) {
        //
        this.dataSource = dataSource;
    }
    
    public String create(DCSocialBoard board) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO social_board (board_id, board_name, commentable_yn, team_id, create_date) ")
               .append("VALUES (?, ?, ?, ?, sysdate()) ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, board.getUsid());
            pstmt.setString(2, board.getName());
            pstmt.setString(3, (board.isCommentable()) ? "Y" : "N");
            pstmt.setString(4, board.getTeamUsid());
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                board.setUsid(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to create");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
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
        
        return board.getUsid();
    }

    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCSocialBoard socialBoard = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT board_id, board_name, commentable_yn, team_id, create_date ")
               .append("  FROM social_board ")
               .append(" WHERE board_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                socialBoard = createSocialBoardDomain(rs);
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
        
        if(socialBoard == null) {
            throw new EmptyResultException("No such a socialBoard --> " + usid);
        }
        
        return socialBoard;
    }

    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCSocialBoard socialBoard = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT board_id, board_name, commentable_yn, team_id, create_date ")
               .append("  FROM social_board ")
               .append(" WHERE board_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
                    
            if (rs.next()) {
                socialBoard = createSocialBoardDomain(rs);
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
        
        if(socialBoard == null) {
            throw new EmptyResultException("No such a board --> " + name);
        }
        
        return socialBoard;
    }

    public List<DCSocialBoard> retrieveAll() {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DCSocialBoard> socialBoards = new ArrayList<DCSocialBoard>();
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT board_id, board_name, commentable_yn, team_id, create_date ")
               .append("  FROM social_board ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                socialBoards.add(createSocialBoardDomain(rs));
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
        
        return socialBoards;
    }

    public void update(DCSocialBoard board) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE social_board ")
               .append("   SET board_name = ?, commentable_yn = ? ")
               .append(" WHERE board_id = ?");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, board.getName());
            pstmt.setString(2, (board.isCommentable()) ? "Y" : "N");
            pstmt.setString(3, board.getUsid());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to update");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to update");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to update");
                }
            }
        }
    }
    
    public void delete(String usid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM social_board WHERE board_id = ? ");
            
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
               .append(" WHERE seq_name = 'board_id' ");
            
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
               .append(" WHERE seq_name = 'board_id' ");
            
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
	public boolean isExist(String usid) {
		// 
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCSocialBoard socialBoard = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT board_id, board_name, commentable_yn, team_id, create_date ")
               .append("  FROM social_board ")
               .append(" WHERE board_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                socialBoard = createSocialBoardDomain(rs);
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
        
        return socialBoard != null;
	}

	@Override
	public boolean isExistByName(String name) {
		// 
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCSocialBoard socialBoard = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT board_id, board_name, commentable_yn, team_id, create_date ")
               .append("  FROM social_board ")
               .append(" WHERE board_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
                    
            if (rs.next()) {
                socialBoard = createSocialBoardDomain(rs);
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
        
        return socialBoard != null;
	}

	private DCSocialBoard createSocialBoardDomain(ResultSet rs) throws SQLException {
        //
        String usid = rs.getString("board_id");
        String name = rs.getString("board_name");
        Date createDate = rs.getDate("create_date");
        String teamUsid = rs.getString("team_id");
        boolean commentable = rs.getBoolean("commentable_yn");
        
        return new DCSocialBoard(usid, name, createDate, teamUsid, commentable);
    }
}
