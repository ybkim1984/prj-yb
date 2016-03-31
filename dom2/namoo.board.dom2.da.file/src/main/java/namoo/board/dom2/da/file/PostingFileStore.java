/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file;

import java.util.ArrayList;
import java.util.List;

import namoo.board.dom2.da.file.repository.BoardUserRepository;
import namoo.board.dom2.da.file.repository.PostingCommentBagRepository;
import namoo.board.dom2.da.file.repository.PostingRepository;
import namoo.board.dom2.da.file.repository.SequenceRepository;
import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public class PostingFileStore implements PostingStore {
    //
    private SequenceRepository seqStore = new SequenceRepository();
    private PostingRepository postingRepository = new PostingRepository();
    private BoardUserRepository userStore = new BoardUserRepository();
    private PostingCommentBagRepository commentBagStore = new PostingCommentBagRepository();
    
    //--------------------------------------------------------------------------
    // DCPosting
    
    @Override
    public void create(DCPosting posting) {
        // 
        postingRepository.insert(posting);
    }

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        // 
        DCPosting posting = postingRepository.select(usid);
        if (posting == null) {
            throw new EmptyResultException("No such a posting --> " + usid);
        }
        
        return setWriterName(posting);
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        // 
        // 조회조건에 해당하는 목록 검색후 페이지만큼 잘라낸다.
        List<DCPosting> postings = postingRepository.selectListByBoard(boardUsid);
        int totalSize = postings.size();
        pageCriteria.setTotalItemCount(totalSize);

        List<DCPosting> pageResults = new ArrayList<DCPosting>();
        int start = pageCriteria.getStart();
        int itemPageCount = pageCriteria.getItemLimitOfPage();
        
        for (int i = start; i < (start + itemPageCount); i ++) {
            //
            if (i >= totalSize) {
                break;
            }
            pageResults.add(postings.get(i));
        }
        
        return new Page<DCPosting>(setWriterName(pageResults), pageCriteria);
    }

    @Override
    public void update(DCPosting posting) {
        // 
        DCPostingContents contents = postingRepository.selectContents(posting.getUsid());
        posting.setContents(contents);
        
        postingRepository.update(posting);
    }

    @Override
    public void delete(String usid) {
        // 
        commentBagStore.deleteInPosting(usid);
        postingRepository.delete(usid);
    }

    @Override
    public void deleteByBoard(String boardUsid) {
        // 
        List<DCPosting> postings = postingRepository.selectListByBoard(boardUsid);
        List<String> postingUsids = new ArrayList<String>();
        
        for (DCPosting posting : postings) {
            //
            postingUsids.add(posting.getUsid());
        }
        
        commentBagStore.deleteByUsids(postingUsids);
        postingRepository.deleteByBoard(boardUsid);
    }

    @Override
    public int nextSequence(String boardUsid) {
        // 
         return seqStore.selectPostingNextSeq(boardUsid);
    }

    @Override
    public void increaseReadCount(String usid) {
        // 
        DCPosting posting = postingRepository.select(usid);
        posting.setReadCount(posting.getReadCount() + 1);
        
        postingRepository.update(posting);
    }
    
    @Override
    public boolean isExist(String usid) {
        // 
        DCPosting posting = postingRepository.select(usid);
        return posting != null;
    }

    private DCPosting setWriterName(DCPosting posting) {
        //
        if (posting == null) {
            return null;
        }
        DCBoardUser user = userStore.select(posting.getWriterEmail());
        posting.setWriterName(user.getName());
        
        return posting;
    }
    
    private List<DCPosting> setWriterName(List<DCPosting> postings) {
        //
        List<DCPosting> results = new ArrayList<DCPosting>(postings.size());
        
        for (DCPosting posting : postings) {
            results.add(setWriterName(posting));
        }
        return results;
    }

    //--------------------------------------------------------------------------
    // DCPostingContents
    
    @Override
    public void createContents(DCPostingContents contents) {
        // 
        DCPosting posting = postingRepository.select(contents.getPostingUsid());
        posting.setContents(contents);
        
        postingRepository.update(posting);
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        //
        DCPostingContents contents = postingRepository.selectContents(postingUsid);
        if (contents == null) {
            throw new EmptyResultException("No such a posting contents --> " + postingUsid);
        }
        
        return setComments(contents);
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        // 
        DCPosting posting = postingRepository.select(contents.getPostingUsid());
        posting.setContents(contents);
        
        postingRepository.update(posting);
    }
    
    private DCPostingContents setComments(DCPostingContents contents) {
        //
        if (contents == null) {
            return null;
        }
        DCCommentBag commentBag = commentBagStore.select(contents.getPostingUsid());
        if (commentBag != null) {
            contents.setCommentBag(commentBag);
        }
        
        return contents;
    }
    
    //--------------------------------------------------------------------------
    // DCPostingComment
    
    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        // 
        commentBagStore.insert(postingUsid, comment);
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        // 
        DCCommentBag commentBag = commentBagStore.select(postingUsid);
        if (commentBag == null || commentBag.getComment(sequence) == null) {
            throw new EmptyResultException("No such a posting comment --> postingUsid: " + postingUsid + ", sequence: " + sequence);
        }
        
        return commentBag.getComment(sequence);
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        // 
        commentBagStore.delete(postingUsid, sequence);
    }

    
    @Override
    public int nextCommentSequence(String postingUsid) {
        //
        return seqStore.selectCommentNextSeq(postingUsid);
    }

    @Override
    public boolean isExistComment(String postingUsid, int sequence) {
        // 
        DCCommentBag bag = commentBagStore.select(postingUsid);
        return bag.getComment(sequence) != null;
    }

}
