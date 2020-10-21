/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import cogentstocks.Dashboard_1;
import cogentstocks.SysParam;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author IS Mohammed
 */
public class CartBox {
    
    public static ArrayList<ItemObj> items = new ArrayList<ItemObj>();// add // scan
    public static HashMap<String,Integer> qtyMap = new HashMap<String,Integer>();
    public static HashMap<String,Integer> priceMap = new HashMap<String,Integer>();
    public static HashMap<String, Boolean> taxInclMap = new HashMap<String, Boolean>();
    public static DefaultTableModel model = (DefaultTableModel) Dashboard_1.jTable_billList.getModel();
    public static int cartTotal = 0;
    
    public static void updateTable(){
        
        int n = model.getRowCount();
        //Empty the Model    
        if (n > 0) {
                for (int i = 0; i <= n-1; i++) {
                    model.removeRow(0);
                }
            }
            
        
        for (ItemObj eachObj : items) {
            model.addRow(new Object[]{
                        sNoOrder(),//BillBoard Item Serial No
                        eachObj.itemName,// Bill Board Item Name
                        SysParam.CurrentBill.get(eachObj.itemName).getCustPrice(),//Bill Board Item Price
                        qtyMap.get(eachObj.itemName).toString(),//BillBoard Item Quantity
                        priceMap.get(eachObj.getItemName()).toString(),//BillBoard Item Price
                        Boolean.parseBoolean(taxInclMap.get(eachObj.getItemName()).toString())// BillBoard Tax Include map
                    });
            cartTotal += priceMap.get(eachObj.getItemName());
        }
        Dashboard_1.jLabel1_total.setText(cartTotal+"");
    }
    
    public static int sNoOrder(){
        int returnVal = 0;
        
        int totalcount = model.getRowCount();
        returnVal = totalcount + 1;
        int ii=1;
        if(totalcount < 1){
            return 1;
        }
        for(int k=0;k<totalcount;k++){
        model.setValueAt(ii, (k), 0);
        ii++;
        }
        return returnVal;
    }
    
    public static boolean addItem_popup(ItemObj obj,int Qty){
        boolean toReturn = true;
        
        if(items.contains(obj.itemName))return false;
        items.add(obj);
        qtyMap.put(obj.itemName, Qty);
        int price = Qty * Integer.parseInt(String.valueOf(obj.custPrice));
        priceMap.put(obj.itemName, price);
        taxInclMap.put(obj.itemName, true);
        
        updateTable();
        return toReturn;
    }
    
    public static boolean addItem_scan(ItemObj obj){
        boolean toReturn = true;
        
        if(items.contains(obj.itemName)){
            int existing_qty = Integer.parseInt(qtyMap.get(obj.itemName).toString());
            int newQty = existing_qty+1;
            qtyMap.put(obj.itemName, newQty);
            priceMap.put(obj.itemName, (newQty)*Integer.parseInt(obj.getCustPrice()+""));
        }else{
            items.add(obj);
            qtyMap.put(obj.itemName, 1);
            priceMap.put(obj.itemName, Integer.parseInt(String.valueOf(obj.custPrice)));
            taxInclMap.put(obj.itemName, true);
        }
        updateTable();
        return toReturn;
    }
    
    public static void removeItem(String itemName){
        int idx = 0;
        ItemObj itemtobeRemoved = null;
        for (ItemObj eachObj : items) {
            if (eachObj.getItemName().equalsIgnoreCase(itemName)) {
                idx = items.indexOf(eachObj);
                itemtobeRemoved = eachObj;
            }
        }
        items.remove(idx);
        //priceMap.remove(itemtobeRemoved);
        //taxInclMap.remove(itemtobeRemoved);
        updateTable();
    }
    
    public static void clearCart(){
        items.clear();
        updateTable();
    }
}
