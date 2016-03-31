/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 11.
 */
package namoo.board.dom2.da.file.repo;

import java.io.File;

import namoo.board.dom2.da.file.repo.accessor.FileAccessor;
import namoo.board.dom2.da.file.repo.mapper.SequenceMapper;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.StoreType;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;

public class SequenceRepository {
    //
    private final int FIRST_SEQUENCE = 1;
    
    private FileAccessor fileAccessor;
    
    public SequenceRepository() {
        //
        StoreType storeType = StoreType.Sequence;
        
        String dataFilePath = FileConst.STORE_DIR + File.separator + storeType.storeName() + FileConst.DATA_FILE_EXTENSION;
        this.fileAccessor = new FileAccessor(dataFilePath);
    }
    
    //--------------------------------------------------------------------------
    
    public int selectTeamNextSeq() {
        // 
        return nextSeq(SequenceMapper.createTeamSeqNameColumnValue(), 
                SequenceMapper.createTeamSeqColumnValues(FIRST_SEQUENCE));
    }
    
    public int selectBoardNextSeq() {
        // 
        return nextSeq(SequenceMapper.createBoardSeqNameColumnValue(), 
                SequenceMapper.createBoardSeqColumnValues(FIRST_SEQUENCE));
    }
    
    public int selectPostingNextSeq(String boardUsid) {
        // 
        return nextSeq(SequenceMapper.createPostingSeqNameColumnValue(boardUsid), 
                SequenceMapper.createPostingSeqColumnValues(boardUsid, FIRST_SEQUENCE));
    }
    
    public int selectCommentNextSeq(String postingUsid) {
        // 
        return nextSeq(SequenceMapper.createCommentSeqNameColumnValue(postingUsid), 
                SequenceMapper.createCommentSeqColumnValues(postingUsid, FIRST_SEQUENCE));
    }
    
    
    private int nextSeq(ColumnValueList seqNameColumnValue, ColumnValueList insertColumnValues) {
        //
        ColumnValueList conditions = seqNameColumnValue;
        String resultCsv = fileAccessor.selectOneCsv(conditions);
        Integer nextSeq = SequenceMapper.createSequence(resultCsv);
        
        // 시퀀스 정보가 없으면 새로운 레코드 생성
        if (nextSeq == null) {
            fileAccessor.insertWithoutIndex(insertColumnValues);
            nextSeq = FIRST_SEQUENCE;
        }
        
        // 시퀀스 1 증가 업데이트
        seqNameColumnValue.addColumnValue("nextSeq", nextSeq + 1);
        fileAccessor.updateWithoutIndex(seqNameColumnValue);
        
        return nextSeq;
    }
}
