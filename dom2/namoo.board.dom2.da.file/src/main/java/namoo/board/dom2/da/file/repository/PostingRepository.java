/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.file.repository.accessor.FileAccessor;
import namoo.board.dom2.da.file.repository.mapper.PostingMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingContents;

public class PostingRepository {
    //
    private FileAccessor fileAccessor;
    
    
    public PostingRepository() {
        //
        StoreType storeType = StoreType.Posting;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCPosting posting) {
        // 
        ColumnValueList newColumnValues = PostingMapper.createColumnValues(posting);
        fileAccessor.insert(newColumnValues);
    }

    public DCPosting select(String usid) {
        // 
        ColumnValueList keyColumnValues = PostingMapper.createKeyColumnValues(usid);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
        return PostingMapper.createDomain(resultCsv);
    }
    
    public DCPostingContents selectContents(String usid) {
        //
        ColumnValueList keyColumnValues = PostingMapper.createKeyColumnValues(usid);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
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
        ColumnValueList updateColumnValues = PostingMapper.createColumnValues(posting);
        fileAccessor.update(updateColumnValues);
    }

    public void delete(String usid) {
        // 
        ColumnValueList keyColumnValues = PostingMapper.createKeyColumnValues(usid);
        fileAccessor.delete(keyColumnValues);
    }
    
    public void deleteByBoard(String boardUsid) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("boardUsid", boardUsid);
        
        ColumnValueList conditionColumnValues = PostingMapper.createColumnnValues(conditionMap);
        fileAccessor.delete(conditionColumnValues);
    }
}
