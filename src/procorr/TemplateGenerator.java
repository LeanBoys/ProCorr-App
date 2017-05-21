/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procorr;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

class TemplateGenerator extends PdfPageEventHelper {
    Phrase[] header = new Phrase[2];
    int pagenumber = 0;
    Font white = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.WHITE);
    Phrase sidetall;
    
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {}
    
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            pagenumber++;
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
        FileInputStream template = new FileInputStream("src/template.pdf");
        PdfReader reader = new PdfReader(template);
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        PdfContentByte cb = writer.getDirectContent();
        cb.addTemplate(page, 0, 0);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        Rectangle rect = writer.getBoxSize("art");
        sidetall = new Phrase("side " + pagenumber, white);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(MainFrame.getDato()), rect.getRight(), rect.getTop(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, sidetall, (rect.getRight()), rect.getBottom() - 45, 0);
    }
}