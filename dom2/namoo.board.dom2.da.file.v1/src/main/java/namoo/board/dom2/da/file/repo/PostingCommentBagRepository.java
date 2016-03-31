/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo;

import java.io.File;
import java.util.List;

import namoo.board.dom2.da.file.repo.accessor.FileAccessor;
import namoo.board.dom2.da.file.repo.mapper.PostingCommentBagMapper;
import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCCommentBag;
import namoo.board.dom2.entity.board.DCPostingComment;

public class PostingCommentBagRepository {
    //
    private FileAccessor fileAccessor;
    
    public PostingCommentBagRepository() {
        //
        StoreType storeType = StoreType.CommentBag;
        
        String indexFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + "_index" + FileConst.INDEX_FILE_EXTENSION;
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(indexFilePath, dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(String postingUsid, DCPostingComment comment) {
        // 
        DCCommentBag commentBag = selectCommentBag(postingUsid);
        
        if (commentBag == null) {
            commentBag = new DCCommentBag();
            commentBag.addComment(comment);
            
            ColumnValueList newColumnValues = PostingCommentBagMapper.createColumnValues(postingUsid, commentBag);
            fileAccessor.insertWithIndex(newColumnValues);
        }
        else {
            commentBag.addComment(comment);
            ColumnValueList updateColumnValues = PostingCommentBagMapper.createColumnValues(postingUsid, commentBag);
            fileAccessor.updateWithIndex(updateColumnValues);
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
        fileAccessor.updateWithIndex(updateColumnValues);
    }
    
    public void deleteInPosting(String postingUsid) {
        // 
        ColumnValue conditionColumnValue = PostingCommentBagMapper.createIndexColumnValue(postingUsid);
        fileAccessor.deleteByIndex(conditionColumnValue);
    }
    
    public void deleteByUsids(List<String> postingUsids) {
        //
        for (String postingUsid : postingUsids) {
            ColumnValue conditionColumnValue = PostingCommentBagMapper.createIndexColumnValue(postingUsid);
            fileAccessor.deleteByIndex(conditionColumnValue);
        }
    }

    //--------------------------------------------------------------------------
    
    private DCCommentBag selectCommentBag(String postingUsid) {
        // 
        ColumnValue indexCondition = PostingCommentBagMapper.createIndexColumnValue(postingUsid);
        String resultCsv = fileAccessor.selectCsvByIndex(indexCondition);
        
        return PostingCommentBagMapper.createDomain(resultCsv);
    }
}
