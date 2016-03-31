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

import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

public class BoardUserJdbcStore implements BoardUserStore {
    //
    private DataSource dataSource;
    
    //--------------------------------------------------------------------------

    public BoardUserJdbcStore(DataSource dataSource) {
        //
        this.dataSource = dataSource;
    }

    public void create(DCBoardUser user) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO board_user (user_email, user_name, phone_number, reg_date) ")
               .append("VALUES (?, ?, ?, sysdate()) ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to create");
        } finally {
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

    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardUser boardUser = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ")
               .append(" WHERE user_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boardUser = createBoardUserDomain(rs);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveByEmail");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByEmail");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByEmail");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveByEmail");
                }
            }
        }
        
        if(boardUser == null) {
            throw new EmptyResultException("No such a boardUser --> " + email);
        }
        
        return boardUser;
    }

    public List<DCBoardUser> retrieveAll() {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();

        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ");
            
            pstmt = conn.prepareStatement(sql.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                boardUsers.add(createBoardUserDomain(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveAll");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
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
        
        return boardUsers;
    }

    public List<DCBoardUser> retrieveByName(String name) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();
        
        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ")
               .append(" WHERE user_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
                    
            if (rs.next()) {
                boardUsers.add(createBoardUserDomain(rs));
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
        
        return boardUsers;
    }

    public void update(DCBoardUser user) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE board_user ")
               .append("   SET phone_number = ? ")
               .append(" WHERE user_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, user.getPhoneNumber());
            pstmt.setString(2, user.getEmail());
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

    public void deleteByEmail(String email) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM board_user WHERE user_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to deleteByEmail");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteByEmail");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteByEmail");
                }
            }
        }
    }

    public boolean isExistByEmail(String email) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCBoardUser boardUser = null;
        
        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT user_email, user_name, phone_number ")
               .append("  FROM board_user ")
               .append( "WHERE user_email = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
                    
            if (rs.next()) {
                boardUser = createBoardUserDomain(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to isExistByEmail");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByEmail");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByEmail");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to isExistByEmail");
                }
            }
        }
        
        return boardUser != null;
    }

    private DCBoardUser createBoardUserDomain(ResultSet rs) throws SQLException {
        //
        String email = rs.getString("user_email");
        String name = rs.getString("user_name");
        String phoneNumber = rs.getString("phone_number");
        
        DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
        return boardUser;
    }
}
