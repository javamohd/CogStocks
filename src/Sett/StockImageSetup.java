/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sett;

import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author IS Mohammed
 */
public class StockImageSetup {

    /**
     * @param args the command line arguments
     */
    
    public static void startP(){
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Stocks File .xlsx", "xls", ".xls");
        jf.setFileFilter(filter);
        int retVal = jf.showOpenDialog(null);
        File ff = new File("/");
        jf.setMultiSelectionEnabled(false);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            SystemParam.Stocks_file_path = jf.getSelectedFile().getAbsolutePath();
            boolean valid = validateFile(SystemParam.Stocks_file_path);
            if (valid) {
                JOptionPane.showMessageDialog(jf, "File Okay");
                new SetupImg().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(jf, "Invalid File");
                startP();
            }
        }
    } 
    
    public static void main(String[] args) throws Exception {
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Stocks File .xlsx", "xlsx", ".xlsx");
        jf.setFileFilter(filter);
        int retVal = jf.showOpenDialog(null);
        File ff = new File("/");
        jf.setMultiSelectionEnabled(false);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            SystemParam.Stocks_file_path = jf.getSelectedFile().getAbsolutePath();
            boolean valid = validateFile(SystemParam.Stocks_file_path);
            if (valid) {
                JOptionPane.showMessageDialog(jf, "File Okay");
                new SetupImg().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(jf, "Invalid File");
                startP();
            }
        }
    }

    public static boolean validateFile(String filePath) {
        boolean toReturn = true;
        try {

            if (!new File(filePath).getName().equalsIgnoreCase("Stocks.xlsx")) {
                toReturn = false;
            }

            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = null;
            workbook = new XSSFWorkbook(inputStream);
            if (workbook.getNumberOfSheets() != 3) {
                toReturn = false;
            }

        } catch (Exception d) {
            d.printStackTrace();
            toReturn = false;
        }
        return toReturn;
    }
}
