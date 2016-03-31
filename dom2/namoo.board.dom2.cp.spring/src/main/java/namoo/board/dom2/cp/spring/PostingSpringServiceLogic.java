/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.cp.spring;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.logic.PostingServiceLogic;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostingSpringServiceLogic implements PostingService {
    //
    private PostingService service;

    @Autowired
    public PostingSpringServiceLogic(BoardStoreLifecycler storeLifecycler) {
        //
        this.service = new PostingServiceLogic(storeLifecycler);
    }

    // -------------------------------------------------------------------------

    @Override
    public String registerPosting(String boardUsid, PostingCdo postingCdo) {
        // 
        return service.registerPosting(boardUsid, postingCdo);
    }

    @Override
    public DCPosting findPosting(String postingUsid) {
        // 
        return service.findPosting(postingUsid);
    }

    @Override
    public DCPosting findPostingWithContents(String postingUsid) {
        // 
        return service.findPostingWithContents(postingUsid);
    }
    
    @Override
    public Page<DCPosting> findPostingPage(String boardUsid, PageCriteria pageCriteria) {
        // 
        return service.findPostingPage(boardUsid, pageCriteria);
    }

    @Override
    public void modifyPostingOption(String postingUsid, NameValueList nameValues) {
        // 
        service.modifyPostingOption(postingUsid, nameValues);
    }

    @Override
    public void modifyPostingContents(String postingUsid, String contents) {
        // 
        service.modifyPostingContents(postingUsid, contents);
    }

    @Override
    public void removePosting(String postingUsid) {
        // 
        service.removePosting(postingUsid);
    }

    @Override
    public void increasePostingReadCount(String postingUsid) {
        // 
        service.increasePostingReadCount(postingUsid);
    }
    
    @Override
    public void attachComment(String postingUsid, CommentCdo commentCdo) {
        // 
        service.attachComment(postingUsid, commentCdo);
    }

    @Override
    public void detachComment(String postingUsid, int sequence) {
        // 
        service.detachComment(postingUsid, sequence);
    }

}
