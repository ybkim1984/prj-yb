/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.sp.ws.PostingWs;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public class PostingWsPresenter implements PostingService {
    //
    private WsRequester wsRequester;
    
    // -------------------------------------------------------------------------
    
    public PostingWsPresenter() {
        //
        this.wsRequester = new WsRequester();
    }

    @Override
    public String registerPosting(String boardUsid, PostingCdo postingCdo) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String postingCdoJson = JsonUtil.toJson(postingCdo);
        String postingId = postingWs.registerPosting(boardUsid, postingCdoJson);
        
        return postingId;
    }

    @Override
    public DCPosting findPosting(String postingUsid) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String postingJson = postingWs.findPosting(postingUsid);
        
        return (DCPosting) JsonUtil.fromJson(postingJson, DCPosting.class);
    }

    @Override
    public DCPosting findPostingWithContents(String postingUsid) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String PostingJson = postingWs.findPostingWithContents(postingUsid);
        
        return (DCPosting) JsonUtil.fromJson(PostingJson, DCPosting.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<DCPosting> findPostingPage(String boardUsid,
            PageCriteria pageCriteria) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String pageCriteriaJson = JsonUtil.toJson(pageCriteria);
        String pagePostingJson = postingWs.findPostingPage(boardUsid, pageCriteriaJson);
        
        Map<String,Object> map = (Map<String,Object>) JsonUtil.fromJson(pagePostingJson, Map.class);
        String criteriaJson = (String) map.get("criteriaJson");
        List<String> postingsJson = (List<String>) map.get("postingsJson");
        
        PageCriteria criteira = (PageCriteria) JsonUtil.fromJson(criteriaJson, PageCriteria.class);
        List<DCPosting> postings = new ArrayList<DCPosting>();
        
        for(String postingJson : postingsJson) {
            postings.add((DCPosting) JsonUtil.fromJson(postingJson, DCPosting.class));
        }
        
        return new Page<DCPosting>(postings, criteira);
    }

    @Override
    public void modifyPostingOption(String postingUsid, NameValueList nameValues) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String nameValuesJson = JsonUtil.toJson(nameValues);
        postingWs.modifyPostingOption(postingUsid, nameValuesJson);
    }

    @Override
    public void modifyPostingContents(String postingUsid, String contents) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        postingWs.modifyPostingContents(postingUsid, contents);
    }

    @Override
    public void removePosting(String postingUsid) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        postingWs.removePosting(postingUsid);
    }

    @Override
    public void increasePostingReadCount(String postingUsid) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        postingWs.increasePostingReadCount(postingUsid);
    }

    @Override
    public void attachComment(String postingUsid, CommentCdo commentCdo) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        String commentCdoJson = JsonUtil.toJson(commentCdo);
        postingWs.attachComment(postingUsid, commentCdoJson);
    }

    @Override
    public void detachComment(String postingUsid, int sequence) {
        // 
        PostingWs postingWs = wsRequester.getPostingWs();
        postingWs.detachComment(postingUsid, sequence);
    }
}
