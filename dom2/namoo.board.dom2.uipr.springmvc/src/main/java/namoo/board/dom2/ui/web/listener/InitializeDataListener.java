/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, Hyunoh</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.ui.web.listener;

import java.io.File;
import java.net.URL;

import namoo.board.dom2.cp.lifecycle.BoardServiceSpringLifecycler;
import namoo.board.dom2.cp.spring.ExcelFileBatchSpringLoaderLogic;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;
import namoo.board.dom2.lifecycle.BoardServiceLifecycler;
import namoo.board.dom2.service.ExcelFileBatchLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class InitializeDataListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BoardStoreLifecycler storeLifecycler;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//
		BoardServiceLifecycler serviceLifecycler = BoardServiceSpringLifecycler.getInstance(storeLifecycler);
		ExcelFileBatchLoader excelLoader = new ExcelFileBatchSpringLoaderLogic(serviceLifecycler);

		URL path = this.getClass().getResource("/BoardUsers.xlsx");

		excelLoader.registerServiceUsers(new File(path.getPath()));

	}

}
