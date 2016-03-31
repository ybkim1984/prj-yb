/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.board.sp.jersey.resource.board;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.sp.jersey.common.BoardServiceFactory;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/board")
public class SocialBoardResource {
	
	//
	private SocialBoardService socialBoardService;
	
	public SocialBoardResource(){
		//
		socialBoardService = BoardServiceFactory.getInstance().createSocialBoardService();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String registSocialBoard(@FormParam("teamUsid")String teamUsid, @FormParam("boardName") String boardName,  @FormParam("commentable") boolean commentable){
		//
		return socialBoardService.registerSocialBoard(teamUsid, boardName, commentable);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DCSocialBoard> findAllSocialBoards(){
		//
		return socialBoardService.findAllSocialBoards();
	}
	
	@GET
	@Path("/{boardUsid}")
	@Produces(MediaType.APPLICATION_JSON)
	public DCSocialBoard findSocialBoard(@PathParam("boardUsid") String boardUsid){
		//
		return socialBoardService.findSocialBoard(boardUsid);
	}
	
	@PUT
	@Path("/{boardUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modifySocialBoard(@PathParam("boardUsid") String boardUsid, NameValueList nameValues){
		//
		socialBoardService.modifySocialBoard(boardUsid, nameValues);
	}
	
	@DELETE
	@Path("/{boardUsid}")
	public void removeSocialBoard(@PathParam("boardUsid") String boardUsid){
		//
		socialBoardService.removeSocialBoard(boardUsid);
	}
}