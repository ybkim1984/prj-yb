package namoo.board.publish.socket.server;

public class EventSource {
	//
	private Object source; 
	
	public EventSource(Object source) {
		this.source = source; 
	}
	
	public Object getSource() {
		return this.source; 
	}
}