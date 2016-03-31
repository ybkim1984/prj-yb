/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.service;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;

public interface BoardUserService {
    //
    public void registerUser(DCBoardUser user); 
    public DCBoardUser findUserWithEmail(String userEmail);
    public List<DCBoardUser> findAllUsers();
    public void removeUserWithEmail(String userEmail);
    
    public boolean loginAsUser(String userEmail, String password);
    
    public String registerBoardTeam(String teamName, String adminEmail);
    public List<DCBoardTeam> findAllBoardTeams();
    public void removeBoardTeam(String teamUsid); 
    
    public void addToBoardTeam(String teamUsid, List<String> userEmails);
    public List<DCBoardMember> findTeamBoardMembers(String teamUsid);
    public void removeFromBoardTeam(String teamUsid, String userEmail); 
}