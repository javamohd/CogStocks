/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author IS Mohammed
 */
public class PopulateHistory extends Thread{

    public static String custName = "";
    
    @Override
    public void run() {
        try{
        //Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        DefaultTableModel modd = (DefaultTableModel)CustomerBill.jTable_custHist.getModel();
        
        CustomerObj retObj = DataStore.getCustDetsByName(custName);
        
        for(BillObj eachBill : retObj.CustBillHistory){
            Vector v =new Vector();
            String dateStr = new SimpleDateFormat("ddMMMyy").format(eachBill.billDateTime);
            v.add(eachBill.CustName+"("+dateStr+")");
            v.add(eachBill.billAmount);
            v.add(eachBill.billBalance);
            modd.addRow(v);
        }
    }

    
    
}
