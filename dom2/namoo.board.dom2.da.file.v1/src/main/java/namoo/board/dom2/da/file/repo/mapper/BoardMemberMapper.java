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

import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.entity.user.DCBoardMember;
import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.StringUtils;

public class BoardMemberMapper {
    //
    public static ColumnValueList createColumnValues(String teamUsid, DCBoardMember member) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Member);
        
        columnValues.addColumnValue("usid", member.getUsid());
        columnValues.addColumnValue("teamUsid", teamUsid);
        columnValues.addColumnValue("memberEmail", member.getUser().getEmail());
        
        return columnValues;
    }
    
    public static ColumnValueList createColumnnValues(Map<String, String> map) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Member);
        
        String usid = map.get("usid");
        if (StringUtils.isNotEmpty(usid)) {
            columnValues.addColumnValue("usid", usid);
        }
        
        String teamUsid = map.get("teamUsid");
        if (StringUtils.isNotEmpty(teamUsid)) {
            columnValues.addColumnValue("teamUsid", teamUsid);
        }
        
        String memberEmail = map.get("memberEmail");
        if (StringUtils.isNotEmpty(memberEmail)) {
            columnValues.addColumnValue("memberEmail", memberEmail);
        }
        
        return columnValues;
    }
    
    public static DCBoardMember createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String usid = values[0];
        // FIXME: 팀이름을 채워줘야 하는데 가지고 있는 데이터가 팀 usid밖에 없음..
        // 그래서 store쪽에서 인자로 받은 teamUsid로 teamName채워넣음
        //String teamUsid = values[1];
        String memberEmail = values[2];
                
        DCBoardMember member = new DCBoardMember(usid);

        //member.setTeamName(teamName);
        member.setUser(new DCBoardUser(memberEmail));
        
        return member;
    }
    
    public static List<DCBoardMember> createDomains(List<String> csvs) {
        //
        List<DCBoardMember> members = new ArrayList<DCBoardMember>(csvs.size());
        
        for (String csv : csvs) {
            members.add(createDomain(csv));
        }
        return members;
    }
}
