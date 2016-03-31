/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:kssong@nextree.co.kr">Song, KyungSun</a>
 * @since 2015. 3. 12.
 */

package namoo.board.dom2.da.mongospring.repository;

import namoo.board.dom2.da.mongospring.document.PostingDoc;

import org.springframework.data.repository.CrudRepository;

public interface PostingRepository extends CrudRepository<PostingDoc, String> {
	//
	PostingDoc findByUsid(String usid);
	
	PostingDoc findByBoardUsid(String findByBoardUsid);
	
	Long deleteByUsid(String usid);

	Long deleteByBoardUsid(String boardUsid);
	
	Long countByUsid(String usid);
}