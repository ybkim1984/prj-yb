/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.da.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mybatis.mapper.PostingMapper;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostingMyBatisStore implements PostingStore {
    //
    @Autowired
    private PostingMapper postingMapper;
    
    @Override
    public void create(DCPosting posting) {
        // 
        postingMapper.insert(posting);
    }

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        // 
        DCPosting posting = postingMapper.select(usid);
        if(posting == null) {
            throw new EmptyResultException("No such posting -->" + usid);
        }
        return posting;
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        // 
        PageCriteria criteria = postingMapper.selectPageCriteria(boardUsid, pageCriteria);
        List<DCPosting> postings = postingMapper.selectPage(boardUsid, pageCriteria);
        return new Page<DCPosting>(postings, criteria);
    }

    @Override
    public void update(DCPosting posting) {
        // 
        postingMapper.update(posting);
    }

    @Override
    public void delete(String usid) {
        // 
        postingMapper.deleteCommentInPosting(usid);
        postingMapper.delete(usid);
    }
    
    @Override
    public void deleteByBoard(String boardUsid) {
        // 
        List<String> postingUsids = postingMapper.selectUsidByBoard(boardUsid);
        
        if (postingUsids.size() > 0) {
            postingMapper.deleteCommentByUsids(postingUsids);
        }
        postingMapper.deleteByBoard(boardUsid);
    }

    @Override
    public int nextSequence(String boardUsid) {
        // 
        String seqName = boardUsid + "-posting_id";
        
        Map<String, Object> resultParam = new HashMap<String, Object>();
        resultParam.put("seqName", seqName);
        
        Integer seq = postingMapper.selectSequence(seqName);
        if (seq == null) {
            postingMapper.insertSequence(seqName);
        }
        postingMapper.nextSequence(resultParam);
        
        return (int) resultParam.get("nextSeq");
    }
    
    @Override
    public void increaseReadCount(String usid) {
        // 
        postingMapper.updateReadCount(usid);
    }

    @Override
    public boolean isExist(String usid) {
        // 
        return postingMapper.select(usid) != null;
    }
    
    //--------------------------------------------------------------------------
    // contents
    
    @Override
    public void createContents(DCPostingContents contents) {
        // 
        postingMapper.updateContents(contents);
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        // 
        DCPostingContents postingContents = postingMapper.selectContents(postingUsid);
        if(postingContents == null) {
            throw new EmptyResultException("No such postingContents -->" + postingUsid);
        }
        return postingContents;
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        // 
        postingMapper.updateContents(contents);
    }

    //--------------------------------------------------------------------------
    // comment
    
    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        // 
        postingMapper.insertComment(postingUsid, comment);
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        // 
        DCPostingComment postingComment = postingMapper.selectComment(postingUsid, sequence);
        if(postingComment == null) {
            throw new EmptyResultException("No such postingComment -->" + postingUsid);
        }
        return postingComment;
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        // 
        postingMapper.deleteComment(postingUsid, sequence);
    }

    @Override
    public int nextCommentSequence(String postingUsid) {
        // 
        Map<String, Object> resultParam = new HashMap<String, Object>();
        String seqName = postingUsid + "-comment";
        resultParam.put("seqName", seqName);
        
        Integer seq = postingMapper.selectSequence(seqName);
        
        if (seq == null) {
            postingMapper.insertSequence(seqName);
        }
        postingMapper.nextSequence(resultParam);
        
        return (int) resultParam.get("nextSeq");
    }

    @Override
    public boolean isExistComment(String postingUsid, int sequence) {
        // 
        return postingMapper.selectComment(postingUsid, sequence) != null;
    }

}
