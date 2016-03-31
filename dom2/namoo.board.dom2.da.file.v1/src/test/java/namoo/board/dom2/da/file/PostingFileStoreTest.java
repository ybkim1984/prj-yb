/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.board.dom2.da.file.BoardTeamFileStore;
import namoo.board.dom2.da.file.BoardUserFileStore;
import namoo.board.dom2.da.file.PostingFileStore;
import namoo.board.dom2.da.file.SocialBoardFileStore;
import namoo.board.dom2.da.file.shared.BaseFileStoreTest;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.Test;

public class PostingFileStoreTest extends BaseFileStoreTest {
    //
    private PostingStore postingStore = new PostingFileStore();
    private BoardUserStore userStore = new BoardUserFileStore();
    private BoardTeamStore teamStore = new BoardTeamFileStore();
    private SocialBoardStore boardStore = new SocialBoardFileStore();
    
    private final String postingUsid = "001-0001";
    
    @Override
    public void setUp() {
        // 
        // DCBoardUser 데이터 생성
        List<DCBoardUser> users = new ArrayList<DCBoardUser>();
        
        users.add(new DCBoardUser("tsong@nextree.co.kr", "송태국", "010-1111-2222"));
        users.add(new DCBoardUser("hkkang@nextree.co.kr", "강형구", "010-1234-5678"));
        users.add(new DCBoardUser("syhan@nextree.co.kr", "한성영", "010-0000-0000"));
        users.add(new DCBoardUser("eschoi@nextree.co.kr", "최은선", "010-2222-2222"));
        
        for (DCBoardUser user : users) {
            userStore.create(user);
        }

        // DCBoardTeam 생성
        DCBoardTeam team = new DCBoardTeam("컨설팅팀", new DCBoardUser("tsong@nextree.co.kr"));
        team.setUsid("001");
        
        teamStore.create(team);
        teamStore.nextSequence();
        
        // DCBoardMember 데이터 생성
        List<DCBoardMember> members = new ArrayList<DCBoardMember>();
        
        DCBoardMember member = new DCBoardMember("001-hkkang@nextree.co.kr");
        member.setUser(new DCBoardUser("hkkang@nextree.co.kr"));
        members.add(member);
        
        member = new DCBoardMember("001-syhan@nextree.co.kr");
        member.setUser(new DCBoardUser("syhan@nextree.co.kr"));
        members.add(member);
        
        for (DCBoardMember boardMember : members) {
            teamStore.createMember("001", boardMember);
        }
        
        // DCSocialBoard 데이터 생성
        DCSocialBoard board = new DCSocialBoard("001", "일정게시판", new Date(), "001", true);
        boardStore.create(board);
        boardStore.nextSequence();
        
        // DCPosting 데이터 생성
        DCPosting posting = new DCPosting("001", "금주 회식 공지", new DCBoardUser("hkkang@nextree.co.kr"));
        posting.setUsid("001-0001");
        posting.setReadCount(0);
        
        DCPostingOption option = new DCPostingOption().commentable(true).anonymousComment(false);
        posting.setOption(option);
        posting.setContents(new DCPostingContents(posting, "팀 회식이 금주 금요일 7시에 있습니다. 장소는 추후 공지하도록 하겠습니다. 많은 참석 바랍니다."));
        
        postingStore.create(posting);
        postingStore.nextSequence("001");
        
        
        posting = new DCPosting("001", "1월 휴가계획일을 체크합니다.", new DCBoardUser("hkkang@nextree.co.kr"));
        posting.setUsid("001-0002");
        posting.setReadCount(0);
        
        option = new DCPostingOption().commentable(true).anonymousComment(true);
        posting.setOption(option);
        posting.setContents(new DCPostingContents(posting, "1월 휴가일정을 체크하려 합니다. 개인 별 휴가계획 일자를 담당자에게 메일로 알려주시기 바랍니다."));
        
        postingStore.create(posting);
        postingStore.nextSequence("001");
        
        // DCPostingComment 데이터 생성
        DCPostingComment comment = new DCPostingComment("네 알겠습니다.", "syhan@nextree.co.kr");
        comment.setSequence(1);
        
        postingStore.createComment("001-0001", comment);
        postingStore.nextCommentSequence("001-0001");
        
        
        comment = new DCPostingComment("참석하도록 하겠습니다.", "eschoi@nextree.co.kr");
        comment.setSequence(2);
        
        postingStore.createComment("001-0001", comment);
        postingStore.nextCommentSequence("001-0001");
        
    }
    
    //--------------------------------------------------------------------------
    // posting
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardUser user = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        DCPosting posting = new DCPosting("001", "팀별 일정 집계 중입니다.", user);
        posting.setUsid("001-0003");
        
        postingStore.create(posting);
        
        DCPosting created = postingStore.retrieve("001-0003");
        
