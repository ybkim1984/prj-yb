package namoo.board.publish.socket.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.json.JsonRequest;
import namoo.board.dom2.util.json.JsonResponse;
import namoo.board.dom2.util.json.JsonUtil;

public class SocketWorker{

	private BufferedInputStream inputStream; 	// buffered inputStream
	
	public SocketWorker() {
	}
	
	public JsonRequest read(EventSource eventSource) {
		//
		int byteArraySize = 0; 
		int readByteCount = 0; 
		
		try {
			Socket clientSocket = (Socket)eventSource.getSource(); 
			this.inputStream = new BufferedInputStream(clientSocket.getInputStream());
			byte[] messageSizeBytes = new byte[10]; 
			inputStream.read(messageSizeBytes, 0, 10);	// read int
			String sizeStr = new String(messageSizeBytes); 
			byteArraySize = Integer.valueOf(sizeStr); 
		} catch (IOException ioe) {
			throw new NamooBoardException("Socket read error. " + ioe.getMessage()); 
		}

		byte[] byteArray = new byte[byteArraySize]; 

		try {
			readByteCount = inputStream.read(byteArray, 0, byteArraySize); 
			while(readByteCount < byteArraySize) {
				int reReadByteCount = inputStream.read(byteArray, readByteCount, (byteArraySize-readByteCount)); 
				readByteCount += reReadByteCount; 
			}
		} catch (IOException ioe){
			throw new NamooBoardException("Socket read error. " + ioe.getMessage()); 
		}

		String requestJson = new String(byteArray); 
		
		return (JsonRequest)JsonUtil.fromJson(requestJson, JsonRequest.class); 
	}
	
	public void write(EventSource eventSource, JsonResponse response) {
		
		BufferedOutputStream outputStream;	// buffered outputStream
		String responseJson = JsonUtil.toJson(response); 
		try {
			Socket clientSocket = (Socket)eventSource.getSource();
			outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
			outputStream.write(responseJson.getBytes()); 
			outputStream.flush();
		} catch (IOException ioe) {
		} catch (Exception e) {
		}
	}
}
