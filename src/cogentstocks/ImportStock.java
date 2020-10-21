/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

//import com.sun.rowset.internal.Row;
//import com.sun.glass.events.KeyEvent;
import Cart.ItemObj;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SERVER
 */
public class ImportStock extends javax.swing.JDialog {

    /**
     * Creates new form BillEntry
     */
    
    DefaultListModel<Object> stockModel = new DefaultListModel<>();
    List<String> sortedstock = new ArrayList<String>();
    Map priceMap = new HashMap();
    Map qtyMap = new HashMap();
    private JComboBox<String> comboBox;
    private static String[] arr = { "Afghanistan", "Australia", "America", "Argentina", "United Kingdom",
   "United States", "United Arab Emirates", "Ukraine", "Uganda", "Albania", "Algeria", "Andorra", "Angola",
   "Antigua and Barbuda", "Austria", "India" };
    public static double total = 0.0;
    public ImportStock() {
        initComponents();
        setModal(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        jList1_Stock.setModel(stockModel);
        try {
            loadStock();
        } catch (IOException ex) {
            Logger.getLogger(BillEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TRy Start
        
        //jTextField1.setBackground(new Color(255, 222, 173));
  //jTextField1.setFont(new Font("Tahoma", Font.BOLD, 15));
        
        this.comboBox = new JComboBox<String>();
  this.comboBox.setFocusCycleRoot(true);
  this.comboBox.setFocusTraversalPolicyProvider(true);
  this.comboBox.setAutoscrolls(true);
  this.comboBox.setBorder(null);
  this.comboBox.setOpaque(false);
  this.comboBox.setBounds(350, 35, 300, 27);
        
  jTextField1.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent arg0) {
    jTextField1.setText(null);
   }
  });
  jTextField1.addKeyListener(new KeyAdapter() {
   @Override
   public void keyPressed(KeyEvent e) {
       comboBox.setVisible(true);
    if (e.getKeyCode() == 38) {
     int x = comboBox.getSelectedIndex();
     if (x > 0) {
      comboBox.setSelectedIndex(x - 1);
     }
     getContentPane().add(comboBox);
    } else if (e.getKeyCode() == 40) {
     int x = comboBox.getSelectedIndex();
     int y = comboBox.getItemCount();
     if (x + 1 < y)
      comboBox.setSelectedIndex(x + 1);
     getContentPane().add(comboBox);
     comboBox.showPopup();
    }
    try{ 
    comboBox.showPopup();
    }catch(Exception er){
        
    }
   }
  });
  
  
  
  jTextField1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    try {
     jTextField1.setText(comboBox.getSelectedItem().toString());
     String price = priceMap.get(jTextField1.getText()).toString();
    String qty = qtyMap.get(jTextField1.getText()).toString();
    jLabel5_status.setText("");
     //System.out.println("On Enter CAtch");
     if(Integer.parseInt(qty)<= 0){
                jLabel5_status.setText("Out of Stock !!!");
                jButton1.setEnabled(false);
            }else{
                
                if(Integer.parseInt(qty)<= 100){
                jLabel5_status.setText("Place the Order !!!");    
                jButton1.setEnabled(true);
                //}else{
                }
     }
    
    jLabel5_avail.setText(qty);
    //jTextField3.setText("1");
     
     comboBox.removeAllItems();
     comboBox.hidePopup();
     getContentPane().remove(comboBox);
     }catch (Exception e) {
    }
   }
  });
  //jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
  //jTextField1.setText("Name");
  jTextField1.addCaretListener(new TextFieldCaretListener());
  //jTextField1.setBounds(24, 17, 269, 39);
  this.getContentPane().add(jTextField1);
  //jTextField1.setColumns(10);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1_Stock = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jLabel5_avail = new javax.swing.JLabel();
        jLabel5_status = new javax.swing.JLabel();
        jLabel_preview = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Purchase");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Load Quantity");

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 204, 204));
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jList1_Stock.setBackground(new java.awt.Color(102, 102, 102));
        jList1_Stock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jList1_Stock.setForeground(new java.awt.Color(204, 255, 255));
        jList1_Stock.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jList1_Stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1_StockMouseClicked(evt);
            }
        });
        jList1_Stock.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1_StockValueChanged(evt);
            }
        });
        jList1_Stock.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jList1_StockPropertyChange(evt);
            }
        });
        jList1_Stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList1_StockKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jList1_StockKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jList1_Stock);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Available");

        jLabel5_avail.setBackground(java.awt.SystemColor.inactiveCaption);
        jLabel5_avail.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        jLabel5_avail.setForeground(java.awt.Color.red);
        jLabel5_avail.setText("---");

        jLabel5_status.setBackground(java.awt.SystemColor.inactiveCaption);
        jLabel5_status.setFont(new java.awt.Font("Wide Latin", 2, 10)); // NOI18N
        jLabel5_status.setForeground(java.awt.Color.red);

        jLabel_preview.setBackground(new java.awt.Color(153, 153, 153));
        jLabel_preview.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_preview.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_preview.setText("Img Preview");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(128, 128, 128)
                        .addComponent(jLabel5_avail, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_preview, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5_status, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5_avail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5_status, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_preview, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void loadStock() throws IOException{
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
        
        boolean skiprow = true;
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
             if(skiprow){
                 skiprow = false;
                 continue;
             }
             String rowString = "";
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                 
                //System.out.println(cell.getCellType());
                
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if(cell.getColumnIndex() == 1){
                        String stockName = cell.getStringCellValue();
                        rowString += stockName;
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        int stockPrice = 0;
                        if(cell.getColumnIndex() == 2){
                            stockPrice = (int) cell.getNumericCellValue();
                            rowString += "~"+stockPrice+"";
                        }
                        int stockQty = 0;
                        if(cell.getColumnIndex() == 3){
                            stockQty = (int) cell.getNumericCellValue();
                            rowString += "~"+stockQty+"";
                        }
                        break;
                }
            }
            sortedstock.add(rowString);
        }
         
        
        inputStream.close();
        Collections.sort(sortedstock);
        for(String s : sortedstock){
        stockModel.addElement(s.split("~")[0]);
        priceMap.put(s.split("~")[0], s.split("~")[1]);
        qtyMap.put(s.split("~")[0], s.split("~")[2]);
        //System.out.println(priceMap);
        }
    }
 

        
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(jTextField1.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Stock Name Please");
            return;
        }
        
        if(jTextField3.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Quantity Please");
            return;
        } 
       
        // TODO add your handling code here:
        ItemObj item = new ItemObj();
        item.setItemName(jTextField1.getText());
        item.setItemQty(Integer.parseInt(jTextField3.getText()) + Integer.parseInt(jLabel5_avail.getText()));
        
        DataStore.updateStock(item);
        JOptionPane.showMessageDialog(rootPane, "Stock Updated Successfully.");
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList1_StockValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1_StockValueChanged
        // TODO add your handling code here:
        
        //this.jTextField1.setText(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex()).toString());
        
    }//GEN-LAST:event_jList1_StockValueChanged

    private void jList1_StockPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jList1_StockPropertyChange
        // TODO add your handling code here:
        //this.jTextField1.setText(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex()).toString());
    }//GEN-LAST:event_jList1_StockPropertyChange

    private void jList1_StockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1_StockKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.jTextField1.setText(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex()).toString());
            String price = priceMap.get(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex())).toString();
            this.jTextField3.setText("1");
        }
        
        
    }//GEN-LAST:event_jList1_StockKeyTyped

    private void jList1_StockKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1_StockKeyReleased
        // TODO add your handling code here:
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //System.out.println("okay");
            this.jTextField1.setText(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex()).toString());
            String price = priceMap.get(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex())).toString();
            String qty = qtyMap.get(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex())).toString();
            this.jLabel5_avail.setText(qty);
            this.jTextField3.setText("1");
            if(Integer.parseInt(qty)<= 0){
                this.jLabel5_status.setText("Out of Stock !!!");
                jButton1.setEnabled(false);
            }else{
                
                if(Integer.parseInt(qty)<= 100){
                this.jLabel5_status.setText("Place the Order !!!"); 
                jButton1.setEnabled(true);
                }else{
                
                this.jLabel5_status.setText("");
                jButton1.setEnabled(true);
                }
            }
            
        }
        
    }//GEN-LAST:event_jList1_StockKeyReleased

    private void jList1_StockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1_StockMouseClicked
         // TODO add your handling code here:
        
        //System.out.println(qtyMap);
        
        comboBox.setVisible(false);
        if (evt.getClickCount() == 2) {
            this.jTextField1.setText(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex()).toString());
            String price = priceMap.get(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex())).toString();
            String qty = qtyMap.get(jList1_Stock.getModel().getElementAt(jList1_Stock.getSelectedIndex())).toString();
            this.jLabel5_avail.setText(qty);
            //this.jTextField3.setText("1");
            this.comboBox.setVisible(false);

            if (Integer.parseInt(qty) <= 0) {
                this.jLabel5_status.setText("Out of Stock !!!");
                jButton1.setEnabled(false);
            } else {

                if (Integer.parseInt(qty) <= 100) {
                    this.jLabel5_status.setText("Place the Order !!!");
                    jButton1.setEnabled(true);
                } else {

                    this.jLabel5_status.setText("");
                    jButton1.setEnabled(true);
                }
            }
        showItemImg(jTextField1.getText());
        }
    }//GEN-LAST:event_jList1_StockMouseClicked

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        int price = Integer.parseInt(priceMap.get(jTextField1.getText()).toString());
        if(!jTextField3.getText().isEmpty()){
        int newP = price * Integer.parseInt(jTextField3.getText());
        }
        
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        // TODO add your handling code here:
        jTextField3.selectAll();
    }//GEN-LAST:event_jTextField3FocusGained

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowStateChanged

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && evt.isControlDown()){
            System.out.println("Shortcut Working...");
        }
        
    }//GEN-LAST:event_formKeyPressed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && evt.isControlDown()){
            System.out.println("Shortcut Working...2222");
        }
        
    }//GEN-LAST:event_formKeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && evt.isControlDown()){
            System.out.println("Shortcut Working...");
        }
        
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(BillEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ImportStock().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5_avail;
    private javax.swing.JLabel jLabel5_status;
    private javax.swing.JLabel jLabel_preview;
    private javax.swing.JList jList1_Stock;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    private class TextFieldCaretListener implements CaretListener {
  public void caretUpdate(CaretEvent e) {

   try {
    comboBox.removeAllItems();
    comboBox.hidePopup();
    getContentPane().remove(comboBox);
    if (e.getMark() > 0) {
     for (String string : sortedstock) {
      if (string.toLowerCase().startsWith(jTextField1.getText().toLowerCase())) {
       getContentPane().add(comboBox);
       comboBox.addItem(string.split("~")[0]);
       comboBox.showPopup();
      }
     }
    }
   } catch (Exception e1) {
   }
   if (e.getMark() < 2) {
       try{
    getContentPane().remove(comboBox);
       }catch(Exception ec){
       //ec.printStackTrace();
   }
   }
  }
 }
   
    private Image fitimage(byte[] buf, int w , int h)
    {
    BufferedImage resizedimage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedimage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    try{
    g2.drawImage(ImageIO.read(new ByteArrayInputStream(buf)), 0, 0,w,h,null);
    }catch(Exception ex){
        ex.printStackTrace();
    }
    g2.dispose();
    return resizedimage;
}
    
    
    public void showItemImg(String ItemName){
        try {
                XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("Stocks.xlsx")));
                /*List<XSSFPictureData> pics = wb.getAllPictures();
                for (XSSFPictureData eachpic : pics) {
                    Image img = fitimage(eachpic.getData(), jLabel_preview.getWidth(), jLabel_preview.getHeight());
                    jLabel_preview.setIcon(new ImageIcon(img));
                    break;
                }
                
                XSSFSheet sht = wb.getSheet("Stocks");
                int x = sht.getLastRowNum();*/
                int i=0;
                    i++;
                        XSSFDrawing dp = wb.getSheetAt(0).createDrawingPatriarch();
                        List<XSSFShape> picss = dp.getShapes();
                        for(XSSFShape eachPic : picss){
                            XSSFPicture pic = (XSSFPicture)eachPic;
                        XSSFClientAnchor anc = pic.getClientAnchor();
                        
                        
                            if (true && wb.getSheet("Stocks").getRow(anc.getRow1())
                                    .getCell(1).getStringCellValue().equalsIgnoreCase(ItemName)) {
                                Image img = fitimage(pic.getPictureData().getData(), jLabel_preview.getWidth(), jLabel_preview.getHeight());
                                jLabel_preview.setIcon(new ImageIcon(img));
                                break;
                            }else{
                                Image img = fitimage(new byte[0], jLabel_preview.getWidth(), jLabel_preview.getHeight());
                                jLabel_preview.setIcon(new ImageIcon(img));
                            }
                        

                        }
        
        
        } catch (Exception cc) {
                cc.printStackTrace();
            }
    }
}
