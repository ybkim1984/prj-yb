/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.mongospring.repository;

import namoo.board.dom2.da.mongospring.document.BoardTeamDoc;
import namoo.board.dom2.da.mongospring.repository.custom.BoardTeamCustomRepository;

import org.springframework.data.repository.CrudRepository;

public interface BoardTeamRepository extends CrudRepository<BoardTeamDoc, String>, BoardTeamCustomRepository {

	//
	BoardTeamDoc findByUsid(String usid);

	BoardTeamDoc findByName(String name);

	Long deleteByUsid(String usid);

	BoardTeamDoc findByUsidAndMembersUserEmail(String teamUsid, String memberEmail);
	
}
