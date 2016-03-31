package namoo.board.dom2.sp.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.sp.ws.logic.BoardUserWsLogic;
import namoo.board.dom2.sp.ws.logic.PostingWsLogic;
import namoo.board.dom2.sp.ws.logic.SocialBoardWsLogic;
import namoo.board.dom2.sp.ws.server.Server;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class PostingWsTest {
	//
	private PostingWs postingWs;
	
	private SocialBoardWs socialBoardWs;
	
	private BoardUserWs boardUserWs;
	
	//--------------------------------------------------------------------------
	
	@BeforeClass
	public static void setUpBefore() {
		//
		new Server();
	}
	
	@Before
	public void setUp() throws Exception {
		//
		postingWs = new PostingWsLogic(); 
		socialBoardWs = new SocialBoardWsLogic();
		boardUserWs = new BoardUserWsLogic();
		
		String email = "test1@nextree.co.kr";
		String name = "테스트1";
		String phoneNumber = "010-9999-9999";
		DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
		String boardUserJson = JsonUtil.toJson(boardUser);
		boardUserWs.registerUser(boardUserJson);
		
		email = "test2@nextree.co.kr";
		name = "테스트2";
		phoneNumber = "010-8888-8888";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserJson = JsonUtil.toJson(boardUser);
		boardUserWs.registerUser(boardUserJson);
		
		email = "test3@nextree.co.kr";
		name = "테스트3";
		phoneNumber = "010-7777-7777";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserJson = JsonUtil.toJson(boardUser);
		boardUserWs.registerUser(boardUserJson);
		
		email = "test4@nextree.co.kr";
		name = "테스트4";
		phoneNumber = "010-6666-6666";
		boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserJson = JsonUtil.toJson(boardUser);
		boardUserWs.registerUser(boardUserJson);
		
		String teamName = "Board team 1";
		String adminEmail = "test1@nextree.co.kr";
		boardUserWs.registerBoardTeam(teamName, adminEmail);
		
		teamName = "Board team 2";
		adminEmail = "test2@nextree.co.kr";
		boardUserWs.registerBoardTeam(teamName, adminEmail);
		
		String teamUsid = "001";
		String boardName = "게시판1";
		boolean commentable = true;
		socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "001";
		boardName = "게시판2";
		commentable = true;
		socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "002";
		boardName = "게시판3";
		commentable = true;
		socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
		
		teamUsid = "002";
		boardName = "게시판4";
		commentable = true;
		socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
		
		String boardUsid = "001";
		String title = "안녕하세요.";
		String contents = "반갑습니다. 모두 행복이 가득하시길 바랍니다.";
		String writerEmail = "test1@nextree.co.kr";
		PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
		String postingCdoJson = JsonUtil.toJson(postingCdo);
		postingWs.registerPosting(boardUsid, postingCdoJson);
		
		boardUsid = "001";
		title = "좋은날입니다.";
		contents = "오늘 하루도 힘내서 활기차게!!";
		writerEmail = "test2@nextree.co.kr";
		postingCdo = new PostingCdo(title, contents, writerEmail);
		postingCdoJson = JsonUtil.toJson(postingCdo);
		postingWs.registerPosting(boardUsid, postingCdoJson);
	}
	
	@Test
	public void testRegisterPosting() {
		//
		String boardUsid = "001";
		String title = "안녕하세요.";
		String contents = "반갑습니다. 모두 행복이 가득하시길 바랍니다.";
		String writerEmail = "test1@nextree.co.kr";
		PostingCdo postingCdo = new PostingCdo(title, contents, writerEmail);
		String postingCdoJson = JsonUtil.toJson(postingCdo);
		String postingUsid = postingWs.registerPosting(boardUsid, postingCdoJson);
		
		assertNotNull(postingUsid);
	}
	
	@Test
	public void testFindPosting() {
		//
		String postingUsid = "001-0002";
		String postingJson = postingWs.findPosting(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals("001-0002", posting.getUsid());
		assertEquals("좋은날입니다.", posting.getTitle());
		assertEquals("test2@nextree.co.kr", posting.getWriterEmail());
	}
	
	@Test
	public void testFindPostingWithContents() {
		//
		String postingUsid = "001-0001";
		String postingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals("001-0001", posting.getContents().getPostingUsid());
		assertEquals("반갑습니다. 모두 행복이 가득하시길 바랍니다.", posting.getContents().getContents());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindPostingPage() {
		//
		String boardUsid = "001";
		PageCriteria pageCriteria = new PageCriteria(1, 10);
		String pageCriteriaJson = JsonUtil.toJson(pageCriteria);
		
		String pagePostingJson =  postingWs.findPostingPage(boardUsid, pageCriteriaJson);
		
		Map<String,Object> map = (Map<String,Object>) JsonUtil.fromJson(pagePostingJson, Map.class);
        String criteriaJson = (String) map.get("criteriaJson");
        List<String> postingsJson = (List<String>) map.get("postingsJson");
        
        PageCriteria criteira = (PageCriteria) JsonUtil.fromJson(criteriaJson, PageCriteria.class);
        List<DCPosting> postings = new ArrayList<DCPosting>();
        
        for(String postingJson : postingsJson) {
            postings.add((DCPosting) JsonUtil.fromJson(postingJson, DCPosting.class));
        }
        
        Page<DCPosting> pagePosting = new Page<DCPosting>(postings, criteira);
		
		assertNotNull(pagePosting);
		assertEquals(2, pagePosting.getResults().size());
	}
	
	@Test
	public void testModifyPostingOption() {
		//
		String postingUsid = "001-0001";
		
		//
		String postingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals(true, posting.getOption().isCommentable());
		assertEquals(false, posting.getOption().isAnonymousComment());
		
		//
		NameValueList nameValues = NameValueList.getInstance();
		nameValues.add("commentable", false);
		nameValues.add("anonymousComment", true);
		String nameValuesJson = JsonUtil.toJson(nameValues);
		postingWs.modifyPostingOption(postingUsid, nameValuesJson);
		
		String modifiedPostingJson = postingWs.findPosting(postingUsid);
		DCPosting modifiedPosting = (DCPosting)JsonUtil.fromJson(modifiedPostingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals(false, modifiedPosting.getOption().isCommentable());
		assertEquals(true, modifiedPosting.getOption().isAnonymousComment());
	}
	
	@Test
	public void testModifyPostingContents() {
		//
		String postingUsid = "001-0002";
		
		//
		String postingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals("오늘 하루도 힘내서 활기차게!!", posting.getContents().getContents());
		
		//
		String contents = "다함께 커피한잔할까요?";
		postingWs.modifyPostingContents(postingUsid, contents);
		String modifiedPostingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting modifiedPosting = (DCPosting)JsonUtil.fromJson(modifiedPostingJson, DCPosting.class);
		
		assertNotNull(modifiedPosting);
		assertEquals("다함께 커피한잔할까요?", modifiedPosting.getContents().getContents());
	}
	
	@Test
	public void testRemovePosting() {
		//
		String postingUsid = "001-0001";
		postingWs.removePosting(postingUsid);
		
		String postingJson = null;
		try {
			postingJson = postingWs.findPosting(postingUsid);
			fail("Didn't throw exception");
		} catch (Exception e) {}
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNull(posting);
	}
	
	@Test
	public void testIncreasePostingReadCount() {
		//
		String postingUsid = "001-0001";
		
		// 
		String postingJson = postingWs.findPosting(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(posting));
		assertNotNull(posting);
		assertEquals(0, posting.getReadCount());
		
		//
		postingWs.increasePostingReadCount(postingUsid);
		
		//
		postingJson = postingWs.findPosting(postingUsid);
		posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
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
		String commentCdoJson = JsonUtil.toJson(commentCdo);
		postingWs.attachComment(postingUsid, commentCdoJson);
		
		String postingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
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
		postingWs.attachComment(postingUsid, JsonUtil.toJson(commentCdo));
		
		//
		int sequence = 1;
		postingWs.detachComment(postingUsid, sequence);
		
		//
		String postingJson = postingWs.findPostingWithContents(postingUsid);
		DCPosting posting = (DCPosting)JsonUtil.fromJson(postingJson, DCPosting.class);
		
		assertNotNull(posting);
		assertEquals(0, posting.getContents().getCommentBag().getComments().size());
	}
}
