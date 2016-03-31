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
import namoo.board.dom2.da.file.repository.mapper.SocialBoardMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCSocialBoard;

public class SocialBoardRepository {
    //
    private FileAccessor fileAccessor;
    
    
    public SocialBoardRepository() {
        //
        StoreType storeType = StoreType.Board;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCSocialBoard board) {
        // 
        ColumnValueList newColumnValues = SocialBoardMapper.createColumnValues(board);
        fileAccessor.insert(newColumnValues);
    }

    public DCSocialBoard select(String usid) {
        // 
        ColumnValueList keyColumnValues = SocialBoardMapper.createKeyColumnValues(usid);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
        return SocialBoardMapper.createDomain(resultCsv);
    }
    
    public DCSocialBoard selectByName(String name) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);
        
        ColumnValueList columnValues = SocialBoardMapper.createColumnnValues(conditionMap);
        String resultCsv = fileAccessor.selectOneCsv(columnValues);
        
        return SocialBoardMapper.createDomain(resultCsv);
    }
    
    public List<DCSocialBoard> selectAll() {
        // 
        return SocialBoardMapper.createDomains(fileAccessor.selectAll());
    }

    public void update(DCSocialBoard board) {
        // 
        ColumnValueList updateColumnValues = SocialBoardMapper.createColumnValues(board);
        fileAccessor.update(updateColumnValues);
    }

    public void delete(String usid) {
        // 
        ColumnValueList keyColumnValues = SocialBoardMapper.createKeyColumnValues(usid);
        fileAccessor.delete(keyColumnValues);
    }
}