        assertNotNull(created);
        assertEquals(posting.getUsid(), created.getUsid());
        assertEquals(posting.getTitle(), created.getTitle());
        assertEquals(posting.getWriterEmail(), created.getWriterEmail());
        assertEquals(posting.getWriterName(), created.getWriterName());
        assertEquals(posting.getReadCount(), created.getReadCount());
        assertEquals(posting.getBoardUsid(), created.getBoardUsid());
        assertEquals(posting.getRegisterDate().toString(), created.getRegisterDate().toString());
        assertEquals(posting.getOption().isCommentable(), created.getOption().isCommentable());
        assertEquals(posting.getOption().isAnonymousComment(), created.getOption().isAnonymousComment());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        
        assertNotNull(posting);
        assertEquals("금주 회식 공지", posting.getTitle());
        assertEquals("hkkang@nextree.co.kr", posting.getWriterEmail());
        assertEquals("강형구", posting.getWriterName());
        assertEquals(0, posting.getReadCount());
        assertEquals("001", posting.getBoardUsid());
        assertEquals(true, posting.getOption().isCommentable());
        assertEquals(false, posting.getOption().isAnonymousComment());
    }
    
    @Test
    public void testRetrievePage() {
        //
        PageCriteria criteria = new PageCriteria(2, 1);
        Page<DCPosting> postingPage = postingStore.retrievePage("001", criteria);
        
        assertEquals(1, postingPage.getResults().size());
        assertEquals(2, postingPage.getTotalItemCount());
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        
        posting.setTitle("회식일자가 변경되었습니다.");
        posting.getOption().setCommentable(false);
        posting.getOption().setAnonymousComment(true);
        
        postingStore.update(posting);
        
        DCPosting updated = postingStore.retrieve(this.postingUsid);
        assertEquals(posting.getTitle(), updated.getTitle());
        assertEquals(posting.getOption().isCommentable(), updated.getOption().isCommentable());
        assertEquals(posting.getOption().isAnonymousComment(), updated.getOption().isAnonymousComment());
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        assertNotNull(posting);
        
        postingStore.delete(this.postingUsid);
        
        try {
            postingStore.retrieve(this.postingUsid);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
        try {
            postingStore.retrieveComment(this.postingUsid, 1);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }

    @Test
    public void testDeleteByBoard() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        assertNotNull(posting);
        
        postingStore.deleteByBoard("001");
        
        try {
            postingStore.retrieve(this.postingUsid);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
        try {
            postingStore.retrieveComment(this.postingUsid, 1);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
    }

    @Test
    public void testNextSequence() {
        //
        int nextSequence = postingStore.nextSequence("001");
        assertEquals(3, nextSequence);
        
        nextSequence = postingStore.nextSequence("002");
        assertEquals(1, nextSequence);
    }
    
    @Test
    public void testIncreaseReadCount() throws EmptyResultException {
        //
        postingStore.increaseReadCount(this.postingUsid);
        
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        assertEquals(1, posting.getReadCount());
    }

    //--------------------------------------------------------------------------
    // contents
    
    @Test
    public void testCreateContents() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        String contentsStr = "안녕하세요. 반갑습니다.";
        DCPostingContents contents = new DCPostingContents(posting, contentsStr);
        
        postingStore.createContents(contents);
        
        DCPostingContents createdContents = postingStore.retrieveContents(this.postingUsid);
        assertEquals(contents.getContents(), createdContents.getContents());
    }
    
    @Test
    public void testRetrieveContents() throws EmptyResultException {
        //
        DCPostingContents contents = postingStore.retrieveContents(this.postingUsid);
        
        assertNotNull(contents);
        assertEquals("팀 회식이 금주 금요일 7시에 있습니다. 장소는 추후 공지하도록 하겠습니다. 많은 참석 바랍니다.", contents.getContents());
        
        List<DCPostingComment> comments = contents.getCommentBag().getComments();
        assertEquals(2, comments.size());
    }
    
    @Test
    public void testUpdateContents() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        String contentsStr = "안녕하세요. 반갑습니다.";
        DCPostingContents contents = new DCPostingContents(posting, contentsStr);
        
        postingStore.updateContents(contents);
        
        DCPostingContents updatedContents = postingStore.retrieveContents(this.postingUsid);
        assertEquals(contents.getContents(), updatedContents.getContents());
    }
    
    @Test
    public void testCreateComment() throws EmptyResultException {
        //
        DCPostingComment comment = new DCPostingComment("저도 참석하겠습니다. 즐거운 회식 기대합니다!.", "hkkang@nextree.co.kr");
        comment.setSequence(3);
        
        postingStore.createComment(this.postingUsid, comment);
        
        DCPostingContents contents = postingStore.retrieveContents(this.postingUsid);
        assertEquals(3, contents.getCommentBag().getComments().size());
    }
    
    @Test
    public void testRetrieveComment() throws EmptyResultException {
        //
        DCPostingComment comment = postingStore.retrieveComment(this.postingUsid, 1);
        
        assertNotNull(comment);
        assertEquals(1, comment.getSequence());
        assertEquals("네 알겠습니다.", comment.getComment());
        assertEquals("syhan@nextree.co.kr", comment.getWriterEmail());
    }
    
    @Test
    public void testDeleteComment() throws EmptyResultException {
        //
        postingStore.deleteComment(this.postingUsid, 1);
        
        try {
            postingStore.retrieveComment(this.postingUsid, 1);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
        
        DCPostingContents contents = postingStore.retrieveContents(this.postingUsid);
        assertEquals(1, contents.getCommentBag().getComments().size());
    }
    
    @Test
    public void testNextCommentSequence() {
        //
        int nextCommentSequence = postingStore.nextCommentSequence("001-0001");
        assertEquals(3, nextCommentSequence);
        
        nextCommentSequence = postingStore.nextCommentSequence("001-0002");
        assertEquals(1, nextCommentSequence);
    }
    
}
