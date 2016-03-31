/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.shared.CommentCdo;
import namoo.board.dom2.shared.PostingCdo;
import namoo.board.dom2.sp.ws.PostingWs;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

@WebService(endpointInterface = "namoo.board.dom2.sp.ws.PostingWs", serviceName = "PostingWs")
public class PostingWsLogic implements PostingWs {
    //
    private PostingService postingService;
    
    //--------------------------------------------------------------------------
    
    public PostingWsLogic() {
        //
        BoardStoreLifecycler boardStoreLifecycler = BoardStoreMyBatisLifecycler.getInstance();
        BoardServiceLifecycler boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(boardStoreLifecycler);
        
        this.postingService = boardServiceLifecycler.getPostingService();
    }

    @Override
    public String registerPosting(String boardUsid, String postingCdoJson) {
        // 
        PostingCdo postingCdo = (PostingCdo) JsonUtil.fromJson(postingCdoJson, PostingCdo.class);
        String postingId = postingService.registerPosting(boardUsid, postingCdo);
        
        return postingId;
    }
    
    @Override
    public String findPosting(String postingUsid) {
        // 
        DCPosting posting = postingService.findPosting(postingUsid);
        
        return JsonUtil.toJson(posting);
    }

    @Override
    public String findPostingWithContents(String postingUsid) {
        // 
        DCPosting posting = postingService.findPostingWithContents(postingUsid);
        
        return JsonUtil.toJson(posting);
    }

    @Override
    public String findPostingPage(String boardUsid, String pageCriteriaJson) {
        // 
        PageCriteria pageCriteria = (PageCriteria) JsonUtil.fromJson(pageCriteriaJson, PageCriteria.class);
        Page<DCPosting> pagePosting = postingService.findPostingPage(boardUsid, pageCriteria);
        
        String criteriaJson = JsonUtil.toJson(pagePosting.getCriteria());
        List<DCPosting> postings = pagePosting.getResults();
        List<String> postingsJson = new ArrayList<String>();
        
        for(DCPosting posting : postings) {
            postingsJson.add(JsonUtil.toJson(posting));
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("criteriaJson", criteriaJson);
        map.put("postingsJson", postingsJson);
        
        return JsonUtil.toJson(map);
    }

    @Override
    public void modifyPostingOption(String postingUsid, String nameValuesJson) {
        // 
        NameValueList nameValues = (NameValueList) JsonUtil.fromJson(nameValuesJson, NameValueList.class);
        postingService.modifyPostingOption(postingUsid, nameValues);
    }

    @Override
    public void modifyPostingContents(String postingUsid, String contents) {
        // 
        postingService.modifyPostingContents(postingUsid, contents);
    }

    @Override
    public void removePosting(String postingUsid) {
        // 
        postingService.removePosting(postingUsid);
    }

    @Override
    public void increasePostingReadCount(String postingUsid) {
        // 
        postingService.increasePostingReadCount(postingUsid);
    }

    @Override
    public void attachComment(String postingUsid, String commentCdoJson) {
        // 
        CommentCdo commentCdo = (CommentCdo) JsonUtil.fromJson(commentCdoJson, CommentCdo.class);
        postingService.attachComment(postingUsid, commentCdo);
    }

    @Override
    public void detachComment(String postingUsid, int sequence) {
        // 
        postingService.detachComment(postingUsid, sequence);
    }
}
