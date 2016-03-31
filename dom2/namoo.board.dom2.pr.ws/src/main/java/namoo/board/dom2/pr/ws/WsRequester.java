/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.pr.ws;

import namoo.board.dom2.sp.ws.BoardUserWs;
import namoo.board.dom2.sp.ws.PostingWs;
import namoo.board.dom2.sp.ws.SocialBoardWs;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class WsRequester {
    //
    private static final String HOST = "http://localhost:8080/";
    
    private static final String ADDR_BOARD_USER = HOST + "boardUserWs";
    
    private static final String ADDR_POSTING = HOST + "postingWs";
    
    private static final String ADDR_SOCIAL_BOARD = HOST + "socialBoardWs";
    
    // -------------------------------------------------------------------------
    
    public BoardUserWs getBoardUserWs() {
        //
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(BoardUserWs.class);
        factoryBean.setAddress(ADDR_BOARD_USER);
        
        BoardUserWs boardUserWs = (BoardUserWs) factoryBean.create();

        return boardUserWs;
    }
    
    public PostingWs getPostingWs() {
        //
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setServiceClass(PostingWs.class);
        factoryBean.setAddress(ADDR_POSTING);
        
        PostingWs postingWs = (PostingWs) factoryBean.create();

        return postingWs;
    }
    
    public SocialBoardWs getSocialBoardWs() {
        //
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingInInterceptor());
        factoryBean.setServiceClass(SocialBoardWs.class);
        factoryBean.setAddress(ADDR_SOCIAL_BOARD);
        
        SocialBoardWs socialBoardWs = (SocialBoardWs) factoryBean.create();
        
        return socialBoardWs;
    }
    
}
