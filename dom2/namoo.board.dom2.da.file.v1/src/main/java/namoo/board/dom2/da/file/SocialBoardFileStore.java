/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 6.
 */
package namoo.board.dom2.da.file;

import java.util.List;

import namoo.board.dom2.da.file.repo.SequenceRepository;
import namoo.board.dom2.da.file.repo.SocialBoardRepository;
import namoo.board.dom2.entity.board.DCSocialBoard;
import namoo.board.dom2.store.SocialBoardStore;
import namoo.board.dom2.util.exception.EmptyResultException;

public class SocialBoardFileStore implements SocialBoardStore {
    //
    private SequenceRepository seqStore = new SequenceRepository();
    private SocialBoardRepository boardStore = new SocialBoardRepository();
    
    //--------------------------------------------------------------------------
    
    @Override
    public String create(DCSocialBoard board) {
        // 
        boardStore.insert(board);
        return board.getUsid();
    }

    @Override
    public DCSocialBoard retrieve(String usid) throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.select(usid); 
        if (board == null) {
            throw new EmptyResultException("No such a board --> " + usid);
        }
        return board;
    }

    @Override
    public DCSocialBoard retrieveByName(String name) throws EmptyResultException {
        //
        DCSocialBoard board = boardStore.selectByName(name); 
        if (board == null) {
            throw new EmptyResultException("No such a board --> " + name);
        }
        return board;
    }

    @Override
    public List<DCSocialBoard> retrieveAll() {
        // 
        return boardStore.selectAll();
    }

    @Override
    public void update(DCSocialBoard board) {
        // 
        boardStore.update(board);
    }

    @Override
    public void delete(String usid) {
        // 
        boardStore.delete(usid);
    }

    @Override
    public int nextSequence() {
        // 
        return seqStore.selectBoardNextSeq();
    }

    @Override
    public boolean isExist(String usid) {
        // 
        DCSocialBoard board = boardStore.select(usid);
        return board != null;
    }

    @Override
    public boolean isExistByName(String name) {
        // 
        DCSocialBoard board = boardStore.selectByName(name);
        return board != null;
    }
    

}
