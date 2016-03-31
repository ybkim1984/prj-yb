/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 28.
 */
package namoo.board.publish.socket;

import java.util.List;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.json.JsonRequest;
import namoo.board.dom2.util.json.JsonResponse;
import namoo.board.dom2.util.namevalue.NameValueList;

public class SocialBoardPublisher {
	//
	private static SocialBoardPublisher socialBoardPublisher;
	private SocialBoardService socialBoardService;
	
	private SocialBoardPublisher() {
		//
		BoardStoreLifecycler storeLifecycler = BoardStoreMyBatisLifecycler.getInstance();
		BoardServiceLifecycler serviceLifecycler = BoardServicePojoLifecycler.getInstance(storeLifecycler);
		this.socialBoardService = serviceLifecycler.getSocialBoardService();
	}
	
	public synchronized static SocialBoardPublisher getInstance() {
		//
		if (socialBoardPublisher == null) {
			socialBoardPublisher = new SocialBoardPublisher();
		}
		
		return socialBoardPublisher;
	}
	
	public JsonResponse request(JsonRequest request) {
		//
		switch(request.getRequestId()){
		case "socialBoardService.registerSocialBoard":
			return registerSocialBoard(request);
		case "socialBoardService.findSocialBoard":
			return findSocialBoard(request);
		case "socialBoardService.findAllSocialBoards":
			return findAllSocialBoards(request);
		case "socialBoardService.modifySocialBoard":
			return modifySocialBoard(request);
		case "socialBoardService.removeSocialBoard":
			return removeSocialBoard(request);
		
		}
		return JsonResponse.getExceptionInstance(request, "Bad requestId");
	}
	
	private JsonResponse registerSocialBoard(JsonRequest request) {
		// 
		String teamUsid = request.getJsonParam(0); 
		String boardName = request.getJsonParam(1); 
		String commentable = request.getJsonParam(2); 
		
		Boolean comment = true;
		if("on".equals(commentable)) {
			comment = false;
		}
		
		try {
			String result = socialBoardService.registerSocialBoard(teamUsid,boardName,comment); 
			return JsonResponse.getInstance(request, result); 
		} catch (Exception e) {
			return JsonResponse.getExceptionInstance(request, e.getMessage()); 
		}
	}

	private JsonResponse findSocialBoard(JsonRequest request) {
		// 
		String boardUsid = request.getParamAsStr(0);  
		try {
			DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
			return JsonResponse.getInstance(request, socialBoard); 
		} catch (Exception e) {
			return JsonResponse.getExceptionInstance(request, e.getMessage()); 
		}
	}
	
	private JsonResponse findAllSocialBoards(JsonRequest request) {
		// 
		try {
			List<DCSocialBoard> socialBoards = socialBoardService.findAllSocialBoards();
			return JsonResponse.getInstance(request, socialBoards); 
		} catch (Exception e) {
			return JsonResponse.getExceptionInstance(request, e.getMessage()); 
		}
	}
	
	private JsonResponse modifySocialBoard(JsonRequest request) {
		// 
		String boardUsid = request.getParamAsStr(0);  
		String boardName = request.getParamAsStr(1);  
		
		NameValueList nameValues = NameValueList.getInstance();			
		nameValues.add("name", boardName);
		try {
			socialBoardService.modifySocialBoard(boardUsid, nameValues);
			return JsonResponse.getInstance(request); 
		} catch (Exception e) {
			return JsonResponse.getExceptionInstance(request, e.getMessage()); 
		}
	}

	private JsonResponse removeSocialBoard(JsonRequest request) {
		// 
		String boardUsid = request.getParamAsStr(0);  
		try {
			socialBoardService.removeSocialBoard(boardUsid);
			return JsonResponse.getInstance(request); 
		} catch (Exception e) {
			return JsonResponse.getExceptionInstance(request, e.getMessage()); 
		}
	}
}
