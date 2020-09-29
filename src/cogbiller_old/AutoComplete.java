/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogbiller;

/**
 *
 * @author IS Mohammed
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class AutoComplete extends JFrame {
 private static final long serialVersionUID = 1L;
 private static String[] arr = { "Afghanistan", "Australia", "America", "Argentina", "United Kingdom",
   "United States", "United Arab Emirates", "Ukraine", "Uganda", "Albania", "Algeria", "Andorra", "Angola",
   "Antigua and Barbuda", "Austria", "India" };
 private JPanel contentPane;
 private JTextField txtName;
 private JComboBox<String> comboBox;

 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     AutoComplete frame = new AutoComplete();
     frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 public AutoComplete() {
  setUndecorated(true);
  //setType(Type.UTILITY);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBounds(100, 100, 318, 186);
  this.contentPane = new JPanel();
  contentPane.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent arg0) {
    System.exit(0);
   }
  });
  this.contentPane.setAutoscrolls(true);
  this.contentPane.setBackground(new Color(46, 139, 87));
  this.contentPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3), "Auto Complete by MSFATA",
    TitledBorder.LEADING, TitledBorder.ABOVE_BOTTOM, null, null));
  setContentPane(this.contentPane);
  this.contentPane.setLayout(null);

  this.txtName = new JTextField();
  txtName.setBackground(new Color(255, 222, 173));
  txtName.setFont(new Font("Tahoma", Font.BOLD, 15));
  txtName.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent arg0) {
    txtName.setText(null);
   }
  });
  txtName.addKeyListener(new KeyAdapter() {
   @Override
   public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 38) {
     int x = comboBox.getSelectedIndex();
     if (x > 0) {
      comboBox.setSelectedIndex(x - 1);
     }
     getContentPane().add(comboBox);
     comboBox.showPopup();
    } else if (e.getKeyCode() == 40) {
     int x = comboBox.getSelectedIndex();
     int y = comboBox.getItemCount();
     if (x + 1 < y)
      comboBox.setSelectedIndex(x + 1);
     getContentPane().add(comboBox);
     comboBox.showPopup();
    }
   }
  });
  txtName.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    try {
     txtName.setText(comboBox.getSelectedItem().toString());
     comboBox.removeAllItems();
     comboBox.hidePopup();
     contentPane.remove(comboBox);
    } catch (Exception e) {
    }
   }
  });
  this.txtName.setHorizontalAlignment(SwingConstants.CENTER);
  this.txtName.setText("Name");
  this.txtName.addCaretListener(new TextFieldCaretListener());
  this.txtName.setBounds(24, 17, 269, 39);
  this.contentPane.add(this.txtName);
  this.txtName.setColumns(10);

  this.comboBox = new JComboBox<String>();
  this.comboBox.setFocusCycleRoot(true);
  this.comboBox.setFocusTraversalPolicyProvider(true);
  this.comboBox.setAutoscrolls(true);
  this.comboBox.setBorder(null);
  this.comboBox.setOpaque(false);
  this.comboBox.setBounds(25, 19, 268, 37);
  // this.contentPane.add(comboBox);
 }

 private class TextFieldCaretListener implements CaretListener {
  public void caretUpdate(CaretEvent e) {

   try {
    comboBox.removeAllItems();
    comboBox.hidePopup();
    contentPane.remove(comboBox);
    if (e.getMark() > 0) {
     for (String string : arr) {
      if (string.toLowerCase().startsWith(txtName.getText().toLowerCase())) {
       contentPane.add(comboBox);
       comboBox.addItem(string);
       comboBox.showPopup();
      }
     }
    }
   } catch (Exception e1) {
   }
   if (e.getMark() < 2) {
    contentPane.remove(comboBox);
   }
  }
 }
}
