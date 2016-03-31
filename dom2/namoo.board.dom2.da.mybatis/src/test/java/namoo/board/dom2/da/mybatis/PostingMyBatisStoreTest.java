/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */
package namoo.board.dom2.da.mybatis;

import static org.junit.Assert.*;

import java.util.List;

import namoo.board.dom2.da.mybatis.BoardUserMyBatisStore;
import namoo.board.dom2.da.mybatis.PostingMyBatisStore;
import namoo.board.dom2.da.mybatis.shared.BaseMyBatisStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.Test;

public class PostingMyBatisStoreTest extends BaseMyBatisStoreDbUnitTest {
    //
    private PostingStore postingStore = new PostingMyBatisStore();
    private BoardUserStore userStore = new BoardUserMyBatisStore();
    
    private final String postingUsid = "001-0001";
    
    @Override
    protected String getDatasetXml() {
        // 
        return "/dataset/postings.xml";
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
        assertEquals(posting.getRegisterDate(), created.getRegisterDate());
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
    
    /**
     * 동적쿼리 테스트
     * @throws EmptyResultException 
     */
    @Test
    public void testUpdate4DynamicSQL() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve(this.postingUsid);
        
        String beforeTitle = posting.getTitle();
        
        posting.setTitle(null);
        postingStore.update(posting);
        
        DCPosting updated = postingStore.retrieve(this.postingUsid);
        assertEquals(beforeTitle, updated.getTitle());
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
    public void testAddComment() throws EmptyResultException {
        //
        DCPostingComment comment = new DCPostingComment("저도 참석하겠습니다. 즐거운 회식 기대합니다!.", "hkkang@nextree.co.kr");
        comment.setSequence(3);
        
        postingStore.createComment(this.postingUsid, comment);
        
        DCPostingContents contents = postingStore.retrieveContents(this.postingUsid);
        assertEquals(3, contents.getCommentBag().getComments().size());
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
        int sequence = postingStore.nextCommentSequence(this.postingUsid);
        assertEquals(3, sequence);
        
        sequence = postingStore.nextCommentSequence("001-0002");
        assertEquals(1, sequence);
    }

}
