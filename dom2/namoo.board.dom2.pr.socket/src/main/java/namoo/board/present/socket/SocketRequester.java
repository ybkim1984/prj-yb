package namoo.board.present.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import namoo.board.dom2.util.exception.NamooBoardException;
import namoo.board.dom2.util.json.JsonRequest;
import namoo.board.dom2.util.json.JsonResponse;
import namoo.board.dom2.util.json.JsonUtil;

public class SocketRequester {
	//
	public SocketRequester() {
		//
	}

	public JsonResponse request(JsonRequest request) {
		//
        Socket socket = null;
		try {
	        socket = new Socket("localhost", 1200);
	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String requestJson = JsonUtil.toJson(request); 
	        bufferedWriter.write(String.format("%010d", requestJson.length()));
	        bufferedWriter.write(JsonUtil.toJson(request));
	        bufferedWriter.flush(); 
	
	        while (!bufferedReader.ready()) {}
			String responseJson = bufferedReader.readLine(); 

	        bufferedReader.close();
	        return (JsonResponse)JsonUtil.fromJson(responseJson, JsonResponse.class);  
	    } catch(Exception e) {
	    	e.printStackTrace();
			throw new NamooBoardException("Has some problem with " + e.getMessage()); 
	    } finally {
	    	if (!socket.isClosed()) {
	    		try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
	    	}
	    }
	}
}