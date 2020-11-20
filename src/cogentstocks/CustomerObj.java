/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.util.ArrayList;

/**
 *
 * @author IS Mohammed
 */
public class CustomerObj {
    
    public String CustomerName;
    
    public String CustomerPhone;
    
    public String CustEmail;
    
    public String CustAddr;
    
    public ArrayList<BillObj> CustBillHistory = new ArrayList<>();

    public CustomerObj() {
    }

    public String getCustAddr() {
        return CustAddr;
    }

    public void setCustAddr(String CustAddr) {
        this.CustAddr = CustAddr;
    }

    public String getCustEmail() {
        return CustEmail;
    }

    public void setCustEmail(String CustEmail) {
        this.CustEmail = CustEmail;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String CustomerPhone) {
        this.CustomerPhone = CustomerPhone;
    }

    public CustomerObj(ArrayList<BillObj> CustBillHistory) {
        this.CustBillHistory = CustBillHistory;
    }
    
    
}
