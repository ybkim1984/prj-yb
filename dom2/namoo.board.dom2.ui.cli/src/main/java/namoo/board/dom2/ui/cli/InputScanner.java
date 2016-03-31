/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 1. 9.
 */
package namoo.board.dom2.ui.cli;

import java.util.Scanner;

public class InputScanner {
    //
    private static InputScanner instance = new InputScanner();
    
    private Scanner scanner = new Scanner(System.in);
    
    //--------------------------------------------------------------------------
    
    private InputScanner() {}
    
    public static InputScanner getInstance() {
        //
        return instance;
    }
    
    
    public String getStringInput(String message) {
        // 
        System.out.print(message + ": ");
        String inputText = scanner.nextLine();
        
        return inputText;
    }
    
    public boolean getBooleanInput(String message) {
        //
        System.out.print(message + "(y/n): ");
        String inputText = scanner.nextLine();
        
        if ("y".equalsIgnoreCase(inputText)) {
            return true;
        } else if ("n".equalsIgnoreCase(inputText)) {
            return false;
        }
        System.out.println("잘못 입력했습니다. y 또는 n으로 입력하세요.");
        return getBooleanInput(message);
    }
    
    public int getIntInput(String message) {
        //
        System.out.print(message + ": ");
        String inputText = scanner.nextLine();
        
        try {
            return Integer.valueOf(inputText);
            
        } catch (NumberFormatException e) {
            System.out.println("값을 잘못 입력했습니다. 숫자만 입력하세요.");
            return getIntInput(message);
        }
    }
    
    public void waitInputEnter(String message) {
        //
        System.out.print(message + " Press enter.");
        scanner.nextLine();
    }
}
