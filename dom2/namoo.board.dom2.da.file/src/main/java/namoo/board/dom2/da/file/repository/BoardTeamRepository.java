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
import namoo.board.dom2.da.file.repository.mapper.BoardTeamMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardTeam;

public class BoardTeamRepository {
    //
    private FileAccessor fileAccessor;

    
    public BoardTeamRepository() {
        //
        StoreType storeType = StoreType.Team;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(DCBoardTeam team) {
        // 
        ColumnValueList newColumnValues = BoardTeamMapper.createColumnValues(team);
        fileAccessor.insert(newColumnValues);
    }

    public DCBoardTeam select(String usid) {
        // 
        ColumnValueList keyColumnValues = BoardTeamMapper.createKeyColumnnValues(usid);
        String resultCsv = fileAccessor.selectOneCsv(keyColumnValues);
        
        return BoardTeamMapper.createDomain(resultCsv);
    }

    public DCBoardTeam selectOneByName(String name) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);
        
        ColumnValueList columnValues = BoardTeamMapper.createColumnnValues(conditionMap);
        String resultCsv = fileAccessor.selectOneCsv(columnValues);
        return BoardTeamMapper.createDomain(resultCsv);
    }
    
    public List<DCBoardTeam> selectAll() {
        // 
        return BoardTeamMapper.createDomains(fileAccessor.selectAll());
    }

    public void delete(String usid) {
        // 
        fileAccessor.delete(BoardTeamMapper.createKeyColumnnValues(usid));
    }

}
