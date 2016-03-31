/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:jipark@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */

package namoo.board.dom2.da.mongospring;

import org.springframework.stereotype.Repository;


import namoo.board.dom2.da.mongospring.document.BoardSeqDoc;
import namoo.board.dom2.da.mongospring.document.CommentBagDoc;
import namoo.board.dom2.da.mongospring.document.PostingCommentDoc;
import namoo.board.dom2.da.mongospring.document.PostingContentsDoc;
import namoo.board.dom2.da.mongospring.document.PostingDoc;
import namoo.board.dom2.da.mongospring.repository.BoardSeqRepository;
import namoo.board.dom2.da.mongospring.repository.PostingRepository;
import namoo.board.dom2.da.mongospring.repository.custom.PostingCustomRepository;
import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

@Repository
public class PostingMongoSpringStore implements PostingStore {
	//
	private PostingRepository postingRepository;
	private PostingCustomRepository postingCustomRepository;
	private BoardSeqRepository boardSeqRepository;
	
	public PostingMongoSpringStore() {
	}
	
	public PostingMongoSpringStore(PostingRepository postingRepository, PostingCustomRepository postingCustomRepository, BoardSeqRepository boardSeqRepository) {
		//
		this.postingRepository = postingRepository;
		this.postingCustomRepository= postingCustomRepository;
		this.boardSeqRepository = boardSeqRepository;
	}

	@Override
	public void create(DCPosting posting) {
		//
		PostingDoc postingDoc = new PostingDoc(posting);
		this.postingRepository.save(postingDoc);
	}

	@Override
	public DCPosting retrieve(String usid) {
		//
		PostingDoc postingDoc = this.postingRepository.findByUsid(usid);
		return PostingDoc.createDomain(postingDoc);
	}

	@Override
	public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
		//
		Page<DCPosting> paging = this.postingCustomRepository.retrievePage(boardUsid, pageCriteria);

        return paging;
	}

	@Override
	public void update(DCPosting posting) {
		//
		PostingDoc postingDoc = new PostingDoc(posting);
		this.postingRepository.save(postingDoc);
	}

	@Override
	public void delete(String usid) {
		//
		this.postingRepository.deleteByUsid(usid);
	}

	@Override
	public void deleteByBoard(String boardUsid) {
		//
		this.postingRepository.deleteByBoardUsid(boardUsid);
	}

	@Override
	public int nextSequence(String boardUsid) {
		//
		BoardSeqDoc seqDoc = this.boardSeqRepository.findByName(boardUsid);
		if (seqDoc == null){
			seqDoc = new BoardSeqDoc(boardUsid);
		}
		seqDoc.setSeqNo(seqDoc.getSeqNo()+1);
		this.boardSeqRepository.save(seqDoc);
		return seqDoc.getSeqNo();
	}

	@Override
	public void increaseReadCount(String usid) {
		//
		PostingDoc postingDoc = this.postingRepository.findByUsid(usid);
		postingDoc.setReadCount(postingDoc.getReadCount()+1);
		this.postingRepository.save(postingDoc);
	}
	
	@Override
    public boolean isExist(String usid) {
        //
		Long count = this.postingRepository.countByUsid(usid);
    	return count > 0;
    }

	@Override
	public void createContents(DCPostingContents contents) {
		PostingDoc postingDoc = this.postingRepository.findByUsid(contents.getPostingUsid());
		PostingContentsDoc postingContentsDoc = new PostingContentsDoc(contents);
		postingDoc.setContentsDoc(postingContentsDoc);
		this.postingRepository.save(postingDoc);
	}

	@Override
	public DCPostingContents retrieveContents(String postingUsid) {
		PostingDoc postingDoc = this.postingRepository.findByUsid(postingUsid);
		DCPostingContents postingContents =  PostingContentsDoc.createDomain(postingDoc.getContentsDoc());
		
		return postingContents;
	}

	@Override
	public void updateContents(DCPostingContents contents) {
		PostingDoc postingDoc = this.postingRepository.findByUsid(contents.getPostingUsid());
		PostingContentsDoc postingContentsDoc = new PostingContentsDoc(contents);
		postingDoc.setContentsDoc(postingContentsDoc);
		this.postingRepository.save(postingDoc);
	}

	@Override
	public void createComment(String postingUsid, DCPostingComment comment) {
		PostingDoc postingDoc = this.postingRepository.findByUsid(postingUsid);
		DCCommentBag commentBag = new DCCommentBag();
		if(postingDoc.getContentsDoc().getCommentBag() != null){
			commentBag = CommentBagDoc.createDomain(postingDoc.getContentsDoc().getCommentBag());
		}
		comment.setSequence(this.nextCommentSequence(postingDoc.getContentsDoc().getPostingUsid()));
		commentBag.addComment(comment);
		
		postingDoc.getContentsDoc().setCommentBag(new CommentBagDoc(commentBag));
		
		this.postingRepository.save(postingDoc);
	}

	@Override
	public DCPostingComment retrieveComment(String postingUsid, int sequence) {
		//
		PostingDoc postingDoc = this.postingRepository.findByUsid(postingUsid);
		
    	DCPostingComment postingComment = null;
        for (PostingCommentDoc commentDoc : postingDoc.getContentsDoc().getCommentBag().getComments()){
        	if (commentDoc.getSequence() == sequence){
        		postingComment = PostingCommentDoc.createDomain(commentDoc);
        	}
        }		
		return postingComment;
	}

	@Override
	public void deleteComment(String postingUsid, int sequence) {
		//
		PostingDoc postingDoc = this.postingRepository.findByUsid(postingUsid);
		DCCommentBag commentBag = new DCCommentBag();
		
		if(postingDoc.getContentsDoc().getCommentBag() != null){
			commentBag = CommentBagDoc.createDomain(postingDoc.getContentsDoc().getCommentBag());
		}
		commentBag.removeComment(sequence);
		
		postingDoc.getContentsDoc().setCommentBag(new CommentBagDoc(commentBag));
		this.postingRepository.save(postingDoc);
	}

	@Override
    public int nextCommentSequence(String postingUsid) {
		BoardSeqDoc seqDoc = this.boardSeqRepository.findByName(postingUsid);
		if (seqDoc == null){
			seqDoc = new BoardSeqDoc(postingUsid);
		}
		seqDoc.setSeqNo(seqDoc.getSeqNo()+1);
		this.boardSeqRepository.save(seqDoc);
		return seqDoc.getSeqNo();
    }

    @Override
    public boolean isExistComment(String postingUsid, int sequence) {
		//
    	DCPostingComment comment = this.retrieveComment(postingUsid, sequence);
    	if (comment != null){
    		return true;
    	}
		return false;
    }
}
