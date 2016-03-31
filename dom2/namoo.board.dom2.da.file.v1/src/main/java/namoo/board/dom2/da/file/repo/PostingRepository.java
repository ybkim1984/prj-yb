/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.file.repo.accessor.FileAccessor;
import namoo.board.dom2.da.file.repo.mapper.PostingMapper;
import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingContents;

public class PostingRepository {
    //
    private FileAccessor fileAccessor;
    
    public PostingRepository() {
        //
        StoreType storeType = StoreType.Posting;
        
        String indexFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + "_index" + FileConst.INDEX_FILE_EXTENSION;
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(indexFilePath, dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCPosting posting) {
        // 
        ColumnValueList newColumnValues = PostingMapper.createColumnValues(posting);
        fileAccessor.insertWithIndex(newColumnValues);
    }

    public DCPosting select(String usid) {
        // 
        ColumnValue indexCondition = PostingMapper.createIndexColumnValue(usid);
        String resultCsv = fileAccessor.selectCsvByIndex(indexCondition);
        
        return PostingMapper.createDomain(resultCsv);
    }
    
    public DCPostingContents selectContents(String postingUsid) {
        //
        ColumnValue indexCondition = PostingMapper.createIndexColumnValue(postingUsid);
        String resultCsv = fileAccessor.selectCsvByIndex(indexCondition);
        
        return PostingMapper.createPostingContents(resultCsv);
    }
    
    public List<DCPosting> selectListByBoard(String boardUsid) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("boardUsid", boardUsid);
        
        ColumnValueList columnValues = PostingMapper.createColumnnValues(conditionMap);
        List<String> resultCsvs = fileAccessor.selectListCsv(columnValues);
        
        return PostingMapper.createDomains(resultCsvs);
    }

    public void update(DCPosting posting) {
        // 
        ColumnValueList updateValues = PostingMapper.createColumnValues(posting);
        fileAccessor.updateWithIndex(updateValues);
    }

    public void delete(String usid) {
        // 
        fileAccessor.deleteByIndex(PostingMapper.createIndexColumnValue(usid));
    }
    
    public void deleteByBoard(String boardUsid) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("boardUsid", boardUsid);
        
        fileAccessor.deleteByCondition(PostingMapper.createColumnnValues(conditionMap));
    }
}
