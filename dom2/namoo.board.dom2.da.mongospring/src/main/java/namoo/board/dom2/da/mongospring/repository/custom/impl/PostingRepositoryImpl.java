package namoo.board.dom2.da.mongospring.repository.custom.impl;

import java.util.List;

import namoo.board.dom2.da.mongospring.document.PostingDoc;
import namoo.board.dom2.da.mongospring.repository.custom.PostingCustomRepository;
import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class PostingRepositoryImpl implements PostingCustomRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
    @Override
    public Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria) {
        //
    	int numToSkip = pageCriteria.getItemLimitOfPage() * (pageCriteria.getPage() - 1);
    	int limitSize = pageCriteria.getItemLimitOfPage();

    	Query query = new Query();
        query.addCriteria(Criteria.where("boardUsid").is(boardUsid));
        query.skip(numToSkip);
        query.limit(limitSize);
        
        List<PostingDoc> results = mongoTemplate.find(query, PostingDoc.class);
        
        Page<DCPosting> paging = new Page<DCPosting>(PostingDoc.createDomain(results), pageCriteria);
        
        return paging;
    }

}
