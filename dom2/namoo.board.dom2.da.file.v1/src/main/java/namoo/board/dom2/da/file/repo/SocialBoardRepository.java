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
import namoo.board.dom2.da.file.repo.mapper.SocialBoardMapper;
import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCSocialBoard;

public class SocialBoardRepository {
    //
    private FileAccessor fileAccessor;
    
    public SocialBoardRepository() {
        //
        StoreType storeType = StoreType.Board;
        
        String indexFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + "_index" + FileConst.INDEX_FILE_EXTENSION;
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(indexFilePath, dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCSocialBoard board) {
        // 
        ColumnValueList newColumnValues = SocialBoardMapper.createColumnValues(board);
        fileAccessor.insertWithIndex(newColumnValues);
    }

    public DCSocialBoard select(String usid) {
        // 
        ColumnValue indexCondition = SocialBoardMapper.createIndexColumnValue(usid);
        String resultCsv = fileAccessor.selectCsvByIndex(indexCondition);
        
        return SocialBoardMapper.createDomain(resultCsv);
    }
    
    public List<DCSocialBoard> selectAll() {
        // 
        return SocialBoardMapper.createDomains(fileAccessor.selectAll());
    }

    public DCSocialBoard selectByName(String name) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);
        
        ColumnValueList columnValues = SocialBoardMapper.createColumnnValues(conditionMap);
        String resultCsv = fileAccessor.selectOneCsv(columnValues);
        return SocialBoardMapper.createDomain(resultCsv);
    }
    

    public void update(DCSocialBoard board) {
        // 
        ColumnValueList updateValues = SocialBoardMapper.createColumnValues(board);
        fileAccessor.updateWithIndex(updateValues);
    }

    public void delete(String usid) {
        // 
        fileAccessor.deleteByIndex(SocialBoardMapper.createIndexColumnValue(usid));
    }
}
