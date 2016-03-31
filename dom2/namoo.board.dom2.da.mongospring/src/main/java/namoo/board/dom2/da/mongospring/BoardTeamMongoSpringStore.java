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

import org.springframework.stereotype.Repository;

import namoo.board.dom2.da.mongospring.document.BoardMemberDoc;
import namoo.board.dom2.da.mongospring.document.BoardSeqDoc;
import namoo.board.dom2.da.mongospring.document.BoardTeamDoc;
import namoo.board.dom2.da.mongospring.repository.BoardSeqRepository;
import namoo.board.dom2.da.mongospring.repository.BoardTeamRepository;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.store.BoardTeamStore;
import namoo.board.dom2.util.exception.EmptyResultException;

@Repository
public class BoardTeamMongoSpringStore implements BoardTeamStore {
	//
	private BoardTeamRepository boardTeamRepository;
	
	private BoardSeqRepository boardSeqRepository;

	public BoardTeamMongoSpringStore() {
	}
	
	public BoardTeamMongoSpringStore(BoardTeamRepository boardTeamRepository, BoardSeqRepository boardSeqRepository) {
		//
		this.boardTeamRepository = boardTeamRepository;
		this.boardSeqRepository = boardSeqRepository;
	}
	
	@Override
	public void create(DCBoardTeam team) {
		//
		BoardTeamDoc boardTeamDoc = new BoardTeamDoc(team);
		this.boardTeamRepository.save(boardTeamDoc);
	}

	@Override
	public DCBoardTeam retrieve(String usid) throws EmptyResultException {
		//
		BoardTeamDoc boardTeamDoc = this.boardTeamRepository.findByUsid(usid);
		if (boardTeamDoc == null) {
        	throw new EmptyResultException("No such boardTeam -->" + usid);
        }
		return BoardTeamDoc.createDomain(boardTeamDoc);
	}

	@Override
	public DCBoardTeam retrieveByName(String name) throws EmptyResultException{
		//
		BoardTeamDoc boardTeamDoc  = this.boardTeamRepository.findByName(name);
		if (boardTeamDoc == null) {
        	throw new EmptyResultException("No such boardTeam -->" + name);
        }
		return BoardTeamDoc.createDomain(boardTeamDoc);
	}

	@Override
	public List<DCBoardTeam> retrieveAll() {
		//
		Iterable<BoardTeamDoc> boardTeamDocs = this.boardTeamRepository.findAll();

		if(boardTeamDocs == null) return null;
		List<DCBoardTeam> boardTeams = new ArrayList<DCBoardTeam>();
		for (Iterator<BoardTeamDoc> iter = boardTeamDocs.iterator(); iter.hasNext(); ) {
			BoardTeamDoc boardTeamDoc = iter.next();
			boardTeams.add(BoardTeamDoc.createDomain(boardTeamDoc));
		}

		return boardTeams;
	}

	@Override
	public void delete(String usid) {
		//
		this.boardTeamRepository.deleteByUsid(usid);
	}

	@Override
	public int nextSequence() {
		//
		BoardSeqDoc boardSeqDoc = this.boardSeqRepository.findByName("team");
		int nextSeq = 1;
		if(boardSeqDoc == null){
			boardSeqDoc = new BoardSeqDoc();
			boardSeqDoc.setName("team");
			boardSeqDoc.setSeqNo(nextSeq);
		}else{
			nextSeq = boardSeqDoc.getSeqNo() + 1;
			boardSeqDoc.setSeqNo(nextSeq);
		}
		this.boardSeqRepository.save(boardSeqDoc);
		return nextSeq;
	}

	@Override
	public void createMember(String teamUsid, DCBoardMember member){
		//
		BoardTeamDoc boardTeamDoc = this.boardTeamRepository.findByUsid(teamUsid);
		boardTeamDoc.addMemberDoc(new BoardMemberDoc(member));
		this.boardTeamRepository.save(boardTeamDoc);
	}

	@Override
	public DCBoardMember retrieveMember(String teamUsid, String memberEmail) throws EmptyResultException{
		//
		BoardTeamDoc boardTeamDoc = this.boardTeamRepository.findByUsidAndMembersUserEmail(teamUsid, memberEmail);
		if (boardTeamDoc == null) {
        	throw new EmptyResultException("No such boardTeamMember-->" + teamUsid + " Email-->" + memberEmail);
        }
		DCBoardMember dcBoardMember = boardTeamDoc.createBoardMemberDomainByEmail(memberEmail);
		return dcBoardMember;
	}

	@Override
	public List<DCBoardMember> retrieveMembers(String teamUsid) {
		//
		BoardTeamDoc boardTeamDoc = this.boardTeamRepository.findByUsid(teamUsid);
		List<DCBoardMember> dcBoardMembers = BoardMemberDoc.createDomain(boardTeamDoc.getMembers());
		return dcBoardMembers;
	}

	@Override
	public void deleteMember(String teamUsid, String memberEmail) {
		//
		this.boardTeamRepository.deleteByUsidAndMembersUserEmail(teamUsid, memberEmail);
	}

    @Override
    public boolean isExist(String usid) {
    	//
        return this.boardTeamRepository.findByUsid(usid) != null;
    }

    @Override
    public boolean isExistByName(String name) {
        // 
        return this.boardTeamRepository.findByName(name) != null;
    }

    @Override
    public boolean isExistMember(String teamUsid, String memberEmail) {
        // 
        return this.boardTeamRepository.findByUsidAndMembersUserEmail(teamUsid, memberEmail) != null;
    }
}
