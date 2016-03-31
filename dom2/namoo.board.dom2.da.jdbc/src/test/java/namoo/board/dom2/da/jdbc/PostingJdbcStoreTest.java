/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hjyeom@nextree.co.kr">Yeom, Hyojun</a>
 * @since 2015. 1. 27.
 */

package namoo.board.dom2.da.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import namoo.board.dom2.da.jdbc.shared.BaseJdbcStoreDbUnitTest;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.Before;
import org.junit.Test;

public class PostingJdbcStoreTest extends BaseJdbcStoreDbUnitTest{
    //
    private BoardUserJdbcStore boardUserStore;
    private PostingJdbcStore postingStore;
    
    private final String postingUsid = "001-0001";
    
    private final String boardUsid = "001";
    
    @Override
    protected String getDatasetXml() {
        // 
        return "/dataset/postings.xml";
    }
    
    @Before
    public void setUp() throws Exception {
        //
        super.setUp();
        this.boardUserStore = new BoardUserJdbcStore(getDataSource());
        this.postingStore = new PostingJdbcStore(getDataSource());
    }
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardUser user = boardUserStore.retrieveByEmail("hkkang@nextree.co.kr");
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
        } catch (Exception e) {}
        
    }
    
    @Test
    public void testDeleteByBoard() throws EmptyResultException {
        //
    	String postingUsid = "001-0003";
    	DCBoardUser user = boardUserStore.retrieveByEmail("hkkang@nextree.co.kr");
        DCPosting posting = new DCPosting("001", "팀별 일정 집계 중입니다.", user);
        posting.setUsid(postingUsid);
        
        postingStore.create(posting);
        
        posting = postingStore.retrieve(postingUsid);
        
        assertNotNull(posting);
        
        postingStore.deleteByBoard(boardUsid);
        
        try {
        	postingStore.retrieve(postingUsid);
        	fail();
        } catch (EmptyResultException e) {
            // PASS
        }
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
        
        postingStore.createContents(contents);
        
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
        DCPostingComment postingComment = postingStore.retrieveComment(this.postingUsid, 1);
        
        assertNotNull(postingComment);
        assertEquals("네 알겠습니다.", postingComment.getComment());
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
        int nextSequence = postingStore.nextCommentSequence(this.postingUsid);
        
        assertEquals(nextSequence, 3);
    }
    
    @Test
    public void testIsExist() {
    	//
    	boolean isExist = postingStore.isExist(this.postingUsid);
    	
    	assertTrue(isExist);
    }
    
    @Test 
    public void testIsExistComment() {
    	//
    	boolean isExist = postingStore.isExistComment(this.postingUsid, 1);
    	
    	assertTrue(isExist);
    }

}
