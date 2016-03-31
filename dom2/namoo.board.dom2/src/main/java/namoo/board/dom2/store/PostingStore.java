/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.store;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public interface PostingStore {
    //
    // posting
    public void create(DCPosting posting); 
    public DCPosting retrieve(String usid) throws EmptyResultException; 
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria);
    public void update(DCPosting posting); 
    public void delete(String usid); 
    public void deleteByBoard(String boardUsid);

    public int nextSequence(String boardUsid);
    public void increaseReadCount(String usid);
    public boolean isExist(String usid);
    
    // contents
    public void createContents(DCPostingContents contents); 
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException; 
    public void updateContents(DCPostingContents contents);
    
    // comment
    public void createComment(String postingUsid, DCPostingComment comment);
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException;
    public void deleteComment(String postingUsid, int sequence);
    
    public int nextCommentSequence(String postingUsid);
    public boolean isExistComment(String postingUsid, int sequence);
}
