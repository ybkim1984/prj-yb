/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.store;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.util.exception.EmptyResultException;

public interface BoardTeamStore {
    // board team
    public void create(DCBoardTeam team);
    public DCBoardTeam retrieve(String usid) throws EmptyResultException; 
    public DCBoardTeam retrieveByName(String name) throws EmptyResultException; 
    public List<DCBoardTeam> retrieveAll(); 
    public void delete(String usid); 
    
    public int nextSequence();             // for usid
    public boolean isExist(String usid); 
    public boolean isExistByName(String name);
    
    // board member
    public void createMember(String teamUsid, DCBoardMember member); 
    public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException; 
    public List<DCBoardMember> retrieveMembers(String teamUsid); 
    public void deleteMember(String teamUsid, String memberEmail); 
    
    public boolean isExistMember(String teamUsid, String memberEmail);
}
