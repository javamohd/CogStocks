/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import cogentstocks.*;

/**
 *
 * @author IS Mohammed
 */
public class ItemObj {
    
public String itemName;
public String itemCode;
public int itemQty;
public double purPrice;
public double custPrice;
public int taxIncl;
public String itemBarcode;
public String itemImgref;

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemImgref() {
        return itemImgref;
    }

    public void setItemImgref(String itemImgref) {
        this.itemImgref = itemImgref;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getCustPrice() {
        return custPrice;
    }

    public void setCustPrice(double custPrice) {
        this.custPrice = custPrice;
    }

    public double getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(double purPrice) {
        this.purPrice = purPrice;
    }

    public int getTaxIncl() {
        return taxIncl;
    }

    public void setTaxIncl(int taxIncl) {
        this.taxIncl = taxIncl;
    }



}
