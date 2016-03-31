/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */
package namoo.board.dom2.da.mongojava.shared;

import namoo.board.dom2.da.mongojava.mongoclientfactory.MongoClientFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder;

public abstract class BaseMongoTestCase {
    
    private static final String DATABASE_NAME = "namoo_board_test";
    
    @Rule
    public MongoDbRule mongoDbRule = 
        MongoDbRuleBuilder.newMongoDbRule().defaultManagedMongoDb(DATABASE_NAME);
    
    @BeforeClass
    public static void initDatabase() {
        MongoClientFactory.overrideDatabase(DATABASE_NAME);
    }
    
    @AfterClass
    public static void dropDatabase() {
        MongoClientFactory.getDB().dropDatabase();
    }
}
