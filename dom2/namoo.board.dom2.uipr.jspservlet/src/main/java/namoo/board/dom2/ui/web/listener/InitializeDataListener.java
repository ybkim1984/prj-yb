/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.web.listener;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import namoo.board.dom2.cp.lifecycle.BoardServicePojoLifecycler;
import namoo.board.dom2.da.lifecycle.BoardStoreJdbcLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.service.ExcelFileBatchLoader;

@WebListener
public class InitializeDataListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 
        BoardStoreLifecycler persistLifecycler = BoardStoreJdbcLifecycler.getInstance();
        BoardServiceLifecycler serviceLifecycler = BoardServicePojoLifecycler.getInstance(persistLifecycler);
        ExcelFileBatchLoader excelLoader = serviceLifecycler.getExcelFileBatchLoader();
        
        URL path = this.getClass().getResource("/BoardUsers.xlsx");
        
        excelLoader.registerServiceUsers(new File(path.getPath()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 
        
    }

}
