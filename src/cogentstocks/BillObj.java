/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.util.Date;

/**
 *
 * @author IS Mohammed
 */
public class BillObj {

    public String CustName;
    
    public Date billDateTime;
    
    public double billAmount;
    
    public double billBalance;
    
    public String billNumber;

    public BillObj() {
    }

    public BillObj(String CustName, Date billDateTime, double billAmount, double billBalance, String billNumber) {
        this.CustName = CustName;
        this.billDateTime = billDateTime;
        this.billAmount = billAmount;
        this.billBalance = billBalance;
        this.billNumber = billNumber;
    }
    
    
}
