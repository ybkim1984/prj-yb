/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2014. 6. 14.
 */

package namoo.board.dom2.da.mybatis.shared;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import namoo.board.dom2.da.mybatis.sessionfactory.MyBatisSessionFactory;

import org.apache.ibatis.io.Resources;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class BaseMyBatisStoreDbUnitTest {
    //
    private IDatabaseTester databaseTester;
    private static DataSource dataSource;
    
    // -------------------------------------------------------------------------
    
    @BeforeClass
    public static void initDatabase() throws SQLException {
        //
        Connection conn = getDataSource().getConnection();
        InputStream is = 
            BaseMyBatisStoreDbUnitTest.class.getResourceAsStream("/schema-test.ddl");
        Reader reader = new InputStreamReader(is);
        RunScript.execute(conn, reader);
    }
    
    @Before
    public void setUp() throws Exception {
        //
        if (databaseTester == null) {
            initDatabaseTester();
        }
        databaseTester.onSetup();
    }
    
    @After
    public void tearDown() throws Exception {
        //
        if (databaseTester == null) {
            initDatabaseTester();
        }
        databaseTester.onTearDown();
    }
    
    protected static DataSource getDataSource() {
        //
        if (dataSource == null) {
            try {
                String resource = "jdbc-test.properties";
                Properties properties = Resources.getResourceAsProperties(resource);
                
                JdbcDataSource jdbcDataSource = new JdbcDataSource();
                jdbcDataSource.setUrl(properties.getProperty("database.url"));
                jdbcDataSource.setUser(properties.getProperty("database.username"));
                
                dataSource = jdbcDataSource;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
    
    private void initDatabaseTester() throws DataSetException {
        //
        if (getDatasetXml() == null || "".equals(getDatasetXml())) {
            return;
        }
        
        databaseTester = new DataSourceDatabaseTester(getDataSource());
        
        InputStream is = this.getClass().getResourceAsStream(getDatasetXml());
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
        // Test용 myBatis config 설정
        try {
            String resource = "namoo/board/dom2/da/mybatis/mybatis-config-test.xml";
            Reader resourceReader = Resources.getResourceAsReader(resource);
            MyBatisSessionFactory.setSqlSessionFactory(resourceReader);
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * return DataSet XML file location in classpath
     * 
     * example. 
     * return "/dataset/social_boards.xml"
     * @return
     */
    protected abstract String getDatasetXml();

}
