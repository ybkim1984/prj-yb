/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws.server;

import namoo.board.dom2.sp.ws.BoardUserWs;
import namoo.board.dom2.sp.ws.PostingWs;
import namoo.board.dom2.sp.ws.SocialBoardWs;
import namoo.board.dom2.sp.ws.logic.BoardUserWsLogic;
import namoo.board.dom2.sp.ws.logic.PostingWsLogic;
import namoo.board.dom2.sp.ws.logic.SocialBoardWsLogic;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server {
    //
    private static final String HOST = "http://localhost:8080/";
    
    public static final String ADDR_BOARD_USER = HOST + "boardUserWs";
    
    public static final String ADDR_POSTING = HOST + "postingWs";
    
    public static final String ADDR_SOCIAL_BOARD = HOST + "socialBoardWs";
    
    // -------------------------------------------------------------------------
    
    public Server() {
        //
        System.out.println("Starting Server");
        
        BoardUserWsLogic boardUserWsLogic = new BoardUserWsLogic();
        JaxWsServerFactoryBean boardServerFactory = new JaxWsServerFactoryBean();
        boardServerFactory.setServiceClass(BoardUserWs.class);
        boardServerFactory.setAddress(ADDR_BOARD_USER);
        boardServerFactory.setServiceBean(boardUserWsLogic);
        boardServerFactory.getInInterceptors().add(new LoggingInInterceptor());
        boardServerFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        boardServerFactory.create();
        
        PostingWsLogic postingWsLogic = new PostingWsLogic();
        JaxWsServerFactoryBean postingServerFactory = new JaxWsServerFactoryBean();
        postingServerFactory.setServiceClass(PostingWs.class);
        postingServerFactory.setAddress(ADDR_POSTING);
        postingServerFactory.setServiceBean(postingWsLogic);
        postingServerFactory.getInInterceptors().add(new LoggingInInterceptor());
        postingServerFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        postingServerFactory.create();
        
        SocialBoardWsLogic socialBoardWsLogic = new SocialBoardWsLogic();
        JaxWsServerFactoryBean socialBoardServerFactory = new JaxWsServerFactoryBean();
        socialBoardServerFactory.setServiceClass(SocialBoardWs.class);
        socialBoardServerFactory.setAddress(ADDR_SOCIAL_BOARD);
        socialBoardServerFactory.setServiceBean(socialBoardWsLogic);
        socialBoardServerFactory.getInInterceptors().add(new LoggingInInterceptor());
        socialBoardServerFactory.getOutInterceptors().add(new LoggingOutInterceptor());
        socialBoardServerFactory.create();
    }
}
