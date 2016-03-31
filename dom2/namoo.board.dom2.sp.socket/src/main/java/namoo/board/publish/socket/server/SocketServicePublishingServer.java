package namoo.board.publish.socket.server;


public class SocketServicePublishingServer {

	ServiceHandler serviceHandler; 
	ReactorManager reactorManager;  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SocketServicePublishingServer nexBroker = new SocketServicePublishingServer(); 
		nexBroker.start(); 
	}

	private void loadServerConfig() {
	}

	public void start() {
		loadServerConfig(); 
		startBroker(); 
	}

	private void startBroker() {
		serviceHandler = new ServiceHandler();

		reactorManager = ReactorManager.getInstance(serviceHandler); 
		reactorManager.startReaction(); 
		System.out.println("Socket service publisher is ready...."); 
	}
}
