/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate;

import javax.persistence.EntityManager;

import namoo.board.dom2.da.hibernate.entityFactory.JpaEntityManagerFactory;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public class PostingHibernateStore implements PostingStore {
    //
    private EntityManager em;
    
    public PostingHibernateStore() {
        //
        em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void create(DCPosting posting) {
        // TODO Auto-generated method stub

    }

    @Override
    public DCPosting retrieve(String usid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid,
            PageCriteria pageCriteria) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(DCPosting posting) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String usid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteByBoard(String boardUsid) {
        // TODO Auto-generated method stub

    }

    @Override
    public int nextSequence(String boardUsid) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void increaseReadCount(String usid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createContents(DCPostingContents contents) {
        // TODO Auto-generated method stub

    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        // TODO Auto-generated method stub

    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isExist(String usid) {
        // TODO
        return false;
    }

    @Override
    public int nextCommentSequence(String postingUsid) {
        // TODO
        return 0;
    }

    @Override
    public boolean isExistComment(String postingUsid, int sequence) {
        // TODO
        return false;
    }

}
