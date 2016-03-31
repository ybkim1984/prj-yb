/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 5.
 */

package namoo.board.dom2.da.mybatis.sessionfactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

public class MyBatisSessionFactory {
    //
    private static SqlSessionFactory sqlSessionFactory;
    
    // -------------------------------------------------------------------------
    
    static {
        try {
            initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private MyBatisSessionFactory() {
    }
    
    // -------------------------------------------------------------------------
    
    public static void initDatabase() throws SQLException {
        //
        Connection conn = getSession().getConnection();
        
        InputStream is = MyBatisSessionFactory.class.getResourceAsStream("/schema.ddl");
        Reader reader = new InputStreamReader(is);
        
        RunScript.execute(conn, reader);
    
        // Web UI Client 에서 데이터 확인하기 위해 H2 웹서버 실행
        Server server = Server.createWebServer("-webPort", "8082");
        server.start();
    }
    
    public static SqlSessionFactory getSqlSessionFactory() {
        //
        try {
            String resource = "/namoo/board/dom2/da/mybatis/mybatis-config.xml";
            InputStream is = MyBatisSessionFactory.class.getResourceAsStream(resource);
            Reader reader = new InputStreamReader(is);
            
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }
    
    public static void setSqlSessionFactory(Reader resourceReader) {
        //
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
    }
    
    public static SqlSession getSession() {
        //
        if (sqlSessionFactory == null) {
            sqlSessionFactory = getSqlSessionFactory();
        }
        return sqlSessionFactory.openSession(true);
    }
    
}
