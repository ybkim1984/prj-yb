/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.cp.lifecycle;

import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.BoardUserService;
import namoo.board.dom2.service.ExcelFileBatchLoader;
import namoo.board.dom2.service.PostingService;
import namoo.board.dom2.service.SocialBoardService;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class BoardServiceSpringLifecycler implements BoardServiceLifecycler {

    private static BoardServiceSpringLifecycler serviceLifecycler;
    private GenericApplicationContext context;
    
    //--------------------------------------------------------------------------
    
    private BoardServiceSpringLifecycler(BoardStoreLifecycler storeLifecycler) {
        // 
        context = new GenericApplicationContext();
        context.getBeanFactory().registerSingleton("storeLifecycler", storeLifecycler);

        BeanDefinitionReader beanDefReader = new XmlBeanDefinitionReader(context) ;
        beanDefReader.loadBeanDefinitions("/applicationContext.xml");
        context.refresh();
    }
    
    public static BoardServiceLifecycler getInstance(BoardStoreLifecycler storeLifecycler) {
        //
        if(serviceLifecycler == null) {
            serviceLifecycler = new BoardServiceSpringLifecycler(storeLifecycler);
        }
        
        return serviceLifecycler; 
    }

    // -------------------------------------------------------------------------

    
    @Override
    public SocialBoardService getSocialBoardService() {
        // 
        return context.getBean(SocialBoardService.class);
    }

    @Override
    public PostingService getPostingService() {
        //
        return context.getBean(PostingService.class);
    }

    @Override
    public BoardUserService getBoardUserService() {
        // 
        return context.getBean(BoardUserService.class);
    }
    
    @Override
    public ExcelFileBatchLoader getExcelFileBatchLoader() {
        //
        return context.getBean(ExcelFileBatchLoader.class);
    }
    
}
