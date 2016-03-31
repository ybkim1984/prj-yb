/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 12.
 */
package namoo.board.dom2.da.mem.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import namoo.board.dom2.util.json.JsonUtil;

public class ObjectCopyUtil {
    //
    private ObjectCopyUtil() {}
    
    /**
     * 객체 복사
     * <pre>객체의 reference를 끊기 위해서 json으로 변환 한 후 다시 객체로 변환</pre> 
     * 
     * @param object    Json으로 변환 할 객체
     * @param clazz     객체 클래스 정보
     * @return  복사 된 객체
     */
    public static <T> T copyObject(T object, Class<T> clazz) {
        //
        String objStr = JsonUtil.toJson(object);
        T result = JsonUtil.fromJson(objStr, clazz);
        
        return result;
    }
    
    /**
     * Java Collection 객체를 복사하여 리스트로 반환
     * <pre>객체의 reference를 끊기 위해서 json으로 변환 한 후 다시 객체로 변환</pre>
     * 
     * @param collectionObject  Json으로 변환 할 Java Collection 객체
     * @param clazz             Collection 엘리먼트 클래스 정보
     * @return  복사 된 List
     */
    public static <E> List<E> copyObjects(Collection<E> collectionObject, Class<E> clazz) {
        //
        List<E> results = new ArrayList<E>();
        
        for (E type : collectionObject) {
            results.add(copyObject(type, clazz));
        }
        return results;
    }
}
