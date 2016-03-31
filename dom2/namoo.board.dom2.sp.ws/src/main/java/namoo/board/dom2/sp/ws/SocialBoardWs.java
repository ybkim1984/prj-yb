/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SocialBoardWs {
    //
    public String registerSocialBoard(@WebParam(name="teamUsid") String teamUsid, @WebParam(name = "boardName") String boardName, @WebParam(name = "commentable") boolean commentable);
    public String findSocialBoard(@WebParam(name = "boardUsid") String boardUsid);
    public List<String> findAllSocialBoards();
    public void modifySocialBoard(@WebParam(name = "boardUsid") String boardUsid, @WebParam(name = "namveValues") String nameValuesJson);
    public void removeSocialBoard(@WebParam(name = "boardUsid") String boardUsid);
}
