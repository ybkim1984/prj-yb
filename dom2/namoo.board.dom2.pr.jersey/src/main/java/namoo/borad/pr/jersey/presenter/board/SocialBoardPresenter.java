/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jsseo@nextree.co.kr">Seo, Jisu</a>
 * @since 2015. 2. 10.
 */

package namoo.borad.pr.jersey.presenter.board;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.borad.pr.jersey.request.JerseyPresentRequester;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource.Builder;

public class SocialBoardPresenter implements SocialBoardService{
	
	//
	private JerseyPresentRequester requester;
	
	
	public SocialBoardPresenter() {
		//
		this.requester = new JerseyPresentRequester();
	}
	
	@Override
	public List<DCSocialBoard> findAllSocialBoards() {
		//
		String url ="/board";
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.get(ClientResponse.class);
		
		String resultJson;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		resultJson = response.getEntity(String.class);
		return JsonUtil.fromJsonArray(resultJson, DCSocialBoard[].class);
	}
	
	@Override
	public DCSocialBoard findSocialBoard(String boardUsid) {
		//
		String url ="/board/"+boardUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.get(ClientResponse.class);
		
		String resultJson;
		if (response.getStatus() != Status.OK.getStatusCode()) {
			resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
		
		resultJson = response.getEntity(String.class);
		return JsonUtil.fromJson(resultJson, DCSocialBoard.class);
	}
	
	@Override
	public void modifySocialBoard(String boardUsid, NameValueList nameValues) {
		//
		String url ="/board/"+boardUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.put(ClientResponse.class, JsonUtil.toJson(nameValues));
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
	
	@Override
	public String registerSocialBoard(String teamUsid, String boardName, boolean commentable) {
		//
		String url ="/board";
		String param = "teamUsid=" + teamUsid + "&boardName=" + boardName + "&commentable=" + commentable;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.post(ClientResponse.class, param);
		
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
	public void removeSocialBoard(String boardUsid) {
		//
		String url ="/board/"+boardUsid;
		
		Builder requestBuilder = requester.getRequestBuilder(url);
		ClientResponse response = requestBuilder.delete(ClientResponse.class);
		
		if (response.getStatus() != Status.NO_CONTENT.getStatusCode()) {
			String resultJson = response.getEntity(String.class);
			NamooBoardException exception = JsonUtil.fromJson(resultJson, NamooBoardException.class);
			throw new NamooBoardException(exception.getMessage(), exception.getCode());
		}
	}
}
