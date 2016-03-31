package namoo.board.dom2.da.mongospring.repository.custom.impl;

import namoo.board.dom2.da.mongospring.document.BoardTeamDoc;
import namoo.board.dom2.da.mongospring.repository.custom.BoardTeamCustomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;

public class BoardTeamRepositoryImpl implements BoardTeamCustomRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void deleteByUsidAndMembersUserEmail(String teamUsid, String memberEmail) {
		//
		Query query = new Query(Criteria.where("usid").is(teamUsid));
		Update update = new Update().pull("members", new BasicDBObject("user.email", memberEmail));
		mongoTemplate.updateMulti(query, update, BoardTeamDoc.class);
	}

}
