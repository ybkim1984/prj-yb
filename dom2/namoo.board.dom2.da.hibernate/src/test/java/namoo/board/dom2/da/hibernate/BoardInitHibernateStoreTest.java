/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 2. 2.
 */
package namoo.board.dom2.da.hibernate;

import java.io.InputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class BoardInitHibernateStoreTest {
    //
    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    protected static IDatabaseConnection connection;
    protected IDataSet dataset;
    
    @BeforeClass
    public static void initDatabase() throws DatabaseUnitException, SQLException {
        //
        //
        entityManagerFactory = Persistence.createEntityManagerFactory("namoo.board.dom2");
        entityManager = entityManagerFactory.createEntityManager();
        
        entityManager.getTransaction().begin();
        
        connection = new DatabaseConnection(((SessionImpl)(entityManager.getDelegate())).connection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
     }
    
    @AfterClass
    public static void closeEntityManager(){
        entityManager.clear();
        entityManagerFactory.close();
    }
    
    @Before
    public void setUp() throws Exception {
        if(dataset == null) {
            initDatabaseLoad();
        }
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
        entityManager.getTransaction().commit();
    }
    
    @After
    public void tearDown() throws Exception {
        //
//        if (dataset == null) {
//            initDatabaseTester();
//        }
    //    DatabaseOperation.DELETE_ALL.execute(connection, dataset);
    }
    
    protected void initDatabaseLoad() throws DataSetException {
        if (getDatasetXml() == null || "".equals(getDatasetXml())) {
            return;
        }
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);
        
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(getDatasetXml());
        dataset = flatXmlDataSetBuilder.build(is);
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
