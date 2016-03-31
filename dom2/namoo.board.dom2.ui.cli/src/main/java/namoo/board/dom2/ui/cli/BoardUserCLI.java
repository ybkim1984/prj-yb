/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.cli;

import java.io.File;
import java.net.URL;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;

public class BoardUserCLI {
    //
    private ExcelFileBatchLoader excelLoader;
    private BoardUserService userService;
    
    //--------------------------------------------------------------------------

    public BoardUserCLI(ExcelFileBatchLoader excelLoader, BoardUserService userService) {
        //
        this.excelLoader = excelLoader;
        this.userService = userService;
    }
    
    //--------------------------------------------------------------------------
    
    public void executeExcelLoader() {
        //
        URL path = this.getClass().getResource("/BoardUsers.xlsx");
        boolean result = excelLoader.registerServiceUsers(new File(path.getPath()));
        
        System.out.println("Excel Loader was executed. --> " + result);
    }
    
    public void findAllBoardUsers() {
        //
        System.out.println("====================================================");
        System.out.println(" 1. BoardUser 전체목록 조회");
        System.out.println("====================================================");
        
        List<DCBoardUser> allUsers = userService.findAllUsers();
        
        if (allUsers.isEmpty()) {
            InputScanner.getInstance().waitInputEnter("Not any user.");
            return;
        }
        
        System.out.println("====================================================");
        
        for (DCBoardUser user : allUsers) {
            //
            System.out.println(user.toString());
        }
        InputScanner.getInstance().waitInputEnter("User inquiry is complete.");
    }
    
    public void findAllBoardTeams() {
        //
        System.out.println("====================================================");
        System.out.println(" 2. BoardTeam 전체목록 조회");
        System.out.println("====================================================");
        
        List<DCBoardTeam> allTeams = userService.findAllBoardTeams();
        
        if (allTeams.isEmpty()) {
            InputScanner.getInstance().waitInputEnter("Not any team.");
            return;
        }
        
        System.out.println("====================================================");
        
        for (DCBoardTeam team : allTeams) {
            //
            System.out.println(team.toString());
        }
        InputScanner.getInstance().waitInputEnter("Team inquiry completed.");
    }
    
    public void findTeamMembers() {
        //
        System.out.println("====================================================");
        System.out.println(" 3. TeamMember 목록 조회");
        System.out.println("====================================================");
        
        String teamUsid = InputScanner.getInstance().getStringInput("Team id");

        List<DCBoardMember> allMembers = userService.findTeamBoardMembers(teamUsid);
        
        if (allMembers.isEmpty()) {
            InputScanner.getInstance().waitInputEnter("No such a team --> " + teamUsid + " or not any member.");
            return;
        }
        
        System.out.println("====================================================");
        
        for (DCBoardMember member : allMembers) {
            //
            System.out.println(member.toString());
        }
        InputScanner.getInstance().waitInputEnter("Member inquiry is complete.");
    }
}
