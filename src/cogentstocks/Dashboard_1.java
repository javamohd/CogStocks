/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import Cart.CartBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
            //Vector v = new Vector();
            //v.add("ISM");
            //v.add(123);
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
        
        //Single Click Editor
        final JTextField tt = new JTextField();
        tt.addFocusListener( new FocusAdapter()
        {
            public void focusGained( final FocusEvent e )
            {
                tt.selectAll();
            }
        } );
        DefaultCellEditor singleclick = new DefaultCellEditor(tt);
    singleclick.setClickCountToStart(1);
    jTable_billList.setDefaultEditor(jTable_billList.getColumnClass(3), singleclick);
        
        modified = false;
        jTable_billList.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                int selectedRow = jTable_billList.getSelectedRow();
                if(selectedRow == -1)return;
                validateQtyButtons();
                
                if(e.getColumn() == 3 && selectedRow != -1){// For Quantity Change
                        String itemN = jTable_billList.getValueAt(selectedRow, 1).toString();
                        String modVal = jTable_billList.getValueAt(selectedRow, 3).toString();
                        //Validate Qty here 
                        
                        int Stockavailable = SysParam.quantityMappings.get(itemN);
                        int reqVal = Integer.parseInt(modVal);
                        if(Stockavailable<reqVal){
                            JOptionPane.showMessageDialog(rootPane, "Invalid Qty!");
                            return;
                        }
                        
                        try{
                        int reqQty = Integer.parseInt(modVal);
                        }catch(Exception ee){
                            JOptionPane.showMessageDialog(rootPane, "Invalid Qty!");
                            return;
                        }
                        
                        CartBox.qtyMap.put(itemN, Integer.parseInt(modVal));
                        int newP = (int)CartBox.getItemByName(itemN).getCustPrice() * Integer.parseInt(modVal);
                        CartBox.priceMap.put(itemN, newP);
                }

                if (e.getColumn() == 4 && selectedRow != -1) {//For Price Changes
                        String itemN = jTable_billList.getValueAt(selectedRow, 1).toString();
                        String modVal = jTable_billList.getValueAt(selectedRow, 4).toString();
                        try{
                        CartBox.priceMap.put(itemN, Integer.parseInt(modVal));
                        }catch(Exception gg){
                            JOptionPane.showMessageDialog(rootPane, "Invalid Values Entered.");
                            screenupdate();
                        }

                }
                modified = true;
            }
        });
        
        jButton_Plus.setEnabled(false);
        jButton_minus.setEnabled(false);
        jButton_changeQty.setEnabled(false);
        
        jTable_billList.setRowHeight(50);
        jTable_billList.setFont(new Font("Serif", Font.BOLD, 16));
        
        jTable_billList.setBackground(Color.getColor("#696969"));
        jTable_billList.setForeground(Color.BLACK);
        
        this.jTextField_bar.setBackground(Color.darkGray);
        jTextField_bar.setCaretColor(Color.darkGray);
        Border b = new BevelBorder(BevelBorder.LOWERED, Color.darkGray, Color.darkGray);
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

        //jTable_billList.getCellEditor(0, 1).addCellEditorListener(jTable_billList);
        
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
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        this.getContentPane().setBackground(Color.darkGray);
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
        jButton_minus = new javax.swing.JButton();
        jButton_Plus = new javax.swing.JButton();
        jButton_changeQty = new javax.swing.JButton();

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
                false, false, false, true, true
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
        jTable_billList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable_billListPropertyChange(evt);
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
        jLabel1_total.setFont(new java.awt.Font("Cinzel Black", 1, 24)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Vrinda", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
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

        jButton_minus.setBackground(new java.awt.Color(102, 102, 102));
        jButton_minus.setForeground(new java.awt.Color(204, 204, 204));
        jButton_minus.setText("-1");
        jButton_minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_minusActionPerformed(evt);
            }
        });

        jButton_Plus.setBackground(new java.awt.Color(102, 102, 102));
        jButton_Plus.setForeground(new java.awt.Color(204, 204, 204));
        jButton_Plus.setText("+1");
        jButton_Plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PlusActionPerformed(evt);
            }
        });

        jButton_changeQty.setBackground(new java.awt.Color(102, 102, 102));
        jButton_changeQty.setForeground(new java.awt.Color(204, 204, 204));
        jButton_changeQty.setToolTipText("Change Quantity.");
        jButton_changeQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_changeQtyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_shop_name, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addGap(9, 9, 9)
                                                        .addComponent(jLabel5_lastGalla, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addGap(7, 7, 7)
                                                        .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton3_newS, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jButton3_updS, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton_reset)
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton_Sett, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16)
                                                .addComponent(jButton_minus)
                                                .addGap(7, 7, 7)
                                                .addComponent(jButton_changeQty, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(3, 3, 3)
                                                .addComponent(jButton_Plus))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(74, 74, 74)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(3, 3, 3)
                                                .addComponent(jButton3_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(316, 316, 316)
                                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(182, 182, 182)
                                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jTextField_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_shop_name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel2))
                            .addComponent(jLabel1_total, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3_newS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3_updS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton_Sett, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton_minus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton_changeQty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton_Plus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4_gallaCash, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5_lastGalla))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))))
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

        validateQtyButtons();
            if(modified){
            int selectedRow = jTable_billList.getSelectedRow();if(selectedRow == -1)return;
            screenupdate();
            }
        
    }//GEN-LAST:event_jTable_billListPropertyChange

    private void screenupdate(){
        if(modified)CartBox.updateTable();
        if(jTable_billList.getSelectedRowCount() <= 0){
            jButton3_remove.setEnabled(false);
        }else{
            jButton3_remove.setEnabled(true);
        }
        validateQtyButtons();
    }
    
    private void jTable_billListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_billListMouseClicked
        validateQtyButtons();
            modified = true;
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
        //return;
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

    private void jButton_changeQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_changeQtyActionPerformed
        ///JOptionPane.showInputDialog(rootPane, "Enter the Item Quantity.");
        
        int selectedqty = Integer.parseInt(jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 3).toString());
        String selectedItemName = jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 1).toString();
        int availableQty = SysParam.quantityMappings.get(selectedItemName);
        
        Icon errorIcon = UIManager.getIcon("OptionPane.informationIcon");
        //Object[] possibilities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        
        ArrayList qty = new ArrayList();
        for(int i=1;i<=availableQty;i++){
            qty.add(i);
        }
        
        Integer newQty = (Integer) JOptionPane.showInputDialog(null,
                "Select puchase quantity:\n\r From below", "Available Quantity",
                JOptionPane.PLAIN_MESSAGE, errorIcon, qty.toArray(), "Quantity");
        
        
        CartBox.qtyMap.put(selectedItemName, newQty);
        int newPrice = newQty * (int)CartBox.getItemByName(selectedItemName).getCustPrice();
        CartBox.priceMap.put(selectedItemName, newPrice);
        CartBox.updateTable();
        validateQtyButtons();
        
    }//GEN-LAST:event_jButton_changeQtyActionPerformed

    private void jButton_PlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PlusActionPerformed
        // TODO add your handling code here:
        int selectedR = jTable_billList.getSelectedRow();
        int selectedqty = Integer.parseInt(jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 3).toString());
        String selectedItemName = jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 1).toString();
        int newQty = selectedqty+1;
        
        int availableQty = SysParam.quantityMappings.get(selectedItemName);
        
        if(newQty > availableQty){
            JOptionPane.showMessageDialog(rootPane, "Quantity Not Applicable");
            return;
        }
        
        CartBox.qtyMap.put(selectedItemName, newQty);
        int newPrice = newQty * (int)CartBox.getItemByName(selectedItemName).getCustPrice();
        CartBox.priceMap.put(selectedItemName, newPrice);
        CartBox.updateTable();
        validateQtyButtons();
        //jTable_billList.setRowSelectionInterval(selectedR, selectedR);
        
    }//GEN-LAST:event_jButton_PlusActionPerformed

    private void jButton_minusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_minusActionPerformed
        // TODO add your handling code here:
        
        int selectedqty = Integer.parseInt(jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 3).toString());
        
        if (selectedqty <= 1) {
            JOptionPane.showConfirmDialog(rootPane, "Quantity Not Applicable");
        return;
        }
        
        String selectedItemName = jTable_billList.getValueAt
                (jTable_billList.getSelectedRow(), 1).toString();
        int newQty = selectedqty-1;
        CartBox.qtyMap.put(selectedItemName, newQty);
        int newPrice = newQty * (int)CartBox.getItemByName(selectedItemName).getCustPrice();
        CartBox.priceMap.put(selectedItemName, newPrice);
        CartBox.updateTable();
        validateQtyButtons();
    }//GEN-LAST:event_jButton_minusActionPerformed

    public void validateQtyButtons(){
        try{
            
            jButton_changeQty.setEnabled(false);
            jButton_Plus.setEnabled(false);
            jButton_minus.setEnabled(false);
            jButton_changeQty.setText("");
            
            //Remove Button
            //+1 button
            //-1 button
            //Chang Qty button
            
            int selectedRow = jTable_billList.getSelectedRow();
            if (selectedRow == -1) {
                jButton3_remove.setEnabled(false);
                return;
            }
            if(selectedRow >= jTable_billList.getRowCount())return;
            String selectedItemName = jTable_billList.getValueAt(selectedRow, 1).toString();
            int selectedItemQty = 1;
            try{
            selectedItemQty = Integer.parseInt(jTable_billList.getValueAt(selectedRow, 3).toString());
            }catch(Exception w){
                throw new NumberFormatException();
            }
            int selectedItemAvailableQty = SysParam.quantityMappings.get(selectedItemName);
            
            
            if(selectedItemQty < selectedItemAvailableQty){
                jButton_Plus.setEnabled(true);
                jButton_changeQty.setEnabled(true);
                jButton_changeQty.setText(selectedItemQty+"");
                
                if(selectedItemQty > 1 && selectedItemAvailableQty > 2){
                    jButton_minus.setEnabled(true);
                }
                
            }else{
                jButton_minus.setEnabled(true);
                jButton_Plus.setEnabled(false);
                jButton_changeQty.setText(selectedItemQty+"");
                jButton_changeQty.setEnabled(true);
            }
            
            //Remove Button Validation
            if (jTable_billList.getSelectedRowCount() <= 0) {
                jButton3_remove.setEnabled(false);
            } else {
                jButton3_remove.setEnabled(true);
            }
            
        }catch(Exception e){
            
                JOptionPane.showMessageDialog(rootPane, "Invalid Values Entered.");
                screenupdate();
            
            e.printStackTrace();
        }
    }
    
    
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
    private javax.swing.JButton jButton_Plus;
    private javax.swing.JButton jButton_Sett;
    private javax.swing.JButton jButton_changeQty;
    private javax.swing.JButton jButton_minus;
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
