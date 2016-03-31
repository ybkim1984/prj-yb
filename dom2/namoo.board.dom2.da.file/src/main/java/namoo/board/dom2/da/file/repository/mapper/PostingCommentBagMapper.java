/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repository.mapper;

import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.util.json.JsonUtil;

public class PostingCommentBagMapper {
    //
    public static ColumnValueList createColumnValues(String postingUsid, DCCommentBag commentBag) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.CommentBag);
        
        columnValues.addColumnValue("postingUsid", postingUsid);
        
        String commentBagJson = JsonUtil.toJson(commentBag);
        columnValues.addColumnValue("commentBagJson", commentBagJson);
        
        return columnValues;
    }
    
    public static ColumnValueList createKeyColumnValues(String postingUsid) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.CommentBag);
        columnValues.addColumnValue("postingUsid", postingUsid);
        
        return columnValues;
    }
    
    public static DCCommentBag createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR, 2);

        String commentBagJson = values[1];
        
        return JsonUtil.fromJson(commentBagJson, DCCommentBag.class);
    }
    
}
