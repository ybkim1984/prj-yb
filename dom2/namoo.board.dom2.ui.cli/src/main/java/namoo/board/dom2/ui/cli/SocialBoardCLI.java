/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.cli;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.namevalue.NameValueList;

public class SocialBoardCLI {
    //
    private SocialBoardService socialBoardService;
    
    //--------------------------------------------------------------------------

    public SocialBoardCLI(SocialBoardService socialBoardService) {
        //
        this.socialBoardService = socialBoardService;
    }
    
    //--------------------------------------------------------------------------
    
    public void registerSocialBoard() throws NamooBoardException {
        //
        System.out.println("====================================================");
        System.out.println(" 11. SocialBoard 등록 ");
        System.out.println("====================================================");
        
        String teamUsid = InputScanner.getInstance().getStringInput("게시판을 사용할 팀 Id");
        String boardName = InputScanner.getInstance().getStringInput("게시판명");
        boolean commentable = InputScanner.getInstance().getBooleanInput("댓글 허용여부");

        
        String boardId = socialBoardService.registerSocialBoard(teamUsid, boardName, commentable);
        InputScanner.getInstance().waitInputEnter("Board has been id --> " + boardId);
    }
    
    public void findAllSocialBoards() throws NamooBoardException {
        //
        System.out.println("====================================================");
        System.out.println(" 12. SocialBoard 목록조회");
        System.out.println("====================================================");
        
        List<DCSocialBoard> allBoards = socialBoardService.findAllSocialBoards();
        
        if (allBoards.isEmpty()) {
            InputScanner.getInstance().waitInputEnter("Not any board.");
            return;
        }
        
        for (DCSocialBoard board : allBoards) {
            //
            System.out.println(board.toString());
        }
        
        InputScanner.getInstance().waitInputEnter("Board inquiry completed.");
    }
    
    public void findSocialBoard() throws NamooBoardException {
        //
        System.out.println("====================================================");
        System.out.println(" 13. SocialBoard 상세정보 조회");
        System.out.println("====================================================");
        
        String boardUsid = InputScanner.getInstance().getStringInput("게시판 Id");
        
        DCSocialBoard board = socialBoardService.findSocialBoard(boardUsid);
        
        if (board == null) {
            InputScanner.getInstance().waitInputEnter("No such a board --> " + boardUsid);
            return;
        }
        System.out.println(board.toString());
        InputScanner.getInstance().waitInputEnter("Board inquiry completed.");
        
    }
    
    public void modifySocialBoard() throws NamooBoardException {
        //
        System.out.println("====================================================");
        System.out.println(" 14. SocialBoard 수정");
        System.out.println("====================================================");
        
        String boardUsid = InputScanner.getInstance().getStringInput("게시판 Id");
        
        DCSocialBoard board = socialBoardService.findSocialBoard(boardUsid);
        
        if (board == null) {
            InputScanner.getInstance().waitInputEnter("No such a board --> " + boardUsid);
            return;
        }
        
        System.out.println("게시판 Id: " + board.getUsid());
        System.out.println("게시판명: " + board.getName());
        System.out.println("댓글 허용여부: " + board.isCommentable());
        System.out.println("생성일: " + board.getCreateDate());
        
        System.out.println("====================================================");
        
        String boardName = InputScanner.getInstance().getStringInput("게시판명");
        boolean commentable = InputScanner.getInstance().getBooleanInput("댓글허용여부");
        
        NameValueList nameValues = NameValueList.getInstance();
        nameValues.add(DCSocialBoard.PROPERTY_NAME, boardName)
                .add(DCSocialBoard.PROPERTY_COMMENTABLE, commentable);
        
        socialBoardService.modifySocialBoard(boardUsid, nameValues);
        
        InputScanner.getInstance().waitInputEnter("Board has been modified.");
    }
    
    public void removeSocialBoard() throws NamooBoardException {
        //
        System.out.println("====================================================");
        System.out.println(" 15. SocialBoard 삭제");
        System.out.println("====================================================");
        
        String boardId = InputScanner.getInstance().getStringInput("게시판 Id");
        
        socialBoardService.removeSocialBoard(boardId);
        
        InputScanner.getInstance().waitInputEnter("Board has been removed.");
    }
}
