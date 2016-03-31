/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring.repository;

import namoo.board.dom2.da.mongospring.document.SocialBoardDoc;

import org.springframework.data.repository.CrudRepository;

public interface SocialBoardRepository extends CrudRepository<SocialBoardDoc, String> {
	//
	SocialBoardDoc findByName(String name);
	
	SocialBoardDoc findByUsid(String usid);
	
	Long deleteByUsid(String usid);
	
	Long countByUsid(String usid);

	Long countByName(String name);
}
