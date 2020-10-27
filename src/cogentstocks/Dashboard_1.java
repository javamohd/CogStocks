/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import Cart.CartBox;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
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
    public boolean modified = false;
    //public static HashMap itemDet = new HashMap();
    //public static HashMap priceDet = new HashMap();
    //public static double totalPrice = 0.0;
    //public static double saletotal = 0.0;
    //Map priceMap = new HashMap();

    

    
    
    
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
        jTable_billList.setRowHeight(50);
        jTable_billList.setFont(new Font("Serif", Font.BOLD, 16));
        
        jTable_billList.setBackground(Color.getColor("#696969"));
        jTable_billList.setForeground(Color.BLACK);
        
        this.jTextField_bar.setBackground(Color.getHSBColor(60, 150, 50));
        jTextField_bar.setCaretColor(Color.getHSBColor(60, 150, 50));
        Border b = new BevelBorder(BevelBorder.LOWERED, Color.getHSBColor(60, 150, 50), Color.getHSBColor(60, 150, 50));
        this.jTextField_bar.setBorder(b);
        
        SaleConfig.load();
        this.setTitle("Cogent Point of Sale");
        jLabel_shop_name.setText(SaleConfig.shopName);
        model = (DefaultTableModel) this.jTable_billList.getModel();
        jTable_billList.getColumn("S.No. ").setPreferredWidth(30);
        jTable_billList.getColumn("Purchase").setPreferredWidth(350);
        jTable_billList.getColumn("Quantity").setPreferredWidth(50);
        jTable_billList.getColumn("Price").setPreferredWidth(50);
        
        this.jSpinner1.setValue(10);
        
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable_billList.getColumn("S.No. ").setCellRenderer(centerRenderer);
        jTable_billList.getColumn("Quantity").setCellRenderer(centerRenderer);
        jTable_billList.getColumn("Price").setCellRenderer(centerRenderer);
        jTable_billList.getColumn("MRP").setCellRenderer(centerRenderer);
        jButton3_remove.setEnabled(false);
        
        try{
            File folder = new File("Receipts");
            if(!folder.exists()){
                folder.mkdir();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon("Images/Logo4s.jpg");
        
        icon = new ImageIcon(icon.getImage().getScaledInstance(jLabel1_logo.getWidth(), jLabel1_logo.getHeight(), BufferedImage.SCALE_SMOOTH));
        jLabel1_logo.setIcon(icon);
        jLabel1_logo.repaint();
        prepareGallaReport();
        try{
        }catch(Exception e){
            e.printStackTrace();
        }
        loadPendingCustomers();
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        this.getContentPane().setBackground(Color.getHSBColor(60, 150, 50));
        
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
        jToggleButton1 = new javax.swing.JToggleButton();
        jTextField_bar = new javax.swing.JTextField();
        jButton3_updS = new javax.swing.JButton();
        jButton3_newS = new javax.swing.JButton();

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
                "S.No. ", "Purchase", "MRP", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
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
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable_billListInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
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
        jLabel1_total.setFont(new java.awt.Font("Cinzel Black", 1, 18)); // NOI18N
        jLabel1_total.setForeground(new java.awt.Color(153, 255, 0));
        jLabel1_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_total.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable_billList, org.jdesktop.beansbinding.ELProperty.create("${totalPrice}"), jLabel1_total, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel1_total.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel1_totalPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sitka Small", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 255, 255));
        jLabel2.setText("Total :");

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
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Galla Cash :");

        jLabel3.setFont(new java.awt.Font("Sitka Small", 3, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Last Galla :");

        jLabel4_gallaCash.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel4_gallaCash.setForeground(new java.awt.Color(204, 204, 204));

        jLabel5_lastGalla.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel5_lastGalla.setForeground(new java.awt.Color(204, 204, 204));
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
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
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
        jButton_Sett.setText("Imgs");
        jButton_Sett.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SettActionPerformed(evt);
            }
        });

        jToggleButton1.setBackground(new java.awt.Color(102, 102, 102));
        jToggleButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(204, 204, 204));
        jToggleButton1.setText("Scan");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jTextField_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_barActionPerformed(evt);
            }
        });
        jTextField_bar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_barFocusLost(evt);
            }
        });

        jButton3_updS.setBackground(new java.awt.Color(102, 102, 102));
        jButton3_updS.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3_updS.setForeground(new java.awt.Color(204, 204, 204));
        jButton3_updS.setText("Import Stock");
        jButton3_updS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_updSActionPerformed(evt);
            }
        });

        jButton3_newS.setBackground(new java.awt.Color(102, 102, 102));
        jButton3_newS.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3_newS.setForeground(new java.awt.Color(204, 204, 204));
        jButton3_newS.setText("New Stock");
        jButton3_newS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_newSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton3_newS, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3_updS, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jButton3_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton_reset, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel_shop_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_Sett, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
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
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton9))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(15, 15, 15))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jLabel1)
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jTextField_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3_remove, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3_updS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3_newS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton_Sett, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel_shop_name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(7, 7, 7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(379, 379, 379)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*public static void addItem(ItemObj obj, int qty) {
        
        int n = model.getRowCount();
            if (n > 0) {
                for (int i = 0; i <= n-1; i++) {
                    model.removeRow(0);
                }
            }
        
        if (itemDet.containsKey(obj.itemName)) {
            int existingQty = Integer.parseInt(itemDet.get(obj.itemName).toString());
            itemDet.put(obj.itemName, qty + existingQty);
            priceDet.put(obj.getItemName(),
                    (obj.getCustPrice() * Integer.parseInt(itemDet.get(obj.itemName).toString())));
            Iterator saleItem = itemDet.keySet().iterator();
            while (saleItem.hasNext()) {
                ItemObj eachItem = SysParam.CurrentBill.get(saleItem.next().toString());
                //double mrp = SysParam.CurrentBill.get(eachItem.itemName).getCustPrice() / Integer.parseInt(itemDet.get(eachItem.itemName).toString());
                model.addRow(new Object[]{sNoOrder(),
                            eachItem.itemName,SysParam.CurrentBill.get(eachItem.itemName).getCustPrice(), itemDet.get(eachItem.getItemName()),
                            priceDet.get(eachItem.getItemName()),true});
            }
        } else {
            itemDet.put(obj.getItemName(), qty);
            SysParam.CurrentBill.put(obj.itemName, obj);
            priceDet.put(obj.getItemName(),
                    (obj.getCustPrice() * Integer.parseInt(itemDet.get(obj.itemName).toString())));
            Iterator saleItem = itemDet.keySet().iterator();
            while (saleItem.hasNext()) {
                ItemObj eachItem = SysParam.CurrentBill.get(saleItem.next().toString());
                double mrp = SysParam.CurrentBill.get(eachItem.itemName).getCustPrice();
                model.addRow(new Object[]{sNoOrder(),
                            eachItem.itemName,mrp, itemDet.get(eachItem.getItemName()),
                            eachItem.getCustPrice()*Integer.parseInt(itemDet.get(eachItem.getItemName()).toString()),
                            true});
            }
        }
        jLabel1_total.setText(Dashboard_1.getCurrentSaleTotal());
    }*/
    
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
        
        new BillEntry().setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_removeActionPerformed
        
        int selectedRow = jTable_billList.getSelectedRow();
        String itemName = jTable_billList.getModel().getValueAt(selectedRow, 1).toString();
        CartBox.removeItem(itemName);
        
        if(jTable_billList.getModel().getRowCount() == 0 || jTable_billList.getSelectedRow() == -1)
            jButton3_remove.setEnabled(false);
        
        return;
        
        
    }//GEN-LAST:event_jButton3_removeActionPerformed

    private void jTable_billListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable_billListPropertyChange

            if(modified){
            int selectedRow = jTable_billList.getSelectedRow();if(selectedRow == -1)return;
            String itemN = jTable_billList.getValueAt(selectedRow, 1).toString();
            String modVal = jTable_billList.getValueAt(selectedRow, 4).toString();
            CartBox.priceMap.put(itemN, Integer.parseInt(modVal));
            screenupdate();
            }
        /* int selectedRow = jTable_billList.getSelectedRow();
        if (selectedRow == -1) { return; }
        String itemName = jTable_billList.getValueAt(selectedRow, 1).toString();
        int selectedPrice = (int)Double.parseDouble(jTable_billList.getValueAt(selectedRow, 3).toString());
        int selectedQty = Integer.parseInt(jTable_billList.getValueAt(selectedRow, 3).toString());
        itemDet.put(itemName, selectedQty);
        priceDet.put(itemName, selectedPrice);
        /*System.out.println("Manual Editing... -- " + itemName + "--" + selectedQty + "--" + selectedPrice);
        int newPr = Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[0])
                / Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[1]);
        if (selectedQty != Integer.parseInt(SaleConst.CurrentBill.get(itemName).toString().split("~")[1])) {
            jTable_billList.getModel().setValueAt(newPr * selectedQty, selectedRow, 3);
            SaleConst.CurrentBill.put(itemName, selectedPrice + "~" + selectedQty);
        } else {
        }
        priceDet.put(itemName, selectedPrice);
        SysParam.tmpPrice = selectedPrice;
        Set s = priceDet.keySet();
        ArrayList<String> l = new ArrayList(s);
        BillEntry.total = 0.0;
        for (String each : l) {
            BillEntry.total += Integer.parseInt(priceDet.get(each).toString());
        }*/
        //Dashboard_1.jLabel1_total.setText(BillEntry.total + "");
    }//GEN-LAST:event_jTable_billListPropertyChange

    private void screenupdate(){
        if(modified)CartBox.updateTable();
        if(jTable_billList.getSelectedRowCount() <= 0){
            jButton3_remove.setEnabled(false);
        }else{
            jButton3_remove.setEnabled(true);
        }
    }
    
    private void jTable_billListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_billListMouseClicked
        if(jTable_billList.getSelectedRowCount() <= 0){
            jButton3_remove.setEnabled(false);
        }else{
            jButton3_remove.setEnabled(true);
        }
            modified = true;
            int selectedRow = jTable_billList.getSelectedRow();
            String itemN = jTable_billList.getValueAt(selectedRow, 1).toString();
            String modVal = jTable_billList.getValueAt(selectedRow, 4).toString();
            CartBox.priceMap.put(itemN, Integer.parseInt(modVal));
            //CartBox.updateTable();
        
        
        
        /*String ChkVal = jTable_billList.getModel().getValueAt(selectedRow, 5).toString();
        String itemName = jTable_billList.getModel().getValueAt(selectedRow, 1).toString();
        ItemObj obj = SysParam.CurrentBill.get(itemName);
        
        if(ChkVal.equals("true")){
            jTable_billList.getModel().setValueAt(obj.getCustPrice(), selectedRow, 4);
            priceDet.put(obj.getItemName(), (int)obj.getCustPrice());
        }else{
            jTable_billList.getModel().setValueAt(obj.getPurPrice(), selectedRow, 4);
            priceDet.put(obj.getItemName(), (int)obj.getPurPrice());
        }
        Dashboard_1.jLabel1_total.setText(Dashboard_1.getCurrentSaleTotal());
        */
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
        
        //checkDate();
        try{
            //ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Test/Logo.jpg"));
            File f = new File("Images/Logo4s.jpg");
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
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        CustomerBill.pass_amt = CartBox.cartTotal+"";
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
//jLabel1_total.setText(totalPrice+"");
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_billListVetoableChange

    private void jTable_billListHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jTable_billListHierarchyChanged
        // TODO add your handling code here:
        //jLabel1_total.setText(totalPrice+"");
    }//GEN-LAST:event_jTable_billListHierarchyChanged

    private void jTable_billListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_billListKeyPressed
        // TODO add your handling code here:
        
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
        new LastMonthReportFrame().setVisible(true);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        // TODO add your handling code here:
        //SysParam.CurrentBill.clear();
        if (jTable_billList.getRowCount() <= 0) {
            return;
        }

        //BillEntry.total = 0.0;
        //Dashboard_1.jLabel1_total.setText(BillEntry.total + "");
        CartBox.clearCart();
        /*int cnt = model.getRowCount();
        for (int i = 0; i < cnt; i++) {
            Dashboard_1.model.removeRow(0);
        }
        sNoOrder();
        jButton3_remove.setEnabled(false);*/
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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        
        if(jToggleButton1.isSelected()){
            jTextField_bar.requestFocus();
        }
        return;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jTextField_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_barActionPerformed
        // TODO add your handling code here:
        
        String purItmStr = jTextField_bar.getText();
        Cart.ItemObj item = SysParam.barCodeMappings.get(purItmStr);
        CartBox.addItem_scan(item);
        jTextField_bar.setText("");
    }//GEN-LAST:event_jTextField_barActionPerformed

    private void jButton3_updSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_updSActionPerformed
        // TODO add your handling code here:
        new ImportStock().setVisible(true);
        
    }//GEN-LAST:event_jButton3_updSActionPerformed

    private void jButton3_newSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_newSActionPerformed
        // TODO add your handling code here:
        new NewStock().setVisible(true);
    }//GEN-LAST:event_jButton3_newSActionPerformed

    private void jTextField_barFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_barFocusLost
        // TODO add your handling code here:
        jToggleButton1.setSelected(false);
    }//GEN-LAST:event_jTextField_barFocusLost

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
                
                ProgressFrame p = new ProgressFrame();
        p.setVisible(true);
        Thread t = new Thread(new ProgressThread());
        t.start();
        try{        
        //t.join();
        }catch(Exception e){
            e.printStackTrace();
        }
                new Dashboard_1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3_newS;
    private javax.swing.JButton jButton3_remove;
    private javax.swing.JButton jButton3_updS;
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
    public static javax.swing.JTable jTable_billList;
    private javax.swing.JTable jTable_pendings;
    private javax.swing.JTextField jTextField_bar;
    private javax.swing.JToggleButton jToggleButton1;
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
    
    public static String getCurrentSaleTotal() {
        return CartBox.cartTotal+"";
    }
    
}
