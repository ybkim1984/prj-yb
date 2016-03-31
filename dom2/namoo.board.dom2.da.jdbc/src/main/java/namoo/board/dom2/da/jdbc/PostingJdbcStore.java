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

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public class PostingJdbcStore implements PostingStore {
    //
    private DataSource dataSource;
    
    //--------------------------------------------------------------------------
    
    public PostingJdbcStore(DataSource dataSource) {
        //
        this.dataSource = dataSource;
    }
    
    @Override
    public void create(DCPosting posting) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO posting (posting_id, board_id, title, writer_email, ")
               .append("                     read_count, reg_date, commentable_yn, anonymous_comment_yn) ")
               .append("VALUES (?, ?, ?, ?, ?, sysdate(), ?, ?) ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, posting.getUsid());
            pstmt.setString(2, posting.getBoardUsid());
            pstmt.setString(3, posting.getTitle());
            pstmt.setString(4, posting.getWriterEmail());
            pstmt.setInt(5, posting.getReadCount());
            pstmt.setString(6, (posting.getOption().isCommentable()) ? "Y" : "N");
            pstmt.setString(7, (posting.getOption().isAnonymousComment()) ? "Y" : "N");
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

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCPosting posting = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT posting.posting_id, posting.title, posting.read_count, posting.board_id, ")
               .append("       posting.reg_date, posting.writer_email, board_user.user_name, posting.commentable_yn, ")
               .append("       posting.anonymous_comment_yn ")
               .append("  FROM posting ")
               .append("  JOIN board_user ON posting.writer_email = board_user.user_email ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                posting = createPostingDomain(rs);
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
        
        if(posting == null) {
            throw new EmptyResultException("No such a posting --> " + usid);
        }
        
        return posting;
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PageCriteria criteria = null;
        List<DCPosting> postings = new ArrayList<DCPosting>();
        
        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ? AS page, ? AS item_limit_of_page, ")
               .append("       COUNT(posting_id) AS total_item_count ")
               .append("  FROM posting ")
               .append(" WHERE board_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, pageCriteria.getPage());
            pstmt.setInt(2, pageCriteria.getItemLimitOfPage());
            pstmt.setString(3, boardUsid);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                criteria = createPageCriteriaDomain(rs);
            }
            
            pstmt.close();

            sql = new StringBuilder();
            sql.append("SELECT posting.posting_id, posting.title, posting.read_count, posting.board_id, ")
               .append("       posting.reg_date, posting.writer_email, board_user.user_name, posting.commentable_yn, ")
               .append("       posting.anonymous_comment_yn ")
               .append("  FROM posting ")
               .append("  JOIN board_user ON posting.writer_email = board_user.user_email ")
               .append(" WHERE posting.board_id = ? ")
               .append(" ORDER BY posting.reg_date DESC ")
               .append(" LIMIT ?, ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, boardUsid);
            pstmt.setInt(2, pageCriteria.getStart());
            pstmt.setInt(3, pageCriteria.getItemLimitOfPage());
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                postings.add(createPostingDomain(rs));
            }
            
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrievePage");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrievePage");
                }
            }

            if (pstmt != null) {
            	try {
            		pstmt.close();
            	} catch (Exception e) { 
            		e.printStackTrace();
            		throw new NamooBoardException("error to retrievePage");
            	}
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrievePage");
                }
            }
        }
        
        return new Page<DCPosting>(postings, criteria);
    }

    @Override
    public void update(DCPosting posting) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE posting ")
               .append("   SET title = ?,")
               .append("       commentable_yn = ?,")
               .append("       anonymous_comment_yn = ? ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, posting.getTitle());
            pstmt.setString(2, (posting.getOption().isCommentable()) ? "Y" : "N");
            pstmt.setString(3, (posting.getOption().isAnonymousComment()) ? "Y" : "N");
            pstmt.setString(4, posting.getUsid());
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

    @Override
    public void delete(String usid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM posting_comment WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);
            pstmt.executeUpdate();
            
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("DELETE FROM posting WHERE posting_id = ? ");
            
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
    public void deleteByBoard(String boardUsid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM posting_comment ")
               .append("      WHERE posting_id IN (SELECT posting_id")
               .append("                             FROM posting")
               .append("                            WHERE board_id = ?)");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, boardUsid);
            pstmt.executeUpdate();
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("DELETE FROM posting WHERE board_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, boardUsid);
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
    public int nextSequence(String boardUsid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int seq = 0;
        
        String seqName = boardUsid + "-posting_id";
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, seqName);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                seq = rs.getInt(1);
            }
            
            rs.close();
            pstmt.close();
            
            if(seq == 0) {
                sql = new StringBuilder();
                sql.append("INSERT INTO board_seq (seq_name, next_seq) ")
                   .append("VALUES (?, 1) ");
                
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, seqName);
                pstmt.executeUpdate();
                pstmt.close();
            }
            
            sql = new StringBuilder();
            sql.append("SELECT next_seq ")
               .append("  FROM board_seq ")
               .append(" WHERE seq_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, seqName);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                seq = rs.getInt(1);
            }
            
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("UPDATE board_seq ")
               .append("   SET next_seq = ? + 1 ")
               .append(" WHERE seq_name = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, seq);
            pstmt.setString(2, seqName);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to nextSequence");
        } finally {
        	if (rs != null) {
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
    public void increaseReadCount(String usid) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE posting ")
               .append("   SET read_count = (SELECT read_count + 1")
               .append("                       FROM posting")
               .append("                      WHERE posting_id = ?)")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, usid);
            pstmt.setString(2, usid);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to increaseReadCount");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to increaseReadCount");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to increaseReadCount");
                }
            }
        }
    }

    @Override
    public void createContents(DCPostingContents contents) {
        // 
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE posting ")
               .append("   SET contents = ? ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, contents.getContents());
            pstmt.setString(2, contents.getPostingUsid());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to createContents");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createContents");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createContents");
                }
            }
        }
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCPostingContents postingContents = null;
            
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT posting_id, contents ")
               .append("  FROM posting ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, postingUsid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                postingContents = createPostingContentsDomain(rs);
            }

            rs.close();
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("SELECT posting_id, sequence, comment, writer_email, written_time ")
               .append("  FROM posting_comment ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, postingUsid);

            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                postingContents.addComment(createPostingCommentDomain(rs));
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveContents");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveContents");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveContents");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveContents");
                }
            }
        }
        
        if(postingContents == null) {
            throw new EmptyResultException("No such a postingContents --> " + postingUsid);
        }
        
        return postingContents;
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE posting ")
               .append("   SET contents = ? ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, contents.getContents());
            pstmt.setString(2, contents.getPostingUsid());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to updateContents");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to updateContents");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to updateContents");
                }
            }
        }
    }

    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO posting_comment (posting_id, sequence, comment, writer_email, written_time) ")
               .append("VALUES (?, ?, ?, ?, sysdate()) ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, postingUsid);
            pstmt.setInt(2, comment.getSequence());
            pstmt.setString(3, comment.getComment());
            pstmt.setString(4, comment.getWriterEmail());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to createComment");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createComment");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to createComment");
                }
            }
        }
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCPostingComment postingComment = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT posting_id, sequence, ")
               .append("       comment, written_time, writer_email, board_user.user_name ")
               .append("  FROM posting_comment ")
               .append("  JOIN board_user ON writer_email = board_user.user_email ")
               .append(" WHERE posting_id = ? AND sequence = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, postingUsid);
            pstmt.setInt(2, sequence);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                postingComment = createPostingCommentDomain(rs);
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveComment");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
        }
        
        if(postingComment == null) {
            throw new EmptyResultException("No such a postingComment --> postingUsid : " +  postingUsid + ", sequence : " + sequence);
        }
        
        return postingComment;
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        //
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM posting_comment WHERE posting_id = ? AND sequence = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, postingUsid);
            pstmt.setInt(2, sequence);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to deleteComment");
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteComment");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to deleteComment");
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
        DCPosting posting = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT posting.posting_id, posting.title, posting.read_count, posting.board_id, ")
               .append("       posting.reg_date, posting.writer_email, board_user.user_name, posting.commentable_yn, ")
               .append("       posting.anonymous_comment_yn ")
               .append("  FROM posting ")
               .append("  JOIN board_user ON posting.writer_email = board_user.user_email ")
               .append(" WHERE posting_id = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, usid);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                posting = createPostingDomain(rs);
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
        
        return posting != null;
	}

	@Override
	public int nextCommentSequence(String postingUsid) {
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
               .append(" WHERE seq_name = ? ");
            
            StringBuilder sequenceName = new StringBuilder();
            sequenceName.append(postingUsid).append("-comment");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, sequenceName.toString());
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                seq = rs.getInt(1);
            }
            
            pstmt.close();
            
            sql = new StringBuilder();
            sql.append("UPDATE board_seq ")
               .append("   SET next_seq = ? + 1 ")
               .append(" WHERE seq_name = ?");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, seq);
            pstmt.setString(2, sequenceName.toString());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to nextCommentSequence");
        } finally {
        	if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to nextCommentSequence");
                }
            }
        	
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to nextCommentSequence");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to nextCommentSequence");
                }
            }
        }
        
        return seq;
	}

	@Override
	public boolean isExistComment(String postingUsid, int sequence) {
		// 
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DCPostingComment postingComment = null;
        
        try {
            conn = dataSource.getConnection();
            
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT comment_id, posting_id, sequence, ")
               .append("       comment, written_time, writer_email, board_user.user_name ")
               .append("  FROM posting_comment ")
               .append("  JOIN board_user ON writer_email = board_user.user_email ")
               .append(" WHERE posting_id = ? AND sequence = ? ");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setString(1, postingUsid);
            pstmt.setInt(2, sequence);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                postingComment = createPostingCommentDomain(rs);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NamooBoardException("error to retrieveComment");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) { 
                    e.printStackTrace();
                    throw new NamooBoardException("error to retrieveComment");
                }
            }
        }
        
        return postingComment != null;
	}

	private DCPosting createPostingDomain(ResultSet rs) throws SQLException {
        //
        String posting_id = rs.getString("posting.posting_id");
        String title = rs.getString("posting.title");
        String board_id = rs.getString("posting.board_id");
        String writer_email = rs.getString("posting.writer_email");
        String user_name = rs.getString("board_user.user_name");
        int read_count = rs.getInt("posting.read_count");
        boolean commentable_yn = rs.getBoolean("posting.commentable_yn");
        boolean anonymous_comment_yn = rs.getBoolean("posting.anonymous_comment_yn");
        Date reg_date = rs.getDate("posting.reg_date");
        
        DCBoardUser boardUser = new DCBoardUser(writer_email, user_name);
        
        DCPosting posting = new DCPosting(board_id, title, boardUser);
        posting.setUsid(posting_id);
        posting.setReadCount(read_count);
        posting.setRegisterDate(reg_date);
        posting.getOption().setCommentable(commentable_yn);
        posting.getOption().setAnonymousComment(anonymous_comment_yn);
        
        return posting;
    }
    
    private PageCriteria createPageCriteriaDomain(ResultSet rs) throws SQLException {
        //
        int page = rs.getInt("page");
        int itemLimitOfPage = rs.getInt("item_limit_of_page");
        int totalItemCount = rs.getInt("total_item_count");
        
        PageCriteria pageCriteria = new PageCriteria(page, itemLimitOfPage);
        pageCriteria.setTotalItemCount(totalItemCount);
        
        return pageCriteria;
    }
    
    private DCPostingContents createPostingContentsDomain(ResultSet rs) throws SQLException {
        //
        String posting_usid = rs.getString("posting.posting_id");
        String contents = rs.getString("posting.contents");
        DCPosting posting = new DCPosting(posting_usid);
        
        return new DCPostingContents(posting, contents);
    }
    
    private DCPostingComment createPostingCommentDomain(ResultSet rs) throws SQLException {
        //
        String posting_id = rs.getString("posting_id");
        String comment = rs.getString("comment");
        String writer_email = rs.getString("writer_email");
        int sequence = rs.getInt("sequence");
        Date written_time = rs.getDate("written_time");
        
        DCPostingComment postingComment = new DCPostingComment(comment, writer_email);
        postingComment.setPostingUsid(posting_id);
        postingComment.setSequence(sequence);
        postingComment.setWrittenTime(written_time);
        
        return postingComment;
    }
}
