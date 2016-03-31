/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim Eunyoung</a>
 * @since 2015. 4. 16.
 */
package namoo.board.dom2.pr.ws.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import namoo.board.dom2.pr.ws.proxy.DcBoardUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class SpecialBoardUserServiceTest {
	
	@Autowired
	private SpecialBoardUserService specialBoardUserService;
	
	@Test
	public void testFindAllSpecialUsers() {
		List<DcBoardUser> findAllSpecialUsers = specialBoardUserService.findAllSpecialUsers();
		assertNotNull(findAllSpecialUsers);
	}

}
