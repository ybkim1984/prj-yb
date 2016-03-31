/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import namoo.board.dom2.da.mongojava.PostingMongoJavaStore;
import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;
import namoo.board.dom2.da.mongojava.shared.BaseMongoTestCase;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostingMongoJavaStoreTest extends BaseMongoTestCase {
    //
    private PostingStore postingStore;

    @Before
    public void setUp() throws Exception {
        //
        //데이터베이스 초기화
        MongoClientFactory.getDB().getCollection("boardposting").drop();
        this.postingStore = new PostingMongoJavaStore();
       
        //테스트 팀 등록
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        
        //테스트 포스팅 등록
        DCPosting posting = new DCPosting("0001", "테스트 타이틀", userPark);
        posting.setUsid("P001");
        //포스팅 옵션
        DCPostingOption option = new DCPostingOption();
        posting.setOption(option);
        //포스팅 내용
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다");
        contents.addComment(new DCPostingComment("테스트 커맨", userPark.getEmail()));
        posting.setContents(contents);
        
        this.postingStore.create(posting);
        
        //테스트 포스팅 등록
        DCPosting posting2 = new DCPosting("0002", "테스트 타이틀2", userPark);
        posting2.setUsid("P002");
        //포스팅 옵션
        DCPostingOption option2 = new DCPostingOption();
        posting2.setOption(option2);
        //포스팅 내용
        DCPostingContents contents2 = new DCPostingContents(posting2, "테스트로 포스팅을 해봅시다");
        contents2.addComment(new DCPostingComment("테스트 커맨", userPark.getEmail()));
        posting2.setContents(contents2);
        
        this.postingStore.create(posting2);
    }
    
    @After
    public void tearDown() throws Exception {
        //
        //MongoClientFactory.getDB().getCollection("boardusers").drop();
    }
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("001", "팀별 일정 집계 중입니다.", userPark);
        posting.setUsid("001-0003");
        
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다3");
        postingStore.createContents(contents);
//        DCPosting created = postingStore.retrieve("001-0003");
//        
//        assertNotNull(created);
//        assertEquals(posting.getUsid(), created.getUsid());
//        assertEquals(posting.getTitle(), created.getTitle());
//        assertEquals(posting.getWriterEmail(), created.getWriterEmail());
//        assertEquals(posting.getWriterName(), created.getWriterName());
//        assertEquals(posting.getReadCount(), created.getReadCount());
//        assertEquals(posting.getBoardUsid(), created.getBoardUsid());
//        assertEquals(posting.getRegisterDate().toString(), created.getRegisterDate().toString());
//        assertEquals(posting.getOption().isCommentable(), created.getOption().isCommentable());
//        assertEquals(posting.getOption().isAnonymousComment(), created.getOption().isAnonymousComment());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCPosting posting = this.postingStore.retrieve("P001");
        assertThat("jipark@nextree.co.kr", is(posting.getWriterEmail()));
    }
    
    @Test
    public void testRetrievePage() {
        //
        PageCriteria pageCriteria = new PageCriteria(1,10);
        Page<DCPosting> posting = this.postingStore.retrievePage("0002", pageCriteria);

        assertThat("테스트 타이틀2", is(posting.getResults().get(0).getTitle()));
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        //테스트 팀 등록
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        
        //테스트 포스팅 등록
        DCPosting posting = new DCPosting("0002", "테스트 타이틀 edited", userPark);
        posting.setUsid("P002");
        //포스팅 옵
        DCPostingOption option = new DCPostingOption();
        posting.setOption(option);
        //포스팅 내용
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다");
        contents.addComment(new DCPostingComment("테스트 커맨", userPark.getEmail()));
        posting.setContents(contents);
        
        this.postingStore.update(posting);
        
        DCPosting retrievedPosting = this.postingStore.retrieve("P002");
        assertThat("jipark@nextree.co.kr", is(retrievedPosting.getWriterEmail()));
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCPosting posting = postingStore.retrieve("P002");
        assertNotNull(posting);
        
        this.postingStore.delete("P002");
        
        try {
            postingStore.retrieve("P002");
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }
    
    @Test
    public void testDeleteByBoard() {
        //
        this.postingStore.deleteByBoard("0002");
    
        try {
            postingStore.retrieve("P002");
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }
    
    @Test
    public void testNextSequence() {
        //
        int seq = this.postingStore.nextSequence("0002");
    System.out.println(seq);
        //assertThat(3, is(seq));
    }
    
    @Test
    public void testIncreaseReadCount() throws EmptyResultException {
        //
        this.postingStore.increaseReadCount("P001");
       
    
        //assertThat(1, is(retrievedPosting.getReadCount()));
    }
    
    //--------------------------------------------------------------------------
    // contents
    
    @Test
    public void testCreateContents() throws EmptyResultException {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다2");
        postingStore.createContents(contents);
        DCPosting retrievedPosting = postingStore.retrieve("P003");
    
        assertThat("테스트로 포스팅을 해봅시다2", is(retrievedPosting.getContents().getContents()));
    }
    
    @Test
    public void testRetrieveContents() throws EmptyResultException {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다2");
        postingStore.createContents(contents);
        DCPostingContents postingContents = postingStore.retrieveContents("P003");
    
        assertThat("테스트로 포스팅을 해봅시다2", is(postingContents.getContents()));
    }
    
    @Test
    public void testUpdateContents() throws EmptyResultException {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다4");
        postingStore.createContents(contents);
        DCPosting retrievedPosting = postingStore.retrieve("P003");
        DCPostingContents updateContents = new DCPostingContents(retrievedPosting, "테스트로 포스팅을 해봅시다5");
        postingStore.updateContents(updateContents);
        
    }
    @Test
    public void testCreateComment() {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다2");
        postingStore.createContents(contents);
        DCPostingComment postingComment = new DCPostingComment("댓글입력","eschoi@nextree.co.kr");
        postingStore.createComment("P003", postingComment);
        
        
    }
    
    @Test
    public void testRetrieveComment() throws EmptyResultException {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다2");
        postingStore.createContents(contents);
        DCPostingComment postingComment = new DCPostingComment("댓글입력","eschoi@nextree.co.kr");
        postingStore.createComment("P003", postingComment);
        DCPostingComment comment = postingStore.retrieveComment("P003", 0);
    
        assertThat("댓글입력", is(comment.getComment()));
        
    }
    
    @Test
    public void testDeleteComment() {
        //
        DCBoardUser userPark = new DCBoardUser("jipark@nextree.co.kr","박종일","010-8580-XXXX");
        DCPosting posting = new DCPosting("0003", "테스트 타이틀", userPark);
        posting.setUsid("P003");
        postingStore.create(posting);
        DCPostingContents contents = new DCPostingContents(posting, "테스트로 포스팅을 해봅시다2");
        postingStore.createContents(contents);
        DCPostingComment postingComment = new DCPostingComment("댓글입력","eschoi@nextree.co.kr");
        postingStore.createComment("P003", postingComment);
        postingStore.deleteComment("P003", 0);
       
    }
    
    @Test
    public void testNextCommentSequence() {
        //
        postingStore.nextCommentSequence(null);
        
    }
}
