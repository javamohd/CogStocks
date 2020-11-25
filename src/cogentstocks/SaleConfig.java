/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SERVER
 */
public class SaleConfig {

    public static String shopName = new String();;
    public static String guestcust = new String();
    public static  String printerName = new String();;
    public static String passCode = new String();
    public static String filePrefix = new String();
    public static String initPass = new String();
    public static int printedSales = 0;
    public static boolean setup;
    public static Properties prop = new Properties();
    public static int gallaCash = 0;
    public static int lastGalla = 0;
    public static String bill_No = "";
    public static String expire = "";

    public static String getBill_No() {
        String billno = shopName.substring(0, 2) +new SimpleDateFormat("ddMM").format(new Date())+printedSales;
        return billno;
    }

    public static void setBill_No(String bill_No) {
        SaleConfig.bill_No = bill_No;
    }
    

    public static void load(){
        try{
            
            if(new File("Settings.jxt").exists()){
                
            }else{
                JOptionPane.showMessageDialog(null, "Please Contact Cogent !!!");
                setup = false;
                return;
            }
            prop.load(new FileInputStream("Settings.jxt"));
            shopName = prop.getProperty("shopName");
            expire = prop.getProperty("expire");
            guestcust = prop.getProperty("gurstcust");
            //if(setup == null)setup = "false";
            setup = ( prop.getProperty("setup").equals("true") ) ? true : false;
            printerName = prop.getProperty("printerName");
            passCode = prop.getProperty("passCode");
            filePrefix = prop.getProperty("filePrefix");
            printedSales = Integer.parseInt(prop.getProperty("printedSales").toString());
try{
            gallaCash = Integer.parseInt(prop.getProperty("gallaCash").toString().split("/")[0]);
            lastGalla = Integer.parseInt(prop.getProperty("lastGalla").toString());
            
            String dateGalla = prop.getProperty("gallaCash").toString().split("/")[1];
            
            if(new SimpleDateFormat("ddMM").format(new Date()).equalsIgnoreCase(dateGalla)){
                
            }else{
                lastGalla = Integer.parseInt(prop.getProperty("gallaCash").toString().split("/")[0]);
                gallaCash = 0;
            }
            
}catch(Exception e){
    System.out.println("No galla Cash --");
    gallaCash = 0;
    lastGalla = 0;
    e.printStackTrace();
}
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void Store(){
        try{
            prop.setProperty("shopName", shopName);
            prop.setProperty("passCode", passCode);
            prop.setProperty("printerName", printerName);
            prop.setProperty("printedSales", printedSales+"");
            prop.setProperty("filePrefix", filePrefix);
            prop.setProperty("guestcust", guestcust);
            /*int temp = 0;
            try{
                temp = CartBox.cartTotal;
            }catch(Exception e){
                
            }
            gallaCash += temp;*/
            prop.setProperty("gallaCash", gallaCash+"/"+new SimpleDateFormat("ddMM").format(new Date()));
            prop.setProperty("lastGalla", lastGalla+"");
            prop.store(new FileOutputStream("Settings.jxt"), null);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public boolean isSetup() {
        return setup;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public String getInitPass() {
        return initPass;
    }

    public void setInitPass(String initPass) {
        this.initPass = initPass;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    public static void updateQty(HashMap purItems){
        try{
            
            String excelFilePath = "Stocks.xlsx";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BillEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(BillEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        //Iterate row
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            
            if(currentRow.getRowNum() == 0){
                continue;
            }
            
            //System.out.println(currentRow.getCell(1).getStringCellValue()+" --value"+currentRow.getCell(3).getNumericCellValue());
            String itemName = currentRow.getCell(1).getStringCellValue();
            double qty = currentRow.getCell(3).getNumericCellValue();
            if(purItems.containsKey(itemName)){
                currentRow.getCell(3).setCellValue(qty-Integer.parseInt(purItems.get(itemName).toString()));
                System.out.println("Updating qty "+itemName+" value in Excel");
            }
        }
            OutputStream os = new FileOutputStream(new File("Stocks.xlsx"));
            workbook.write(os);
            inputStream.close();
            os.close();
        
            



        //Endddddddddddddd.............
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main (String a[]){
        HashMap item = new HashMap();
        item.put("123456", 265);
        SaleConfig.updateQty(item);
    }
    
}
