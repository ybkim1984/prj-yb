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
import namoo.board.dom2.da.file.repository.mapper.BoardMemberMapper;
import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardMember;

public class BoardMemberRepository {
    //
    private FileAccessor fileAccessor;
    
    public BoardMemberRepository() {
        //
        StoreType storeType = StoreType.Member;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public void insert(String teamUsid, DCBoardMember member) {
        // 
        ColumnValueList newColumnValues = BoardMemberMapper.createColumnValues(teamUsid, member);
        fileAccessor.insert(newColumnValues);
    }

    public DCBoardMember select(String teamUsid, String memberEmail) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("teamUsid", teamUsid);
        conditionMap.put("memberEmail", memberEmail);
        
        ColumnValueList columnValues = BoardMemberMapper.createColumnnValues(conditionMap);
        String resultCsv = fileAccessor.selectOneCsv(columnValues);
        return BoardMemberMapper.createDomain(resultCsv);
    }
    
    public List<DCBoardMember> selectByTeam(String teamUsid) {
        //
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("teamUsid", teamUsid);
        
        ColumnValueList columnValues = BoardMemberMapper.createColumnnValues(conditionMap);
        List<String> resultCsvs = fileAccessor.selectListCsv(columnValues);
        return BoardMemberMapper.createDomains(resultCsvs);
    }
    
    public void delete(String teamUsid, String memberEmail) {
        // 
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("teamUsid", teamUsid);
        conditionMap.put("memberEmail", memberEmail);
        
        fileAccessor.delete(BoardMemberMapper.createColumnnValues(conditionMap));
    }
    
    public void deleteByTeam(String teamUsid) {
        //
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("teamUsid", teamUsid);
        
        fileAccessor.delete(BoardMemberMapper.createColumnnValues(conditionMap));
    }


}
