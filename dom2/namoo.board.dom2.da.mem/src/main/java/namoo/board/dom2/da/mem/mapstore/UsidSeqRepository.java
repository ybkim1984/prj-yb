/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 15.
 */
package namoo.board.dom2.da.mem.mapstore;

import java.util.HashMap;
import java.util.Map;

public class UsidSeqRepository {
    //
    private static final String SEQ_NAME_TEAM = "team";
    private static final String SEQ_NAME_BOARD = "board";
    private static final String SEQ_NAME_SUFFIX_POSTING = "-posting";
    private static final String SEQ_NAME_SUFFIX_COMMENT = "-comment";
    
    private static UsidSeqRepository instance = new UsidSeqRepository();
    private Map<String, Integer> usidSeqMap = new HashMap<String, Integer>();
    
    
    private UsidSeqRepository() {
        //
        usidSeqMap.put("team", 1);
        usidSeqMap.put("board", 1);
    }
    
    public static UsidSeqRepository getInstance() {
        //
        return instance;
    }
    
    //--------------------------------------------------------------------------
    
    public int nextTeamSequence() {
        //
        int seq = usidSeqMap.get(SEQ_NAME_TEAM);
        return usidSeqMap.put(SEQ_NAME_TEAM, seq + 1);
    }
    
    public int nextBoardSequence() {
        //
        int seq = usidSeqMap.get(SEQ_NAME_BOARD); 
        return usidSeqMap.put(SEQ_NAME_BOARD, seq + 1);
    }
    
    public int nextPostingSequence(String boardUsid) {
        //
        String mapKey = boardUsid + SEQ_NAME_SUFFIX_POSTING;
        Integer seq = 1;
        Integer mapSeq = usidSeqMap.get(mapKey); 
        
        if (mapSeq != null) {
            seq = mapSeq;
        }
        usidSeqMap.put(mapKey, seq + 1);
        return seq;
    }
    
    public int nextCommentSequence(String postingUsid) {
        //
        String mapKey = postingUsid + SEQ_NAME_SUFFIX_COMMENT;
        Integer seq = 1;
        Integer mapSeq = usidSeqMap.get(mapKey); 
        
        if (mapSeq != null) {
            seq = mapSeq;
        }
        usidSeqMap.put(mapKey, seq + 1);
        return seq;
    }

}
