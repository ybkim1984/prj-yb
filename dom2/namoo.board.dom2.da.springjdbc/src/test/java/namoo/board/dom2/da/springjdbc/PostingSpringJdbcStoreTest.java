/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.da.springjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import namoo.board.dom2.da.springjdbc.shared.BaseSpringJdbcStoreDbUnitTest;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;

@DatabaseSetup(value = "/dataset/postings.xml", type = DatabaseOperation.CLEAN_INSERT)
public class PostingSpringJdbcStoreTest extends BaseSpringJdbcStoreDbUnitTest {
    //
    @Autowired
    private PostingStore postingStore;
    @Autowired
    private BoardUserStore userStore;
    
    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardUser boardUser = userStore.retrieveByEmail("hkkang@nextree.co.kr");
        DCPosting posting = new DCPosting("001", "팀별 일정 집계 중입니다.", boardUser);
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
        DCPosting posting = postingStore.retrieve("001-0001");
        
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
        PageCriteria pageCriteria = new PageCriteria(2, 1);
        Page<DCPosting> postingPage = postingStore.retrievePage("001", pageCriteria);
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(postingPage));
        System.out.println(postingPage.getResults().size());
        System.out.println(postingPage.getTotalItemCount());
        
        assertEquals(1, postingPage.getResults().size());
        assertEquals(2, postingPage.getTotalItemCount());
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve("001-0001");
        
        posting.setTitle("회신일자가 변경되었습니다.");
        posting.getOption().setCommentable(false);
        posting.getOption().setAnonymousComment(true);
        
        postingStore.update(posting);
        
        DCPosting update = postingStore.retrieve("001-0001");
        assertEquals(posting.getTitle(), update.getTitle());
        assertEquals(posting.getOption().isCommentable(), update.getOption().isCommentable());
        assertEquals(posting.getOption().isAnonymousComment(), update.getOption().isAnonymousComment());
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve("001-0001");
        assertNotNull(posting);
        
        postingStore.delete("001-0001");
        
        try {
            postingStore.retrieve("001-0001");
            fail("Didn't throw exception");
        } catch (Exception e) {}
    }
    
    @Test
    public void testDeleteByBoard() {
        //
        PageCriteria pageCriteria = new PageCriteria(2,1);
        Page<DCPosting> pages = postingStore.retrievePage("001", pageCriteria);
        
        assertEquals(1, pages.getResults().size());
        
        postingStore.deleteByBoard("001");
        
        Page<DCPosting> deletePages = postingStore.retrievePage("001", pageCriteria);
        
        assertEquals(0, deletePages.getResults().size());
    }
    
    @Test
    public void testNextSequence() {
        //
        int nextSequence = postingStore.nextSequence("001");
        assertEquals(3, nextSequence);
        
        nextSequence = postingStore.nextSequence("002");
        assertEquals(1, nextSequence);
        
        nextSequence = postingStore.nextSequence("002");
        assertEquals(2, nextSequence);
    }
    
    @Test
    public void testIncreaseReadCount() throws EmptyResultException {
        //
        postingStore.increaseReadCount("001-0001");
        
        DCPosting posting = postingStore.retrieve("001-0001");
        assertEquals(1, posting.getReadCount());
    }
    
    @Test
    public void testCreateContent() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve("001-0001");
        String contents = "안녕하세요. 반갑습니다.";
        DCPostingContents PostingContents = new DCPostingContents(posting, contents);
        
        postingStore.createContents(PostingContents);
        
        DCPostingContents createdContents = postingStore.retrieveContents("001-0001");
        assertEquals(PostingContents.getContents(), createdContents.getContents());
    }
    
    @Test
    public void testRetrieveContents() throws EmptyResultException {
        //
        DCPostingContents postingContents = postingStore.retrieveContents("001-0001");
        
        assertNotNull(postingContents);
        assertEquals("팀 회식이 금주 금요일 7시에 있습니다. 장소는 추후 공지하도록 하겠습니다. 많은 참석 바랍니다.", postingContents.getContents());
        
        List<DCPostingComment> postingComments = postingContents.getCommentBag().getComments();
        assertEquals(2, postingComments.size());
    }
    
    @Test
    public void testUpdateContents() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve("001-0001");
        String contents = "안녕하세요. 반갑습니다.";
        DCPostingContents postingContents = new DCPostingContents(posting, contents);
        
        postingStore.createContents(postingContents);
        
        DCPostingContents updateContents = postingStore.retrieveContents("001-0001");
        assertEquals(postingContents.getContents(), updateContents.getContents());
    }
    
    @Test
    public void testCreateComment() throws EmptyResultException {
        //
        DCPostingComment postingComment = new DCPostingComment("저도 참석하겠습니다. 즐거운 회식 기대합니다!.", "hkkang@nextree.co.kr");
        postingComment.setSequence(3);
        
        postingStore.createComment("001-0001", postingComment);
        
        DCPostingContents postingContents = postingStore.retrieveContents("001-0001");
        assertEquals(3, postingContents.getCommentBag().getComments().size());
        
        Gson gson = new Gson();
        System.out.println(gson.toJson(postingContents));
    }
    
    @Test
    public void testRetrieveComment() throws EmptyResultException {
        //
        DCPostingComment postingComment = postingStore.retrieveComment("001-0001", 1);
        
        assertNotNull(postingComment);
        assertEquals("syhan@nextree.co.kr", postingComment.getWriterEmail());
        assertEquals("네 알겠습니다.", postingComment.getComment());
        assertEquals("001-0001", postingComment.getPostingUsid());
    }
    
    @Test
    public void testDeleteComment() throws EmptyResultException {
        //
        postingStore.deleteComment("001-0001", 1);
        
        try {
            postingStore.retrieveComment("001-0001", 1);
            fail("Didn't throw exception");
        } catch (Exception e) {}

        DCPostingContents deleteContents = postingStore.retrieveContents("001-0001");
        assertEquals(1, deleteContents.getCommentBag().getComments().size());
    }
    
    @Test
    public void testNextCommentSequence() {
        //
        int nextCommentSequence = postingStore.nextCommentSequence("001");
        
        assertNotNull(nextCommentSequence);
        assertEquals(1, nextCommentSequence);
    }
    
    @Test
    public void testIsExist() {
    	//
    	boolean isExist = postingStore.isExist("001-0001");
    	
    	assertTrue(isExist);
    }
    
    @Test 
    public void testIsExistComment() {
    	//
    	boolean isExist = postingStore.isExistComment("001-0001", 1);
    	
    	assertTrue(isExist);
    }
}
