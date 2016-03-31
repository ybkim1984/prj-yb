/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.board.sp.jersey.resource.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.sp.jersey.common.BoardServiceFactory;

import com.sun.jersey.api.representation.Form;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/user")
public class BoardUserResource {

	//
	private BoardUserService boardUserService;
	
	public BoardUserResource() {
		//		
		this.boardUserService = BoardServiceFactory.getInstance().createBoardUserService();
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DCBoardUser> findAllUsers(){
		//
		return boardUserService.findAllUsers();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registUser(DCBoardUser user){
		//
		boardUserService.registerUser(user);
	}
	
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public DCBoardUser findUserWithEmail(@PathParam("email") String email){
		//
		return boardUserService.findUserWithEmail(email);
	}
	
	@DELETE
	@Path("{email}")
	public void removeUserWithEmail(@PathParam("email") String email){
		//
		boardUserService.removeUserWithEmail(email);
	}
	
	@GET
	@Path("/boardTeam")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DCBoardTeam> findAllBoardTeams(){
		//
		return boardUserService.findAllBoardTeams();
	}
	
	@POST
	@Path("/boardTeam")
	public String registBoardTeam(@FormParam("teamName") String teamName, @FormParam("adminEmail") String adminEmail){
		//
		return boardUserService.registerBoardTeam(teamName, adminEmail);
	}
	
	@DELETE
	@Path("/boardTeam/{teamUsid}")
	public void removeBoardTeam(@PathParam("teamUsid") String teamUsid){
		//
		boardUserService.removeBoardTeam(teamUsid);
	}
	
	@GET
	@Path("/boardMember/{teamUsid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DCBoardMember> findTeamBoardMembers(@PathParam("teamUsid") String teamUsid){
		//
		return boardUserService.findTeamBoardMembers(teamUsid);
	}
	
	@POST
	@Path("/boardMember/{teamUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToBoardTeam(@PathParam("teamUsid") String teamUsid, List<String> userEmails){
		//
		boardUserService.addToBoardTeam(teamUsid, userEmails);
	}
	
	@DELETE
	@Path("/boardMember/{teamUsid}/{userEmail}")
	public void removeFromBoardTeam(@PathParam("teamUsid") String teamUsid, @PathParam("userEmail") String userEmail){
		//
		boardUserService.removeFromBoardTeam(teamUsid, userEmail);
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public DCBoardUser login(Form form, @Context HttpServletRequest req) {
		//
		String userEmail = form.getFirst("userEmail");
		String password = form.getFirst("password");
		DCBoardUser user = null;
		
		if(boardUserService.loginAsUser(userEmail, password)) {
			user = boardUserService.findUserWithEmail(userEmail);
		}
		
		return user;
	}
	
}
