/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws.logic;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.sp.ws.BoardUserWs;
import namoo.board.dom2.util.json.JsonUtil;

@WebService(endpointInterface = "namoo.board.dom2.sp.ws.BoardUserWs", serviceName = "BoardUserWs")
public class BoardUserWsLogic implements BoardUserWs {
    //
    private BoardUserService boardUserService;
    
    //--------------------------------------------------------------------------
    
    public BoardUserWsLogic() {
        //
        BoardStoreLifecycler boardStoreLifecycler = BoardStoreMyBatisLifecycler.getInstance();
        BoardServiceLifecycler boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(boardStoreLifecycler);
        
        this.boardUserService = boardServiceLifecycler.getBoardUserService();
    }
    
    @Override
    public void registerUser(String boardUserJson) {
        // 
        DCBoardUser boardUser = (DCBoardUser)JsonUtil.fromJson(boardUserJson, DCBoardUser.class);
        boardUserService.registerUser(boardUser);
    }

    @Override
    public String findUserWithEmail(String userEmail) {
        // 
        DCBoardUser boardUser = boardUserService.findUserWithEmail(userEmail);
        
        return JsonUtil.toJson(boardUser);
    }

    @Override
    public List<String> findAllUsers() {
        // 
        List<DCBoardUser> boardUsers = boardUserService.findAllUsers();
        List<String> boardUsersJson = new ArrayList<String>();
        for(DCBoardUser boardUser : boardUsers) {
            boardUsersJson.add(JsonUtil.toJson(boardUser));
        }
        
        return boardUsersJson;
    }

    @Override
    public void removeUserWithEmail(String userEmail) {
        // 
        boardUserService.removeUserWithEmail(userEmail);
    }

    @Override
    public boolean loginAsUser(String userEmail, String password) {
        // 
        return boardUserService.loginAsUser(userEmail, password);
    }

    @Override
    public String registerBoardTeam(String teamName, String adminEmail) {
        // 
        return boardUserService.registerBoardTeam(teamName, adminEmail);
    }

    @Override
    public List<String> findAllBoardTeams() {
        // 
        List<DCBoardTeam> boardTeams = boardUserService.findAllBoardTeams();
        
        List<String> boardTeamsJson = new ArrayList<String>();
        for(DCBoardTeam boardTeam : boardTeams) {
            boardTeamsJson.add(JsonUtil.toJson(boardTeam));
        }
        
        return boardTeamsJson;
    }

    @Override
    public void removeBoardTeam(String teamUsid) {
        // 
        boardUserService.removeBoardTeam(teamUsid);
    }

    @Override
    public void addToBoardTeam(String teamUsid, List<String> userEmails) {
        // 
        boardUserService.addToBoardTeam(teamUsid, userEmails);
    }

    @Override
    public List<String> findTeamBoardMembers(String teamUsid) {
        // 
        List<DCBoardMember> boardMembers = boardUserService.findTeamBoardMembers(teamUsid);
        
        List<String> boardMembersJson = new ArrayList<String>();
        for(DCBoardMember boardMember : boardMembers) {
            boardMembersJson.add(JsonUtil.toJson(boardMember));
        }
        
        return boardMembersJson;
    }

    @Override
    public void removeFromBoardTeam(String teamUsid, String userEmail) {
        // 
        boardUserService.removeFromBoardTeam(teamUsid, userEmail);
    }
}
