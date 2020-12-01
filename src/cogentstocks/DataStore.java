/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import Cart.ItemObj;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author IS Mohammed
 */
public class DataStore {
    
    public static String File_Name = "Stocks.xlsx";
    
    
    public static void populateBarcodeMappings(){
        try{
            
            //SysParam.barCodeMappings.put("barCodeString", ItemObj);
            
            FileInputStream inputStream = new FileInputStream(new File(File_Name));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            //SysParam.itemCount = firstSheet.getLastRowNum()-1;
            Iterator<Row> iterator = firstSheet.iterator();
            boolean skiprow = true;
            int max = firstSheet.getLastRowNum();
            int jobstatus = 1;
            while (iterator.hasNext()) {
                Row currRow = iterator.next();
                if(skiprow){
                 skiprow = false;
                 continue;
             }
                float progVal = jobstatus/max;
                //ProgressFrame.jProgressBar1.setValue(progVal);
            //ProgressFrame.jLabel2.setText(progVal + "%");
            //Thread.sleep(1000);
            jobstatus++;
            //Thread.sleep(500);
            
            //System.out.println(progVal +" --");
            
                ProgressThread.setProgStatus((int)progVal);
                
                ItemObj obj = new ItemObj();
                
                String barCodeStr = "";
                String itemName = "";
                double itemPrice = 0.0;
                int itemQty = 0;
                
                if(currRow.getCell(5) != null)
                barCodeStr = currRow.getCell(5).getStringCellValue();
                
                itemName = currRow.getCell(1).getStringCellValue();
                itemPrice = currRow.getCell(2).getNumericCellValue();
                itemQty = (int)currRow.getCell(3).getNumericCellValue();
                
                obj.setItemBarcode(barCodeStr);
                obj.setItemName(itemName);
                obj.setCustPrice(itemPrice);
                obj.setItemQty(itemQty);
                
                SysParam.barCodeMappings.put(barCodeStr, obj);
                SysParam.quantityMappings.put(obj.itemName, obj.itemQty);
            }
            //System.out.println(SysParam.barCodeMappings.size()+" --");
            
            inputStream.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void clearPendings(String user){
        try{
            
            FileInputStream inputStream = new FileInputStream(new File(File_Name));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet billsSheet = workbook.getSheetAt(1);
            int lastRow = billsSheet.getLastRowNum();
            int firstRow = billsSheet.getFirstRowNum();
            
            int idx = firstRow;
            while(idx<=lastRow){
                Row currRow = billsSheet.getRow(idx);
                
                String rowUser = currRow.getCell(1).getStringCellValue();
                if(rowUser.equalsIgnoreCase(user)){
                    Cell c = currRow.getCell(4);
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    c.setCellValue("0");
                }
                idx++;
            }
            
            OutputStream os = new FileOutputStream(new File(File_Name));
            workbook.write(os);
            inputStream.close();
            os.close();
            
        }catch(Exception e){
        e.printStackTrace();
    }
    }
    
    public static void createNewStock(ItemObj obj){
        try{
            
            FileInputStream inputStream = new FileInputStream(new File(File_Name));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet stockSheet = workbook.getSheetAt(0);
            Row currRow = stockSheet.createRow(stockSheet.getLastRowNum()+1);
            
            currRow.createCell(0).setCellValue(stockSheet.getLastRowNum());
            currRow.createCell(1).setCellValue(obj.getItemName());
            currRow.createCell(2).setCellValue(obj.getCustPrice());
            currRow.createCell(3).setCellValue(obj.getItemQty());
            currRow.createCell(5).setCellValue(obj.getItemBarcode());
            currRow.createCell(6).setCellValue(obj.getTaxIncl());
            
            if (!obj.getItemImgref().isEmpty()) {
                
                XSSFDrawing drawing = (XSSFDrawing) stockSheet.createDrawingPatriarch();
                InputStream my_banner_image = new FileInputStream(obj.getItemImgref().toString());
                byte[] bytes = IOUtils.toByteArray(my_banner_image);
                int my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                currRow.setHeight((short) 1400);
                XSSFClientAnchor my_anchor = new XSSFClientAnchor();
                my_anchor.setCol1(4);
                my_anchor.setRow1((currRow.getRowNum() + 1) - 1);
                my_anchor.setCol2(5);
                my_anchor.setRow2(currRow.getRowNum() + 1);
                /*
                 * Invoke createPicture and pass the anchor point and ID
                 */
                XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
                my_picture.resize(1, .95);//Picture Size Specification
            }
            
            OutputStream os = new FileOutputStream(new File(File_Name));
            workbook.write(os);
            inputStream.close();
            os.close();
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "New Stock Error !!");
        }
    }
    
    public static void updateStock(ItemObj obj) {
        try {

            FileInputStream inputStream = new FileInputStream(new File(File_Name));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet stockSheet = workbook.getSheetAt(0);
            Row currRow = null;
            int TotalRows = stockSheet.getLastRowNum();
            //find Specific Row to update
            for (int i = 1; i <= TotalRows; i++) {
                Row row = stockSheet.getRow(i);
                if (row.getCell(1).getStringCellValue().equalsIgnoreCase(obj.getItemName())) {
                    currRow = row;
                    break;
                }
            }

            if (obj.getCustPrice() > 0) {
                currRow.createCell(2).setCellValue(obj.getCustPrice());
            }
            if (obj.getItemQty() > 0) {
                currRow.createCell(3).setCellValue(obj.getItemQty());
            }
            if (obj.getTaxIncl() > 0) {
                currRow.createCell(6).setCellValue(obj.getTaxIncl());
            }

            OutputStream os = new FileOutputStream(new File(File_Name));
            workbook.write(os);
            inputStream.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "New Stock Error !!");
        }
    }
    public static CustomerObj getCustDetsByName(String custName){
        CustomerObj toReturn = new CustomerObj();
        
        String excelFilePath = "Stocks.xlsx";
        FileInputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(BillEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        Sheet billSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = billSheet.iterator();
        toReturn.setCustomerName(custName);
        while (iterator.hasNext()) {
            Row currRow = iterator.next();
            String billName = currRow.getCell(1).getStringCellValue();
            if (billName.toLowerCase().equals(custName.toLowerCase())) {
                BillObj obj = new BillObj();
                obj.CustName = billName;
                obj.billAmount = Double.parseDouble(currRow.getCell(3).getStringCellValue());
                obj.billBalance = Double.parseDouble(currRow.getCell(4).getStringCellValue());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                try{
                obj.billDateTime = sdf.parse(currRow.getCell(0).getStringCellValue().split(" ")[0]);
                }catch(Exception e){
                    obj.billDateTime = new Date();
                }
                toReturn.CustBillHistory.add(obj);
            } else {
                continue;
            }
        }
        
        return toReturn;
    }
}
