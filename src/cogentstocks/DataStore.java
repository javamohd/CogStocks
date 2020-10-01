/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
            SysParam.itemCount = firstSheet.getLastRowNum()-1;
            Iterator<Row> iterator = firstSheet.iterator();
            boolean skiprow = true;
            while (iterator.hasNext()) {
                Row currRow = iterator.next();
                if(skiprow){
                 skiprow = false;
                 continue;
             }
                ItemObj obj = new ItemObj();
                
                String barCodeStr = "";
                String itemName = "";
                double itemPrice = 0.0;
                
                if(currRow.getCell(5) != null)
                barCodeStr = currRow.getCell(5).getStringCellValue();
                
                itemName = currRow.getCell(1).getStringCellValue();
                itemPrice = currRow.getCell(2).getNumericCellValue();
                
                obj.setItemBarcode(barCodeStr);
                obj.setItemName(itemName);
                obj.setItemPrice(itemPrice);
                
                SysParam.barCodeMappings.put(barCodeStr, obj);
            }
            //System.out.println(SysParam.barCodeMappings.size()+" --");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
