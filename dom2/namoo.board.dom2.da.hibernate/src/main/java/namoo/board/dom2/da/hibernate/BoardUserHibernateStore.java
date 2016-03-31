/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import namoo.board.dom2.da.hibernate.entityFactory.JpaEntityManagerFactory;
import namoo.board.dom2.da.hibernate.jpao.user.BoardUserJpao;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.exception.NamooBoardException;

public class BoardUserHibernateStore implements BoardUserStore {
    //
    private EntityManager em;    
    
    public BoardUserHibernateStore() {
        //
        em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public void create(DCBoardUser user) {
        //
        BoardUserJpao boardUserJpao = new BoardUserJpao(user);
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(boardUserJpao);
        transaction.commit();
    }

    @Override
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException {
        //
        BoardUserJpao boardUserJpao = em.find(BoardUserJpao.class, email);
        if(boardUserJpao == null) {
            throw new EmptyResultException("No such a User -->"+ email );
        }
        return boardUserJpao.createBoardUser();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DCBoardUser> retrieveAll() {
        //
        String queryStr = "FROM BoardUserJpao b ";
        Query query = em.createQuery(queryStr);
        
        List<BoardUserJpao> boardUserJpaos = query.getResultList();
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();
        
        for (BoardUserJpao boardUserJpao : boardUserJpaos) {
            boardUsers.add(boardUserJpao.createBoardUser());
        }
        
        return boardUsers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DCBoardUser> retrieveByName(String name) {
        // 
        String queryStr = "FROM BoardUserJpao b where b.name = :name";
        Query query = em.createQuery(queryStr);
        query.setParameter("name", name);
        
        List<BoardUserJpao> boardUserJpaos = query.getResultList();
        List<DCBoardUser> boardUsers = new ArrayList<DCBoardUser>();
        
        for (BoardUserJpao boardUserJpao : boardUserJpaos) {
            boardUsers.add(boardUserJpao.createBoardUser());
        }
        return boardUsers;
    }

    @Override
    public void update(DCBoardUser user) {
        // 
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        BoardUserJpao boardUserJpao = em.find(BoardUserJpao.class, user.getEmail());
        if(boardUserJpao == null) {
            throw new NamooBoardException("No such a User -->"+ user.getEmail() );
        }
        boardUserJpao.copyAttrs(user);
        em.merge(boardUserJpao);
        
        transaction.commit();
    }

    @Override
    public void deleteByEmail(String email) {
        //
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        BoardUserJpao boardUserJpao = em.find(BoardUserJpao.class, email);        
        em.remove(boardUserJpao);
        
        transaction.commit();
    }

    @Override
    public boolean isExistByEmail(String email) {
        // 
        BoardUserJpao boardUserJpao = em.find(BoardUserJpao.class, email);
        return boardUserJpao != null;
    }

}
