package namoo.board.present.socket;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.json.JsonRequest;
import namoo.board.dom2.util.json.JsonResponse;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;

public class SocialBoardSocketPresenter implements SocialBoardService{
	//
	private SocketRequester requester; 
	
	public SocialBoardSocketPresenter() {
		//
		this.requester = new SocketRequester();
	}

	@Override
	public String registerSocialBoard(String teamUsid, String boardName,
			boolean commentable) {
		// 
		String requestId = "socialBoardService.registerSocialBoard"; 
		JsonRequest request = JsonRequest.getInstance(requestId);
		System.out.println(teamUsid + boardName + commentable);
		request.addParam(teamUsid); 
		request.addParam(boardName);
		request.addParam(commentable);
		
		JsonResponse response = requester.request(request); 
		if (!response.isSuccess()) {
			throw new NamooBoardException(response.getExceptionStr()); 
		}
		
		return response.getResultAsStr(); 
	}

	@Override
	public DCSocialBoard findSocialBoard(String boardUsid) {
		// 
		String requestId = "socialBoardService.findSocialBoard"; 
		JsonResponse response = requester.request(JsonRequest.getInstance(requestId).addParam(boardUsid)); 
		if(!response.isSuccess()) {
			throw new NamooBoardException(response.getExceptionStr()); 
		}
		
		DCSocialBoard socialBoard = (DCSocialBoard)JsonUtil.fromJson(response.getResultJson(), DCSocialBoard.class); 
		
		return socialBoard;
	}

	@Override
	public List<DCSocialBoard> findAllSocialBoards() {
		// 
		String requestId = "socialBoardService.findAllSocialBoards"; 
		JsonResponse response = requester.request(JsonRequest.getInstance(requestId)); 
		if(!response.isSuccess()) {
			throw new NamooBoardException(response.getExceptionStr()); 
		}
		//Type listType = new TypeToken<ArrayList<DCSocialBoard>>() {}.getType();
		//<DCSocialBoard> socialBoards = new Gson().fromJson(response.getResultJson(), listType);
		List<DCSocialBoard> socialBoards = JsonUtil.fromJsonArray(response.getResultJson(), DCSocialBoard[].class); 
		
		return socialBoards;
	}

	@Override
	public void modifySocialBoard(String boardUsid, NameValueList nameValues) {
		//
		String requestId = "socialBoardService.modifySocialBoard"; 
		JsonRequest request = JsonRequest.getInstance(requestId); 
		request.addParam(boardUsid); 
		request.addParam(nameValues);
		
		JsonResponse response = requester.request(request);
		if(!response.isSuccess()) {
			throw new NamooBoardException(response.getExceptionStr()); 
		}
	}

	@Override
	public void removeSocialBoard(String boardUsid) {
		//
		String requestId = "socialBoardService.removeSocialBoard"; 
		JsonResponse response = requester.request(JsonRequest.getInstance(requestId).addParam(boardUsid)); 
		if(!response.isSuccess()) {
			throw new NamooBoardException(response.getExceptionStr()); 
		}
		
	}
	
	

}
