/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.sp.ws.BoardUserWs;
import namoo.board.dom2.util.json.JsonUtil;

public class BoardUserWsPresenter implements BoardUserService {
    //
    private WsRequester wsRequester;

    // -------------------------------------------------------------------------

    public BoardUserWsPresenter() {
        //
        this.wsRequester = new WsRequester();
    }
    
    @Override
    public void registerUser(DCBoardUser user) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        boardUserWs.registerUser(JsonUtil.toJson(user));
    }

    @Override
    public DCBoardUser findUserWithEmail(String userEmail) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        String boardUserJson = boardUserWs.findUserWithEmail(userEmail);
        DCBoardUser boardUser = (DCBoardUser) JsonUtil.fromJson(boardUserJson, DCBoardUser.class);

        return boardUser;
    }

    @Override
    public List<DCBoardUser> findAllUsers() {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        List<String> allUsersJson = boardUserWs.findAllUsers();
        List<DCBoardUser> allBoardUsers = new ArrayList<DCBoardUser>();
        
        if(allUsersJson != null && allUsersJson.size() > 0) {
            for(String userJson : allUsersJson) {
                allBoardUsers.add((DCBoardUser) JsonUtil.fromJson(userJson, DCBoardUser.class));
            }
        }
        
        return allBoardUsers;
    }

    @Override
    public void removeUserWithEmail(String userEmail) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        boardUserWs.removeUserWithEmail(userEmail);
    }

    @Override
    public boolean loginAsUser(String userEmail, String password) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        
        return boardUserWs.loginAsUser(userEmail, password);
    }

    @Override
    public String registerBoardTeam(String teamName, String adminEmail) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        
        return boardUserWs.registerBoardTeam(teamName, adminEmail);
    }

    @Override
    public List<DCBoardTeam> findAllBoardTeams() {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        List<String> allBoardTeamsJson = boardUserWs.findAllBoardTeams();
        List<DCBoardTeam> allBoardTeams = new ArrayList<DCBoardTeam>();
        
        for(String boardTeamJson : allBoardTeamsJson) {
            allBoardTeams.add((DCBoardTeam) JsonUtil.fromJson(boardTeamJson, DCBoardTeam.class));
        }
        
        return allBoardTeams;
    }

    @Override
    public void removeBoardTeam(String teamUsid) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        boardUserWs.removeBoardTeam(teamUsid);
    }

    @Override
    public void addToBoardTeam(String teamUsid, List<String> userEmails) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        boardUserWs.addToBoardTeam(teamUsid, userEmails);
    }

    @Override
    public List<DCBoardMember> findTeamBoardMembers(String teamUsid) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        List<String> teamBoardMembersJson = boardUserWs.findTeamBoardMembers(teamUsid);
        List<DCBoardMember> teamBoardMembers = new ArrayList<DCBoardMember>();
        
        for(String teamBoardMemberJson : teamBoardMembersJson) {
            teamBoardMembers.add((DCBoardMember) JsonUtil.fromJson(teamBoardMemberJson, DCBoardMember.class));
        }
        
        return teamBoardMembers;
    }

    @Override
    public void removeFromBoardTeam(String teamUsid, String userEmail) {
        // 
        BoardUserWs boardUserWs = wsRequester.getBoardUserWs();
        boardUserWs.removeFromBoardTeam(teamUsid, userEmail);
    }
}
