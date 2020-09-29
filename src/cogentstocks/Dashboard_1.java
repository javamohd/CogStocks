/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import 

/**
 *
 * @author SERVER
 */
public class Dashboard_1 extends javax.swing.JFrame {

    
    public static DefaultTableModel model;
    public static HashMap itemDet = new HashMap();
    public static HashMap priceDet = new HashMap();
    public static double totalPrice = 0.0;
    Map priceMap = new HashMap();

    public static HashMap getItemDet() {
        return itemDet;
    }

    public static void setItemDet(HashMap itemDet) {
        Dashboard_1.itemDet = itemDet;
    }
    
    
    public static void prepareGallaReport(){
        try{
            SaleConfig.load();
           jLabel4_gallaCash.setText(SaleConfig.gallaCash+"");
           jLabel5_lastGalla.setText(SaleConfig.lastGalla+"");
           jLabel4_gallaCash.repaint();
            
            
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    
    /**
     * Creates new form Dashboard
     */
    
    public void loadPendingCustomers(){
        try{
            //System.out.println("Test");
            DefaultTableModel pendModel = new DefaultTableModel();
            pendModel.addColumn("Customer");
            pendModel.addColumn("Balance Amont");
            Vector v = new Vector();
            v.add("ISM");
            v.add(123);
            HashMap pendMap = new HashMap();
            ArrayList list = new ArrayList();
            
            
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
        Sheet billSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = billSheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                
                String name = nextRow.getCell(1).getStringCellValue();
                double bal = 0;
                try{
                bal = Double.parseDouble(nextRow.getCell(4).getStringCellValue());
                }catch(Exception ex){
                    bal = 0;
                }
                if(bal > 0){
                    double prevBal = 0;
                    try{
                     prevBal = Double.parseDouble(pendMap.get(name).toString());
                }catch(Exception ek){
                   prevBal = 0; 
                }
                    //if(!name.isEmpty())
                        //list.add(name+"~"+bal);
                    pendMap.put(name, prevBal+bal);
                }
            }
            
            Iterator hmIterator = pendMap.entrySet().iterator(); 
            while (hmIterator.hasNext()) { 
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                list.add(mapElement.getValue()+"~"+mapElement.getKey());
            }
            
            //Collections.sort(list);
            
            //Collections.sort(list);
            //System.out.println("~"+list);
            Map<String, Double> hm1 = sortByValue(pendMap);
            //System.out.println(hm1);
            ArrayList<String> keyList = new ArrayList<String>(hm1.keySet());
            for(String each : keyList){
                Vector tmp = new Vector();
                tmp.add(each);
                tmp.add(pendMap.get(each));
                pendModel.addRow(tmp);    
            }
            
            jTable_pendings.setModel(pendModel);
    }catch(Exception gg){
        gg.printStackTrace();
    }
    }
    
    public Dashboard_1() {
        initComponents();
        
        this.getContentPane().setBackground(Color.getHSBColor(60, 150, 50));
        this.dispose();
        this.setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2-50);
        SaleConfig.load();
        this.setTitle("Cogent Point of Sale");
        jLabel_shop_name.setText(SaleConfig.shopName);
        model = (DefaultTableModel) this.jTable_billList.getModel();
        jTable_billList.getColumn("S.No. ").setPreferredWidth(30);
        jTable_billList.getColumn("Purchase").setPreferredWidth(350);
        jTable_billList.getColumn("Quantity").setPreferredWidth(50);
        jTable_billList.getColumn("Price").setPreferredWidth(50);
        this.jSpinner1.setValue(10);
        //jTable_billList.getModel()
        
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable_billList.getColumn("S.No. ").setCellRenderer(centerRenderer);
        jTable_billList.getColumn("Quantity").setCellRenderer(centerRenderer);
        jTable_billList.getColumn("Price").setCellRenderer(centerRenderer);
        jButton3_remove.setEnabled(false);
        //jLabel1_total.setText(model.getRowCount()+"");
        
        //Creating Receipt Folder
        try{
            File folder = new File("Receipts");
            if(!folder.exists()){
                folder.mkdir();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //this.getRootPane().setDefaultButton(jButton2);
        this.getRootPane().setDefaultButton(jButton2);
        //this.setUndecorated(true);
        
        ImageIcon icon = new ImageIcon("src/Test/Logo.jpg");
        icon = new ImageIcon(icon.getImage().getScaledInstance(jLabel1_logo.getWidth()+70, jLabel1_logo.getHeight(), BufferedImage.SCALE_SMOOTH));
        jLabel1_logo.setIcon(icon);
        jLabel1_logo.repaint();
        prepareGallaReport();
        try{
        //Thread.sleep(2000);
        //new BillEntry().setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
//        jLabel1_logo.setIcon(icon);
        loadPendingCustomers();
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_billList = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3_remove = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1_total = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton_reset = new javax.swing.JButton();
        jLabel1_logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4_gallaCash = new javax.swing.JLabel();
        jLabel5_lastGalla = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel_shop_name = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_pendings = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton_Sett = new javax.swing.JButton();
        jButton3_scan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jTable_billList.setAutoCreateRowSorter(true);
        jTable_billList.setBorder(new javax.swing.border.SoftBevelBorder(0));
        jTable_billList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No. ", "Purchase", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_billList.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTable_billList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_billListMouseClicked(evt);
            }
        });
        jTable_billList.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jTable_billListHierarchyChanged(evt);
            }
        });
        jTable_billList.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable_billListInputMethodTextChanged(evt);
            }
        });
        jTable_billList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable_billListPropertyChange(evt);
            }
        });
        jTable_billList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable_billListKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable_billListKeyTyped(evt);
            }
        });
        jTable_billList.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                jTable_billListVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_billList);

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 204, 204));
        jButton2.setText("Add");
        jButton2.setSelected(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3_remove.setBackground(new java.awt.Color(102, 102, 102));
        jButton3_remove.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3_remove.setForeground(new java.awt.Color(204, 204, 204));
        jButton3_remove.setText("Remove");
        jButton3_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_removeActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Proceed Bills");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1_total.setBackground(java.awt.Color.white);
        jLabel1_total.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel1_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_total.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable_billList, org.jdesktop.beansbinding.ELProperty.create("${totalPrice}"), jLabel1_total, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel1_total.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel1_totalPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sitka Small", 3, 12)); // NOI18N
        jLabel2.setText("Total Price :");

        jButton_reset.setBackground(new java.awt.Color(102, 102, 102));
        jButton_reset.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_reset.setForeground(new java.awt.Color(204, 204, 204));
        jButton_reset.setText("Reset");
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });

        jLabel1_logo.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel1.setFont(new java.awt.Font("Sitka Small", 3, 12)); // NOI18N
        jLabel1.setText("Galla Cash :");

        jLabel3.setFont(new java.awt.Font("Sitka Small", 3, 12)); // NOI18N
        jLabel3.setText("Last Galla :");

        jLabel4_gallaCash.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N

        jLabel5_lastGalla.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel5_lastGalla.setText("  ");
        jLabel5_lastGalla.setToolTipText("  Last working day Collection in Rs.(INR)");

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setForeground(new java.awt.Color(204, 204, 204));
        jButton4.setText("ShowList");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Stock below <");

        jButton5.setBackground(new java.awt.Color(102, 102, 102));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setForeground(new java.awt.Color(204, 204, 204));
        jButton5.setText("Today Sales");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(102, 102, 102));
        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jButton6.setForeground(new java.awt.Color(204, 204, 204));
        jButton6.setText("Last 7D Sales");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(204, 204, 204));
        jButton7.setText("Last Month ...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel_shop_name.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel_shop_name.setForeground(new java.awt.Color(102, 255, 255));
        jLabel_shop_name.setText("jLabel5");

        jTable_pendings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_pendings);

        jButton8.setBackground(new java.awt.Color(102, 102, 102));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(204, 204, 204));
        jButton8.setText("Add Customer");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(102, 102, 102));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton9.setForeground(new java.awt.Color(204, 204, 204));
        jButton9.setText("Edit Customer");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton_Sett.setBackground(new java.awt.Color(102, 102, 102));
        jButton_Sett.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_Sett.setForeground(new java.awt.Color(204, 204, 204));
        jButton_Sett.setText("S");
        jButton_Sett.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SettActionPerformed(evt);
            }
        });

        jButton3_scan.setBackground(new java.awt.Color(204, 204, 204));
        jButton3_scan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3_scan.setForeground(new java.awt.Color(255, 51, 51));
        jButton3_scan.setText("Scan");
        jButton3_scan.setSelected(true);
        jButton3_scan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_scanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton3_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3_scan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButton_Sett, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButton_reset))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel3)
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel5_lastGalla, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton9))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel1)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel_shop_name, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 84, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3_scan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel_shop_name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5_lastGalla))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_Sett, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void addItem(String item, int qty, int price){
        
        //System.out.println("--"+totalPrice+"--");
        model.addRow(new Object[]{sNoOrder(),item, qty, price});
        itemDet.put(item, qty);
        priceDet.put(item, price);
        
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
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        try{//Stocks.xls not found
            
            File f = new File("Stocks.xlsx");
            if(!f.exists()){
                JOptionPane.showMessageDialog(rootPane, "[Stocks.xlsx] Stocks not found!!!");
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //this.setVisible(false);
        new BillEntry().setVisible(true);
        /*if(jTable_billList.getRowCount() > 0){
            jButton3_remove.setEnabled(true);
        }else{
            jButton3_remove.setEnabled(false);
        }*/
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_removeActionPerformed
        
        // TODO add your handling code here:
        
        int i = jTable_billList.getSelectedRow();
        int removePrice = Integer.parseInt(jTable_billList.getValueAt(i, 3).toString());
        BillEntry.total -= removePrice;
        Dashboard_1.jLabel1_total.setText(BillEntry.total+"");
        itemDet.remove(jTable_billList.getValueAt(i, 1).toString());
        priceDet.remove(jTable_billList.getValueAt(i, 1).toString());
        SaleConst.CurrentBill.remove(jTable_billList.getValueAt(i, 1).toString());
        Dashboard_1.model.removeRow(i);
        sNoOrder();
        jButton3_remove.setEnabled(false);
        return;
        
        
    }//GEN-LAST:event_jButton3_removeActionPerformed

    private void jTable_billListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable_billListPropertyChange
            int selectedRow = jTable_billList.getSelectedRow();
        if (selectedRow == -1) { return; }
        String itemName = jTable_billList.getValueAt(selectedRow, 1).toString();
        int selectedPrice = Integer.parseInt(jTable_billList.getValueAt(selectedRow, 3).toString());
        int selectedQty = Integer.parseInt(jTable_billList.getValueAt(selectedRow, 2).toString());
        itemDet.put(itemName, selectedQty);
        priceDet.put(itemName, selectedPrice);
        /*System.out.println("Manual Editing... -- " + itemName + "--" + selectedQty + "--" + selectedPrice);
        int newPr = Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[0])
                / Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[1]);
        if (selectedQty != Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[1])) {
            jTable_billList.getModel().setValueAt(newPr * selectedQty, selectedRow, 3);
            SaleConst.CurrentBill.put(itemName, selectedPrice + "~" + selectedQty);
        } else {
        }*/
        priceDet.put(itemName, selectedPrice);
        SaleConst.tmpPrice = selectedPrice;
        Set s = priceDet.keySet();
        ArrayList<String> l = new ArrayList(s);
        BillEntry.total = 0.0;
        for (String each : l) {
            BillEntry.total += Integer.parseInt(priceDet.get(each).toString());
        }
        Dashboard_1.jLabel1_total.setText(BillEntry.total + "");
    }//GEN-LAST:event_jTable_billListPropertyChange

    private void jTable_billListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_billListMouseClicked
        // TODO add your handling code here:
        if(jTable_billList.getSelectedColumnCount() <= 0){
            jButton3_remove.setEnabled(false);
        }else{
            jButton3_remove.setEnabled(true);
        }
    }//GEN-LAST:event_jTable_billListMouseClicked

    public void checkDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
        Date toDate = new Date();  
        toDate.setHours(8);
        toDate.setMonth(Calendar.NOVEMBER);
        System.out.println(formatter.format(toDate));
        if(new Date().before(toDate)){
        }else{
            if(new File("Settings.jxt").delete()){
                JOptionPane.showMessageDialog(rootPane, "Trial version Coompleted, Please Contact Cogent!!!");
            }
            JOptionPane.showMessageDialog(rootPane, "Trial version Coompleted, Please Contact Cogent!!!");
            System.exit(0);
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        // TODO add your handling code here:
        //checkDate();
        
        //SaleConst.CurrentBill.clear();
        try{//java4s not found
            
            File f = new File("src/Test/Logo.jpg");
            
            System.out.println(f.getAbsolutePath());
            
            if(!f.exists()){
                JOptionPane.showMessageDialog(rootPane, "[***] Logo not found!!!");
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            
            if(jTable_billList.getRowCount() <= 0){
                JOptionPane.showMessageDialog(rootPane, "Please add Purchased Items!!!");
                return;
            }
            
            
        ArrayList<String> ll = new ArrayList<String>();
        //populate list
        List<String>keys = new ArrayList<>();
        int tableCount = jTable_billList.getRowCount();
        for(int i=0;i<tableCount;i++){
            keys.add(jTable_billList.getValueAt(i, 1).toString());
        }
        
        for(String key : keys ){
            try{
            String val = new String();
            val += key+","+itemDet.get(key).toString() +","+priceDet.get(key).toString();
            ll.add(val);
            }catch(Exception ex){
                System.out.println(key+ " in Error");
                System.out.println(keys);
            }
        }
        System.out.println("Saving --"+ll);
        //PdfGen.saveIt(ll);
        SaleConfig.updateQty(itemDet);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //JOptionPane.showMessageDialog(rootPane, "Saved in Receipts");
        Dashboard_1.totalPrice = Double.parseDouble(Dashboard_1.jLabel1_total.getText());
        CustomerBill.pass_amt = Dashboard_1.totalPrice+"";
        CustomerBill c = new CustomerBill();
        c.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void jLabel1_totalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel1_totalPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1_totalPropertyChange

    private void jTable_billListVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_jTable_billListVetoableChange
jLabel1_total.setText(totalPrice+"");
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_billListVetoableChange

    private void jTable_billListHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jTable_billListHierarchyChanged
        // TODO add your handling code here:
        jLabel1_total.setText(totalPrice+"");
    }//GEN-LAST:event_jTable_billListHierarchyChanged

    private void jTable_billListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_billListKeyPressed
        // TODO add your handling code here:
        
        int selectedRow = 0;
        String selectedItem = "";
        int selectedqty = 0;
        int selectedPrice = 0;
        
        //if(evt.getClickCount()==2){
            selectedRow = jTable_billList.getSelectedRow();
            selectedItem = model.getValueAt(selectedRow, 1).toString();
            //System.out.println(selectedRow+"--"+selectedItem);
        //}
        
    }//GEN-LAST:event_jTable_billListKeyPressed

    private void jTable_billListInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable_billListInputMethodTextChanged
        //System.out.println("Yes Its working...");
// TODO add your handling code here:
    }//GEN-LAST:event_jTable_billListInputMethodTextChanged

    private void jTable_billListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_billListKeyTyped
        // TODO add your handling code here:
        //System.out.println("Yes Its working...");
    }//GEN-LAST:event_jTable_billListKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ReportFrame.filterSize = (int)jSpinner1.getValue();
        new ReportFrame().setVisible(true);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        new AddCustomer().setVisible(true);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        
        new EditCustomer().setVisible(true);
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        new TodayReportFrame().setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        
        new SevenDayReportFrame().setVisible(true);
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
        //new LastMonthReportFrame().setVisible(true);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        // TODO add your handling code here:
        SaleConst.CurrentBill.clear();
        if (jTable_billList.getRowCount() <= 0) {
            return;
        }

        BillEntry.total = 0.0;
        Dashboard_1.jLabel1_total.setText(BillEntry.total + "");
        itemDet.clear();
        priceDet.clear();
        int cnt = model.getRowCount();
        for (int i = 0; i < cnt; i++) {
            Dashboard_1.model.removeRow(0);
        }
        sNoOrder();
        jButton3_remove.setEnabled(false);
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton_SettActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SettActionPerformed
        // TODO add your handling code here:
        
        String a[] = null;
        try{
        new Sett.StockImageSetup().main(a);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton_SettActionPerformed

    private void jButton3_scanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_scanActionPerformed
        // TODO add your handling code here:
        
        JOptionPane.showMessageDialog(rootPane, "Scanning inProgress");
        
    }//GEN-LAST:event_jButton3_scanActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboard_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Dashboard_1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3_remove;
    private javax.swing.JButton jButton3_scan;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_Sett;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel1_logo;
    public static javax.swing.JLabel jLabel1_total;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel4_gallaCash;
    public static javax.swing.JLabel jLabel5_lastGalla;
    private javax.swing.JLabel jLabel_shop_name;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable_billList;
    private javax.swing.JTable jTable_pendings;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public JTable getjTable_billList() {
        return jTable_billList;
    }

    public void setjTable_billList(JTable jTable_billList) {
        this.jTable_billList = jTable_billList;
    }
    
    
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Double> > list = 
               new LinkedList<Map.Entry<String, Double> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() { 
            public int compare(Map.Entry<String, Double> o1,  
                               Map.Entry<String, Double> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>(); 
        for (Map.Entry<String, Double> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
}
