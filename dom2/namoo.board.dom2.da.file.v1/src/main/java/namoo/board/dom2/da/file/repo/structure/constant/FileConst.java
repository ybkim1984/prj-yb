/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.structure.constant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

public class FileConst {
    //
    public static final Charset FILE_ENCODING = Charset.forName("UTF-8");
    public static final String STORE_DIR = getProperties();
    public static final String DATA_FILE_EXTENSION = ".csv";
    public static final String INDEX_FILE_EXTENSION = ".idx";
    
    public static final String SEPARATOR = ",";
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final int NEW_LINE_BYTE_LENGTH = NEW_LINE.getBytes().length;
    
    public static final int BUFFER_SIZE = 256;
    
    public static final int INDEX_COLUMN_SEQ = 0;
    
    //--------------------------------------------------------------------------
    
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
