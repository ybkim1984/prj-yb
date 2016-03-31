/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface BoardUserWs {
    //
    public void registerUser(@WebParam(name = "boardUserJson") String boardUserJson); 
    public String findUserWithEmail(@WebParam(name = "userEmail") String userEmail);
    public List<String> findAllUsers();
    public void removeUserWithEmail(@WebParam(name = "userEmail") String userEmail);
    
    public boolean loginAsUser(@WebParam(name = "userEmail") String userEmail, @WebParam(name = "password") String password);
    
    public String registerBoardTeam(@WebParam(name = "teamName") String teamName, @WebParam(name = "adminEmail") String adminEmail);
    public List<String> findAllBoardTeams();
    public void removeBoardTeam(@WebParam(name = "teamUsid") String teamUsid); 
    
    public void addToBoardTeam(@WebParam(name = "teamUsid") String teamUsid, @WebParam(name = "userEmails") List<String> userEmailsJson);
    public List<String> findTeamBoardMembers(@WebParam(name = "teamUsid") String teamUsid);
    public void removeFromBoardTeam(@WebParam(name = "teamUsid") String teamUsid, @WebParam(name = "userEmail") String userEmail); 
}
