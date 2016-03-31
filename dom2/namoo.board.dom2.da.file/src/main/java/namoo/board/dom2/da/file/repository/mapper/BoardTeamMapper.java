/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repository.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.file.repository.structure.ColumnValueList;
import namoo.board.dom2.da.file.repository.structure.StoreType;
import namoo.board.dom2.da.file.repository.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardTeam;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.StringUtils;

public class BoardTeamMapper {
    //
    public static ColumnValueList createColumnValues(DCBoardTeam team) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Team);
        
        columnValues.addColumnValue("usid", team.getUsid());
        columnValues.addColumnValue("name", team.getName());
        columnValues.addColumnValue("adminEmail", team.getAdmin().getEmail());
        
        return columnValues;
    }
    
    public static ColumnValueList createColumnnValues(Map<String, String> map) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Team);
        
        String usid = map.get("usid");
        if (StringUtils.isNotEmpty(usid)) {
            columnValues.addColumnValue("usid", usid);
        }
        
        String name = map.get("name");
        if (StringUtils.isNotEmpty(name)) {
            columnValues.addColumnValue("name", name);
        }
        
        String adminEmail = map.get("adminEmail");
        if (StringUtils.isNotEmpty(adminEmail)) {
            columnValues.addColumnValue("adminEmail", adminEmail);
        }
        
        return columnValues;
    }
    
    public static ColumnValueList createKeyColumnnValues(String usid) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Team);
        columnValues.addColumnValue("usid", usid);
        
        return columnValues;
    }
    
    public static DCBoardTeam createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String usid = values[0];
        String name = values[1];
        String adminEmail = values[2];
                
        DCBoardTeam team = new DCBoardTeam(usid);
        team.setName(name);
        team.setAdmin(new DCBoardUser(adminEmail));
        
        return team;
    }
    
    public static List<DCBoardTeam> createDomains(List<String> csvs) {
        //
        List<DCBoardTeam> teams = new ArrayList<DCBoardTeam>(csvs.size());
        
        for (String csv : csvs) {
            teams.add(createDomain(csv));
        }
        return teams;
    }
}
