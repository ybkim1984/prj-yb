/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 15.
 */
package namoo.board.dom2.da.mem.mapstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mem.util.ObjectCopyUtil;
import namoo.board.dom2.entity.user.DCBoardUser;

public class BoardUserRepository {
    //
    private static BoardUserRepository instance = new BoardUserRepository();
    private Map<String, DCBoardUser> userMap = new HashMap<String, DCBoardUser>();
    
    
    private BoardUserRepository() {}
    
    public static BoardUserRepository getInstance() {
        //
        return instance;
    }
    
    //--------------------------------------------------------------------------
    
    public void put(DCBoardUser user) {
        //
        DCBoardUser copied = ObjectCopyUtil.copyObject(user, DCBoardUser.class);
        this.userMap.put(user.getEmail(), copied);
    }
    
    public DCBoardUser get(String email) {
        //
        DCBoardUser user = this.userMap.get(email);
        return ObjectCopyUtil.copyObject(user, DCBoardUser.class);
    }
    
    public List<DCBoardUser> getByName(String name) {
        //
        List<DCBoardUser> users = new ArrayList<DCBoardUser>();
        
        for (DCBoardUser user : this.userMap.values()) {
            if (user.getName().equals(name)) {
                users.add(user);
            }
        }
        
        return ObjectCopyUtil.copyObjects(users, DCBoardUser.class);
    }
    
    public List<DCBoardUser> getAll() {
        //
        return ObjectCopyUtil.copyObjects(this.userMap.values(), DCBoardUser.class);
    }
    
    public void remove(String email) {
        //
        this.userMap.remove(email);
    }
}
