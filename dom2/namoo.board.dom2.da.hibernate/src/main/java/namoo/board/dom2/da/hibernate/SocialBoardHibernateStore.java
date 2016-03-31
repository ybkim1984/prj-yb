/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate;

import java.util.List;

import javax.persistence.EntityManager;

import namoo.board.dom2.da.hibernate.entityFactory.JpaEntityManagerFactory;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;

public class SocialBoardHibernateStore implements SocialBoardStore {
    //
    private EntityManager em;
    
    public SocialBoardHibernateStore() {
        //
        em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public String create(DCSocialBoard board) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DCSocialBoard retrieve(String usid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DCSocialBoard retrieveByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DCSocialBoard> retrieveAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(DCSocialBoard board) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String usid) {
        // TODO Auto-generated method stub

    }

    @Override
    public int nextSequence() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isExist(String usid) {
        // TODO
        return false;
    }

    @Override
    public boolean isExistByName(String name) {
        // TODO
        return false;
    }

}
