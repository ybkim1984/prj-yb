/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws.logic;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreMyBatisLifecycler;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.sp.ws.SocialBoardWs;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;

@WebService(endpointInterface = "namoo.board.dom2.sp.ws.SocialBoardWs", serviceName = "SocialBoardWs")
public class SocialBoardWsLogic implements SocialBoardWs {
    //
    private SocialBoardService socialBoardService;
    
    // -------------------------------------------------------------------------
    
    public SocialBoardWsLogic() {
        //
        BoardStoreLifecycler boardStoreLifecycler = BoardStoreMyBatisLifecycler.getInstance();
        BoardServiceLifecycler boardServiceLifecycler = BoardServicePojoLifecycler.getInstance(boardStoreLifecycler);
        
        this.socialBoardService = boardServiceLifecycler.getSocialBoardService();
    }

    @Override
    public String registerSocialBoard(String teamUsid, String boardName,
            boolean commentable) {
        // 
        String boardId = socialBoardService.registerSocialBoard(teamUsid, boardName, commentable);
        
        return boardId;
    }

    @Override
    public String findSocialBoard(String boardUsid) {
        // 
        DCSocialBoard socialBoard = socialBoardService.findSocialBoard(boardUsid);
        
        return JsonUtil.toJson(socialBoard);
    }

    @Override
    public List<String> findAllSocialBoards() {
        // 
        List<DCSocialBoard> allSocialBoards =  socialBoardService.findAllSocialBoards();
        
        List<String> allSocialBoardsJson = new ArrayList<String>();
        for(DCSocialBoard socialBoard : allSocialBoards) {
            allSocialBoardsJson.add(JsonUtil.toJson(socialBoard));
        }
        
        return allSocialBoardsJson;
    }

    @Override
    public void modifySocialBoard(String boardUsid, String nameValuesJson) {
        // 
        NameValueList nameValues = (NameValueList) JsonUtil.fromJson(nameValuesJson, NameValueList.class);
        socialBoardService.modifySocialBoard(boardUsid, nameValues);
    }

    @Override
    public void removeSocialBoard(String boardUsid) {
        // 
        socialBoardService.removeSocialBoard(boardUsid);
    }
}
