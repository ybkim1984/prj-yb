/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */
package namoo.board.dom2.cp.spring;

import java.util.List;

import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.logic.SocialBoardServiceLogic;
import namoo.board.dom2.service.SocialBoardService;
import namoo.board.dom2.util.namevalue.NameValueList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialBoardSpringServiceLogic implements SocialBoardService {
    //
    private SocialBoardService service;

    @Autowired
    public SocialBoardSpringServiceLogic(BoardStoreLifecycler storeLifecycler) {
        //
        this.service = new SocialBoardServiceLogic(storeLifecycler);
    }

    // -------------------------------------------------------------------------

    @Override
    public String registerSocialBoard(String teamUsid, String boardName, boolean commentable) {
        // 
        return service.registerSocialBoard(teamUsid, boardName, commentable);
    }


    @Override
    public DCSocialBoard findSocialBoard(String boardUsid) {
        // 
        return service.findSocialBoard(boardUsid);
    }


    @Override
    public List<DCSocialBoard> findAllSocialBoards() {
        // 
        return service.findAllSocialBoards();
    }


    @Override
    public void modifySocialBoard(String boardUsid, NameValueList nameValues) {
        // 
        service.modifySocialBoard(boardUsid, nameValues);
    }


    @Override
    public void removeSocialBoard(String boardUsid) {
        // 
        service.removeSocialBoard(boardUsid);
    }
}
