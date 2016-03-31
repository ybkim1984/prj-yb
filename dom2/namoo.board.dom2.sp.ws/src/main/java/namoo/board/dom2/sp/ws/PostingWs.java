/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface PostingWs {
    //
    public String registerPosting(@WebParam(name = "boardUsid") String boardUsid, @WebParam(name = "postingCdoJson") String postingCdoJson);
    public String findPosting(@WebParam(name = "postingUsid") String postingUsid);
    public String findPostingWithContents(@WebParam(name = "postingUsid") String postingUsid);
    public String findPostingPage(@WebParam(name = "boardUsid") String boardUsid, @WebParam(name = "pageCriteriaJson") String pageCriteriaJson);
    public void modifyPostingOption(@WebParam(name = "postingUsid") String postingUsid, @WebParam(name = "nameValuesJson") String nameValuesJson);
    public void modifyPostingContents(@WebParam(name = "postingUsid") String postingUsid, @WebParam(name = "contents") String contents);
    public void removePosting(@WebParam(name = "postingUsid") String postingUsid);
    public void increasePostingReadCount(@WebParam(name = "postingUsid") String postingUsid);
    public void attachComment(@WebParam(name = "postingUsid") String postingUsid, @WebParam(name = "commentCdoJson") String commentCdoJson);
    public void detachComment(@WebParam(name = "postingUsid") String postingUsid, @WebParam(name = "sequence")int sequence); 
}
