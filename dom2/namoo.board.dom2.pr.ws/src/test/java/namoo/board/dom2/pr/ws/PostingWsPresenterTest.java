/*
u * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.sp.ws.server.Server;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class PostingWsPresenterTest {
	//
	private PostingWsPresenter postingWsPresenter;
	
	private SocialBoardWsPresenter socialBoardWsPresenter;
	
	private BoardUserWsPresenter boardUserWsPresenter;
	
	//--------------------------------------------------------------------------
	
	@BeforeClass
	public static void setUpBefore() {
		//
		new Server();
	}
	
	@Before
	public void setUp() throws Exception {
		//
		postingWsPresenter = new PostingWsPresenter(); 
		socialBoardWsPresenter = new SocialBoardWsPresenter();
		boardUserWsPresenter = new BoardUserWsPresenter();
		
		String email = "test1@nextree.co.kr";
		String name = "테스트1";
		String phoneNumber = "010-9999-9999";
		DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWsPresenter.registerUser(boardUser);
		
		email = "test2@nextree.co.kr";
		name = "테스트2";
		phoneNumber = "010-8888-8888";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWsPresenter.registerUser(boardUser);
		
		email = "test3@nextree.co.kr";
		name = "테스트3";
		phoneNumber = "010-7777-7777";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWsPresenter.registerUser(boardUser);
		
		email = "test4@nextree.co.kr";
		name = "테스트4";
		phoneNumber = "010-6666-6666";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWsPresenter.registerUser(boardUser);
		
		String teamName = "Board team 1";
		String adminEmail = "test1@nextree.co.kr";
		boardUserWsPresenter.registerBoardTeam(teamName, adminEmail);
		
		teamName = "Board team 2";
		adminEmail = "test2@nextree.co.kr";
		boardUserWsPresenter.registerBoardTeam(teamName, adminEmail);
		
		String teamUsid = "001";
		String boardName = "게시판1";
		boolean commentable = true;
		socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "001";
		boardName = "게시판2";
		commentable = true;
		socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "002";
		boardName = "게시판3";
		commentable = true;
		socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "002";
		boardName = "게시판4";
		commentable = true;
		socialBoardWsPresenter.registerSocialBoard(teamUsid, boardName, commentable);
		
		String boardUsid = "001";
		String title = "안녕하세요.";
		String contents = "반갑습니다. 모두 행복이 가득하시길 바랍니다.";
		String writerEmail = "test1@nextree.co.kr";
		PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
		postingWsPresenter.registerPosting(boardUsid, postingCdo);
		
		boardUsid = "001";
		title = "좋은날입니다.";
		contents = "오늘 하루도 힘내서 활기차게!!";
		writerEmail = "test2@nextree.co.kr";
		postingCdo = new PostingCdo(title, contents, writerEmail);
		postingWsPresenter.registerPosting(boardUsid, postingCdo);
	}
	
	@Test
	public void testRegisterPosting() {
		//
		String boardUsid = "001";
		String title = "안녕하세요.";
		String contents = "반갑습니다. 모두 행복이 가득하시길 바랍니다.";
		String writerEmail = "test1@nextree.co.kr";
		PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
		String postingUsid = postingWsPresenter.registerPosting(boardUsid, postingCdo);
		
		assertNotNull(postingUsid);
	}
	
	@Test
	public void testFindPosting() {
		//
		String postingUsid = "001-0002";
		DCPosting posting = postingWsPresenter.findPosting(postingUsid);
		
		assertNotNull(posting);
		assertEquals("001-0002", posting.getUsid());
		assertEquals("좋은날입니다.", posting.getTitle());
		assertEquals("test2@nextree.co.kr", posting.getWriterEmail());
	}
	
	@Test
	public void testFindPostingWithContents() {
		//
		String postingUsid = "001-0001";
		DCPosting posting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(posting);
		assertEquals("001-0001", posting.getContents().getPostingUsid());
		assertEquals("반갑습니다. 모두 행복이 가득하시길 바랍니다.", posting.getContents().getContents());
	}
	
	@Test
	public void testFindPostingPage() {
		//
		String boardUsid = "001";
		PageCriteria pageCriteria = new PageCriteria(1, 10);
		
		Page<DCPosting> pagePosting = postingWsPresenter.findPostingPage(boardUsid, pageCriteria);
		
		assertNotNull(pagePosting);
		assertEquals(2, pagePosting.getResults().size());
	}
	
	@Test
	public void testModifyPostingOption() {
		//
		String postingUsid = "001-0001";
		
		//
		DCPosting posting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(posting);
		assertEquals(true, posting.getOption().isCommentable());
		assertEquals(false, posting.getOption().isAnonymousComment());
		
		//
		NameValueList nameValues = NameValueList.getInstance();
		nameValues.add("commentable", false);
		nameValues.add("anonymousComment", true);
		postingWsPresenter.modifyPostingOption(postingUsid, nameValues);
		
		DCPosting modifiedPosting = postingWsPresenter.findPosting(postingUsid);
		assertNotNull(posting);
		assertEquals(false, modifiedPosting.getOption().isCommentable());
		assertEquals(true, modifiedPosting.getOption().isAnonymousComment());
	}
	
	@Test
	public void testModifyPostingContents() {
		//
		String postingUsid = "001-0002";
		
		//
		DCPosting posting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(posting);
		assertEquals("오늘 하루도 힘내서 활기차게!!", posting.getContents().getContents());
		
		//
		String contents = "다함께 커피한잔할까요?";
		postingWsPresenter.modifyPostingContents(postingUsid, contents);
		DCPosting modifiedPosting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(modifiedPosting);
		assertEquals("다함께 커피한잔할까요?", modifiedPosting.getContents().getContents());
	}
	
	@Test
	public void testRemovePosting() {
		//
		String postingUsid = "001-0001";
		postingWsPresenter.removePosting(postingUsid);
		
		DCPosting posting = postingWsPresenter.findPosting(postingUsid);
		
		assertNull(posting);
	}
	
	@Test
	public void testIncreasePostingReadCount() {
		//
		String postingUsid = "001-0001";
		
		// 
		DCPosting posting = postingWsPresenter.findPosting(postingUsid);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(posting));
		assertNotNull(posting);
		assertEquals(0, posting.getReadCount());
		
		//
		postingWsPresenter.increasePostingReadCount(postingUsid);
		
		//
		posting = postingWsPresenter.findPosting(postingUsid);
		
		assertNotNull(posting);
		assertEquals(1, posting.getReadCount());
	}
	
	@Test
	public void testAttachComment() {
		//
		String postingUsid = "001-0001";
		String comment = "반갑습니다.";
		String email = "test2@nextree.co.kr";
		CommentCdo commentCdo = new CommentCdo(comment, email);
		postingWsPresenter.attachComment(postingUsid, commentCdo);
		
		DCPosting posting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(posting);
		assertEquals("반갑습니다.", posting.getContents().getCommentBag().getComments().get(0).getComment());
		assertEquals(1, posting.getContents().getCommentBag().getComments().get(0).getSequence());
		assertEquals("test2@nextree.co.kr", posting.getContents().getCommentBag().getComments().get(0).getWriterEmail());
	}
	
	@Test
	public void testDetachComment() {
		//
		String postingUsid = "001-0001";
		
		//
		String comment = "반갑습니다.";
		String email = "test2@nextree.co.kr";
		CommentCdo commentCdo = new CommentCdo(comment, email);
		postingWsPresenter.attachComment(postingUsid, commentCdo);
		
		//
		int sequence = 1;
		postingWsPresenter.detachComment(postingUsid, sequence);
		
		//
		DCPosting posting = postingWsPresenter.findPostingWithContents(postingUsid);
		
		assertNotNull(posting);
		assertEquals(0, posting.getContents().getCommentBag().getComments().size());
	}
}
