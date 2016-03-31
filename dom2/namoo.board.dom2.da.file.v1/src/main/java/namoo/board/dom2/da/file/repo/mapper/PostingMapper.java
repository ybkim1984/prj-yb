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
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingContents;
import namoo.board.dom2.entity.board.DCPostingOption;
import namoo.board.dom2.util.DateUtils;
import namoo.board.dom2.util.StringUtils;

public class PostingMapper {
    //
    public static ColumnValueList createColumnValues(DCPosting posting) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Posting);
        
        columnValues.addColumnValue("usid", posting.getUsid());
        columnValues.addColumnValue("boardUsid", posting.getBoardUsid());
        columnValues.addColumnValue("title", posting.getTitle());
        columnValues.addColumnValue("writerEmail", posting.getWriterEmail());
        columnValues.addColumnValue("readCount", posting.getReadCount());
        columnValues.addColumnValue("regDate", DateUtils.parseStr(posting.getRegisterDate()));
        
        DCPostingOption option = posting.getOption();
        if (option == null) {
            return columnValues;
        }
        columnValues.addColumnValue("commentable", option.isCommentable());
        columnValues.addColumnValue("anonymousComment", option.isAnonymousComment());
        
        DCPostingContents contents = posting.getContents();
        if (contents == null) {
            return columnValues;
        }
        columnValues.addColumnValue("contents", contents.getContents());
        
        return columnValues;
    }
    
    public static ColumnValueList createColumnnValues(Map<String, String> map) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Posting);
        
        String usid = map.get("usid");
        if (StringUtils.isNotEmpty(usid)) {
            columnValues.addColumnValue("usid", usid);
        }
        
        String boardUsid = map.get("boardUsid");
        if (StringUtils.isNotEmpty(boardUsid)) {
            columnValues.addColumnValue("boardUsid", boardUsid);
        }
        
        String title = map.get("title");
        if (StringUtils.isNotEmpty(title)) {
            columnValues.addColumnValue("title", title);
        }
        
        String writerEmail = map.get("writerEmail");
        if (StringUtils.isNotEmpty(writerEmail)) {
            columnValues.addColumnValue("writerEmail", writerEmail);
        }
        
        return columnValues;
    }
    
    public static ColumnValue createIndexColumnValue(String usid) {
        //
        ColumnValueList columnValues = new ColumnValueList(StoreType.Posting);
        columnValues.addColumnValue("usid", usid);
        
        return columnValues.getIndexColumnValue();
    }
    
    
    public static DCPosting createDomain(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String usid = values[0];
        String boardUsid = values[1];
        String title = values[2];
        String writerEmail = values[3];
        String readCount = values[4];
        String regDate = values[5];
        String commentable = values[6];
        String anonymousComment = values[7];
                
        DCPosting posting = new DCPosting(usid);
        posting.setBoardUsid(boardUsid);
        posting.setTitle(title);
        posting.setWriterEmail(writerEmail);
        posting.setReadCount(Integer.valueOf(readCount));
        posting.setRegisterDate(DateUtils.parseDate(regDate));
        
        
        DCPostingOption option = new DCPostingOption().
                commentable(Boolean.valueOf(commentable)).
                anonymousComment(Boolean.valueOf(anonymousComment));
        
        posting.setOption(option);
        
        return posting;
    }
    
    public static DCPostingContents createPostingContents(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String usid = values[0];
        String contents = values[8];
                
        DCPostingContents postingContents = new DCPostingContents(new DCPosting(usid), contents);
        
        return postingContents;
    }
    
    public static List<DCPosting> createDomains(List<String> csvs) {
        //
        List<DCPosting> postings = new ArrayList<DCPosting>(csvs.size());
        
        for (String csv : csvs) {
            postings.add(createDomain(csv));
        }
        return postings;
    }
    
}
