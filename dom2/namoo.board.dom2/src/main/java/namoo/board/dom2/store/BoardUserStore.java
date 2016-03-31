/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.store;

import java.util.List;

import namoo.board.dom2.entity.user.DCBoardUser;
import namoo.board.dom2.util.exception.EmptyResultException;

public interface BoardUserStore {
    //
    public void create(DCBoardUser user);
    public DCBoardUser retrieveByEmail(String email) throws EmptyResultException;
    public List<DCBoardUser> retrieveAll();
    public List<DCBoardUser> retrieveByName(String name); 
    public void update(DCBoardUser user); 
    public void deleteByEmail(String email); 
    
    public boolean isExistByEmail(String email);
}
