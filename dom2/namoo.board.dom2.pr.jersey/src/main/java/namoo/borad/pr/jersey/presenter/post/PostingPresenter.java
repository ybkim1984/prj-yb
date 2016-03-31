/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.borad.pr.jersey.presenter.post;

import java.util.List;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;
import namoo.borad.pr.jersey.request.JerseyPresentRequester;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource.Builder;

public class PostingPresenter implements PostingService{
	
	//
	private JerseyPresentRequester requester;
	
	public PostingPresenter() {
		//
		this.requester = new JerseyPresentRequester();
	}
	
	@Override
	public void attachComment(String postingUsid, CommentCdo commentCdo) {
		//
		String url ="/post/comment/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.post(ClientResponse.class,JsonUtil.toJson(commentCdo) );
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
	
	@Override
	public void detachComment(String postingUsid, int sequence) {
		//
		String url ="/post/comment/"+postingUsid+"/"+sequence;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.delete(ClientResponse.class);
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
	
	@Override
	public DCPosting findPosting(String postingUsid) {
		//
		String url ="/post/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.get(ClientResponse.class);
		
		String resultJson;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		resultJson = response.getEntity(String.class);
		return JsonUtil.fromJson(resultJson, DCPosting.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<DCPosting> findPostingPage(String boardUsid, PageCriteria pageCriteria) {
		// 
		String url ="/post/page/"+boardUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.post(ClientResponse.class, JsonUtil.toJson(pageCriteria));
		
		String resultJson;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		resultJson = response.getEntity(String.class);
		Page<DCPosting> page = (Page<DCPosting>)JsonUtil.fromJson(resultJson, Page.class); 
		List<DCPosting> list = JsonUtil.fromJsonArray(page.getResults().toString(), DCPosting[].class);
		page.setResults(list);
		return page; 
	}
	
	@SuppressWarnings("static-access")
	@Override
	public DCPosting findPostingWithContents(String postingUsid) {
		//
		String url ="/post/contents/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.get(ClientResponse.class);
		
		String resultJson;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		resultJson = response.getEntity(String.class);
		return (DCPosting)new JsonUtil().fromJson(resultJson, DCPosting.class);
	}
	
	@Override
	public void increasePostingReadCount(String postingUsid) {
		//
		String url ="/post/count/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.put(ClientResponse.class);
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
	
	@Override
	public void modifyPostingContents(String postingUsid, String contents) {
		//
		String url ="/post";
		String param = "postingUsid=" + postingUsid +"&contents=" +contents;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.put(ClientResponse.class,param);
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	};
	
	@Override
	public void modifyPostingOption(String postingUsid, NameValueList nameValues) {
		//
		String url ="/post/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.put(ClientResponse.class, JsonUtil.toJson(nameValues));
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
	
	@Override
	public String registerPosting(String boardUsid, PostingCdo postingCdo) {
		// 
		String url ="/post/"+boardUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.post(ClientResponse.class, JsonUtil.toJson(postingCdo));
		
		String responseStr;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			responseStr = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(responseStr, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		responseStr = response.getEntity(String.class);
		return responseStr;
	}
	
	@Override
	public void removePosting(String postingUsid) {
		// 
		String url ="/post/"+postingUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.delete(ClientResponse.class);
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
}
