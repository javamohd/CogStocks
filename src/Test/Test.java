/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import cogentstocks.ProgressFrame;
import cogentstocks.ProgressThread;

/**
 *
 * @author IS Mohammed
 */
public class Test {
    
    public static void main(String a[]){
        ProgressFrame p = new ProgressFrame();
        p.setVisible(true);
        Thread t = new Thread(new ProgressThread());
        t.start();
    }
    
}
