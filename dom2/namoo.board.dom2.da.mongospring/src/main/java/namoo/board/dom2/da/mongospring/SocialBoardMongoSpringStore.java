/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import namoo.board.dom2.da.mongospring.document.BoardSeqDoc;
import namoo.board.dom2.da.mongospring.document.SocialBoardDoc;
import namoo.board.dom2.da.mongospring.repository.BoardSeqRepository;
import namoo.board.dom2.da.mongospring.repository.SocialBoardRepository;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;

@Repository
public class SocialBoardMongoSpringStore implements SocialBoardStore {
	//
	private SocialBoardRepository socialBoardRepository;
	private BoardSeqRepository boardSeqRepository;
	
	public SocialBoardMongoSpringStore() {
	}
	
	public SocialBoardMongoSpringStore(SocialBoardRepository socialBoardRepository, BoardSeqRepository boardSeqRepository) {
		//
		this.socialBoardRepository = socialBoardRepository;
		this.boardSeqRepository = boardSeqRepository;
	}

	@Override
	public String create(DCSocialBoard board) {
		//
		SocialBoardDoc boardDoc = new SocialBoardDoc(board);
		this.socialBoardRepository.save(boardDoc);
        return boardDoc.getUsid();
	}

	@Override
	public DCSocialBoard retrieve(String usid) {
		SocialBoardDoc boardDoc = this.socialBoardRepository.findByUsid(usid);
		return SocialBoardDoc.createDomain(boardDoc);
	}

	@Override
	public DCSocialBoard retrieveByName(String name) {
		SocialBoardDoc boardDoc = this.socialBoardRepository.findByName(name);
		return SocialBoardDoc.createDomain(boardDoc);
	}

	@Override
	public List<DCSocialBoard> retrieveAll() {
		//
		Iterable<SocialBoardDoc> boardDocs = this.socialBoardRepository.findAll();
				
		if(boardDocs == null) return null;
		
		List<DCSocialBoard> boards = new ArrayList<>();
		for (Iterator<SocialBoardDoc> iter = boardDocs.iterator(); iter.hasNext(); ) {
			SocialBoardDoc boardDoc = iter.next();
			boards.add(SocialBoardDoc.createDomain(boardDoc));
		}
		
		return boards;
	}

	@Override
	public void update(DCSocialBoard board) {
		//		
		SocialBoardDoc boardDoc = new SocialBoardDoc(board);
		this.socialBoardRepository.save(boardDoc);
	}

	@Override
	public void delete(String usid) {
		this.socialBoardRepository.deleteByUsid(usid);
	}

	@Override
	public int nextSequence() {
		BoardSeqDoc seqDoc = this.boardSeqRepository.findByName("board");
		if (seqDoc == null){
			seqDoc = new BoardSeqDoc("board");
		}
		seqDoc.setSeqNo(seqDoc.getSeqNo()+1);
		this.boardSeqRepository.save(seqDoc);
		return seqDoc.getSeqNo();
	}

    @Override
    public boolean isExist(String usid) {
    	Long count = this.socialBoardRepository.countByUsid(usid);
    	return count > 0;
    }

    @Override
    public boolean isExistByName(String name) {
    	Long count = this.socialBoardRepository.countByName(name);
		return count > 0;
    }
}
