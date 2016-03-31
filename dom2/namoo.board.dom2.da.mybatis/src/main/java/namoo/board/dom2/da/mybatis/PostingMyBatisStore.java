/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */
package namoo.board.dom2.da.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namoo.board.dom2.da.mybatis.mapper.PostingMapper;
import namoo.board.dom2.da.mybatis.sessionfactory.MyBatisSessionFactory;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.entity.board.DCPostingComment;
import namoo.board.dom2.entity.board.DCPostingContents;
//import namoo.board.dom2.da.mybatis.annotationmapper.PostingMapper; //FIXME Annotation 방식 Mapper
import namoo.board.dom2.store.PostingStore;
import namoo.board.dom2.util.exception.EmptyResultException;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.apache.ibatis.session.SqlSession;

public class PostingMyBatisStore implements PostingStore {
    //
    // posting
    
    @Override
    public void create(DCPosting posting) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.insert(posting);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCPosting retrieve(String usid) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            DCPosting posting = mapper.select(usid);
            if (posting == null) {
                throw new EmptyResultException("No such a posting --> " + usid);
            }
            return posting;
        
        } finally {
            session.close();
        }
    }

    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            PageCriteria criteria = mapper.selectPageCriteria(boardUsid, pageCriteria);
            List<DCPosting> postings = mapper.selectPage(boardUsid, pageCriteria);
            
            return new Page<DCPosting>(postings, criteria);
        
        } finally {
            session.close();
        }
    }

    @Override
    public void update(DCPosting posting) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.update(posting);
        
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(String usid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.deleteCommentInPosting(usid);
            mapper.delete(usid);
            
        
        } finally {
            session.close();
        }
    }
    
    @Override
    public void deleteByBoard(String boardUsid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            List<String> postingUsids = mapper.selectUsidByBoard(boardUsid);
            
            if (postingUsids.size() > 0) {
                mapper.deleteCommentByUsids(postingUsids);
            }
            mapper.deleteByBoard(boardUsid);
        
        } finally {
            session.close();
        }
    }

    @Override
    public int nextSequence(String boardUsid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            Map<String, Object> resultParam = new HashMap<String, Object>();
            String seqName = boardUsid + "-posting_id";
            resultParam.put("seqName", seqName);
            
            Integer seq = mapper.selectSequence(seqName);
            
            if (seq == null) {
                mapper.insertSequence(seqName);
            }
            mapper.nextSequence(resultParam);
            
            return (int) resultParam.get("nextSeq");
        
        } finally {
            session.close();
        }
    }
    
    @Override
    public void increaseReadCount(String usid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.updateReadCount(usid);
        
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExist(String usid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            return mapper.select(usid) != null;
        
        } finally {
            session.close();
        }
    }
    
    
    //--------------------------------------------------------------------------
    // contents
    
    @Override
    public void createContents(DCPostingContents contents) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.updateContents(contents);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCPostingContents retrieveContents(String postingUsid) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            DCPostingContents contents = mapper.selectContents(postingUsid);
            if (contents == null) {
                throw new EmptyResultException("No such a posting contents --> " + postingUsid);
            }
            return contents;
            
        } finally {
            session.close();
        }
    }

    @Override
    public void updateContents(DCPostingContents contents) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.updateContents(contents);
        
        } finally {
            session.close();
        }
    }

    //--------------------------------------------------------------------------
    // comment
    
    @Override
    public void createComment(String postingUsid, DCPostingComment comment) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.insertComment(postingUsid, comment);
        
        } finally {
            session.close();
        }
    }

    @Override
    public DCPostingComment retrieveComment(String postingUsid, int sequence) throws EmptyResultException {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            DCPostingComment comment = mapper.selectComment(postingUsid, sequence);
            if (comment == null) {
                throw new EmptyResultException("No such a posting comment --> postingUsid: " + postingUsid + ", sequence: " + sequence);
            }
            return comment;
        
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteComment(String postingUsid, int sequence) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            mapper.deleteComment(postingUsid, sequence);
        
        } finally {
            session.close();
        }
    }

    @Override
    public int nextCommentSequence(String postingUsid) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            
            Map<String, Object> resultParam = new HashMap<String, Object>();
            String seqName = postingUsid + "-comment";
            resultParam.put("seqName", seqName);
            
            Integer seq = mapper.selectSequence(seqName);
            
            if (seq == null) {
                mapper.insertSequence(seqName);
            }
            mapper.nextSequence(resultParam);
            
            return (int) resultParam.get("nextSeq");
            
        } finally {
            session.close();
        }
    }

    @Override
    public boolean isExistComment(String postingUsid, int sequence) {
        // 
        SqlSession session = MyBatisSessionFactory.getSession();
        
        try {
            PostingMapper mapper = session.getMapper(PostingMapper.class);
            DCPostingComment comment = mapper.selectComment(postingUsid, sequence);
            
            return comment != null;
        
        } finally {
            session.close();
        }
    }
    
    

}
