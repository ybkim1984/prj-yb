/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2014. 12. 11.
 */

package namoo.board.dom2.cp.pojo;

import java.io.File;

import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.logic.ExcelFileBatchLoaderLogic;
import namoo.board.dom2.service.ExcelFileBatchLoader;

public class ExcelFileBatchPojoLoaderLogic implements ExcelFileBatchLoader {
    //
    private ExcelFileBatchLoader loader;
    
    
    public ExcelFileBatchPojoLoaderLogic(BoardServiceLifecycler serviceLifecycler) {
        //
        this.loader = new ExcelFileBatchLoaderLogic(serviceLifecycler);
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public boolean registerServiceUsers(File file) {
        // 
        return loader.registerServiceUsers(file);
    }
    
}
