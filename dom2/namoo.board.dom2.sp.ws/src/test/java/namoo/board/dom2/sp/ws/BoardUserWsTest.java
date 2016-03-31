package namoo.board.dom2.sp.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.sp.ws.logic.BoardUserWsLogic;
import namoo.board.dom2.sp.ws.server.Server;
import namoo.board.dom2.util.json.JsonUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardUserWsTest {
	//
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
	}
	
	@Test
	public void testRegisterUser() {
		//
		String email = "elviskim@nextree.co.kr";
		String name = "김준영";
		String phoneNumber = "010-9999-9999";
		DCBoardUser boardUser = new DCBoardUser(email, name, phoneNumber);
		boardUserWs.registerUser(JsonUtil.toJson(boardUser));
		
		String findBoardUserJson = boardUserWs.findUserWithEmail(email);
		DCBoardUser findBoardUser = (DCBoardUser)JsonUtil.fromJson(findBoardUserJson, DCBoardUser.class);
		
		assertNotNull(findBoardUser);
		assertEquals("elviskim@nextree.co.kr", findBoardUser.getEmail());
		assertEquals("김준영", findBoardUser.getName());
		assertEquals("010-9999-9999", findBoardUser.getPhoneNumber());
	}
	
	@Test
	public void testFindUserWithEmail() {
		//
		String userEmail = "test1@nextree.co.kr";
		String boardUserJson = boardUserWs.findUserWithEmail(userEmail);
		DCBoardUser boardUser = (DCBoardUser)JsonUtil.fromJson(boardUserJson, DCBoardUser.class);
		
		assertNotNull(boardUser);
		assertEquals("test1@nextree.co.kr", boardUser.getEmail());
		assertEquals("테스트1", boardUser.getName());
		assertEquals("010-9999-9999", boardUser.getPhoneNumber());
	}
	
	@Test
	public void testFindAllUsers() {
		//
		List<String> allUsersJson = boardUserWs.findAllUsers();
		
		List<DCBoardUser> allUsers = new ArrayList<DCBoardUser>();
		for(String userJson : allUsersJson) {
			allUsers.add((DCBoardUser)JsonUtil.fromJson(userJson, DCBoardUser.class));
		}
		
		assertNotNull(allUsers);
		assertEquals(4, allUsers.size());
	}
	
	@Test
	public void testRemoveUserWithEmail() {
		//
		String userEmail = "test1@nextree.co.kr";
		boardUserWs.removeUserWithEmail(userEmail);
		
		List<String> allUsersJson = boardUserWs.findAllUsers();
		
		List<DCBoardUser> allUsers = new ArrayList<DCBoardUser>();
		for(String userJson : allUsersJson) {
			allUsers.add((DCBoardUser)JsonUtil.fromJson(userJson, DCBoardUser.class));
		}
		
		assertNotNull(allUsers);
		assertEquals(3, allUsers.size());
	}
	
	@Test
	public void testLoginAsUser() {
		//
		String userEmail = "test1@nextree.co.kr";
		String password = "1234";
		boolean result = boardUserWs.loginAsUser(userEmail, password);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testRegisterBoardTeam() {
		//
		String teamName = "컨설팅팀";
		String adminEmail = "test1@nextree.co.kr";
		String boardId = boardUserWs.registerBoardTeam(teamName, adminEmail);
		
		assertNotNull(boardId);
		assertEquals("003", boardId);
	}
	
	@Test
	public void testFindAllBoardTeams() {
		//
		List<String> allBoardTeamsJson = boardUserWs.findAllBoardTeams();
		
		List<DCBoardTeam> allBoardTeams = new ArrayList<DCBoardTeam>();
		for(String boardTeamJson : allBoardTeamsJson) {
			allBoardTeams.add((DCBoardTeam)JsonUtil.fromJson(boardTeamJson, DCBoardTeam.class));
		}
		
		assertNotNull(allBoardTeams);
		assertEquals(2, allBoardTeams.size());
	}
	
	@Test
	public void testRemoveBoardTeam() {
		//
		String teamUsid = "001";
		boardUserWs.removeBoardTeam(teamUsid);
		
		List<String> allBoardTeamsJson = boardUserWs.findAllBoardTeams();
		
		List<DCBoardTeam> allBoardTeams = new ArrayList<DCBoardTeam>();
		for(String boardTeamJson : allBoardTeamsJson) {
			allBoardTeams.add((DCBoardTeam)JsonUtil.fromJson(boardTeamJson, DCBoardTeam.class));
		}
		
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
		
		boardUserWs.addToBoardTeam(teamUsid, userEmails);
		
		List<String> boardMembersJson = boardUserWs.findTeamBoardMembers(teamUsid);
		List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
		for(String boardMemberJson : boardMembersJson) {
			boardMembers.add((DCBoardMember)JsonUtil.fromJson(boardMemberJson, DCBoardMember.class));
		}
		
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
		
		boardUserWs.addToBoardTeam(teamUsid, userEmails);
		
		List<String> boardMembersJson = boardUserWs.findTeamBoardMembers(teamUsid);
		List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
		for(String boardMemberJson : boardMembersJson) {
			boardMembers.add((DCBoardMember)JsonUtil.fromJson(boardMemberJson, DCBoardMember.class));
		}
		
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
		
		boardUserWs.addToBoardTeam(teamUsid, userEmails);
		
		String userEmail = "test3@nextree.co.kr";
		boardUserWs.removeFromBoardTeam(teamUsid, userEmail);
		
		List<String> boardMembersJson = boardUserWs.findTeamBoardMembers(teamUsid);
		List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
		for(String boardMemberJson : boardMembersJson) {
			boardMembers.add((DCBoardMember)JsonUtil.fromJson(boardMemberJson, DCBoardMember.class));
		}
		
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
		
		boardUserWs.addToBoardTeam(teamUsid, userEmails);
		
		List<String> boardMembersJson = boardUserWs.findTeamBoardMembers(teamUsid);
		List<DCBoardMember> boardMembers = new ArrayList<DCBoardMember>();
		for(String boardMemberJson : boardMembersJson) {
			boardMembers.add((DCBoardMember)JsonUtil.fromJson(boardMemberJson, DCBoardMember.class));
		}
		
		assertNotNull(boardMembers);
		assertEquals(3, boardMembers.size());
		assertEquals("test2@nextree.co.kr", boardMembers.get(0).getUser().getEmail());
	}
}
