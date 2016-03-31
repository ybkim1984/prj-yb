/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hjyeom@nextree.co.kr">Yeom, Hyojun</a>
 * @since 2015. 1. 27.
 */

package namoo.board.dom2.da.jdbc.shared;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

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

public abstract class BaseJdbcStoreDbUnitTest {
    //
    private IDatabaseTester databaseTester;
    private static DataSource dataSource;
    
    @BeforeClass
    public static void initDatabase() throws SQLException {
        //
        Connection conn = getDataSource().getConnection();
        InputStream is = 
            BaseJdbcStoreDbUnitTest.class.getResourceAsStream("/schema-test.ddl");
        Reader reader = new InputStreamReader(is);
        RunScript.execute(conn, reader);
    }
    
    protected static DataSource getDataSource() {
        //
        if (dataSource == null) {
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL");
            jdbcDataSource.setUser("sa");
            
            dataSource = jdbcDataSource;
        }
        
        return dataSource;
    }
    
    private void initDatabaseTester() throws DataSetException {
        //
        if (getDatasetXml() == null || "".equals(getDatasetXml())) 
            return;
        
        databaseTester = new DataSourceDatabaseTester(getDataSource());
        
        InputStream is = this.getClass().getResourceAsStream(getDatasetXml());
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
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
    
    /**
     * return DataSet XML file location in classpath
     * 
     * example. 
     * return "/dataset/users.xml"
     * @return
     */
    protected abstract String getDatasetXml();
}
