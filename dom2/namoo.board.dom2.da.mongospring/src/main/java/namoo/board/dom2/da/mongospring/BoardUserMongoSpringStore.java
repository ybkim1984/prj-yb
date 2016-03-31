/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jipark@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */

package namoo.board.dom2.da.mongospring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import namoo.board.dom2.da.mongospring.document.BoardUserDoc;
import namoo.board.dom2.da.mongospring.repository.BoardUserRepository;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.BoardUserStore;

import org.springframework.stereotype.Repository;

@Repository
public class BoardUserMongoSpringStore implements BoardUserStore {
	//
	private BoardUserRepository boardUserRepository;
	
	public BoardUserMongoSpringStore() {
	}
	
	public BoardUserMongoSpringStore(BoardUserRepository boardUserRepository) {
		//
		this.boardUserRepository = boardUserRepository;
	}

	@Override
	public void create(DCBoardUser user) {
		//
		BoardUserDoc userDoc = new BoardUserDoc(user);
		this.boardUserRepository.save(userDoc);
	}

	@Override
	public DCBoardUser retrieveByEmail(String email) {
		//
		BoardUserDoc userDoc = this.boardUserRepository.findByEmail(email);
		return BoardUserDoc.createDomain(userDoc);
	}

	@Override
	public List<DCBoardUser> retrieveAll() {
		//
		Iterable<BoardUserDoc> userDocs = this.boardUserRepository.findAll();
				
		if(userDocs == null) return null;
		
		List<DCBoardUser> users = new ArrayList<>();
		for (Iterator<BoardUserDoc> iter = userDocs.iterator(); iter.hasNext(); ) {
			BoardUserDoc userDoc = iter.next();
			users.add(BoardUserDoc.createDomain(userDoc));
		}
		
		return users;
	}

	@Override
	public List<DCBoardUser> retrieveByName(String name) {
		//
		Iterable<BoardUserDoc> userDocs = this.boardUserRepository.findByName(name);
		
		if(userDocs == null) return null;
		
		List<DCBoardUser> users = new ArrayList<>();
		for (Iterator<BoardUserDoc> iter = userDocs.iterator(); iter.hasNext(); ) {
			BoardUserDoc userDoc = iter.next();
			users.add(BoardUserDoc.createDomain(userDoc));
		}
		
		return users;
	}

	@Override
	public void update(DCBoardUser user) {
		//
		BoardUserDoc userDoc = new BoardUserDoc(user);
		this.boardUserRepository.save(userDoc);
	}

	@Override
	public void deleteByEmail(String email) {
		//
		this.boardUserRepository.deleteByEmail(email);
	}

	@Override
	public boolean isExistByEmail(String email) {
		//
		Long count = boardUserRepository.countByEmail(email);
		return count > 0;
	}
}
