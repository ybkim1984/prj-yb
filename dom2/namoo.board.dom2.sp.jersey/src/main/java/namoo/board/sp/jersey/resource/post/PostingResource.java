/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.board.sp.jersey.resource.post;

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

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;
import namoo.board.sp.jersey.common.BoardServiceFactory;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/post")
public class PostingResource {
	
	//
	private PostingService postingService;
	
	public PostingResource() {
		//
		this.postingService = BoardServiceFactory.getInstance().createPostingService();
	}
	
	@POST
	@Path("/{boardUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String registPosting(@PathParam("boardUsid") String boardUsid, PostingCdo postingCdo){
		//
		return postingService.registerPosting(boardUsid, postingCdo);
	}
	
	@GET
	@Path("/{postingUsid}")
	@Produces(MediaType.APPLICATION_JSON)
	public DCPosting findPosting(@PathParam("postingUsid") String postingUsid){
		//
		return postingService.findPosting(postingUsid);
	}
	
	@POST
	@Path("/page/{boardUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Page<DCPosting> findPostingPage(@PathParam("boardUsid") String boardUsid, PageCriteria pageCriteria ){
		//
		return postingService.findPostingPage(boardUsid, pageCriteria);
	}
	
	@GET
	@Path("/contents/{postingUsid}")
	@Produces(MediaType.APPLICATION_JSON)
	public DCPosting findPostingWithContents(@PathParam("postingUsid") String postingUsid){
		//
		return postingService.findPostingWithContents(postingUsid);
	}
	
	@PUT
	@Path("/{postingUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modifyPostingOption(@PathParam("postingUsid")String postingUsid, NameValueList nameValues) {
		//
		postingService.modifyPostingOption(postingUsid, nameValues);
	}
	
	@PUT
	public void modifyPostingContents(@FormParam("postingUsid")String postingUsid, @FormParam("contents") String contents) {
		//
		postingService.modifyPostingContents(postingUsid, contents);
	}
	
	@DELETE
	@Path("/{postingUsid}")
	public void removePosting(@PathParam("postingUsid")String postingUsid){
		//
		postingService.removePosting(postingUsid);
	}
	
	@PUT
	@Path("/count/{postingUsid}")
	public void increasePostingReadCount(@PathParam("postingUsid")String postingUsid){
		//
		postingService.increasePostingReadCount(postingUsid);
	}
	
	@DELETE
	@Path("/comment/{postingUsid}/{sequence}")
	public void detachComment(@PathParam("postingUsid")String postingUsid, @PathParam("sequence") int sequence){
		//
		postingService.detachComment(postingUsid,sequence);
	}
	
	@POST
	@Path("/comment/{postingUsid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void attachComment(@PathParam("postingUsid")String postingUsid, CommentCdo commentCdo){
		//
		postingService.attachComment(postingUsid,commentCdo);
	}
}
