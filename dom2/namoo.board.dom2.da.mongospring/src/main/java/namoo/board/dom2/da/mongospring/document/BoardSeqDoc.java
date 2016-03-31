package namoo.board.dom2.da.mongospring.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = BoardSeqDoc.CollectionName)
public class BoardSeqDoc {
	//
	public static final String CollectionName = "boardseq";
	
	private String name;
	
	private int seqNo;

	public BoardSeqDoc() {}

	public BoardSeqDoc(String name) {
		this.name = name;
		this.seqNo = 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

}
