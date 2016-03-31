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

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.sp.ws.SocialBoardWs;
import namoo.board.dom2.util.json.JsonUtil;
import namoo.board.dom2.util.namevalue.NameValueList;

public class SocialBoardWsPresenter implements SocialBoardService {
    //
    private WsRequester wsRequester;
    
    // -------------------------------------------------------------------------
    
    public SocialBoardWsPresenter() {
        //
        this.wsRequester = new WsRequester();
    }
    
    @Override
    public String registerSocialBoard(String teamUsid, String boardName,
            boolean commentable) {
        // 
        SocialBoardWs socialBoardWs = wsRequester.getSocialBoardWs();
        String socialBoardId = socialBoardWs.registerSocialBoard(teamUsid, boardName, commentable);
        
        return socialBoardId;
    }

    @Override
    public DCSocialBoard findSocialBoard(String boardUsid) {
        // 
        SocialBoardWs socialBoardWs = wsRequester.getSocialBoardWs();
        String socialBoardJson = socialBoardWs.findSocialBoard(boardUsid);
        
        return (DCSocialBoard) JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class);
    }

    @Override
    public List<DCSocialBoard> findAllSocialBoards() {
        // 
        SocialBoardWs socialBoardWs = wsRequester.getSocialBoardWs();
        List<String> allSocialBoardJson = socialBoardWs.findAllSocialBoards();
        
        List<DCSocialBoard> allSocialBoard = new ArrayList<DCSocialBoard>();
        for(String socialBoardJson : allSocialBoardJson) {
            allSocialBoard.add((DCSocialBoard) JsonUtil.fromJson(socialBoardJson, DCSocialBoard.class));
        }
        
        return allSocialBoard;
    }

    @Override
    public void modifySocialBoard(String boardUsid, NameValueList nameValues) {
        // 
        SocialBoardWs socialBoardWs = wsRequester.getSocialBoardWs();
        String nameValuesJson = JsonUtil.toJson(nameValues);
        socialBoardWs.modifySocialBoard(boardUsid, nameValuesJson);
    }

    @Override
    public void removeSocialBoard(String boardUsid) {
        // 
        SocialBoardWs socialBoardWs = wsRequester.getSocialBoardWs();
        socialBoardWs.removeSocialBoard(boardUsid);
    }
}
