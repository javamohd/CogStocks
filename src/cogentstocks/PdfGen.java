/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

//import com.sun.org.apache.xalan.internal.xslt.Process;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import java.awt.Desktop;
//import com.adobe.acrobat.gui.PDFPrint;
import Cart.CartBox;
import Cart.ItemObj;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import java.awt.Desktop;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.OutputStream;
//import java.lang.Process;
import java.util.Date;
 
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
 
public class PdfGen {
    
    public static void printIt(ArrayList<String> bill){
        
        try {
 
              OutputStream file = new FileOutputStream(new File("Receipts/"+SaleConfig.filePrefix+""+SaleConfig.printedSales+".pdf"));
           Document document = new Document();
           PdfWriter.getInstance(document, file);
 
 //Inserting Image in PDF
      Image image = Image.getInstance ("images/java4s.png");
      image.scaleAbsolute(120f, 60f);//image width,height 
 
      String billNo = SaleConfig.getBill_No();
      
 //Inserting Table in PDF
      PdfPTable table=new PdfPTable(4);
      table.setHeaderRows(1);
      table.setTotalWidth(new float[]{ 30, 116,40, 40 });
      PdfPCell cell = new PdfPCell (new Paragraph (SaleConfig.shopName));
 
       cell.setColspan (4);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       cell.setBackgroundColor (new BaseColor (140, 221, 8));
       table.addCell(cell);
       
       
       //PdfPCell newcell = new PdfPCell (new Paragraph ("Sana Technology"));
 
       //newcell.setColspan (4);
       //newcell.setRowspan(2);
       //newcell.setHorizontalAlignment (Element.ALIGN_CENTER);
       //newcell.setPadding (10.0f);
       //newcell.setBackgroundColor (new BaseColor (140, 231, 8));
 
       Phrase phrasesno = new Phrase();
        phrasesno.add(new Chunk("S ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phrasesno.add(new Chunk(" No. ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       
       PdfPCell cellsno = new PdfPCell(phrasesno);
       cellsno.setHorizontalAlignment (Element.ALIGN_CENTER);
       cellsno.setVerticalAlignment(Element.ALIGN_CENTER);
        //cell23.setColspan(4);
        //cell23.setRowspan(2);
       
       Phrase phrasepur = new Phrase();
        phrasepur.add(new Chunk("Purchase Item ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phrasepur.add(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellpur = new PdfPCell(phrasepur);
       cellpur.setHorizontalAlignment (Element.ALIGN_CENTER);
       
       
       Phrase phraseqty = new Phrase();
        phraseqty.add(new Chunk("Quantity ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phraseqty.add(new Chunk("  ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellqty = new PdfPCell(phraseqty);
       cellqty.setHorizontalAlignment (Element.ALIGN_CENTER);
       
       
       Phrase phrasepri = new Phrase();
        phrasepri.add(new Chunk("Price ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phrasepri.add(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellpri = new PdfPCell(phrasepri);
       cellpri.setHorizontalAlignment (Element.ALIGN_CENTER);
       //table.keepRowsTogether(0, 1);
       table.addCell(cellsno);
       table.addCell(cellpur);
       table.addCell(cellqty);
       table.addCell(cellpri);
       /*table.addCell("Java4s");
       table.addCell("NC");
       table.addCell("United States");*/
       int sno=1;
       for(String s: bill){
           String[] dets = s.split(",");
            
           PdfPCell snocell = new PdfPCell();
           Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
           Paragraph para = new Paragraph(sno+". ", font);
           para.setAlignment(Element.ALIGN_CENTER);
           snocell.addElement(para);
           snocell.setHorizontalAlignment(Element.ALIGN_CENTER);
           snocell.setVerticalAlignment(Element.ALIGN_CENTER);
           snocell.setVerticalAlignment(Element.ALIGN_CENTER);
           snocell.setPaddingBottom(10.0f);
            
            table.addCell(snocell);
           //table.addCell(sno+". ");//S.No
           
            table.addCell(dets[0]);//Item
             
           
           //table.addCell(dets[1]);//qty
           PdfPCell qtycell = new PdfPCell();
           Paragraph qtypara = new Paragraph(dets[1], font);
           qtypara.setAlignment(Element.ALIGN_CENTER);
           qtycell.addElement(qtypara);
           qtycell.setHorizontalAlignment(Element.ALIGN_CENTER);
           qtycell.setVerticalAlignment(Element.ALIGN_CENTER);
           table.addCell(qtycell);
           
           //table.addCell(dets[2]);//Price
           
           PdfPCell pricell = new PdfPCell();
           Paragraph pripara = new Paragraph(dets[2], font);
           pripara.setAlignment(Element.ALIGN_CENTER);
           pricell.addElement(pripara);
           pricell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(pricell);
           
           sno++;
       }
        
       //Total row addition
       PdfPCell totcell = new PdfPCell (new Paragraph ("Total Price"));
 
       totcell.setColspan (3);
       totcell.setHorizontalAlignment (Element.ALIGN_RIGHT);
       totcell.setPadding (10.0f);
       totcell.setBackgroundColor (new BaseColor (140, 171, 8));
       table.addCell(totcell);
       
       PdfPCell pricecell = new PdfPCell();
       Paragraph numberpara = new Paragraph(BillEntry.total+"");
       numberpara.setAlignment(Element.ALIGN_CENTER);
       pricecell.addElement(numberpara);
       pricecell.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(pricecell);
       //BillEntry.total = 0.0;
       //Total row addition //End
       
       table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
       table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS								          
 
 //Inserting List in PDF
       //List list=new List(true,30);
        //list.add(new ListItem("Java4s"));
       //list.add(new ListItem("Php4s"));
       //list.add(new ListItem("Some Thing...")); 
 
 //Text formating in PDF
        Chunk chunk=new Chunk("Thanks for coming "+SaleConfig.shopName+"...");
 chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
 Chunk chunk1=new Chunk("Please visit Again");
 chunk1.setUnderline(+4f,-8f);
 //chunk1.setBackground(new BaseColor (17, 46, 193));      
 
 //Now Insert Every Thing Into PDF Document
          document.open();//PDF document opened........			       
 
 document.add(image);
 
 document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
 document.add(new Paragraph("Dear "+SaleConfig.shopName+" Customer"));
 document.add(new Paragraph("Receipt Generated On - "+new Date().toString())); 
 Paragraph billp = new Paragraph(SaleConfig.getBill_No());
 Font f=new Font(FontFamily.TIMES_ROMAN,50.0f,Font.UNDERLINE,BaseColor.RED);
 billp.setFont(f);
 billp.setAlignment(Paragraph.ALIGN_RIGHT);
 document.add(billp);
 document.add(new Paragraph("Receipt Bill On - "+new Date().toString())); 
 document.add(table);
 
 document.add(chunk);
 document.add(chunk1);

 document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
       //document.newPage();            //Opened new page
 //document.add(list);            //In the new page we are going to add list
 
          document.close();
          file.close();
          
          Desktop desktop = Desktop.getDesktop();
          desktop.print(new File("Receipts/"+SaleConfig.filePrefix+""+SaleConfig.printedSales+".pdf"));
          SaleConfig.printedSales++;
          SaleConfig.gallaCash += BillEntry.total;
          BillEntry.total = 0.0;
          SaleConfig.Store();
          Dashboard_1.prepareGallaReport();
        }catch(Exception e){
            e.printStackTrace();
        }
       //System.out.println("Done...............");     
     
    }
    
    public static void saveIt(ArrayList<ItemObj> items){
         
        try {
 
              OutputStream file = new FileOutputStream(new File("Receipts/"+SaleConfig.getBill_No().toUpperCase()+".pdf"));
           Document document = new Document();
           PdfWriter.getInstance(document, file);
 
 //Inserting Image in PDF
      Image image = Image.getInstance ("Images/Logo4s.jpg");
      image.scaleAbsolute(120f, 60f);//image width,height 
 
 //Inserting Table in PDF
      PdfPTable table=new PdfPTable(4);
      table.setHeaderRows(1);
      table.setTotalWidth(new float[]{ 30, 116,40, 40 });
      PdfPCell cell = new PdfPCell (new Paragraph (SaleConfig.shopName));
 
       cell.setColspan (4);
       //cell.setRowspan(4);
       cell.setHorizontalAlignment (Element.ALIGN_CENTER);
       cell.setPadding (10.0f);
       cell.setBackgroundColor (new BaseColor (140, 221, 8));
 
       table.addCell(cell);
       
       
       //PdfPCell newcell = new PdfPCell (new Paragraph ("Sana Technology"));
 
       //newcell.setColspan (4);
       //newcell.setRowspan(2);
       //newcell.setHorizontalAlignment (Element.ALIGN_CENTER);
       //newcell.setPadding (10.0f);
       //newcell.setBackgroundColor (new BaseColor (140, 231, 8));
 
       Phrase phrasesno = new Phrase();
        phrasesno.add(new Chunk("S No.", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        //phrasesno.add(new Chunk(" No. ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       
       PdfPCell cellsno = new PdfPCell(phrasesno);
       cellsno.setHorizontalAlignment (Element.ALIGN_CENTER);
       cellsno.setVerticalAlignment(Element.ALIGN_CENTER);
       //cellsno.setPaddingBottom(20.0f);
        //cell23.setColspan(4);
        //cell23.setRowspan(2);
       
       Phrase phrasepur = new Phrase();
        phrasepur.add(new Chunk("Purchase Item ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phrasepur.add(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellpur = new PdfPCell(phrasepur);
       cellpur.setHorizontalAlignment (Element.ALIGN_CENTER);
       
       
       Phrase phraseqty = new Phrase();
        phraseqty.add(new Chunk("Quantity ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phraseqty.add(new Chunk("  ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellqty = new PdfPCell(phraseqty);
       cellqty.setHorizontalAlignment (Element.ALIGN_CENTER);
       
       
       Phrase phrasepri = new Phrase();
        phrasepri.add(new Chunk("Price ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        phrasepri.add(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_BOLD)));
       PdfPCell cellpri = new PdfPCell(phrasepri);
       cellpri.setHorizontalAlignment (Element.ALIGN_CENTER);
       //table.keepRowsTogether(0, 1);
       table.addCell(cellsno);
       table.addCell(cellpur);
       table.addCell(cellqty);
       table.addCell(cellpri);
       /*table.addCell("Java4s");
       table.addCell("NC");
       table.addCell("United States");*/
       int sno=1;
       for(ItemObj e : items){
           //String[] dets = s.split(",");
           String s =e.itemName;  
           PdfPCell snocell = new PdfPCell();
           Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
           Paragraph para = new Paragraph(sno+". ", font);
           para.setAlignment(Element.ALIGN_CENTER);
           snocell.addElement(para);
           snocell.setHorizontalAlignment(Element.ALIGN_CENTER);
           snocell.setVerticalAlignment(Element.ALIGN_CENTER);
           snocell.setPaddingBottom(10.0f);
            
            table.addCell(snocell);
           //table.addCell(sno+". ");//S.No
           
            table.addCell(s);//Item
             
           
           //table.addCell(dets[1]);//qty
           PdfPCell qtycell = new PdfPCell();
           Paragraph qtypara = new Paragraph(CartBox.qtyMap.get(s).toString(), font);
           qtypara.setAlignment(Element.ALIGN_CENTER);
           qtycell.addElement(qtypara);
           qtycell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(qtycell);
           
           //table.addCell(dets[2]);//Price
           
           PdfPCell pricell = new PdfPCell();
           Paragraph pripara = new Paragraph(CartBox.priceMap.get(s).toString(), font);
           pripara.setAlignment(Element.ALIGN_CENTER);
           pricell.addElement(pripara);
           pricell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(pricell);
           
           sno++;
       }
           
       //Discount row addition
       if(CartBox.cartDiscount > 0){
       PdfPCell discell = new PdfPCell (new Paragraph ("Discount Price."));
 
       discell.setColspan (3);
       discell.setHorizontalAlignment (Element.ALIGN_RIGHT);
       discell.setPadding (10.0f);
       discell.setBackgroundColor (new BaseColor (140, 171, 8));
       table.addCell(discell);
       
      PdfPCell disccellval = new PdfPCell();
       disccellval.setHorizontalAlignment(Element.ALIGN_CENTER);
       Paragraph discpara = new Paragraph(CartBox.cartDiscount+"");
       discpara.setAlignment(Element.ALIGN_CENTER);
       disccellval.addElement(discpara);
       table.addCell(disccellval);
       }
       
       //Total row addition
       PdfPCell totcell = new PdfPCell (new Paragraph ("Total Price"));
 
       totcell.setColspan (3);
       totcell.setHorizontalAlignment (Element.ALIGN_RIGHT);
       totcell.setPadding (10.0f);
       totcell.setBackgroundColor (new BaseColor (140, 171, 8));
       table.addCell(totcell);
       
       PdfPCell pricecell = new PdfPCell();
       Paragraph numberpara = new Paragraph(CustomerBill.pass_amt+"");
       numberpara.setAlignment(Element.ALIGN_CENTER);
       pricecell.addElement(numberpara);
       pricecell.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(pricecell);
       
       //Total row addition //End
       
       table.setSpacingBefore(30.0f);       // Space Before table starts, like margin-top in CSS
       table.setSpacingAfter(30.0f);        // Space After table starts, like margin-Bottom in CSS								          
 
Chunk chunk=new Chunk("Thanks for coming "+SaleConfig.shopName+"...");
 chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
 Chunk chunk1=new Chunk("Please visit Again");
 chunk1.setUnderline(+4f,-8f);
          document.open();//PDF document opened........			       
 
 document.add(image);
 
 document.add(Chunk.NEWLINE);   //Something like in HTML :-)
 
 document.add(new Paragraph("Dear "+SaleConfig.shopName+" Customer"));
 document.add(new Paragraph("Receipt Generated On - "+new Date().toString())); 
 Paragraph billp = new Paragraph(SaleConfig.getBill_No().toUpperCase(), FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.RED));
 billp.setAlignment(Paragraph.ALIGN_RIGHT);
 document.add(billp);
 document.add(table);
 
 document.add(chunk);
 document.add(chunk1);
 
 document.add(Chunk.NEWLINE);
 
          document.close();
          file.close();
          
          /*Desktop desktop = Desktop.getDesktop();
          desktop.print(new File("Receipts/"+SaleConfig.filePrefix+""+SaleConfig.printedSales+".pdf"));*/
          //SaleConfig.printedSales++;
          SaleConfig.gallaCash += BillEntry.total;
          //SaleConfig.Store();
          BillEntry.total = 0.0;
          Dashboard_1.prepareGallaReport();
        }catch(Exception e){
            e.printStackTrace();
        }
       //System.out.println("Done...............");     
    
    }
    
    }

    
