package namoo.board.publish.socket.server;

import namoo.board.dom2.util.json.JsonRequest;
import namoo.board.dom2.util.json.JsonResponse;
import namoo.board.publish.socket.SocialBoardPublisher;





public class ServiceHandler {
	// 
	private SocketWorker socketWorker; 
	private SocialBoardPublisher socialBoardPublisher; 
	
	//--------------------------------------------------------------------------
	public ServiceHandler() {
		// 
		this.socketWorker = new SocketWorker(); 
		this.socialBoardPublisher = SocialBoardPublisher.getInstance(); 
	}
	
	public void dealWith(EventSource eventSource) {
		// 
		JsonRequest request = this.socketWorker.read(eventSource); 
		JsonResponse response = routeServices(request); 
		socketWorker.write(eventSource, response);
	}
	
	private JsonResponse routeServices(JsonRequest request) {
		//
		switch (request.getTargetId()) {
		case "socialBoardService":
			return socialBoardPublisher.request(request); 
		}
		
		return JsonResponse.getExceptionInstance(request, "Bad serviceId --> " + request.getTargetId()); 
	}
}