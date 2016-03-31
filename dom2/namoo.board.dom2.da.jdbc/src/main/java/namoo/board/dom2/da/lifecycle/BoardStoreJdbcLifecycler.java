/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hjyeom@nextree.co.kr">Yeom, Hyojun</a>
 * @since 2015. 1. 27.
 */

package namoo.board.dom2.da.lifecycle;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

import javax.sql.DataSource;

import namoo.board.dom2.da.jdbc.BoardTeamJdbcStore;
import namoo.board.dom2.da.jdbc.BoardUserJdbcStore;
import namoo.board.dom2.da.jdbc.PostingJdbcStore;
import namoo.board.dom2.da.jdbc.SocialBoardJdbcStore;
import namoo.board.dom2.lifecycle.BoardStoreLifecycler;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

public class BoardStoreJdbcLifecycler implements BoardStoreLifecycler {
	//
    private static BoardStoreJdbcLifecycler lifecycler = new BoardStoreJdbcLifecycler();
    
    private DataSource dataSource;
    private BoardTeamJdbcStore boardTeamStore;
    private BoardUserJdbcStore boardUserStore;
    private PostingJdbcStore postingStore;
    private SocialBoardJdbcStore socialBoardStore;

    private BoardStoreJdbcLifecycler() {
        //
        Server server = null;
        try {
            server = Server.createWebServer("-webPort", "8082");
        } catch (SQLException e) {
            //
            e.printStackTrace();
        }
        try {
            server.start();
        } catch (SQLException e) {
            //
            e.printStackTrace();
        }
    }
    
    public static BoardStoreJdbcLifecycler getInstance(DataSource dataSource) {
        //
        lifecycler.dataSource = dataSource;
        return lifecycler;
    }
    
    public static BoardStoreJdbcLifecycler getInstance() {
        //
    	JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:namooboarddb;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        jdbcDataSource.setUser("sa");
        
        InputStream is = 
        		BoardStoreJdbcLifecycler.class.getResourceAsStream("/schema.ddl");
            Reader reader = new InputStreamReader(is);
            try {
				RunScript.execute(jdbcDataSource.getConnection(), reader);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        lifecycler.dataSource = jdbcDataSource;
        
        return lifecycler;
    }
    
    public BoardUserJdbcStore callBoardUserStore() {
        //
        if (boardUserStore == null) {
            boardUserStore = new BoardUserJdbcStore(dataSource);
        }
        return boardUserStore;
    }

    public BoardTeamJdbcStore callBoardTeamStore() {
        //
        if (boardTeamStore == null) {
            boardTeamStore = new BoardTeamJdbcStore(dataSource);
        }
        return boardTeamStore;
    }

    public SocialBoardJdbcStore callSocialBoardStore() {
        //
        if (socialBoardStore == null) {
            socialBoardStore = new SocialBoardJdbcStore(dataSource);
        }
        return socialBoardStore;
    }

    public PostingJdbcStore callPostingStore() {
        //
        if (postingStore == null) {
            postingStore = new PostingJdbcStore(dataSource);
        }
        return postingStore;
    }

}
