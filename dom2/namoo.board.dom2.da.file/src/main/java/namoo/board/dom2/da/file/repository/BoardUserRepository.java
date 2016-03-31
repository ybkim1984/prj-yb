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
import namoo.board.dom2.da.file.repository.mapper.BoardUserMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardUser;

public class BoardUserRepository {
    //
    private FileAccessor fileAccessor;
    
    
    public BoardUserRepository() {
        //
        StoreType storeType = StoreType.User;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCBoardUser user) {
        // 
        ColumnValueList newColumnValues = BoardUserMapper.createColumnValues(user);
        fileAccessor.insert(newColumnValues);
    }

    public DCBoardUser select(String email) {
        // 
        ColumnValueList keyColumnValues = BoardUserMapper.createKeyColumnValues(email);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
        return BoardUserMapper.createDomain(resultCsv);
    }
    
    public List<DCBoardUser> selectAll() {
        // 
        return BoardUserMapper.createDomains(fileAccessor.selectAll());
    }

    public List<DCBoardUser> selectListByName(String name) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);
        
        ColumnValueList columnValues = BoardUserMapper.createColumnnValues(conditionMap);
        List<String> resultCsvs = fileAccessor.selectListCsv(columnValues);
        
        return BoardUserMapper.createDomains(resultCsvs);
    }

    public void update(DCBoardUser user) {
        // 
        ColumnValueList updateColumnValues = BoardUserMapper.createColumnValues(user);
        fileAccessor.update(updateColumnValues);
    }

    public void delete(String email) {
        // 
        fileAccessor.delete(BoardUserMapper.createKeyColumnValues(email));
    }
}
