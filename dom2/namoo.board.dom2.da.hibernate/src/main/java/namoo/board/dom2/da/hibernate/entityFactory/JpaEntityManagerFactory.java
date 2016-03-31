/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:demonpark@nextree.co.kr">SeokJae Park</a>
 * @since 2015. 1. 29.
 */
package namoo.board.dom2.da.hibernate.entityFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManagerFactory {
    //    
    private static EntityManagerFactory entityManagerFactory;
    
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("namoo.board.dom2");
        } catch (Throwable t) {
            new ExceptionInInitializerError(t);
        }
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        //
        return entityManagerFactory;
    }
    
    public static void shutdown() {
        //
        getEntityManagerFactory().close();
    }
}
