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
import namoo.board.dom2.entity.board.DCPosting;

public class PostingRepository {
    //
    private static PostingRepository instance = new PostingRepository();
    private Map<String, DCPosting> postingMap = new HashMap<String, DCPosting>();
    
    
    private PostingRepository() {}
    
    public static PostingRepository getInstance() {
        //
        return instance;
    }
    
    //--------------------------------------------------------------------------
    
    public void put(DCPosting posting) {
        //
        DCPosting copied = ObjectCopyUtil.copyObject(posting, DCPosting.class);
        this.postingMap.put(posting.getUsid(), copied);
    }
    
    public DCPosting get(String usid) {
        //
        DCPosting user = this.postingMap.get(usid);
        return ObjectCopyUtil.copyObject(user, DCPosting.class);
    }
    
    public List<DCPosting> getByBoard(String boardUsid) {
        //
        List<DCPosting> postings = new ArrayList<DCPosting>();
        
        for (DCPosting posting : this.postingMap.values()) {
            if (posting.getBoardUsid().equals(boardUsid)) {
                postings.add(ObjectCopyUtil.copyObject(posting, DCPosting.class));
            }
        }
        return ObjectCopyUtil.copyObjects(postings, DCPosting.class);
    }
    
    public List<DCPosting> getAll() {
        //
        return ObjectCopyUtil.copyObjects(this.postingMap.values(), DCPosting.class);
    }
    
    public void update(DCPosting posting) {
        //
        DCPosting saved = postingMap.get(posting.getUsid());
        if (saved == null) {
            return;
        }
        
        DCPosting copied = ObjectCopyUtil.copyObject(posting, DCPosting.class);
        postingMap.put(copied.getUsid(), copied);
    }
    
    public void remove(String usid) {
        //
        this.postingMap.remove(usid);
    }
    
    public void removeByBoard(String boardUsid) {
        //
        List<String> removeUsids = new ArrayList<String>();
        
        for (DCPosting posting : postingMap.values()) {
            //
            if (posting.getBoardUsid().equals(boardUsid)) {
                removeUsids.add(posting.getUsid());
            }
        }
        for (String usid : removeUsids) {
            postingMap.remove(usid);
        }
    }
}
