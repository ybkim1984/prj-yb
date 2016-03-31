/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.mongospring.repository;

import java.util.List;

import namoo.board.dom2.da.mongospring.document.BoardUserDoc;

import org.springframework.data.repository.CrudRepository;

public interface BoardUserRepository extends CrudRepository<BoardUserDoc, String> {
	//
	BoardUserDoc findByEmail(String email); // Generate Query: { "email" : "jipark@nextree.co.kr"}

	List<BoardUserDoc> findByName(String name); // Generate Query: { "name" : "박종일"}

	Long deleteByEmail(String email); // Generate Query: { "email" : "jipark@nextree.co.kr"}

	Long countByEmail(String email); // Generate Query: { "email" : "jipark@nextree.co.kr"}
}
