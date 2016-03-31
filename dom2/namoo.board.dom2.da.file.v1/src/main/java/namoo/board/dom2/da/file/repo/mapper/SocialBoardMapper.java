/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.util.DateUtils;
import namoo.board.dom2.util.StringUtils;

public class SocialBoardMapper {
    //
    public static ColumnValueList createColumnValues(DCSocialBoard board) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Board);
        
        columnValues.addColumnValue("usid", board.getUsid());
        columnValues.addColumnValue("name", board.getName());
        columnValues.addColumnValue("commentable", board.isCommentable());
        columnValues.addColumnValue("teamUsid", board.getTeamUsid());
        columnValues.addColumnValue("createDate", DateUtils.parseStr(board.getCreateDate()));
        
        return columnValues;
    }
    
    public static ColumnValueList createColumnnValues(Map<String, String> map) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Board);
        
        String usid = map.get("usid");
        if (StringUtils.isNotEmpty(usid)) {
            columnValues.addColumnValue("usid", usid);
        }
        
        String name = map.get("name");
        if (StringUtils.isNotEmpty(name)) {
            columnValues.addColumnValue("name", name);
        }
        
        String commentable = map.get("commentable");
        if (StringUtils.isNotEmpty(commentable)) {
            columnValues.addColumnValue("commentable", commentable);
        }
        
        String teamUsid = map.get("teamUsid");
        if (StringUtils.isNotEmpty(teamUsid)) {
            columnValues.addColumnValue("teamUsid", teamUsid);
        }
        String createDate = map.get("createDate");
        if (StringUtils.isNotEmpty(createDate)) {
            columnValues.addColumnValue("createDate", createDate);
        }
        
        return columnValues;
    }
    
    public static ColumnValue createIndexColumnValue(String usid) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Board);
        columnValues.addColumnValue("usid", usid);
        
        return columnValues.getIndexColumnValue();
    }
    
    
    public static DCSocialBoard createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String usid = values[0];
        String name = values[1];
        String commentable = values[2];
        String teamUsid = values[3];
        String createDate = values[4];
                
        DCSocialBoard board = new DCSocialBoard(usid, name, DateUtils.parseDate(createDate), teamUsid, Boolean.valueOf(commentable));
        
        return board;
    }
    
    public static List<DCSocialBoard> createDomains(List<String> csvs) {
        //
        List<DCSocialBoard> boards = new ArrayList<DCSocialBoard>(csvs.size());
        
        for (String csv : csvs) {
            boards.add(createDomain(csv));
        }
        return boards;
    }
}
