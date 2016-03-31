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
import namoo.board.dom2.da.file.repo.mapper.BoardUserMapper;
import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardUser;

public class BoardUserRepository {
    //
    private FileAccessor fileAccessor;
    
    public BoardUserRepository() {
        //
        StoreType storeType = StoreType.User;
        
        String indexFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + "_index" + FileConst.INDEX_FILE_EXTENSION;
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(indexFilePath, dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCBoardUser user) {
        // 
        ColumnValueList newColumnValues = BoardUserMapper.createColumnValues(user);
        fileAccessor.insertWithIndex(newColumnValues);
    }

    public DCBoardUser select(String email) {
        // 
        ColumnValue indexCondition = BoardUserMapper.createIndexColumnValue(email);
        String resultCsv = fileAccessor.selectCsvByIndex(indexCondition);
        
        return BoardUserMapper.createDomain(resultCsv);
    }
    
    public List<DCBoardUser> selectAll() {
        // 
        return BoardUserMapper.createDomains(fileAccessor.selectAll());
    }

    public List<DCBoardUser> selectByName(String name) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);
        
        ColumnValueList columnValues = BoardUserMapper.createColumnnValues(conditionMap);
        List<String> resultCsvs = fileAccessor.selectListCsv(columnValues);
        
        return BoardUserMapper.createDomains(resultCsvs);
    }

    public void update(DCBoardUser user) {
        // 
        ColumnValueList updateValues = BoardUserMapper.createColumnValues(user);
        fileAccessor.updateWithIndex(updateValues);
    }

    public void delete(String email) {
        // 
        fileAccessor.deleteByIndex(BoardUserMapper.createIndexColumnValue(email));
    }
}
