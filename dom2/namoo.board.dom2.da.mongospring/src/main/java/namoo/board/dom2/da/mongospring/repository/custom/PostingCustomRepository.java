package namoo.board.dom2.da.mongospring.repository.custom;

import namoo.board.dom2.entity.board.DCPosting;
import namoo.board.dom2.util.page.Page;
import namoo.board.dom2.util.page.PageCriteria;

public interface PostingCustomRepository {

    Page<DCPosting> retrievePage(String boardUsid, PageCriteria pageCriteria);

}
