/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:elviskim@nextree.co.kr">Kim, JunYoung</a>
 * @since 2015. 1. 19.
 */

package namoo.board.dom2.sp.ws;

import namoo.board.dom2.sp.ws.server.Server;

public class ServerTest {
    //
    public static void main(String arg[]) throws Exception {
        new Server();
        System.out.println("Server ready...");
        
        Thread.sleep(2 * 60 * 1000);
        
        System.out.println("Server exiting");
        System.exit(0);
    }
}
