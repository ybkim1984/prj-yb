/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repository;

import java.io.File;
import java.util.List;

import namoo.board.dom2.da.file.repository.accessor.FileAccessor;
import namoo.board.dom2.da.file.repository.mapper.PostingCommentBagMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPostingComment;

public class PostingCommentBagRepository {
    //
    private FileAccessor fileAccessor;
    
    
    public PostingCommentBagRepository() {
        //
        StoreType storeType = StoreType.CommentBag;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(String postingUsid, DCPostingComment comment) {
        // 
        DCCommentBag commentBag = selectCommentBag(postingUsid);
        
        if (commentBag == null) {
            commentBag = new DCCommentBag();
            commentBag.addComment(comment);
            
            ColumnValueList newColumnValues = PostingCommentBagMapper.createColumnValues(postingUsid, commentBag);
            fileAccessor.insert(newColumnValues);
        }
        else {
            commentBag.addComment(comment);
            ColumnValueList updateColumnValues = PostingCommentBagMapper.createColumnValues(postingUsid, commentBag);
            fileAccessor.update(updateColumnValues);
        }
    }

    public DCCommentBag select(String postingUsid) {
        // 
        return selectCommentBag(postingUsid);
    }
    
    public void delete(String postingUsid, int sequence) {
        // 
        DCCommentBag commentBag = selectCommentBag(postingUsid);
        commentBag.removeComment(sequence);

        ColumnValueList updateColumnValues = PostingCommentBagMapper.createColumnValues(postingUsid, commentBag);
        fileAccessor.update(updateColumnValues);
    }

    public void deleteInPosting(String postingUsid) {
        // 
        ColumnValueList conditionColumnValues = PostingCommentBagMapper.createKeyColumnValues(postingUsid);
        fileAccessor.delete(conditionColumnValues);
    }
    
    public void deleteByUsids(List<String> postingUsids) {
        //
        for (String postingUsid : postingUsids) {
            ColumnValueList conditionColumnValues = PostingCommentBagMapper.createKeyColumnValues(postingUsid);
            fileAccessor.delete(conditionColumnValues);
        }
    }
    
    //--------------------------------------------------------------------------
    
    private DCCommentBag selectCommentBag(String postingUsid) {
        // 
        ColumnValueList keyColumnValues = PostingCommentBagMapper.createKeyColumnValues(postingUsid);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
        return PostingCommentBagMapper.createDomain(resultCsv);
    }
}
