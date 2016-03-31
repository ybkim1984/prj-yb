/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 15.
 */
package namoo.board.dom2.da.mem.mapstore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mem.util.ObjectCopyUtil;
import namoo.board.dom2.entity.board.DCSocialBoard;

public class SocialBoardRepository {
    //
    private static SocialBoardRepository instance = new SocialBoardRepository();
    private Map<String, DCSocialBoard> boardMap = new HashMap<String, DCSocialBoard>();
    
    
    private SocialBoardRepository() {}
    
    public static SocialBoardRepository getInstance() {
        //
        return instance;
    }
    
    //--------------------------------------------------------------------------
    
    public void put(DCSocialBoard board) {
        //
        DCSocialBoard copied = ObjectCopyUtil.copyObject(board, DCSocialBoard.class);
        this.boardMap.put(board.getUsid(), copied);
    }
    
    public DCSocialBoard get(String usid) {
        //
        DCSocialBoard board = this.boardMap.get(usid);
        return ObjectCopyUtil.copyObject(board, DCSocialBoard.class);
    }
    
    public DCSocialBoard getByName(String name) {
        //
        for (DCSocialBoard board : this.boardMap.values()) {
            if (board.getName().equals(name)) {
                return ObjectCopyUtil.copyObject(board, DCSocialBoard.class);
            }
        }
        return null;
    }
    
    public List<DCSocialBoard> getAll() {
        //
        return ObjectCopyUtil.copyObjects(this.boardMap.values(), DCSocialBoard.class);
    }
    
    public void update(DCSocialBoard board) {
        //
        DCSocialBoard saved = boardMap.get(board.getUsid());
        if (saved == null) {
            return;
        }
        
        DCSocialBoard copied = ObjectCopyUtil.copyObject(board, DCSocialBoard.class);
        boardMap.put(copied.getUsid(), copied);
    }
    
    public void remove(String usid) {
        //
        this.boardMap.remove(usid);
    }
}
