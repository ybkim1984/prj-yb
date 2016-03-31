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
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.StringUtils;



public class BoardUserMapper {
    //
    public static ColumnValueList createColumnValues(DCBoardUser user) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.User);
        
        columnValues.addColumnValue("email", user.getEmail());
        columnValues.addColumnValue("name", user.getName());
        columnValues.addColumnValue("phoneNumber", user.getPhoneNumber());
        
        return columnValues;
    }
    
    public static ColumnValueList createColumnnValues(Map<String, String> map) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.User);
        
        String email = map.get("email");
        if (StringUtils.isNotEmpty(email)) {
            columnValues.addColumnValue("email", email);
        }
        
        String name = map.get("name");
        if (StringUtils.isNotEmpty(name)) {
            columnValues.addColumnValue("name", name);
        }
        
        String phoneNumber = map.get("phoneNumber");
        if (StringUtils.isNotEmpty(phoneNumber)) {
            columnValues.addColumnValue("phoneNumber", phoneNumber);
        }
        
        return columnValues;
    }
    
    public static ColumnValueList createKeyColumnValues(String email) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.User);
        columnValues.addColumnValue("email", email);
        
        return columnValues;
    }
    
    public static DCBoardUser createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String email = values[0];
        String name = values[1];
        String phoneNumber = values[2];
                
        return new DCBoardUser(email, name, phoneNumber);
    }
    
    public static List<DCBoardUser> createDomains(List<String> csvs) {
        //
        List<DCBoardUser> users = new ArrayList<DCBoardUser>(csvs.size());
        
        for (String csv : csvs) {
            users.add(createDomain(csv));
        }
        return users;
    }
}
