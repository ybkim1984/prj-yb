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
import javax.persistence.NoResultException;
import javax.persistence.Query;

import namoo.board.dom2.da.hibernate.entityFactory.JpaEntityManagerFactory;
import namoo.board.dom2.da.hibernate.jpao.board.BoardSeqJpao;
import namoo.board.dom2.da.hibernate.jpao.user.BoardMemberJpao;
import namoo.board.dom2.da.hibernate.jpao.user.BoardTeamJpao;
import namoo.board.dom2.da.hibernate.jpao.user.BoardUserJpao;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

public class BoardTeamHibernateStore implements BoardTeamStore {
    //
    private EntityManager em;
    
    public BoardTeamHibernateStore() {
        //
        em = JpaEntityManagerFactory.getEntityManagerFactory().createEntityManager();
    }
    
    @Override
    public void create(DCBoardTeam team) {
        //        
        BoardTeamJpao boardTeamJpao = new BoardTeamJpao(team);
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(boardTeamJpao);
        transaction.commit();
    }

    @Override
    public DCBoardTeam retrieve(String usid) throws EmptyResultException {
        //
        BoardTeamJpao boardTeamJpao = em.find(BoardTeamJpao.class, usid);
        if(boardTeamJpao == null) {
            throw new EmptyResultException("No such a DCBoardTeam -->"+ usid );
        }
        return boardTeamJpao.createBoardTeam();
    }

    @Override
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException {
        // 
        return findBoardTeamByName(name);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<DCBoardTeam> retrieveAll() {
        // 
        String queryStr = "FROM BoardTeamJpao t";
        Query query = em.createQuery(queryStr);
        
        List<BoardTeamJpao> boardTeamJpaos = query.getResultList();
        List<DCBoardTeam> boardTeams = new ArrayList<DCBoardTeam>();

        for (BoardTeamJpao boardTeamJpao : boardTeamJpaos) {
            boardTeams.add(boardTeamJpao.createBoardTeam());
        }
        return boardTeams;
    }

    @Override
    public void delete(String usid) {
        // 
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        BoardTeamJpao boardTeamJpao = em.find(BoardTeamJpao.class, usid);
        em.remove(boardTeamJpao);    
        transaction.commit();
    }

    @Override
    public int nextSequence() {
        //
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        String queryCurrentSeq = "FROM BoardSeqJpao b where b.seqName = :seqName";
        Query query = em.createQuery(queryCurrentSeq);
        query.setParameter("seqName", "team_id");
        BoardSeqJpao currentBoardSeq = (BoardSeqJpao)query.getSingleResult();
        BoardSeqJpao boardSeq = new BoardSeqJpao("tema_id",currentBoardSeq.getNextSeq()+1);        
        em.merge(boardSeq);
        
        transaction.commit();
        return currentBoardSeq.getNextSeq()+1;
    }

    @Override
    public void createMember(String teamUsid, DCBoardMember member) {
        // 
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        BoardTeamJpao boardTeamJpao = em.find(BoardTeamJpao.class, teamUsid);
        BoardUserJpao boardUserJpao = em.find(BoardUserJpao.class, member.getUser().getEmail());
                
        BoardMemberJpao boardMemberJpao = new BoardMemberJpao(member.getUsid(),boardTeamJpao.createBoardTeam(),
                                                        boardUserJpao.createBoardUser());
        
        em.persist(boardMemberJpao);
        transaction.commit();
    }


    @Override
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) {
        //
        String memberId = teamUsid+"-"+memberEmail;
        BoardMemberJpao boardMemberJpao = em.find(BoardMemberJpao.class, memberId);
        return boardMemberJpao.createBoardMember();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DCBoardMember> retrieveMembers(String teamUsid) {
        // 
        String queryStr = "FROM BoardMemberJpao m where team_id = :teamUsid";
        Query query = em.createQuery(queryStr);
        query.setParameter("teamUsid", teamUsid);
        
        List<BoardMemberJpao> boardMemberJpaos = query.getResultList();
        List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
        
        for (BoardMemberJpao boardMemberJpao : boardMemberJpaos) {
            boardMembers.add(boardMemberJpao.createBoardMember());
        }
        return boardMembers;
    }

    @Override
    public void deleteMember(String teamUsid, String memberEmail) {
        // 
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        String memberId = teamUsid+"-"+memberEmail;
        BoardMemberJpao deleteBoardMemberJpao = em.find(BoardMemberJpao.class, memberId);
        em.remove(deleteBoardMemberJpao);
        
        transaction.commit();
    }

    @Override
    public boolean isExist(String usid) {
        // 
        return null != em.find(BoardTeamJpao.class, usid);
    }

    @Override
    public boolean isExistByName(String name) {
        //
        try {
            return null != findBoardTeamByName(name);
        } catch (EmptyResultException e) {
            return false;
        }
    }

    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
        // 
        String memberId = teamUsid+"-"+memberEmail;
        return null != em.find(BoardMemberJpao.class, memberId);
    }

    private DCBoardTeam findBoardTeamByName(String name) throws EmptyResultException {
        //
        String queryStr = "FROM BoardTeamJpao t where t.name = :name";
        Query query = em.createQuery(queryStr);
        query.setParameter("name", name);
        
        BoardTeamJpao boardTeamJpao = null;
        try{
            boardTeamJpao = (BoardTeamJpao)query.getSingleResult();
        }catch(NoResultException ne){
            throw new EmptyResultException("No such a DCBoardTeam -->"+ name );
        }        
        return boardTeamJpao.createBoardTeam();
    }
    
}
