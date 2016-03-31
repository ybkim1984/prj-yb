/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.sp.ws.server.Server;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class BoardUserWsPresenterTest {
	//
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
	}
	
	@Test
	public void testRegisterUser() {
		//
		String email = "elviskim@nextree.co.kr";
		String name = "김준영";
		String phoneNumber = "010-9999-9999";
		DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWsPresenter.registerUser(boardUser);
		
		DCBoardUser findBoardUser = boardUserWsPresenter.findUserWithEmail(email);
		
		assertNotNull(findBoardUser);
		assertEquals("elviskim@nextree.co.kr", findBoardUser.getEmail());
		assertEquals("김준영", findBoardUser.getName());
		assertEquals("010-9999-9999", findBoardUser.getPhoneNumber());
	}
	
	@Test
	public void testFindUserWithEmail() {
		//
		String userEmail = "test1@nextree.co.kr";
		DCBoardUser boardUser = boardUserWsPresenter.findUserWithEmail(userEmail);
		
		assertNotNull(boardUser);
		assertEquals("test1@nextree.co.kr", boardUser.getEmail());
		assertEquals("테스트1", boardUser.getName());
		assertEquals("010-9999-9999", boardUser.getPhoneNumber());
	}
	
	@Test
	public void testFindAllUsers() {
		//
		List<DCBoardUser> allUsers = boardUserWsPresenter.findAllUsers();
		
		assertNotNull(allUsers);
		assertEquals(4, allUsers.size());
	}
	
	@Test
	public void testRemoveUserWithEmail() {
		//
		String userEmail = "test1@nextree.co.kr";
		boardUserWsPresenter.removeUserWithEmail(userEmail);
		
		List<DCBoardUser> allUsers = boardUserWsPresenter.findAllUsers();
		
		assertNotNull(allUsers);
		assertEquals(3, allUsers.size());
	}
	
	@Test
	public void testLoginAsUser() {
		//
		String userEmail = "test1@nextree.co.kr";
		String password = "1234";
		boolean result = boardUserWsPresenter.loginAsUser(userEmail, password);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testRegisterBoardTeam() {
		//
		String teamName = "컨설팅팀";
		String adminEmail = "test1@nextree.co.kr";
		String boardId = boardUserWsPresenter.registerBoardTeam(teamName, adminEmail);
		
		assertNotNull(boardId);
		assertEquals("003", boardId);
	}
	
	@Test
	public void testFindAllBoardTeams() {
		//
		List<DCBoardTeam> allBoardTeams = boardUserWsPresenter.findAllBoardTeams();
		
		assertNotNull(allBoardTeams);
		assertEquals(2, allBoardTeams.size());
	}
	
	@Test
	public void testRemoveBoardTeam() {
		//
		String teamUsid = "001";
		boardUserWsPresenter.removeBoardTeam(teamUsid);
		
		List<DCBoardTeam> allBoardTeams = boardUserWsPresenter.findAllBoardTeams();
		
		assertNotNull(allBoardTeams);
		assertEquals(1, allBoardTeams.size());
	}
	
	@Test
	public void testAddToBoardTeam() {
		//
		String teamUsid = "002";
		List<String> userEmails = new ArrayList<String>();
		
		userEmails.add("test3@nextree.co.kr");
		userEmails.add("test4@nextree.co.kr");
		
		boardUserWsPresenter.addToBoardTeam(teamUsid, userEmails);
		
		List<DCBoardMember> boardMembers = boardUserWsPresenter.findTeamBoardMembers(teamUsid);
		
		assertNotNull(boardMembers);
		assertEquals(3, boardMembers.size());
	}
	
	@Test
	public void testFindTeamBoardMembers() {
		//
		String teamUsid = "002";
		List<String> userEmails = new ArrayList<String>();
		
		userEmails.add("test3@nextree.co.kr");
		userEmails.add("test4@nextree.co.kr");
		
		boardUserWsPresenter.addToBoardTeam(teamUsid, userEmails);
		
		List<DCBoardMember> boardMembers = boardUserWsPresenter.findTeamBoardMembers(teamUsid);
		
		assertNotNull(boardMembers);
		assertEquals(3, boardMembers.size());
	}
	
	@Test
	public void testRemoveFromBoardTeam() {
		//
		String teamUsid = "002";
		List<String> userEmails = new ArrayList<String>();
		
		userEmails.add("test3@nextree.co.kr");
		userEmails.add("test4@nextree.co.kr");
		
		boardUserWsPresenter.addToBoardTeam(teamUsid, userEmails);
		
		String userEmail = "test3@nextree.co.kr";
		boardUserWsPresenter.removeFromBoardTeam(teamUsid, userEmail);
		
		List<DCBoardMember> boardMembers = boardUserWsPresenter.findTeamBoardMembers(teamUsid);
		
		assertNotNull(boardMembers);
		assertEquals(2, boardMembers.size());
	}
	
	@Test 
	public void testFindTeamBoardMemberss() {
		//
		String teamUsid = "002";
		List<String> userEmails = new ArrayList<String>();
		
		userEmails.add("test3@nextree.co.kr");
		userEmails.add("test4@nextree.co.kr");
		
		boardUserWsPresenter.addToBoardTeam(teamUsid, userEmails);
		
		List<DCBoardMember> boardMembers = boardUserWsPresenter.findTeamBoardMembers(teamUsid);
		
		assertNotNull(boardMembers);
		assertEquals(3, boardMembers.size());
		assertEquals("test2@nextree.co.kr", boardMembers.get(0).getUser().getEmail());
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(boardMembers));
	}
}
