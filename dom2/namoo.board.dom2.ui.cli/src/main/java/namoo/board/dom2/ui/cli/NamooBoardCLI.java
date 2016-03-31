/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.cli;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMemLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.util.exception.NamooBoardException;

public class NamooBoardCLI {
    //
    private BoardUserCLI userCli;
    private SocialBoardCLI boardCli;
    private PostingCLI postingCli;
    
    
    //--------------------------------------------------------------------------

    public NamooBoardCLI() {
        //
        BoardStoreLifecycler storeLifecycler = BoardStoreMemLifecycler.getInstance();
        BoardServiceLifecycler serviceLifecycler = BoardServicePojoLifecycler.getInstance(storeLifecycler);
        
        //
        this.userCli = new BoardUserCLI(serviceLifecycler.getExcelFileBatchLoader(), serviceLifecycler.getBoardUserService());
        this.boardCli = new SocialBoardCLI(serviceLifecycler.getSocialBoardService());
        this.postingCli = new PostingCLI(serviceLifecycler.getPostingService());
    }

    public boolean show() throws NamooBoardException {
        //
        while (true) {
            System.out.println();
            System.out.println("================================================");
            System.out.println(" Menu");
            System.out.println("================================================");
            System.out.println(" 0. Program exit");
            
            System.out.println(" 1. BoardUser 전체목록 조회");
            System.out.println(" 2. BoardTeam 전체목록 조회");
            System.out.println(" 3. TeamMember 목록 조회");
            
            System.out.println("================================================");
            
            System.out.println(" 11. SocialBoard 만들기");
            System.out.println(" 12. SocialBoard 목록조회");
            System.out.println(" 13. SocialBoard 상세정보 조회");
            System.out.println(" 14. SocialBoard 수정");
            System.out.println(" 15. SocialBoard 삭제");
            
            System.out.println("================================================");
            
            System.out.println(" 21. Posting 작성");
            System.out.println(" 22. Posting 페이지조회");
            System.out.println(" 23. Posting 상세정보 조회");
            System.out.println(" 24. Posting 옵션수정");
            System.out.println(" 25. Posting 내용수정");
            System.out.println(" 26. Posting 삭제");
            System.out.println(" 27. PostingComment 작성");
            System.out.println(" 28. PostingComment 삭제");
            
            System.out.println("================================================");
            int inputNumber = InputScanner.getInstance().getIntInput("Select number");

            switch (inputNumber) {
            // BoardTeam 기능
            case 1:
                userCli.findAllBoardUsers();
                break;
            case 2:
                userCli.findAllBoardTeams();
                break;
            case 3:
                userCli.findTeamMembers();
                break;
                
            // SocialBaord 기능
            case 11:
                boardCli.registerSocialBoard();
                break;
            case 12:
                boardCli.findAllSocialBoards();
                break;
            case 13:
                boardCli.findSocialBoard();
                break;
            case 14:
                boardCli.modifySocialBoard();
                break;
            case 15:
                boardCli.removeSocialBoard();
                break;
                
            // Posting 기능
            case 21:
                postingCli.registerPosting();
                break;
            case 22:
                postingCli.findPostings();
                break;
            case 23:
                postingCli.findPosting();
                break;
            case 24:
                postingCli.modifyPostingOption();
                break;
            case 25:
                postingCli.modifyPostingContents();
                break;
            case 26:
                postingCli.removePosting();
                break;
            case 27:
                postingCli.attachComment();
                break;
            case 28:
                postingCli.detachComment();
                break;
                
            case 0:
                System.out.println("Exiting...");
                return true;
            default:
                System.out.println("Choose again!");
            }
        }
    }
    public static void main(String[] args) {
        //
        NamooBoardCLI cli = new NamooBoardCLI();
        cli.userCli.executeExcelLoader();
        
        while (true) {
            try {
                if (cli.show()) {
                    System.exit(0);
                }
            } catch (NamooBoardException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
