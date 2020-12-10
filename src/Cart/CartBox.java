/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import cogentstocks.Dashboard_1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
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
    public static int cartDiscount = 0;
    
    public static void updateTable(){
        
        int n = model.getRowCount();
        //Empty the Model    
        if (n > 0) {
                for (int i = 0; i <= n-1; i++) {
                    model.removeRow(0);
                }
            }
            
        cartTotal = 0;
        
        if (!items.isEmpty()) {
            for (ItemObj eachObj : items) {
                model.addRow(new Object[]{
                            sNoOrder(),//BillBoard Item Serial No
                            eachObj.itemName,// Bill Board Item Name
                            eachObj.getCustPrice(),//Bill Board Item cust Price
                            qtyMap.get(eachObj.itemName).toString(),//BillBoard Item Quantity
                            priceMap.get(eachObj.getItemName()).toString(),//BillBoard Item Price
                            Boolean.parseBoolean(taxInclMap.get(eachObj.getItemName()).toString())// BillBoard Tax Include map
                        });
                cartTotal += priceMap.get(eachObj.getItemName());
            }
        }
        Dashboard_1.jLabel1_total.setText(cartTotal+"");
        
        Dashboard_1.jTable_billList.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        Dashboard_1.jTable_billList.getColumn("MRP").setCellRenderer(centerRenderer);
        Dashboard_1.jTable_billList.getColumn("S.No. ").setCellRenderer(centerRenderer);
        Dashboard_1.jTable_billList.getColumn("Quantity").setCellRenderer(centerRenderer);
        Dashboard_1.jTable_billList.getColumn("Price").setCellRenderer(centerRenderer);
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
        
        for(ItemObj eachObj : items){
            if(eachObj.getItemName().equalsIgnoreCase(obj.itemName))
            return false;
        }
        items.add(obj);
        qtyMap.put(obj.itemName, Qty);
        int price = Qty * (int)Double.parseDouble(String.valueOf(obj.custPrice));
        priceMap.put(obj.itemName, price);
        taxInclMap.put(obj.itemName, true);
        
        updateTable();
        return toReturn;
    }
    
    public static boolean addItem_scan(ItemObj obj){
        
        boolean toReturn = true;
        boolean exist = false;
        for(ItemObj eachObj : items){
            if(eachObj.itemName.equals(obj.itemName))exist = true;
        }
        
        if(exist){
            int existing_qty = Integer.parseInt(qtyMap.get(obj.itemName).toString());
            int newQty = existing_qty+1;
            qtyMap.put(obj.itemName, newQty);
            priceMap.put(obj.itemName, (newQty)*(int)obj.getCustPrice());
        }else{
            items.add(obj);
            qtyMap.put(obj.itemName, 1);
            priceMap.put(obj.itemName, (int)obj.custPrice);
            taxInclMap.put(obj.itemName, true);
        }
        updateTable();
        return toReturn;
    }
    
    public static void addOldPendings(double amount){
        ItemObj obj = new ItemObj();
        
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
        
        obj.setItemName("Bill Clearance "+sdf.format(new Date()));
        obj.setCustPrice(amount);
        obj.setItemQty(01);
        
        qtyMap.put(obj.itemName, 1);
        priceMap.put(obj.itemName, (int)obj.custPrice);
        cartTotal = getCartTotal();
        items.add(obj);
    }
    
    public static int getCartTotal(){
        int toReturn = 0;
        
        for(Map.Entry<String,Integer> each : priceMap.entrySet()){
            toReturn += each.getValue();
        }
        
        return toReturn-cartDiscount;
    }
    
    public static void removeItem(String itemName){
        int idx = 0;
        for (ItemObj eachObj : items) {
            if (eachObj.getItemName().equalsIgnoreCase(itemName)) {
                idx = items.indexOf(eachObj);
            }
        }
        items.remove(idx);
        //priceMap.remove(itemtobeRemoved);
        //taxInclMap.remove(itemtobeRemoved);
        updateTable();
    }
    
    public static void clearCart(){
        items.clear();
        priceMap.clear();
        qtyMap.clear();
        taxInclMap.clear();
        Dashboard_1.jLabel1_total.setText(CartBox.getCartTotal()+"");
        CartBox.cartDiscount = 0;
        updateTable();
    }
    
    public static ItemObj getItemByName(String itemName){
        ItemObj toreturn = new ItemObj();
        
        for(ItemObj eachObj : CartBox.items){
            if(eachObj.getItemName().equalsIgnoreCase(itemName))toreturn = eachObj;
        }
        
        return toreturn;
    }
}
