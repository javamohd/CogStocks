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
            
            ProgressFrame.jProgressBar1.setValue(50);
            ProgressFrame.jLabel2.setText("50" + "%");
            DataStore.populateBarcodeMappings();
            /*for(int i=0;i<=100;i++){
                ProgressFrame.jProgressBar1.setValue(i);
                ProgressFrame.jLabel2.setText(i+"%");
                Thread.sleep(25);
            }*/
            
            SysParam.pf.setVisible(false);
            new Dashboard_1().setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
}
