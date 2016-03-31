/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.shared;

import java.io.File;

import namoo.board.dom2.da.file.repository.structure.constant.FileConst;

import org.junit.After;
import org.junit.Before;

public abstract class BaseFileStoreTest {
    //
    @Before
    public abstract void setUp();
    
    @After
    public void tearDown() {
        //
        File storeDir = new File(FileConst.STORE_DIR);
        if (storeDir.exists() && storeDir.isDirectory()) {
            //
            deleteDir(storeDir.listFiles());
            storeDir.delete();
        }
    }
    
    private void deleteDir(File[] childrenFiles) {
        //
        for (File file : childrenFiles) {
            if (file.isDirectory()) {
                deleteDir(file.listFiles());
            }
            else if (file.isFile()) {
                file.delete();
            }
        }
    }
}
