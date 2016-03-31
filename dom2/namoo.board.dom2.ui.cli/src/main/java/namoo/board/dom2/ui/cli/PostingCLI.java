/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.cli;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public class PostingCLI {
    //
    private PostingService postingService;
    
    //--------------------------------------------------------------------------

    public PostingCLI(PostingService postingService) {
        //
        this.postingService = postingService;
    }
    
    //--------------------------------------------------------------------------
    
    
    public void registerPosting() {
        //
        System.out.println("====================================================");
        System.out.println(" 21. Posting 등록");
        System.out.println("====================================================");
        
        String boardUsid = InputScanner.getInstance().getStringInput("글을 작성할 게시판 Id");
        String writerEmail = InputScanner.getInstance().getStringInput("작성자 Email");
        String title = InputScanner.getInstance().getStringInput("제목");
        String contents = InputScanner.getInstance().getStringInput("내용");
        
        boolean commentable = InputScanner.getInstance().getBooleanInput("commentable");
        boolean anonymousComment = InputScanner.getInstance().getBooleanInput("anonymousComment");
        
        
        PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
        postingCdo.setCommentable(commentable);
        postingCdo.setAnonymousComment(anonymousComment);
        
        String postingUsid = postingService.registerPosting(boardUsid, postingCdo);
        InputScanner.getInstance().waitInputEnter("Posing has been registered. Id --> " + postingUsid);
    }
    
    public void findPostings() {
        //
        System.out.println("====================================================");
        System.out.println(" 22. Posting 페이지조회");
        System.out.println("====================================================");
        
        String boardUsid = InputScanner.getInstance().getStringInput("board id");
        int pageNum = InputScanner.getInstance().getIntInput("page num");
        int itemLimitOfPage = InputScanner.getInstance().getIntInput("item limit of page");
        
        PageCriteria criteria = new PageCriteria(pageNum, itemLimitOfPage);
        
        Page<DCPosting> postingPage = postingService.findPostingPage(boardUsid, criteria);
        
        if (postingPage.isEmptyResult()) {
            InputScanner.getInstance().waitInputEnter("Not any posting");
            return;
        }
        
        System.out.println("====================================================");
        for (DCPosting posting : postingPage.getResults()) {
            System.out.println(posting.toString());
        }
        
        InputScanner.getInstance().waitInputEnter("Posting inquiry completed.");
    }
    
    public void findPosting() {
        //
        System.out.println("====================================================");
        System.out.println(" 23. Posting 상세조회");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("Posting id");
        
        DCPosting posting = postingService.findPostingWithContents(postingUsid);
        
        if (posting == null) {
            InputScanner.getInstance().waitInputEnter("No such a posting --> " + postingUsid);
            return;
        }
        
        displayPosting(posting);
    }
    
    public void modifyPostingOption() {
        //
        System.out.println("====================================================");
        System.out.println(" 24. Posting 옵션 수정");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("수정할 posting id");
        
        DCPosting posting = postingService.findPosting(postingUsid);
        
        if (posting == null) {
            InputScanner.getInstance().waitInputEnter("No such a posting --> " + postingUsid);
            return;
        }
        
        System.out.println("수정할 posting의 기존내용입니다.");
        displayPosting(posting);
        
        
        System.out.println("====================================================");
        System.out.println("수정할 옵션값을 입력하세요.");
        
        boolean commentable = InputScanner.getInstance().getBooleanInput("Comment 작성가능여부");
        boolean anonymousComment = InputScanner.getInstance().getBooleanInput("Comment 익명여부");

        NameValueList nameValues = NameValueList.getInstance().
            add(DCPostingOption.PROPERTY_COMMENTABLE, commentable).
            add(DCPostingOption.PROPERTY_ANONYMOUS_COMMENT, anonymousComment);
        
        postingService.modifyPostingOption(postingUsid, nameValues);
        InputScanner.getInstance().waitInputEnter("Posting option has been modified.");
    }
    
    public void modifyPostingContents() {
        //
        System.out.println("====================================================");
        System.out.println(" 25. Posting 내용 수정");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("수정할 posting id");
        
        DCPosting posting = postingService.findPosting(postingUsid);
        
        if (posting == null) {
            InputScanner.getInstance().waitInputEnter("해당 posting이 없습니다.");
            return;
        }
        System.out.println("수정할 posting의 기존내용입니다.");
        displayPosting(posting);
        
        String contents = InputScanner.getInstance().getStringInput("수정내용");
        
        postingService.modifyPostingContents(postingUsid, contents);
        InputScanner.getInstance().waitInputEnter("Posting contents has been modified.");
    }
    
    public void removePosting() {
        //
        System.out.println("====================================================");
        System.out.println(" 26. Posting 삭제");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("삭제할 posting id");
        
        postingService.removePosting(postingUsid);
        InputScanner.getInstance().waitInputEnter("Posting has been removed.");
    }
    
    public void attachComment() {
        //
        System.out.println("====================================================");
        System.out.println(" 27. PostingComment 등록");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("comment를 작성할 posting id");
        
        DCPosting posting = postingService.findPostingWithContents(postingUsid);
        
        if (posting == null) {
            InputScanner.getInstance().waitInputEnter("No such a posting -->" + postingUsid);
            return;
        }
        displayPosting(posting);
        
        
        String writerEmail = InputScanner.getInstance().getStringInput("작성자 email");
        String comment = InputScanner.getInstance().getStringInput("Comment 내용");
        
        CommentCdo commentCdo = new CommentCdo(comment, writerEmail);
        
        postingService.attachComment(postingUsid, commentCdo);
        InputScanner.getInstance().waitInputEnter("Comment has been attached");
    }
    
    public void detachComment() {
        //
        System.out.println("====================================================");
        System.out.println(" 28. PostingComment 삭제");
        System.out.println("====================================================");
        
        String postingUsid = InputScanner.getInstance().getStringInput("posting id");
        
        DCPosting posting = postingService.findPostingWithContents(postingUsid);
        
        if (posting == null) {
            InputScanner.getInstance().waitInputEnter("No such a posting --> " + postingUsid);
            return;
        }
        displayPosting(posting);
        

        int sequence = InputScanner.getInstance().getIntInput("Comment sequence");
        
        postingService.detachComment(postingUsid, sequence);
        InputScanner.getInstance().waitInputEnter("Comment has been detached.");
    }
    
    //--------------------------------------------------------------------------
    
    private void displayPosting(DCPosting posting) {
        //
        System.out.println("Posting ============================================");
        System.out.println(posting.toString());
        
        if (posting.getContents() != null) {
            displayContents(posting.getContents());
            return;
        }
        
        InputScanner.getInstance().waitInputEnter("Posting inquiry completed.");
    }
    
    private void displayContents(DCPostingContents contents) {
        //
        System.out.println("Posting contents ===================================");
        System.out.println(contents.toString());
        System.out.println("Comments ===========================================");
        
        if (contents.getCommentBag().hasComments()) {
            System.out.println(contents.getCommentBag().toString());
        }
        else {
            System.out.println("Not any comment");
        }
        
        InputScanner.getInstance().waitInputEnter("Contents inquiry completed.");
        System.out.println("====================================================");
    }
    
}
