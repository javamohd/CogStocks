/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

/**
 *
 * @author IS Mohammed
 */
public class ProgressThread implements Runnable{

    @Override
    public void run() {
        try{
            for(int i=0;i<=100;i++){
                ProgressFrame.jProgressBar1.setValue(i);
                ProgressFrame.jLabel2.setText(i+"%");
                Thread.sleep(50);
            }
            
            new Dashboard_1().setVisible(true);
            SysParam.pf.setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
}
