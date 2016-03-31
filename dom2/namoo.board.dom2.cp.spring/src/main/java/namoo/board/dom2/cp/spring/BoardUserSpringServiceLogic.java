/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.cp.spring;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.logic.BoardUserServiceLogic;
import namoo.board.dom2.service.BoardUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardUserSpringServiceLogic implements BoardUserService {
    //
    private BoardUserService service;

    @Autowired
    public BoardUserSpringServiceLogic(BoardStoreLifecycler storeLifecycler) {
        //
        this.service = new BoardUserServiceLogic(storeLifecycler);
    }
    
    // -------------------------------------------------------------------------

    @Override
    public void registerUser(DCBoardUser user) {
        // 
        service.registerUser(user);
    }

    @Override
    public DCBoardUser findUserWithEmail(String userEmail) {
        //
        return service.findUserWithEmail(userEmail);
    }

    @Override
    public List<DCBoardUser> findAllUsers() {
        //
        return service.findAllUsers();
    }

    @Override
    public void removeUserWithEmail(String userEmail) {
        //
        service.removeUserWithEmail(userEmail);
        
    }

    @Override
    public boolean loginAsUser(String userEmail, String password) {
        //
        return service.loginAsUser(userEmail, password);
    }

    @Override
    public String registerBoardTeam(String teamName, String adminEmail) {
        // 
        return service.registerBoardTeam(teamName, adminEmail);
    }

    @Override
    public List<DCBoardTeam> findAllBoardTeams() {
        //
        return service.findAllBoardTeams();
    }

    @Override
    public void removeBoardTeam(String teamUsid) {
        //
        service.removeBoardTeam(teamUsid);
    }

    @Override
    public void addToBoardTeam(String teamUsid, List<String> userEmails) {
        //
        service.addToBoardTeam(teamUsid, userEmails);
    }

    @Override
    public List<DCBoardMember> findTeamBoardMembers(String teamUsid) {
        //
        return service.findTeamBoardMembers(teamUsid);
    }

    @Override
    public void removeFromBoardTeam(String teamUsid, String userEmail) {
        //
        service.removeFromBoardTeam(teamUsid, userEmail);
    }
}
