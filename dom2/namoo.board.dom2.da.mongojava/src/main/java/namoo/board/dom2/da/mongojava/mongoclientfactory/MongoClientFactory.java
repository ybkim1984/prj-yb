/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:eschoi@nextree.co.kr">Choi, Eunsun</a>
 * @since 2015. 2. 16.
 */

package namoo.board.dom2.da.mongojava.mongoclientfactory;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoClientFactory {
	//
    private static MongoClientFactory instance = new MongoClientFactory();
    
    private String databaseName = "namoo_board";
    private MongoClient mongoClient;
    
    private MongoClientFactory() {
        //
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    //--------------------------------------------------------------------------
    
    public static DB getDB() {
        //
        return instance.mongoClient.getDB(instance.databaseName);
    }
    
    public static void overrideDatabase(String databaseName) {
        //
        instance.databaseName = databaseName;
    }
}
