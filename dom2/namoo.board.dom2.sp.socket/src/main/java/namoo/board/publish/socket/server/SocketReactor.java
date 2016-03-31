package namoo.board.publish.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SocketReactor extends Thread {
	//
	private ServerSocket serverSocket; 
	private ReactorManager reactorManager; 
	
	public SocketReactor(ServerSocket serverSocket, ReactorManager reactorManager) {
		//
		this.serverSocket = serverSocket; 
		this.reactorManager = reactorManager; 
	}

	public void startEventWaitLoop() {
		this.start(); 
	}
	
	public void acceptEvent(EventSource eventSource) {
		// identify handler
		ServiceHandler handler = reactorManager.getHandler(); 
		handler.dealWith(eventSource); 
	}
	
	public void run() {
		
        while (true){
            Socket clientSocket = null;
        	System.out.println(">> Ready to receive socket message....");
            try {
                synchronized(this.serverSocket) {
                    clientSocket = serverSocket.accept();
            }
        
            System.out.println(">> I've got a message from " + clientSocket.getLocalPort());
            this.acceptEvent(new EventSource(clientSocket)); 
                
            } catch (SocketException se) {
            	se.printStackTrace();
            } catch (IOException ex) {
            	ex.printStackTrace();
            } catch (RuntimeException re) {
            	re.printStackTrace();
            } catch (Exception e) {
            	e.printStackTrace();
            } finally {
            	closeClientSocket(clientSocket); 
            }
        }
    }
	
	private void closeClientSocket(Socket clientSocket) {
    	if (clientSocket != null && !(clientSocket.isClosed())) {
            try {
				clientSocket.close();
			} catch (IOException e) {
			}
    	}
	}
}
