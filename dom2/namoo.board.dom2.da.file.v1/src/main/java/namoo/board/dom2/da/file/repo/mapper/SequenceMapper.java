/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo.mapper;

import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.SequenceType;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;

public class SequenceMapper {
    //
    public static ColumnValueList createTeamSeqNameColumnValue() {
        //
        return createSeqNameColumnValue(SequenceType.Team.sequenceName());
    }
    public static ColumnValueList createBoardSeqNameColumnValue() {
        //
        return createSeqNameColumnValue(SequenceType.Board.sequenceName());
    }
    public static ColumnValueList createPostingSeqNameColumnValue(String boardUsid) {
        //
        return createSeqNameColumnValue(boardUsid + SequenceType.Posting.suffix());
    }
    
    public static ColumnValueList createCommentSeqNameColumnValue(String postingUsid) {
        //
        return createSeqNameColumnValue(postingUsid + SequenceType.Comment.suffix());
    }
    
    
    public static ColumnValueList createTeamSeqColumnValues(int seq) {
        //
        return createSeqColumnValues(SequenceType.Team.sequenceName(), seq);
    }
    public static ColumnValueList createBoardSeqColumnValues(int seq) {
        //
        return createSeqColumnValues(SequenceType.Board.sequenceName(), seq);
    }
    public static ColumnValueList createPostingSeqColumnValues(String boardUsid, int seq) {
        //
        return createSeqColumnValues(boardUsid + SequenceType.Posting.suffix(), seq);
    }
    
    public static ColumnValueList createCommentSeqColumnValues(String postingUsid, int seq) {
        //
        return createSeqColumnValues(postingUsid + SequenceType.Comment.suffix(), seq);
    }

    
    public static Integer createSequence(String csv) {
        //
        if (csv == null) {
            return null;
        }
        String[] values = csv.split(FileConst.SEPARATOR);
        
        String nextSequence = values[1];
                
        return Integer.valueOf(nextSequence);
    }
    
    //--------------------------------------------------------------------------
    
    private static ColumnValueList createSeqNameColumnValue(String seqName) {
        //
        StoreType storeType = StoreType.Sequence;
        
        ColumnValueList columnValues = new ColumnValueList(storeType);
        columnValues.addColumnValue("seqName", seqName);
        
        return columnValues;
    }
    
    private static ColumnValueList createSeqColumnValues(String seqName, int seq) {
        //
        StoreType storeType = StoreType.Sequence;
        
        ColumnValueList columnValues = new ColumnValueList(storeType);
        columnValues.addColumnValue("seqName", seqName);
        columnValues.addColumnValue("nextSeq", seq);
        
        return columnValues;
    }
    
}
