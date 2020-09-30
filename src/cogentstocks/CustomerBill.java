/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author IS Mohammed
 */
public class CustomerBill extends javax.swing.JFrame {

    /**
     * Creates new form CustomerBill
     */
    
    public static boolean full_pay = true;
    public static String pass_amt = "";
    public HashMap index = new HashMap();
    public HashMap phoneMap = new HashMap();
    
    public void loadCustomers() {
        try {
            String excelFilePath = "Stocks.xlsx";
            FileInputStream inputStream = null;
            inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = null;
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(2);
            int n = sheet.getLastRowNum();
            DefaultListModel demoList = new DefaultListModel();
            index.clear();
            phoneMap.clear();
            for (int i = 0; i <= n; i++) {
                if(i==0)continue;
                String nam = sheet.getRow(i).getCell(0).getStringCellValue();
                demoList.addElement(nam);
                index.put(nam, i+1);
                phoneMap.put(nam, sheet.getRow(i).getCell(1).getStringCellValue());
            }
            jList1.setModel(demoList);
        } catch (Exception ex) {

        }
    }
    
    public CustomerBill() {
        initComponents();
        loadCustomers();
        //bill_Amt = Integer.parseInt(Dashboard.jLabel1_total.getText().split(".")[0]);
        Dashboard_1.totalPrice = Double.parseDouble(Dashboard_1.jLabel1_total.getText());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        ButtonGroup grp = new ButtonGroup();
        grp.add(jToggleButton_fullPay);
        grp.add(jToggleButton_HalfPay);
        jLabel_BillAmt.setText(pass_amt+"");
        jToggleButton_fullPay.setSelected(true);
        jLabel_BalPreview.setText("0.0");
        jTextField_Paid.setText(pass_amt+"");
        jTextField_Paid.setEnabled(false);
        
        this.getContentPane().setBackground(Color.getHSBColor(60, 150, 50));
        this.dispose();
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jToggleButton_fullPay = new javax.swing.JToggleButton();
        jToggleButton_HalfPay = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel_BillAmt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_AmtPaid = new javax.swing.JLabel();
        jTextField_Paid = new javax.swing.JTextField();
        jLabel_Balance = new javax.swing.JLabel();
        jLabel_BalPreview = new javax.swing.JLabel();
        jText_csname = new javax.swing.JTextField();
        jText_csmobile = new javax.swing.JTextField();
        jButton_done = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel_Balance1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jList1.setBackground(new java.awt.Color(102, 102, 102));
        jList1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jList1.setForeground(new java.awt.Color(204, 204, 204));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jToggleButton_fullPay.setBackground(new java.awt.Color(102, 102, 102));
        jToggleButton_fullPay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButton_fullPay.setForeground(new java.awt.Color(204, 204, 204));
        jToggleButton_fullPay.setText("Full Pay");
        jToggleButton_fullPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_fullPayActionPerformed(evt);
            }
        });

        jToggleButton_HalfPay.setBackground(new java.awt.Color(102, 102, 102));
        jToggleButton_HalfPay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButton_HalfPay.setForeground(new java.awt.Color(204, 204, 204));
        jToggleButton_HalfPay.setText("Half Pay");
        jToggleButton_HalfPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_HalfPayActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Teko SemiBold", 0, 14)); // NOI18N
        jLabel1.setText("Customers");

        jLabel_BillAmt.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        jLabel_BillAmt.setForeground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Bill Amount :");

        jLabel_AmtPaid.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel_AmtPaid.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_AmtPaid.setText("Amount PAID:");

        jTextField_Paid.setBackground(new java.awt.Color(204, 255, 255));
        jTextField_Paid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField_Paid.setForeground(new java.awt.Color(102, 102, 102));
        jTextField_Paid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_PaidKeyReleased(evt);
            }
        });

        jLabel_Balance.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel_Balance.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_Balance.setText("Mobile :");

        jLabel_BalPreview.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        jLabel_BalPreview.setForeground(new java.awt.Color(51, 51, 51));

        jText_csname.setBackground(new java.awt.Color(204, 255, 255));
        jText_csname.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jText_csname.setForeground(new java.awt.Color(51, 51, 51));

        jText_csmobile.setBackground(new java.awt.Color(204, 255, 255));
        jText_csmobile.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        jText_csmobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jText_csmobileActionPerformed(evt);
            }
        });

        jButton_done.setBackground(new java.awt.Color(102, 102, 102));
        jButton_done.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_done.setForeground(new java.awt.Color(204, 204, 204));
        jButton_done.setText("Save Purchase");
        jButton_done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_doneActionPerformed(evt);
            }
        });

        jButton_cancel.setBackground(new java.awt.Color(204, 204, 204));
        jButton_cancel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton_cancel.setForeground(new java.awt.Color(255, 51, 51));
        jButton_cancel.setText("X");
        jButton_cancel.setToolTipText("Close App");
        jButton_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Customer Name :");

        jLabel_Balance1.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel_Balance1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_Balance1.setText("Balance :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel_Balance, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_Balance1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jText_csname, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jText_csmobile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_done, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jToggleButton_fullPay, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton_HalfPay, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_BalPreview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_AmtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel_BillAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_Paid))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_cancel)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton_fullPay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton_HalfPay, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jButton_cancel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel_BillAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel_AmtPaid)
                                    .addComponent(jTextField_Paid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_BalPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_Balance1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jText_csname, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Balance, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jText_csmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton_done, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton_HalfPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_HalfPayActionPerformed
        // TODO add your handling code here:
        
        jTextField_Paid.setEnabled(true);
        jTextField_Paid.setText("");
        jTextField_Paid.requestFocus();
        
    }//GEN-LAST:event_jToggleButton_HalfPayActionPerformed

    private void jTextField_PaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PaidKeyReleased
        // TODO add your handling code here:
        
        try{
        double bill_amt = Double.parseDouble(pass_amt);
        int paid = Integer.parseInt(jTextField_Paid.getText());
        jLabel_BalPreview.setText(bill_amt-paid+"");
        }catch(Exception ex){
            jTextField_Paid.setText("");
            jTextField_Paid.requestFocus();
        }
        
    }//GEN-LAST:event_jTextField_PaidKeyReleased

    private void jToggleButton_fullPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_fullPayActionPerformed
        // TODO add your handling code here:
        
        jLabel_BalPreview.setText("0.0");
        jTextField_Paid.setText(pass_amt+"");
        jTextField_Paid.setEnabled(false);
        
    }//GEN-LAST:event_jToggleButton_fullPayActionPerformed

    private void jText_csmobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jText_csmobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jText_csmobileActionPerformed

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        // TODO add your handling code here:
        
        System.exit(0);
        
    }//GEN-LAST:event_jButton_cancelActionPerformed

    
    public void Trial_checkDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
        Date toDate = new Date();  
        toDate.setDate(15);
        toDate.setHours(8);
        toDate.setMonth(Calendar.NOVEMBER);
        System.out.println(formatter.format(toDate));
        if(new Date().before(toDate)){
        }else{
            if(new File("Settings.jxt").delete()){
                JOptionPane.showMessageDialog(rootPane, "Trial version Coompleted, Please Contact Cogent!!! -- +91 9655909777");
            }
            JOptionPane.showMessageDialog(rootPane, "Trial version Coompleted, Please Contact Cogent!!! -- +91 9655909777");
            System.exit(0);
        }
    }
    
    private void jButton_doneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_doneActionPerformed
        // TODO add your handling code here:
        Trial_checkDate();
        
        if(jText_csname.getText().isEmpty() && jText_csmobile.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Please Enter Customer Details!");
            return;
        }
        if(jText_csname.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Please Enter Customer Details!");
            return;
        }
        
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
        
        Sheet BillSheet = workbook.getSheetAt(1);
        //int rowNum = BillSheet.getLastRowNum()+1;
        String dateStr = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date());
        String CustName = jText_csname.getText();
        String phone = jText_csmobile.getText();
        String billAmt = jLabel_BillAmt.getText();
        String balAmt = jLabel_BalPreview.getText();
        String purItems = "";
        //int n = SaleConst.CurrentBill.size();
        
        List<String> keys = new ArrayList<>(SaleConst.CurrentBill.keySet());
        
        for(String eachKey : keys){
            String qty = SaleConst.CurrentBill.get(eachKey).toString().split("~")[1];
            purItems += eachKey+"("+qty+"),";
        }purItems=purItems.substring(0, purItems.lastIndexOf(","));
        
        Row currRow = BillSheet.createRow(BillSheet.getLastRowNum()+1);
        currRow.createCell(0).setCellValue(dateStr);
        currRow.createCell(1).setCellValue(CustName);
        currRow.createCell(2).setCellValue(phone);
        currRow.createCell(3).setCellValue(billAmt);
        currRow.createCell(4).setCellValue(balAmt);
        //XSSFHyperlink file_link=new XSSFHyperlink(XSSFHyperlink.LINK_FILE);
        //XSSFHyperlink file_link = new XSSFHyperlink();
        //file_link.setAddress("Receipts/"+SaleConfig.getBill_No().toUpperCase()+".pdf");
        currRow.createCell(5).setCellValue(SaleConfig.getBill_No().toUpperCase());
        //currRow.getCell(5).setHyperlink(file_link);
        //currRow.createCell(5).setCellValue(SaleConfig.getBill_No().toUpperCase());
        currRow.createCell(6).setCellValue(purItems);
        
        OutputStream os = new FileOutputStream(new File("Stocks.xlsx"));
        workbook.write(os);
        inputStream.close();
        os.close();
        
        SaleConst.CurrentBill.clear();
        
        BillEntry.total = new Double(0.0);    
        Dashboard_1.jLabel1_total = new JLabel("0.0");
        Dashboard_1.jLabel1_total.repaint();
        //PdfGen.saveIt(SaleConst.Curr);
        JOptionPane.showMessageDialog(rootPane, "Transaction Saved Successfully!");
        SaleConfig.printedSales++;
        this.setVisible(false);
        try{
        Dashboard_1.totalPrice = Double.parseDouble(billAmt) - Double.parseDouble(balAmt);
        }catch(Exception e){
        Dashboard_1.totalPrice = 0.0;    
        }
        SaleConfig.Store();
        Dashboard_1 d = new Dashboard_1();
        d.setVisible(true);
        BillEntry.total = 0.0;
        Dashboard_1.jLabel1_total.setText("0.0");
        Dashboard_1.priceDet.clear();
        Dashboard_1.itemDet.clear();
        //Resets all Static values
        
        
        
        
        } catch (IOException ex) {
            Logger.getLogger(BillEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_doneActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        
        if(evt.getClickCount() == 2){
            String key = jList1.getSelectedValue().toString();
            jText_csname.setText(key);
            jText_csmobile.setText(phoneMap.get(key).toString());
        }
        
    }//GEN-LAST:event_jList1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new CustomerBill().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_done;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_AmtPaid;
    private javax.swing.JLabel jLabel_BalPreview;
    private javax.swing.JLabel jLabel_Balance;
    private javax.swing.JLabel jLabel_Balance1;
    private javax.swing.JLabel jLabel_BillAmt;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField_Paid;
    private javax.swing.JTextField jText_csmobile;
    private javax.swing.JTextField jText_csname;
    private javax.swing.JToggleButton jToggleButton_HalfPay;
    private javax.swing.JToggleButton jToggleButton_fullPay;
    // End of variables declaration//GEN-END:variables
}
