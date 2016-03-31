/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;

public class IOUtils {
    //
    private IOUtils() {
        // do no anything.
    }
    
    /**
     * Closeable 인터페이스를 구현한 객체를 close
     * 
     * @param cloasables close 할 객체 다건
     */
    public static void closeQuietly(Closeable...closeables) {
        //
        for (Closeable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * ISO-8859-1 인코딩의 문자열을 UTF-8 인코딩 문자열로 변환
     * 
     * @param iso8859
     * @return
     */
    public static String parseUTF8Str(String iso8859) {
        //
        if (iso8859 == null ) {
            return null;
        }
        return new String(iso8859.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }
}
