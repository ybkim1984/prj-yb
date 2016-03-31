/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.mongojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namoo.board.dom2.da.mongojava.BoardTeamMongoJavaStore;
import namoo.board.dom2.da.mongojava.BoardUserMongoJavaStore;
import namoo.board.dom2.da.mongojava.SocialBoardMongoJavaStore;
import namoo.board.dom2.da.mongojava.shared.BaseMongoTestCase;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.store.BoardUserStore;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SocialBoardMongoJavaStoreTest extends BaseMongoTestCase {
    //
    private SocialBoardStore boardStore = new SocialBoardMongoJavaStore();
    private BoardTeamStore teamStore = new BoardTeamMongoJavaStore();
    private BoardUserStore userStore = new BoardUserMongoJavaStore();
    
    private final String boardUsid = "001";
    
    @Before
    public void setUp() throws Exception {
        // 
        // 사용자 데이터 생성
        List<DCBoardUser> users = new ArrayList<DCBoardUser>();
        
        users.add(new DCBoardUser("tsong@nextree.co.kr", "송태국", "010-1111-2222"));
        users.add(new DCBoardUser("hkkang@nextree.co.kr", "강형구", "010-1234-5678"));
        users.add(new DCBoardUser("syhan@nextree.co.kr", "한성영", "010-0000-0000"));
        users.add(new DCBoardUser("eschoi@nextree.co.kr", "최은선", "010-2222-2222"));
        
        for (DCBoardUser user : users) {
            userStore.create(user);
        }

        // 팀 데이터 생성
        DCBoardTeam team = new DCBoardTeam("컨설팅팀", new DCBoardUser("tsong@nextree.co.kr"));
        team.setUsid("001");
        
        teamStore.create(team);
        //teamStore.nextSequence();
        
        // 팀 멤버 데이터 생성
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
        
        // 게시판 데이터 생성
        DCSocialBoard board = new DCSocialBoard("001", "영화게시판", new Date(), "001", true);
        boardStore.create(board);
        //boardStore.nextSequence();
        
        board = new DCSocialBoard("002", "일정게시판", new Date(), "001", false);
        boardStore.create(board);
        //boardStore.nextSequence();
    }
    
    
    @After
    public void tearDown() throws Exception {
        //
        //MongoClientFactory.getDB().getCollection("boardusers").drop();
    }
    //--------------------------------------------------------------------------
    
    @Test
    public void testCreate() throws EmptyResultException {
        //
        DCBoardTeam team = teamStore.retrieveByName("컨설팅팀");
        DCSocialBoard board = new DCSocialBoard(team, "스터디게시판");
        
        board.setUsid("003");
        
        String createdId = boardStore.create(board);
        
        DCSocialBoard created = boardStore.retrieve(createdId);
        assertNotNull(created);
        assertEquals(board.getName(), created.getName());
        assertEquals(true, board.isCommentable());
        assertEquals(board.getTeamUsid(), created.getTeamUsid());
    }
    
    @Test
    public void testRetrieve() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        assertNotNull(board);
        assertEquals("영화게시판", board.getName());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }
    
    @Test
    public void testRetrieveByName() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieveByName("영화게시판");
        assertNotNull(board);
        assertEquals(this.boardUsid, board.getUsid());
        assertEquals(true, board.isCommentable());
        assertEquals("001", board.getTeamUsid());
    }
    
    @Test
    public void testRetrieveAll() {
        //
        List<DCSocialBoard> allBoards = boardStore.retrieveAll();
        
        assertEquals(allBoards.size(), 9);
    }
    
    @Test
    public void testUpdate() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        board.setName("팁게시판");
        board.setCommentable(false);
        
        boardStore.update(board);
        
        
        DCSocialBoard updated = boardStore.retrieve(this.boardUsid);
        assertEquals(board.getName(), updated.getName());
        assertEquals(board.isCommentable(), updated.isCommentable());
    }
    
    @Test
    public void testDelete() throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.retrieve(this.boardUsid);
        assertNotNull(board);
        
        boardStore.delete(this.boardUsid);
        
        try {
            boardStore.retrieve(this.boardUsid);
            fail("Didn't throw exception");
        } catch (EmptyResultException e) {}
    }
    
    @Test
    public void testNextSequence() {
        //
        boardStore.nextSequence();
        
    }
}
