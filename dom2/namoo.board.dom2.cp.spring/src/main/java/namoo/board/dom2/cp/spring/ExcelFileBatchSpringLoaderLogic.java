/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 01. 16.
 */

package namoo.board.dom2.cp.spring;

import java.io.File;

import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.logic.ExcelFileBatchLoaderLogic;
import namoo.board.dom2.service.ExcelFileBatchLoader;

public class ExcelFileBatchSpringLoaderLogic implements ExcelFileBatchLoader {
    //
    private ExcelFileBatchLoader loader;
    
    public ExcelFileBatchSpringLoaderLogic(BoardServiceLifecycler serviceLifecycler) {
        //
        this.loader = new ExcelFileBatchLoaderLogic(serviceLifecycler);
    }
    
    // -------------------------------------------------------------------------

    @Override
    public boolean registerServiceUsers(File file) {
        // 
        return loader.registerServiceUsers(file);    
    }
}
