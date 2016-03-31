/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.
 *
 * @author <a href="mailto:tsong@nextree.co.kr">Park, Jongil</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.mongospring.repository;

import namoo.board.dom2.da.mongospring.document.BoardSeqDoc;

import org.springframework.data.repository.CrudRepository;

public interface BoardSeqRepository extends CrudRepository<BoardSeqDoc, String> {

	//
	BoardSeqDoc findByName(String name);

}
