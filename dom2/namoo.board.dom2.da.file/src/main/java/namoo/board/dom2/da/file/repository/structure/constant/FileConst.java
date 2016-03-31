/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repository.structure.constant;

import java.io.IOException;
import java.util.Properties;

public class FileConst {
    //
    public static final String STORE_DIR = getProperties();
    public static final String DATA_FILE_EXTENSION = ".csv";
    
    public static final String SEPARATOR = ",";
    public static final String NEW_LINE = System.getProperty("line.separator");
    
    
    private FileConst() {
        // do not anything.
    }
    
    //--------------------------------------------------------------------------
    
    private static String getProperties() {
        //
        Properties properties = new Properties();
        try {
            properties.load(FileConst.class.getResourceAsStream("/filestore.properties"));
            return System.getProperty("user.home") + "/" + properties.getProperty("filestore.name");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
