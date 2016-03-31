package namoo.board.publish.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReactorManager {
	//
	private static ReactorManager reactorManager; 
	
	private ServiceHandler handler; 
	private List<SocketReactor> reactorList; 
	
	//--------------------------------------------------------------------------
	private ReactorManager(ServiceHandler handlerManager) {
		this.handler = handlerManager; 
		this.reactorList = new ArrayList<SocketReactor>(); 
	}
	
	public static ReactorManager getInstance(ServiceHandler handler) {
		if (reactorManager == null) {
			reactorManager = new ReactorManager(handler); 
			reactorManager.prepareReactors(); 
		}
		
		return reactorManager; 
	}
	
	public ServiceHandler getHandler() {
		return this.handler; 
	}
	
	private void prepareReactors() {
		//
		int portNum = 1200;	// 
		
		try {
			ServerSocket serverSocket = new ServerSocket(portNum);
			SocketReactor socketReactor = new SocketReactor(serverSocket, this);
			this.reactorList.add(socketReactor); 
		} catch (IOException ioe) {
		}
	}
	
	public void startReaction() {
		Iterator<SocketReactor> reactorIter = reactorList.iterator(); 
		while (reactorIter.hasNext()) {
			SocketReactor reactor = reactorIter.next(); 
			reactor.startEventWaitLoop(); 
		}
	}
}