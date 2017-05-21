/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procorr;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    
    //To fill tablemodel when program starts (to show the right table layout even if nothing is loaded)
    Object[][] data = new Object[1][1]; 
    String[] kolonner = new String[]{"BestillingsNumer","Kunde"};
    DefaultTableModel tblMdl = new DefaultTableModel(data,kolonner);
    
    ResultSet ordre;
    int teller;

    String fra = "";
    String til = "";
    private static String printFra = "";
    private static String printTil = "";
    ButtonGroup antall = new ButtonGroup();
    
    Border defaultBorder;

    public MainFrame() {
        initComponents();
        defaultBorder = txtSok.getBorder();
        
        fyllOrdre("ny");
        try {
            ResultSet resultat = Kontroll.kontroll.hentTjenester();
            while (resultat.next()){
                cmbTjeneste.addItem(resultat.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        this.getContentPane().setBackground(new Color(73,73,73));
        pnlSetting.setVisible(false);
        tabbedPane.setSelectedIndex(0);
        lablTab1.setBackground(new Color(0,106,150));
        antall.add(rbEnkunde);
        antall.add(rbAllekunder);
        Date date = new Date();
        dpFra.setDate(date);
        dpTil.setDate(date);
        
        lablTab1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                lablTab1.setBackground(new Color(0,106,150));
                lablTab2.setBackground(new Color(0,166,235));
                lablTab3.setBackground(new Color(0,166,235));
                tabbedPane.setSelectedIndex(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pnlTab1.isVisible()==false){
                    lablTab1.setBackground(new Color(0,106,150));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(pnlTab1.isVisible()==false){
                    lablTab1.setBackground(new Color(0,166,235));
                }
            }
        });
        
        lablTab2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                lablTab1.setBackground(new Color(0,166,235));
                lablTab2.setBackground(new Color(0,106,150));
                lablTab3.setBackground(new Color(0,166,235));
                tabbedPane.setSelectedIndex(1);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pnlTab2.isVisible()==false){
                    lablTab2.setBackground(new Color(0,106,150));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(pnlTab2.isVisible()==false){
                    lablTab2.setBackground(new Color(0,166,235));
                }
            }
        });
        
        lablTab3.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                lablTab1.setBackground(new Color(0,166,235));
                lablTab2.setBackground(new Color(0,166,235));
                lablTab3.setBackground(new Color(0,106,150));
                tabbedPane.setSelectedIndex(2);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pnlTab3.isVisible()==false){
                    lablTab3.setBackground(new Color(0,106,150));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(pnlTab3.isVisible()==false){
                    lablTab3.setBackground(new Color(0,166,235));
                }
            }
        });
        
        imgSetting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                if(pnlSetting.isVisible()==false){
                    pnlSetting.setVisible(true);
                } else {
                    pnlSetting.setVisible(false);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pnlSetting.isVisible()==false){
                    ImageIcon newIcon = new ImageIcon(getClass().getResource("/resources/settingcurrent.png"));
                    newIcon.getImage().flush();
                    imgSetting.setIcon(newIcon);
                } else {
                }

            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(pnlSetting.isVisible()==false){
                    ImageIcon oldIcon = new ImageIcon(getClass().getResource("/resources/setting.png"));
                    oldIcon.getImage().flush();
                    imgSetting.setIcon(oldIcon);
                } else {
                    ImageIcon newIcon = new ImageIcon(getClass().getResource("/resources/settingcurrent.png"));
                    newIcon.getImage().flush();
                    imgSetting.setIcon(newIcon);
                }
            }
        });
        
        lablChangepass.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                pnlSetting.setVisible(false);
                ImageIcon oldIcon = new ImageIcon(getClass().getResource("/resources/setting.png"));
                oldIcon.getImage().flush();
                imgSetting.setIcon(oldIcon);
                JOptionPane.showMessageDialog(null, "Temporarily Unavailable");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lablChangepass.setBackground(new Color(0,106,150));
                lablChangepass.setForeground(new Color(255,255,255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lablChangepass.setBackground(new Color(255,255,255));
                lablChangepass.setForeground(new Color(51,51,51));
            }
        });
        
        lablCreateaccount.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                pnlSetting.setVisible(false);
                ImageIcon oldIcon = new ImageIcon(getClass().getResource("/resources/setting.png"));
                oldIcon.getImage().flush();
                imgSetting.setIcon(oldIcon);
                JOptionPane.showMessageDialog(null, "Temporarily Unavailable");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lablCreateaccount.setBackground(new Color(0,106,150));
                lablCreateaccount.setForeground(new Color(255,255,255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lablCreateaccount.setBackground(new Color(255,255,255));
                lablCreateaccount.setForeground(new Color(51,51,51));
            }
        });
        
        lablLogout.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                pnlSetting.setVisible(false);
                ImageIcon oldIcon = new ImageIcon(getClass().getResource("/resources/setting.png"));
                oldIcon.getImage().flush();
                imgSetting.setIcon(oldIcon);
                MainFrame.this.dispose();
                new FrmLogin().setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lablLogout.setBackground(new Color(0,106,150));
                lablLogout.setForeground(new Color(255,255,255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lablLogout.setBackground(new Color(255,255,255));
                lablLogout.setForeground(new Color(51,51,51));
            }
        });
        
        txtAntall.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            if (txtAntall.getText().trim().isEmpty()) {
                txtTlfnr.setEnabled(true);
            } else {
                txtTlfnr.setEnabled(false);
            }
        }
        });
        
        txtTlfnr.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            if (txtTlfnr.getText().trim().isEmpty()) {
                txtAntall.setEnabled(true);
            } else {
                txtAntall.setEnabled(false);
            }
        }
        });
        
        tblResultat.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblResultat.getColumnModel().getColumn(1).setPreferredWidth(90);
        tblResultat.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblResultat.getColumnModel().getColumn(3).setPreferredWidth(40);
        tblResultat.getColumnModel().getColumn(4).setPreferredWidth(40);
    }
    
    public void fyllOrdre(String status){
        try{
            ordre = Kontroll.kontroll.hentOrdre("status", status);
                if (ordre.next()){
                    data = new Object[ordre.getInt(1)][2];
                    teller = 0;
                    do {
                        data[teller][0]=ordre.getInt(2);
                        data[teller][1]=ordre.getString(3);
                        teller++;
                    } while (ordre.next());

                    tblMdl = new DefaultTableModel(data, kolonner){
                    @Override
                    public boolean isCellEditable(int row, int column)
                    {
                      return false;//This causes all cells to not be editable
                    }
                    };                
                }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        tblOrdre.setModel(tblMdl);
    }
        
    private void lagrePDF() throws IOException {
       if (tblResultat.getRowCount() == 0) {
           JOptionPane.showMessageDialog(this, "Tabellen er tom.");
       } else {
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setDialogTitle("Velg hvor du vil lagre filen");
       fileChooser.setFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
       int userSelection = fileChooser.showSaveDialog(this);
       
       if (userSelection == JFileChooser.APPROVE_OPTION) {
           String pdfFil = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
           File minFil = new File(pdfFil);     
           if (minFil.exists()) {
                int respons = JOptionPane.showConfirmDialog(this,
                "Filen finnes allerede. Vil du erstatte filen?",
                "Eksisterende fil", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                if (respons != JOptionPane.YES_OPTION) {
                    lagrePDF();
                    return;
                }
                    
                }
                try {
                    Document doc = new Document();
                    PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfFil));
                    
                    // legg til header
                    TemplateGenerator event = new TemplateGenerator();
                    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
                    writer.setPageEvent(event);
                    
                    doc.open();
                      
                    // legg til tabellen
                    PdfPTable pdfTable = new PdfPTable(tblResultat.getColumnCount());

                    //headers
                    for (int i = 0; i < tblResultat.getColumnCount(); i++) {
                        pdfTable.addCell(tblResultat.getColumnName(i));
                    }
                    //data
                    for (int rows = 0; rows < tblResultat.getRowCount(); rows++) {
                        for (int cols = 0; cols < tblResultat.getColumnCount(); cols++) {
                            pdfTable.addCell(tblResultat.getValueAt(rows, cols).toString());
                        }
                    }
                    doc.add(pdfTable);
                    doc.close();
                } catch (DocumentException | FileNotFoundException ex) {
                
                }
                
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(minFil);
                } else {
                    JOptionPane.showMessageDialog(this, "Awt Desktop er ikke støttet!");
                }
           }
       }
}
    
    public static String getDato() {
        String dato = "Dato: " + printFra + " - " + printTil;
        return dato;
    }
    
    public void tomAlt() {
        tomBest();
        tomKunde();
        btnLagreendring.setEnabled(true);
        btnLagreny.setEnabled(false);
    }
    
    public void tomBest() {
        cmbTjeneste.setSelectedIndex(0);
        cmbStatus2.setSelectedIndex(1);
        txtPris.setText("");
        jdpMøte.setDate(new java.util.Date());
        jdpUtfor.setDate(new java.util.Date());
        txtKommentar.setText("");
    }
    
    public void tomKunde(){
        txtKundenr.setText("");
        txtTlf.setText("");
        txtFornavn.setText("");
        txtEtternavn.setText("");
        txtFirma.setText("");
        txtPostnr.setText("");
        txtPoststed.setText("");
        txtAdresse.setText("");
        txtTlf1.setText("");
        txtEpost.setText("");
    }
    
    private void klarerFelt() {
        chkSand.setSelected(false);
        chkKjemiskrengjøring.setSelected(false);
        chkHøytrykkrengjøring.setSelected(false);
        chkAnnet.setSelected(false);
        chkMetallisering.setSelected(false);
        chkSprøytemaling.setSelected(false);
        antall.clearSelection();
        txtAntall.setEnabled(false);
        txtAntall.setText("");
        txtTlfnr.setEnabled(false);
        txtTlfnr.setText("");
        Date now = new Date(); 
        dpFra.setDate(now);
        dpTil.setDate(now);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpKunde = new javax.swing.ButtonGroup();
        grpSok = new javax.swing.ButtonGroup();
        pnlTop = new javax.swing.JPanel();
        imgLogo = new javax.swing.JLabel();
        imgUser = new javax.swing.JLabel();
        lablUser = new javax.swing.JLabel();
        imgHline = new javax.swing.JLabel();
        lablTab1 = new javax.swing.JLabel();
        lablTab2 = new javax.swing.JLabel();
        lablTab3 = new javax.swing.JLabel();
        imgSetting = new javax.swing.JLabel();
        pnlSetting = new javax.swing.JPanel();
        lablChangepass = new javax.swing.JLabel();
        lablCreateaccount = new javax.swing.JLabel();
        lablLogout = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlTab1 = new javax.swing.JPanel();
        pnlLeftT1 = new javax.swing.JPanel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSok = new javax.swing.JButton();
        radOrdrenr = new javax.swing.JRadioButton();
        radCombo = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOrdre = new javax.swing.JTable();
        txtSok = new javax.swing.JFormattedTextField();
        btnNy = new javax.swing.JButton();
        txtPeriodetittel2 = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        pnlRightT1 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        pnlKunde = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTlf = new javax.swing.JFormattedTextField();
        radEksistkunde = new javax.swing.JRadioButton();
        radNykunde = new javax.swing.JRadioButton();
        btnKundenr = new javax.swing.JButton();
        btnTlf = new javax.swing.JButton();
        txtKundenr = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFornavn = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtEtternavn = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFirma = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPostnr = new javax.swing.JFormattedTextField();
        txtPoststed = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtAdresse = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTlf1 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEpost = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        pnlOvrig = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbTjeneste = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbStatus2 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdpMøte = new org.jdesktop.swingx.JXDatePicker();
        jLabel20 = new javax.swing.JLabel();
        jdpUtfor = new org.jdesktop.swingx.JXDatePicker();
        txtPris = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtKommentar = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBestnr = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        btnLagreendring = new javax.swing.JButton();
        btnLagreny = new javax.swing.JButton();
        pnlTab2 = new javax.swing.JPanel();
        pnlLeftT2 = new javax.swing.JPanel();
        btnSøk = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jSeparator11 = new javax.swing.JSeparator();
        txtPeriodetittel = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dpFra = new org.jdesktop.swingx.JXDatePicker();
        dpTil = new org.jdesktop.swingx.JXDatePicker();
        jPanel13 = new javax.swing.JPanel();
        txtTypetittel = new javax.swing.JTextField();
        chkSand = new javax.swing.JCheckBox();
        chkAnnet = new javax.swing.JCheckBox();
        chkKjemiskrengjøring = new javax.swing.JCheckBox();
        chkHøytrykkrengjøring = new javax.swing.JCheckBox();
        jSeparator10 = new javax.swing.JSeparator();
        chkMetallisering = new javax.swing.JCheckBox();
        chkSprøytemaling = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        rbAllekunder = new javax.swing.JRadioButton();
        txtAntalltittel = new javax.swing.JTextField();
        rbEnkunde = new javax.swing.JRadioButton();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtAntall = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblTlfnr2 = new javax.swing.JLabel();
        txtTlfnr = new javax.swing.JTextField();
        btnKlarer = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        pnlRightT2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResultat = new javax.swing.JTable();
        btnLagrepdf = new javax.swing.JButton();
        pnlTab3 = new javax.swing.JPanel();
        pnlLeftT3 = new javax.swing.JPanel();
        txtPeriodetittel1 = new javax.swing.JTextField();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        txtKundenr1 = new javax.swing.JFormattedTextField();
        btnKundenr1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtTlf2 = new javax.swing.JFormattedTextField();
        btnTlf1 = new javax.swing.JButton();
        pnlRightT3 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txtFornavn1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtEtternavn1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtFirma1 = new javax.swing.JTextField();
        txtPostnr1 = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        txtPoststed1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtAdresse1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtTlf3 = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        txtEpost1 = new javax.swing.JTextField();
        jSeparator19 = new javax.swing.JSeparator();
        btnLagrekude = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ProCorr AS");
        setBackground(new java.awt.Color(73, 73, 73));
        setMinimumSize(new java.awt.Dimension(1200, 690));
        setPreferredSize(new java.awt.Dimension(1200, 690));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 690));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTop.setBackground(new java.awt.Color(0, 166, 235));
        pnlTop.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlTop.setMaximumSize(new java.awt.Dimension(1200, 150));
        pnlTop.setMinimumSize(new java.awt.Dimension(1200, 150));
        pnlTop.setPreferredSize(new java.awt.Dimension(1200, 150));
        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgLogo.setBackground(new java.awt.Color(0, 166, 235));
        imgLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo.png"))); // NOI18N
        imgLogo.setToolTipText("ProCorr AS Logo");
        pnlTop.add(imgLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 21, -1, -1));

        imgUser.setBackground(new java.awt.Color(0, 166, 235));
        imgUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/user.png"))); // NOI18N
        imgUser.setToolTipText("ProCorr AS Logo");
        pnlTop.add(imgUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 21, -1, -1));

        lablUser.setBackground(new java.awt.Color(0, 166, 235));
        lablUser.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lablUser.setForeground(new java.awt.Color(255, 255, 255));
        lablUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lablUser.setText("Admin");
        lablUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lablUser.setMaximumSize(new java.awt.Dimension(280, 38));
        lablUser.setMinimumSize(new java.awt.Dimension(280, 38));
        lablUser.setName(""); // NOI18N
        lablUser.setPreferredSize(new java.awt.Dimension(280, 38));
        pnlTop.add(lablUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(594, 21, -1, -1));

        imgHline.setBackground(new java.awt.Color(255, 255, 255));
        imgHline.setMaximumSize(new java.awt.Dimension(571, 3));
        imgHline.setMinimumSize(new java.awt.Dimension(571, 3));
        imgHline.setOpaque(true);
        imgHline.setPreferredSize(new java.awt.Dimension(571, 3));
        pnlTop.add(imgHline, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 70, -1, -1));

        lablTab1.setBackground(new java.awt.Color(0, 166, 235));
        lablTab1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lablTab1.setForeground(new java.awt.Color(255, 255, 255));
        lablTab1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablTab1.setText("Bestilling");
        lablTab1.setMaximumSize(new java.awt.Dimension(150, 40));
        lablTab1.setMinimumSize(new java.awt.Dimension(150, 40));
        lablTab1.setOpaque(true);
        lablTab1.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTop.add(lablTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, -1, -1));

        lablTab2.setBackground(new java.awt.Color(0, 166, 235));
        lablTab2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lablTab2.setForeground(new java.awt.Color(255, 255, 255));
        lablTab2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablTab2.setText("Rapport");
        lablTab2.setMaximumSize(new java.awt.Dimension(150, 40));
        lablTab2.setMinimumSize(new java.awt.Dimension(150, 40));
        lablTab2.setOpaque(true);
        lablTab2.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTop.add(lablTab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        lablTab3.setBackground(new java.awt.Color(0, 166, 235));
        lablTab3.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lablTab3.setForeground(new java.awt.Color(255, 255, 255));
        lablTab3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablTab3.setText("Kundeinfo");
        lablTab3.setMaximumSize(new java.awt.Dimension(150, 40));
        lablTab3.setMinimumSize(new java.awt.Dimension(150, 40));
        lablTab3.setOpaque(true);
        lablTab3.setPreferredSize(new java.awt.Dimension(150, 40));
        pnlTop.add(lablTab3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, -1, -1));

        imgSetting.setBackground(new java.awt.Color(0, 166, 235));
        imgSetting.setForeground(new java.awt.Color(255, 255, 255));
        imgSetting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/setting.png"))); // NOI18N
        imgSetting.setMaximumSize(new java.awt.Dimension(40, 60));
        imgSetting.setMinimumSize(new java.awt.Dimension(40, 60));
        imgSetting.setPreferredSize(new java.awt.Dimension(40, 60));
        pnlTop.add(imgSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 0, 30, 60));

        pnlSetting.setBackground(new java.awt.Color(255, 255, 255));
        pnlSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlSetting.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lablChangepass.setBackground(new java.awt.Color(255, 255, 255));
        lablChangepass.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lablChangepass.setForeground(new java.awt.Color(51, 51, 51));
        lablChangepass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablChangepass.setText("Change password");
        lablChangepass.setOpaque(true);
        pnlSetting.add(lablChangepass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 30));

        lablCreateaccount.setBackground(new java.awt.Color(255, 255, 255));
        lablCreateaccount.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lablCreateaccount.setForeground(new java.awt.Color(51, 51, 51));
        lablCreateaccount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablCreateaccount.setText("Create Account");
        lablCreateaccount.setOpaque(true);
        pnlSetting.add(lablCreateaccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 130, 30));

        lablLogout.setBackground(new java.awt.Color(255, 255, 255));
        lablLogout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lablLogout.setForeground(new java.awt.Color(51, 51, 51));
        lablLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lablLogout.setText("Logout");
        lablLogout.setOpaque(true);
        pnlSetting.add(lablLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 130, 30));

        pnlTop.add(pnlSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 50, 130, 90));

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(0, 166, 235));
        jTextField1.setBorder(null);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 170, 40));

        tabbedPane.setBackground(new java.awt.Color(73, 73, 73));
        tabbedPane.setMaximumSize(new java.awt.Dimension(1200, 530));
        tabbedPane.setMinimumSize(new java.awt.Dimension(1200, 530));
        tabbedPane.setPreferredSize(new java.awt.Dimension(1200, 530));

        pnlTab1.setBackground(new java.awt.Color(73, 73, 73));
        pnlTab1.setMaximumSize(new java.awt.Dimension(1200, 500));
        pnlTab1.setMinimumSize(new java.awt.Dimension(1200, 500));
        pnlTab1.setPreferredSize(new java.awt.Dimension(1200, 500));
        pnlTab1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeftT1.setBackground(new java.awt.Color(255, 255, 255));
        pnlLeftT1.setMaximumSize(new java.awt.Dimension(360, 470));
        pnlLeftT1.setMinimumSize(new java.awt.Dimension(360, 470));
        pnlLeftT1.setPreferredSize(new java.awt.Dimension(360, 470));
        pnlLeftT1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nett", "Møte planlagt", "Ikke startet", "I gang", "Utført" }));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        pnlLeftT1.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, 156, -1));

        btnSok.setText("Søk");
        btnSok.setBorder(null);
        btnSok.setBorderPainted(false);
        btnSok.setEnabled(false);
        btnSok.setMaximumSize(new java.awt.Dimension(45, 25));
        btnSok.setMinimumSize(new java.awt.Dimension(45, 25));
        btnSok.setPreferredSize(new java.awt.Dimension(45, 25));
        btnSok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSokActionPerformed(evt);
            }
        });
        pnlLeftT1.add(btnSok, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 135, -1, -1));

        radOrdrenr.setBackground(new java.awt.Color(255, 255, 255));
        grpSok.add(radOrdrenr);
        radOrdrenr.setText("Vis basert på ordre nummer");
        radOrdrenr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radOrdrenrActionPerformed(evt);
            }
        });
        pnlLeftT1.add(radOrdrenr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        radCombo.setBackground(new java.awt.Color(255, 255, 255));
        grpSok.add(radCombo);
        radCombo.setSelected(true);
        radCombo.setText("Vis basert på status");
        radCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radComboActionPerformed(evt);
            }
        });
        pnlLeftT1.add(radCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        tblOrdre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }

        ){public boolean isCellEditable(int row, int column){return false;}}
    );
    tblOrdre.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tblOrdreMouseClicked(evt);
        }
    });
    jScrollPane4.setViewportView(tblOrdre);

    pnlLeftT1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 177, 330, 280));

    txtSok.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtSok.setToolTipText("Kun hele nummer");
    txtSok.setEnabled(false);
    txtSok.setMaximumSize(new java.awt.Dimension(105, 20));
    txtSok.setMinimumSize(new java.awt.Dimension(105, 20));
    txtSok.setPreferredSize(new java.awt.Dimension(105, 25));
    pnlLeftT1.add(txtSok, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 135, -1, -1));

    btnNy.setText("Ny ordre");
    btnNy.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNyActionPerformed(evt);
        }
    });
    pnlLeftT1.add(btnNy, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 100, 34));

    txtPeriodetittel2.setEditable(false);
    txtPeriodetittel2.setBackground(new java.awt.Color(255, 255, 255));
    txtPeriodetittel2.setText("Bestillinger");
    txtPeriodetittel2.setBorder(null);
    txtPeriodetittel2.setMaximumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel2.setMinimumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel2.setPreferredSize(new java.awt.Dimension(40, 28));
    txtPeriodetittel2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtPeriodetittel2ActionPerformed(evt);
        }
    });
    pnlLeftT1.add(txtPeriodetittel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, -1));

    jSeparator20.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator20.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator20.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlLeftT1.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 260, -1));

    pnlTab1.add(pnlLeftT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

    pnlRightT1.setBackground(new java.awt.Color(255, 255, 255));
    pnlRightT1.setMaximumSize(new java.awt.Dimension(775, 470));
    pnlRightT1.setMinimumSize(new java.awt.Dimension(775, 470));
    pnlRightT1.setPreferredSize(new java.awt.Dimension(775, 470));
    pnlRightT1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/streker.png"))); // NOI18N
    pnlRightT1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 53, 40, 80));

    jLabel3.setBackground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("Kunde Info");
    jLabel3.setOpaque(true);
    pnlRightT1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 15, 70, -1));

    jSeparator13.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator13.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator13.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlRightT1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 22, 730, -1));

    pnlKunde.setBackground(new java.awt.Color(255, 255, 255));
    pnlKunde.setPreferredSize(new java.awt.Dimension(730, 138));
    pnlKunde.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel11.setText("Kundenr:");
    pnlKunde.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 55, -1, -1));

    jLabel12.setText("Telefon:");
    pnlKunde.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

    txtTlf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtTlf.setToolTipText("Kun hele nummer");
    txtTlf.setEnabled(false);
    txtTlf.setPreferredSize(new java.awt.Dimension(105, 30));
    pnlKunde.add(txtTlf, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 85, 105, 30));

    radEksistkunde.setBackground(new java.awt.Color(255, 255, 255));
    grpKunde.add(radEksistkunde);
    radEksistkunde.setSelected(true);
    radEksistkunde.setText("Eksisterende kunde");
    radEksistkunde.setEnabled(false);
    radEksistkunde.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            radEksistkundeActionPerformed(evt);
        }
    });
    pnlKunde.add(radEksistkunde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, -1));

    radNykunde.setBackground(new java.awt.Color(255, 255, 255));
    grpKunde.add(radNykunde);
    radNykunde.setText("Ny kunde");
    radNykunde.setEnabled(false);
    radNykunde.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            radNykundeActionPerformed(evt);
        }
    });
    pnlKunde.add(radNykunde, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 15, -1, -1));

    btnKundenr.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
    btnKundenr.setText("-->");
    btnKundenr.setEnabled(false);
    btnKundenr.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnKundenr.setMaximumSize(new java.awt.Dimension(55, 30));
    btnKundenr.setMinimumSize(new java.awt.Dimension(55, 30));
    btnKundenr.setPreferredSize(new java.awt.Dimension(55, 30));
    btnKundenr.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKundenrActionPerformed(evt);
        }
    });
    pnlKunde.add(btnKundenr, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

    btnTlf.setText("-->");
    btnTlf.setEnabled(false);
    btnTlf.setMaximumSize(new java.awt.Dimension(55, 30));
    btnTlf.setMinimumSize(new java.awt.Dimension(55, 30));
    btnTlf.setPreferredSize(new java.awt.Dimension(55, 30));
    btnTlf.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnTlfActionPerformed(evt);
        }
    });
    pnlKunde.add(btnTlf, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 85, -1, -1));

    txtKundenr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtKundenr.setEnabled(false);
    txtKundenr.setMaximumSize(new java.awt.Dimension(105, 30));
    txtKundenr.setMinimumSize(new java.awt.Dimension(105, 30));
    txtKundenr.setPreferredSize(new java.awt.Dimension(105, 30));
    txtKundenr.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtKundenrActionPerformed(evt);
        }
    });
    pnlKunde.add(txtKundenr, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

    jLabel13.setText("Fornavn:");
    pnlKunde.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 14, -1, -1));

    txtFornavn.setEnabled(false);
    txtFornavn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtFornavnActionPerformed(evt);
        }
    });
    pnlKunde.add(txtFornavn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 165, -1));

    jLabel14.setText("Etternavn:");
    pnlKunde.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

    txtEtternavn.setEnabled(false);
    pnlKunde.add(txtEtternavn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 55, 165, -1));

    jLabel7.setText("Firma:");
    pnlKunde.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 104, -1, -1));

    txtFirma.setEnabled(false);
    txtFirma.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtFirmaActionPerformed(evt);
        }
    });
    pnlKunde.add(txtFirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 165, -1));

    jLabel15.setText("PostNr:");
    pnlKunde.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 14, -1, -1));

    txtPostnr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtPostnr.setToolTipText("Kun hele nummer");
    txtPostnr.setEnabled(false);
    txtPostnr.setMaximumSize(new java.awt.Dimension(60, 28));
    txtPostnr.setMinimumSize(new java.awt.Dimension(60, 28));
    txtPostnr.setPreferredSize(new java.awt.Dimension(60, 28));
    txtPostnr.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtPostnrActionPerformed(evt);
        }
    });
    txtPostnr.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            txtPostnrKeyReleased(evt);
        }
    });
    pnlKunde.add(txtPostnr, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));

    txtPoststed.setEnabled(false);
    pnlKunde.add(txtPoststed, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 105, -1));

    jLabel16.setText("Adresse:");
    pnlKunde.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 44, -1, -1));

    txtAdresse.setEnabled(false);
    pnlKunde.add(txtAdresse, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 165, -1));

    jLabel17.setText("Telefon:");
    pnlKunde.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 74, -1, -1));

    txtTlf1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtTlf1.setToolTipText("Kun hele nummer");
    txtTlf1.setEnabled(false);
    pnlKunde.add(txtTlf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 165, -1));

    jLabel18.setText("E-post:");
    pnlKunde.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 104, -1, -1));

    txtEpost.setEnabled(false);
    pnlKunde.add(txtEpost, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 165, -1));

    jLabel22.setBackground(new java.awt.Color(102, 102, 102));
    jLabel22.setOpaque(true);
    pnlKunde.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 10, 1, 117));

    pnlRightT1.add(pnlKunde, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 30, 730, 138));

    jLabel23.setBackground(new java.awt.Color(255, 255, 255));
    jLabel23.setText("Bestilling Info");
    jLabel23.setOpaque(true);
    pnlRightT1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 183, 85, -1));

    jSeparator15.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator15.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator15.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlRightT1.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 420, 730, -1));

    pnlOvrig.setBackground(new java.awt.Color(255, 255, 255));
    pnlOvrig.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel6.setText("Tjeneste:");
    pnlOvrig.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, 56, -1));

    cmbTjeneste.setEnabled(false);
    cmbTjeneste.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cmbTjenesteActionPerformed(evt);
        }
    });
    pnlOvrig.add(cmbTjeneste, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 32, 180, 35));

    jLabel5.setText("Status:");
    pnlOvrig.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 14, 45, -1));

    cmbStatus2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ny", "Møte planlagt", "Ikke startet", "I gang", "Utført" }));
    cmbStatus2.setEnabled(false);
    cmbStatus2.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            cmbStatus2ItemStateChanged(evt);
        }
    });
    cmbStatus2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cmbStatus2ActionPerformed(evt);
        }
    });
    pnlOvrig.add(cmbStatus2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 32, 180, 35));

    jLabel19.setText("Pris:");
    pnlOvrig.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 133, 31, -1));

    jLabel4.setText("Dato for møte:");
    pnlOvrig.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 80, 170, -1));

    jdpMøte.setEnabled(false);
    pnlOvrig.add(jdpMøte, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 98, 180, 30));

    jLabel20.setText("Dato for utførelse:");
    pnlOvrig.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 80, 170, -1));

    jdpUtfor.setEnabled(false);
    pnlOvrig.add(jdpUtfor, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 98, 180, 30));

    txtPris.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtPris.setToolTipText("Kun hele nummer");
    txtPris.setEnabled(false);
    txtPris.setMaximumSize(new java.awt.Dimension(180, 30));
    txtPris.setMinimumSize(new java.awt.Dimension(180, 30));
    txtPris.setPreferredSize(new java.awt.Dimension(180, 30));
    pnlOvrig.add(txtPris, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 150, -1, -1));

    txtKommentar.setColumns(20);
    txtKommentar.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
    txtKommentar.setRows(5);
    txtKommentar.setEnabled(false);
    jScrollPane1.setViewportView(txtKommentar);

    pnlOvrig.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 32, 280, 148));

    jLabel21.setText("Kommentar:");
    pnlOvrig.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 14, 80, -1));

    jLabel2.setText("OrdreNr:");
    pnlOvrig.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 132, 53, 20));

    txtBestnr.setEnabled(false);
    txtBestnr.setMaximumSize(new java.awt.Dimension(105, 30));
    txtBestnr.setMinimumSize(new java.awt.Dimension(105, 30));
    txtBestnr.setPreferredSize(new java.awt.Dimension(105, 30));
    pnlOvrig.add(txtBestnr, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 180, -1));

    pnlRightT1.add(pnlOvrig, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 200, 730, 190));

    jSeparator16.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator16.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator16.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlRightT1.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 190, 730, -1));

    btnLagreendring.setText("Lagre endringer");
    btnLagreendring.setEnabled(false);
    btnLagreendring.setMaximumSize(new java.awt.Dimension(150, 30));
    btnLagreendring.setMinimumSize(new java.awt.Dimension(150, 30));
    btnLagreendring.setPreferredSize(new java.awt.Dimension(150, 30));
    btnLagreendring.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLagreendringActionPerformed(evt);
        }
    });
    pnlRightT1.add(btnLagreendring, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, -1, -1));

    btnLagreny.setText("Lagre ny ordre");
    btnLagreny.setEnabled(false);
    btnLagreny.setMaximumSize(new java.awt.Dimension(150, 30));
    btnLagreny.setMinimumSize(new java.awt.Dimension(150, 30));
    btnLagreny.setPreferredSize(new java.awt.Dimension(150, 30));
    btnLagreny.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLagrenyActionPerformed(evt);
        }
    });
    pnlRightT1.add(btnLagreny, new org.netbeans.lib.awtextra.AbsoluteConstraints(606, 430, -1, -1));

    pnlTab1.add(pnlRightT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

    tabbedPane.addTab("tab1", pnlTab1);

    pnlTab2.setBackground(new java.awt.Color(73, 73, 73));
    pnlTab2.setMaximumSize(new java.awt.Dimension(1200, 500));
    pnlTab2.setMinimumSize(new java.awt.Dimension(1200, 500));
    pnlTab2.setPreferredSize(new java.awt.Dimension(1200, 500));
    pnlTab2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    pnlLeftT2.setBackground(new java.awt.Color(255, 255, 255));
    pnlLeftT2.setMaximumSize(new java.awt.Dimension(360, 470));
    pnlLeftT2.setMinimumSize(new java.awt.Dimension(360, 470));
    pnlLeftT2.setPreferredSize(new java.awt.Dimension(360, 470));
    pnlLeftT2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    btnSøk.setText("Søk");
    btnSøk.setMaximumSize(new java.awt.Dimension(60, 30));
    btnSøk.setMinimumSize(new java.awt.Dimension(60, 30));
    btnSøk.setPreferredSize(new java.awt.Dimension(60, 30));
    btnSøk.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSøkActionPerformed(evt);
        }
    });
    pnlLeftT2.add(btnSøk, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, -1, -1));

    jPanel14.setBackground(new java.awt.Color(255, 255, 255));
    jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jSeparator11.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator11.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator11.setPreferredSize(new java.awt.Dimension(280, 13));
    jPanel14.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 15, -1, -1));

    txtPeriodetittel.setEditable(false);
    txtPeriodetittel.setBackground(new java.awt.Color(255, 255, 255));
    txtPeriodetittel.setText("Periode");
    txtPeriodetittel.setBorder(null);
    txtPeriodetittel.setMaximumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel.setMinimumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel.setPreferredSize(new java.awt.Dimension(40, 28));
    txtPeriodetittel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtPeriodetittelActionPerformed(evt);
        }
    });
    jPanel14.add(txtPeriodetittel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, -1));

    jLabel9.setText("Fra:");
    jPanel14.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 30));

    jLabel10.setText("Til:");
    jPanel14.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 80, 30, 30));
    jPanel14.add(dpFra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 180, 30));
    jPanel14.add(dpTil, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 180, 30));

    jPanel13.setBackground(new java.awt.Color(255, 255, 255));
    jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    txtTypetittel.setEditable(false);
    txtTypetittel.setBackground(new java.awt.Color(255, 255, 255));
    txtTypetittel.setText("Tjenester");
    txtTypetittel.setBorder(null);
    txtTypetittel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtTypetittelActionPerformed(evt);
        }
    });
    jPanel13.add(txtTypetittel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 60, 28));

    chkSand.setBackground(new java.awt.Color(255, 255, 255));
    chkSand.setText("Sandblåsing/sandsweeping");
    jPanel13.add(chkSand, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 41, -1, -1));

    chkAnnet.setBackground(new java.awt.Color(255, 255, 255));
    chkAnnet.setText("Annet");
    jPanel13.add(chkAnnet, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

    chkKjemiskrengjøring.setBackground(new java.awt.Color(255, 255, 255));
    chkKjemiskrengjøring.setText("Kjemisk rengjøring");
    jPanel13.add(chkKjemiskrengjøring, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

    chkHøytrykkrengjøring.setBackground(new java.awt.Color(255, 255, 255));
    chkHøytrykkrengjøring.setText("Høytrykkrengjøring");
    jPanel13.add(chkHøytrykkrengjøring, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

    jSeparator10.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator10.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator10.setPreferredSize(new java.awt.Dimension(280, 13));
    jPanel13.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 26, 270, -1));

    chkMetallisering.setBackground(new java.awt.Color(255, 255, 255));
    chkMetallisering.setText("Metallisering");
    jPanel13.add(chkMetallisering, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

    chkSprøytemaling.setBackground(new java.awt.Color(255, 255, 255));
    chkSprøytemaling.setText("Sprøytemaling");
    jPanel13.add(chkSprøytemaling, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

    jPanel14.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 330, 120));

    jPanel12.setBackground(new java.awt.Color(255, 255, 255));
    jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    rbAllekunder.setBackground(new java.awt.Color(255, 255, 255));
    rbAllekunder.setSelected(true);
    rbAllekunder.setText("Alle kunder");
    rbAllekunder.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rbAllekunderActionPerformed(evt);
        }
    });
    jPanel12.add(rbAllekunder, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

    txtAntalltittel.setEditable(false);
    txtAntalltittel.setBackground(new java.awt.Color(255, 255, 255));
    txtAntalltittel.setText("Antall");
    txtAntalltittel.setBorder(null);
    txtAntalltittel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtAntalltittelActionPerformed(evt);
        }
    });
    jPanel12.add(txtAntalltittel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 7, 40, 28));

    rbEnkunde.setBackground(new java.awt.Color(255, 255, 255));
    rbEnkunde.setText("En kunde");
    rbEnkunde.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rbEnkundeActionPerformed(evt);
        }
    });
    jPanel12.add(rbEnkunde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, -1, 20));

    jSeparator14.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator14.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator14.setPreferredSize(new java.awt.Dimension(280, 13));
    jPanel12.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 290, -1));

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/streker v2.0.png"))); // NOI18N
    jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    jPanel12.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 48, 80, 100));

    txtAntall.setEnabled(false);
    jPanel12.add(txtAntall, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 102, 105, -1));

    jLabel8.setText("Kundenr:");
    jPanel12.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 107, -1, -1));

    lblTlfnr2.setText("Telefonnr:");
    jPanel12.add(lblTlfnr2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 137, -1, -1));

    txtTlfnr.setEnabled(false);
    jPanel12.add(txtTlfnr, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 133, 105, -1));

    jPanel14.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 340, 170));

    pnlLeftT2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, 410));

    btnKlarer.setText("Klarer søkefeltene");
    btnKlarer.setMaximumSize(new java.awt.Dimension(120, 30));
    btnKlarer.setMinimumSize(new java.awt.Dimension(120, 30));
    btnKlarer.setPreferredSize(new java.awt.Dimension(120, 30));
    btnKlarer.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKlarerActionPerformed(evt);
        }
    });
    pnlLeftT2.add(btnKlarer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));
    pnlLeftT2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 330, 13));

    pnlTab2.add(pnlLeftT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

    pnlRightT2.setBackground(new java.awt.Color(255, 255, 255));
    pnlRightT2.setMaximumSize(new java.awt.Dimension(775, 470));
    pnlRightT2.setMinimumSize(new java.awt.Dimension(775, 470));

    jPanel10.setBackground(new java.awt.Color(255, 255, 255));
    jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jScrollPane2.setMaximumSize(new java.awt.Dimension(745, 406));
    jScrollPane2.setMinimumSize(new java.awt.Dimension(745, 406));
    jScrollPane2.setPreferredSize(new java.awt.Dimension(745, 406));

    tblResultat.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Kundenummer", "Kundenavn", "Tjeneste", "Dato", "Pris"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane2.setViewportView(tblResultat);

    jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, -1, -1));

    btnLagrepdf.setText("Lagre");
    btnLagrepdf.setMaximumSize(new java.awt.Dimension(60, 30));
    btnLagrepdf.setMinimumSize(new java.awt.Dimension(60, 30));
    btnLagrepdf.setPreferredSize(new java.awt.Dimension(60, 30));
    btnLagrepdf.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLagrepdfActionPerformed(evt);
        }
    });
    jPanel10.add(btnLagrepdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, -1, -1));

    javax.swing.GroupLayout pnlRightT2Layout = new javax.swing.GroupLayout(pnlRightT2);
    pnlRightT2.setLayout(pnlRightT2Layout);
    pnlRightT2Layout.setHorizontalGroup(
        pnlRightT2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 775, Short.MAX_VALUE)
        .addGroup(pnlRightT2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE))
    );
    pnlRightT2Layout.setVerticalGroup(
        pnlRightT2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 470, Short.MAX_VALUE)
        .addGroup(pnlRightT2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
    );

    pnlTab2.add(pnlRightT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));
    pnlRightT2.getAccessibleContext().setAccessibleParent(pnlTab2);

    tabbedPane.addTab("tab1", pnlTab2);

    pnlTab3.setBackground(new java.awt.Color(73, 73, 73));
    pnlTab3.setMaximumSize(new java.awt.Dimension(1200, 500));
    pnlTab3.setMinimumSize(new java.awt.Dimension(1200, 500));
    pnlTab3.setPreferredSize(new java.awt.Dimension(1200, 500));
    pnlTab3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    pnlLeftT3.setBackground(new java.awt.Color(255, 255, 255));
    pnlLeftT3.setMaximumSize(new java.awt.Dimension(360, 470));
    pnlLeftT3.setMinimumSize(new java.awt.Dimension(360, 470));
    pnlLeftT3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    txtPeriodetittel1.setEditable(false);
    txtPeriodetittel1.setBackground(new java.awt.Color(255, 255, 255));
    txtPeriodetittel1.setText("Kunde Info");
    txtPeriodetittel1.setBorder(null);
    txtPeriodetittel1.setMaximumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel1.setMinimumSize(new java.awt.Dimension(50, 28));
    txtPeriodetittel1.setPreferredSize(new java.awt.Dimension(40, 28));
    txtPeriodetittel1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtPeriodetittel1ActionPerformed(evt);
        }
    });
    pnlLeftT3.add(txtPeriodetittel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, -1));

    jSeparator17.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator17.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator17.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlLeftT3.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 260, -1));

    jLabel25.setText("Kundenr:");
    pnlLeftT3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 53, -1, -1));

    txtKundenr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtKundenr1.setMaximumSize(new java.awt.Dimension(120, 30));
    txtKundenr1.setMinimumSize(new java.awt.Dimension(120, 30));
    txtKundenr1.setPreferredSize(new java.awt.Dimension(120, 30));
    pnlLeftT3.add(txtKundenr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 50, 120, -1));

    btnKundenr1.setText("-->");
    btnKundenr1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKundenr1ActionPerformed(evt);
        }
    });
    pnlLeftT3.add(btnKundenr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 55, -1));

    jLabel26.setText("Telefon:");
    pnlLeftT3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 83, -1, -1));

    txtTlf2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtTlf2.setToolTipText("Kun hele nummer");
    txtTlf2.setMaximumSize(new java.awt.Dimension(120, 30));
    txtTlf2.setMinimumSize(new java.awt.Dimension(120, 30));
    txtTlf2.setPreferredSize(new java.awt.Dimension(120, 30));
    pnlLeftT3.add(txtTlf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 80, 120, -1));

    btnTlf1.setText("-->");
    btnTlf1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnTlf1ActionPerformed(evt);
        }
    });
    pnlLeftT3.add(btnTlf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 55, -1));

    pnlTab3.add(pnlLeftT3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 360, 470));

    pnlRightT3.setBackground(new java.awt.Color(255, 255, 255));
    pnlRightT3.setMaximumSize(new java.awt.Dimension(775, 470));
    pnlRightT3.setMinimumSize(new java.awt.Dimension(775, 470));
    pnlRightT3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel34.setBackground(new java.awt.Color(255, 255, 255));
    jLabel34.setText("Kunde Info");
    jLabel34.setOpaque(true);
    pnlRightT3.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 15, 70, -1));

    jSeparator18.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator18.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator18.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlRightT3.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 22, 730, -1));

    jPanel7.setBackground(new java.awt.Color(255, 255, 255));
    jPanel7.setMaximumSize(new java.awt.Dimension(730, 138));
    jPanel7.setMinimumSize(new java.awt.Dimension(730, 138));
    jPanel7.setPreferredSize(new java.awt.Dimension(730, 138));
    jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel27.setText("Fornavn:");
    jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 13, -1, -1));
    jPanel7.add(txtFornavn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 10, 199, -1));

    jLabel28.setText("Etternavn:");
    jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 45, -1, -1));
    jPanel7.add(txtEtternavn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 40, 199, -1));

    jLabel29.setText("Firma:");
    jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 75, -1, -1));

    txtFirma1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtFirma1ActionPerformed(evt);
        }
    });
    jPanel7.add(txtFirma1, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 70, 199, -1));

    txtPostnr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtPostnr1.setToolTipText("Kun hele nummer");
    txtPostnr1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            txtPostnr1KeyReleased(evt);
        }
    });
    jPanel7.add(txtPostnr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 10, 51, -1));

    jLabel30.setText("PostNr:");
    jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 15, -1, -1));

    txtPoststed1.setEnabled(false);
    jPanel7.add(txtPoststed1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 140, -1));

    jLabel31.setText("Adresse:");
    jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 44, -1, -1));
    jPanel7.add(txtAdresse1, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 40, 201, -1));

    jLabel32.setText("Telefon:");
    jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 74, -1, -1));

    txtTlf3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
    txtTlf3.setToolTipText("Kun hele nummer");
    jPanel7.add(txtTlf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 70, 201, -1));

    jLabel33.setText("E-post:");
    jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 105, -1, -1));
    jPanel7.add(txtEpost1, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 100, 201, -1));

    pnlRightT3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 630, -1));

    jSeparator19.setMaximumSize(new java.awt.Dimension(280, 13));
    jSeparator19.setMinimumSize(new java.awt.Dimension(280, 13));
    jSeparator19.setPreferredSize(new java.awt.Dimension(280, 13));
    pnlRightT3.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 420, 730, -1));

    btnLagrekude.setText("Lagre endringer");
    btnLagrekude.setMaximumSize(new java.awt.Dimension(150, 30));
    btnLagrekude.setMinimumSize(new java.awt.Dimension(150, 30));
    btnLagrekude.setPreferredSize(new java.awt.Dimension(150, 30));
    btnLagrekude.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLagrekudeActionPerformed(evt);
        }
    });
    pnlRightT3.add(btnLagrekude, new org.netbeans.lib.awtextra.AbsoluteConstraints(606, 430, -1, -1));

    pnlTab3.add(pnlRightT3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 775, 470));

    tabbedPane.addTab("tab1", pnlTab3);

    getContentPane().add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLagrepdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLagrepdfActionPerformed
        try {
            lagrePDF();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLagrepdfActionPerformed

    private void btnSøkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSøkActionPerformed
        String query = "SELECT bestilling.bestillingnr AS Bestillingsnummer,"
        + " CONCAT(kunde.fornavn, ' ' ,kunde.etternavn) AS Navn,"
        + " tjeneste.tjeneste AS Tjeneste,"
        + " bestilling.utfordato AS Utført dato,"
        + " bestilling.pris AS Pris"
        + " FROM bestilling"
        + " LEFT JOIN kunde ON kunde.kundenr = bestilling.kundenr"
        + " LEFT JOIN tjeneste ON tjeneste.tjenestenr = bestilling.tjenestenr"
        + " WHERE bestilling.status = 'Utført' AND ";
        Boolean typeAntall = false;
        String feilmelding = "";
        
        // -----------Bestillingsantall------------
        if (rbEnkunde.isSelected()) {
            if (txtAntall.getText().trim().isEmpty() && txtTlfnr.getText().trim().isEmpty()) {
                feilmelding += "Velg antall.";
            } else {
                if (txtAntall.getText().trim().isEmpty()) {
                   if (txtTlfnr.getText().matches("[0-9]+") && txtTlfnr.getText().length() == 8) {
                       query+= "kunde.tlf = " + Integer.parseInt(txtTlfnr.getText()) + " AND"; 
                   } else {
                       feilmelding += "Telefonnummer må være tall med 8 siffer.\n";
                   }
                } else if (txtAntall.getText().trim() != "") {
                   if (txtAntall.getText().matches("[0-9]+")) { 
                       query+= "bestilling.kundenr = " + txtAntall.getText() + " AND";
                   } else {
                       feilmelding += "Kundenummer må være tall.\n";
                   }
                } else {
                       feilmelding += "Fyll inn kundenummer eller telefonnummer.\n";
            }
            }
        } else if (rbAllekunder.isSelected()) {
            // Ikke gjør noe
        } else {
            feilmelding += "Velg antall.";
        }


        // -----------Bestillingsperiode------------       
        Date fraDato = dpFra.getDate();
        Date tilDato = dpTil.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        fra = sdf.format(fraDato);
        til = sdf.format(tilDato);
        
        SimpleDateFormat printFormat = new SimpleDateFormat("dd.MM.yyyy");
        printFra = printFormat.format(fraDato);
        printTil = printFormat.format(tilDato);
        
        if (fraDato.before(tilDato)) {
            query += "(utfordato BETWEEN '" + fra + "' AND '" + til + "') AND";
        } else if (fraDato.equals(tilDato)) {
            query += " (utfordato = '" + fra + "') AND";
        } else {
            feilmelding += "Fra-dato må være en dato før til-dato.\n";
        }
       
        // -----------Bestillingstype----------------
        if (chkSand.isSelected() || chkAnnet.isSelected() || chkKjemiskrengjøring.isSelected()
                || chkHøytrykkrengjøring.isSelected() || chkMetallisering.isSelected() || chkSprøytemaling.isSelected()) {
            
        if (chkSand.isSelected()) {
            query+= " (tjeneste.tjeneste = 'Sandblåsing/sandsweeping'";
            typeAntall = true;
        }

        if (chkAnnet.isSelected()) {
            if (typeAntall) {
                query+= " OR tjeneste.tjeneste = 'Annet'";
            } else {
                query += " (tjeneste.tjeneste = 'Annet'";
                typeAntall = true;
            }
        }

        if (chkKjemiskrengjøring.isSelected()) {
            if (typeAntall) {
                query+= " OR tjeneste.tjeneste = 'Kjemisk rengjøring'";
            } else {
                query += " (tjeneste.tjeneste = 'Kjemisk rengjøring'";
                typeAntall = true;
            }
        }

        if (chkHøytrykkrengjøring.isSelected()) {
            if (typeAntall) {
                query+= " OR tjeneste.tjeneste = 'Høytrykkrengjøring'";
            } else {
                query += " (tjeneste.tjeneste = 'Høytrykkrengjøring'";
            }
        }
        
        if (chkMetallisering.isSelected()) {
            if (typeAntall) {
                query+= " OR tjeneste.tjeneste = 'Metallisering'";
            } else {
                query += " (tjeneste.tjeneste = 'Metallisering'";
                typeAntall = true;
            }
        }
        
        if (chkSprøytemaling.isSelected()) {
            if (typeAntall) {
                query+= " OR tjeneste.tjeneste = 'Sprøytemaling'";
            } else {
                query += " (tjeneste.tjeneste = 'Sprøytemaling'";
                typeAntall = true;
            }
        }

        } else {
            feilmelding += "Velg tjeneste.\n";
        }
        
        
        //---------Feilmelding sjekk----------------
        if (feilmelding == "") {
            query += ") GROUP BY bestillingnr";
            System.out.println(query);
            Kontroll.kontroll.fyllTabellen(tblResultat, query);
            if (tblResultat.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Søket ditt fant 0 treff.");
            }
        } else {
            JOptionPane.showMessageDialog(this, feilmelding);
        }
    }//GEN-LAST:event_btnSøkActionPerformed

    private void rbAllekunderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAllekunderActionPerformed
        txtAntall.setEnabled(false);
        txtAntall.setText("");
        txtTlfnr.setEnabled(false);
        txtTlfnr.setText("");
    }//GEN-LAST:event_rbAllekunderActionPerformed

    private void txtAntalltittelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAntalltittelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntalltittelActionPerformed

    private void rbEnkundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEnkundeActionPerformed
        txtAntall.setEnabled(true);
        txtTlfnr.setEnabled(true);
    }//GEN-LAST:event_rbEnkundeActionPerformed

    private void txtTypetittelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTypetittelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTypetittelActionPerformed

    private void txtPeriodetittelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodetittelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodetittelActionPerformed

    private void btnKlarerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlarerActionPerformed
        klarerFelt();
    }//GEN-LAST:event_btnKlarerActionPerformed

    private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
        tblMdl.setRowCount(0); //Emptying the table if no results

        if(evt.getStateChange() == evt.SELECTED){ //When another item is selected
            fyllOrdre(cmbStatus.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cmbStatusItemStateChanged

    private void btnSokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokActionPerformed
        tblMdl.setRowCount(0); //Emptying the table if no results
        try {
            if(radOrdrenr.isSelected()){
                ordre = Kontroll.kontroll.hentOrdre("ordreNr", txtSok.getText());

                if (ordre.next()){
                    data = new Object[ordre.getInt(1)][2];
                    teller = 0;
                    do {
                        data[teller][0]=ordre.getInt(2);
                        data[teller][1]=ordre.getString(3);
                        teller++;
                    } while (ordre.next());

                    tblMdl = new DefaultTableModel(data, kolonner){
                        @Override
                        public boolean isCellEditable(int row, int column)
                        {
                            return false;//This causes all cells to be not editable
                        }
                    };

                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        tblOrdre.setModel(tblMdl);
    }//GEN-LAST:event_btnSokActionPerformed

    private void radOrdrenrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radOrdrenrActionPerformed
        // TODO add your handling code here:
        cmbStatus.setEnabled(false);
        txtSok.setEnabled(true);
        btnSok.setEnabled(true);
    }//GEN-LAST:event_radOrdrenrActionPerformed

    private void radComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radComboActionPerformed
        cmbStatus.setEnabled(true);
        txtSok.setEnabled(false);
        btnSok.setEnabled(false);
    }//GEN-LAST:event_radComboActionPerformed

    private void tblOrdreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdreMouseClicked
        // TODO add your handling code here:
        try{
            tomAlt();
            ResultSet valgtOrdre = Kontroll.kontroll.enOrdre(tblOrdre.getValueAt(tblOrdre.getSelectedRow(),0).toString());
            if (valgtOrdre.next()){
                txtBestnr.setText(valgtOrdre.getString(1));

                ResultSet kunde = Kontroll.kontroll.getKundenr(valgtOrdre.getInt(2));
                if (kunde.next()){
                    txtKundenr.setText(String.valueOf(kunde.getInt(1)));
                    txtFornavn.setText(kunde.getString(2));
                    txtEtternavn.setText(kunde.getString(3));
                    txtAdresse.setText(kunde.getString(4));
                    txtPostnr.setText(kunde.getString(5));
                    ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr.getText()));
                    if (resultat.next()){
                        txtPoststed.setText(resultat.getString(1));
                    }
                    txtTlf1.setText(kunde.getString(6));
                    txtEpost.setText(kunde.getString(7));
                    txtFirma.setText(kunde.getString(8));
                }
                txtKundenr.setEnabled(false);
                txtFornavn.setEnabled(false);
                txtEtternavn.setEnabled(false);
                txtFirma.setEnabled(false);
                txtTlf.setEnabled(false);
                txtPostnr.setEnabled(false);
                txtAdresse.setEnabled(false);
                txtTlf1.setEnabled(false);
                txtEpost.setEnabled(false);
                radNykunde.setEnabled(false);
                radEksistkunde.setEnabled(false);
                radEksistkunde.setSelected(true);
                btnKundenr.setEnabled(false);
                btnTlf.setEnabled(false);
                cmbTjeneste.setEnabled(true);
                cmbStatus2.setEnabled(true);
                txtPris.setEnabled(true);
                jdpMøte.setEnabled(true);
                jdpUtfor.setEnabled(true);
                txtKommentar.setEnabled(true);

                cmbTjeneste.setSelectedIndex(valgtOrdre.getInt(3)-1);
                cmbStatus2.setSelectedItem(valgtOrdre.getString(4));
                txtPris.setText(valgtOrdre.getString(5));
                txtKommentar.setText(valgtOrdre.getString(6));
                jdpMøte.setDate(valgtOrdre.getDate(7));
                jdpUtfor.setDate(valgtOrdre.getDate(8));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tblOrdreMouseClicked

    private void btnNyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNyActionPerformed
        try {
            txtFornavn.setEnabled(false);
            txtEtternavn.setEnabled(false);
            txtFirma.setEnabled(false);
            txtKundenr.setEnabled(true);
            txtTlf.setEnabled(true);
            radEksistkunde.setSelected(true);
            radEksistkunde.setEnabled(true);
            radNykunde.setEnabled(true);
            btnKundenr.setEnabled(true);
            btnTlf.setEnabled(true);
            cmbTjeneste.setEnabled(true);
            cmbStatus2.setEnabled(true);
            txtPris.setEnabled(true);
            jdpMøte.setEnabled(true);
            jdpUtfor.setEnabled(true);
            txtKommentar.setEnabled(true);
            btnLagreendring.setEnabled(false);
            btnLagreny.setEnabled(true);

            int bestNr = Kontroll.kontroll.getMaxBest()+1;
            txtBestnr.setText(String.valueOf(bestNr));
            tomKunde();
            tomBest();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnNyActionPerformed

    private void radNykundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radNykundeActionPerformed
        // TODO add your handling code here:
        txtKundenr.setEnabled(false);
        txtTlf.setEnabled(false);
        txtFornavn.setEnabled(true);
        txtEtternavn.setEnabled(true);
        txtFirma.setEnabled(true);
        txtPostnr.setEnabled(true);
        txtAdresse.setEnabled(true);
        txtTlf1.setEnabled(true);
        txtEpost.setEnabled(true);
        btnKundenr.setEnabled(false);
        btnTlf.setEnabled(false);
        txtKundenr.setBorder(defaultBorder);
        txtTlf.setBorder(defaultBorder);
        txtPostnr.setBorder(defaultBorder);

        tomKunde();
        txtFornavn.requestFocusInWindow();
        try {
            int maxkunde = Kontroll.kontroll.getMaxKunde();
            txtKundenr.setText(String.valueOf(maxkunde+1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_radNykundeActionPerformed

    private void radEksistkundeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radEksistkundeActionPerformed
        // TODO add your handling code here:
        txtKundenr.setEnabled(true);
        txtTlf.setEnabled(true);
        txtFornavn.setEnabled(false);
        txtEtternavn.setEnabled(false);
        txtFirma.setEnabled(false);
        txtPostnr.setEnabled(false);
        txtAdresse.setEnabled(false);
        txtTlf1.setEnabled(false);
        txtEpost.setEnabled(false);
        btnKundenr.setEnabled(true);
        btnTlf.setEnabled(true);
        txtKundenr.setBorder(defaultBorder);
        txtTlf.setBorder(defaultBorder);
        txtPostnr.setBorder(defaultBorder);
        
        tomKunde();
        txtKundenr.requestFocusInWindow();
    }//GEN-LAST:event_radEksistkundeActionPerformed

    private void btnKundenrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKundenrActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet kunde = Kontroll.kontroll.getKundenr(Integer.parseInt(txtKundenr.getText()));
            if (kunde.next()){
                txtKundenr.setText(String.valueOf(kunde.getInt(1)));
                txtFornavn.setText(kunde.getString(2));
                txtEtternavn.setText(kunde.getString(3));
                txtAdresse.setText(kunde.getString(4));
                txtPostnr.setText(kunde.getString(5));
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr.getText()));
                if (resultat.next()){
                    txtPoststed.setText(resultat.getString(1));
                }
                txtTlf.setText(kunde.getString(6));
                txtTlf1.setText(kunde.getString(6));
                txtEpost.setText(kunde.getString(7));
                txtFirma.setText(kunde.getString(8));
                Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                txtKundenr.setBorder(border);
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtKundenr.setBorder(border);

                tomKunde();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnKundenrActionPerformed

    private void btnTlfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTlfActionPerformed
        try {
            ResultSet kunde = Kontroll.kontroll.getKundetlf(txtTlf.getText());
            if (kunde.next()){
                txtKundenr.setText(String.valueOf(kunde.getInt(1)));
                txtFornavn.setText(kunde.getString(2));
                txtEtternavn.setText(kunde.getString(3));
                txtAdresse.setText(kunde.getString(4));
                txtPostnr.setText(kunde.getString(5));
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr.getText()));
                if (resultat.next()){
                    txtPoststed.setText(resultat.getString(1));
                }
                txtTlf1.setText(kunde.getString(6));
                txtEpost.setText(kunde.getString(7));
                txtFirma.setText(kunde.getString(8));
                Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                txtTlf.setBorder(border);
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtTlf.setBorder(border);

                tomKunde();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnTlfActionPerformed

    private void txtFirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirmaActionPerformed

    private void txtPostnrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPostnrKeyReleased
        try {
            if (txtPostnr.getText().length()==4) {
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr.getText()));
                if (resultat.next()){
                    txtPoststed.setText(resultat.getString(1));
                    Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                    txtPostnr.setBorder(border);
                } else {
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    txtPostnr.setBorder(border);
                }
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtPostnr.setBorder(border);
                txtPoststed.setText("");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txtPostnrKeyReleased

    private void cmbStatus2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatus2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatus2ItemStateChanged

    private void cmbStatus2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatus2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatus2ActionPerformed

    private void btnLagreendringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLagreendringActionPerformed
        // TODO add your handling code here:
        try{
            int bestnr = Integer.parseInt(txtBestnr.getText());
            int tjeneste = cmbTjeneste.getSelectedIndex()+1;
            String status = cmbStatus2.getSelectedItem().toString();
            String pris = txtPris.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String mDato = sdf.format(jdpMøte.getDate());
            String uDato = sdf.format(jdpUtfor.getDate());
            String kommentar = txtKommentar.getText();

            Kontroll.kontroll.lagre(bestnr, tjeneste, status, pris, mDato, uDato, kommentar);
            JOptionPane.showMessageDialog(null, "Bestilling endret", "Suksess", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnLagreendringActionPerformed

    private void btnLagrenyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLagrenyActionPerformed
        try {
            int bestnr = Integer.parseInt(txtBestnr.getText());
            int kundenr = Integer.parseInt(txtKundenr.getText());
            String fornavn="";
            String etternavn="";
            String firma="";
            int postnr=1;
            String adresse="";
            String tlf="";
            String epost="";
            int tjeneste = cmbTjeneste.getSelectedIndex()+1;
            String status = cmbStatus.getSelectedItem().toString();
            String pris="";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String mDato="";
            String uDato="";
            String kommentar="";
            int feil=0;

            Border border = BorderFactory.createLineBorder(Color.RED, 1);
            if (txtFornavn.getText().equals("")){
                txtFornavn.setBorder(border);
                feil++;
            } else {
                fornavn=txtFornavn.getText();
            }
            if (txtEtternavn.getText().equals("")){
                txtEtternavn.setBorder(border);
                feil++;
            } else {
                etternavn=txtEtternavn.getText();
            }
            if (txtFirma.getText().equals("")){
                txtFirma.setBorder(border);
                feil++;
            } else {
                firma=txtFirma.getText();
            }
            if (txtPoststed.getText().equals("")){
                txtPostnr.setBorder(border);
                feil++;
            } else {
                postnr=Integer.parseInt(txtPostnr.getText());
            }
            if (txtAdresse.getText().equals("")){
                txtAdresse.setBorder(border);
                feil++;
            } else {
                adresse=txtAdresse.getText();
            }
            if (txtTlf1.getText().equals("")){
                txtTlf1.setBorder(border);
                feil++;
            } else {
                tlf=txtTlf1.getText();
            }
            if (txtEpost.getText().equals("")){
                txtEpost.setBorder(border);
                feil++;
            } else {
                epost=txtEpost.getText();
            }

            if (txtPris.getText().equals("")){
                txtPris.setBorder(border);
                feil++;
            } else {
                pris=txtPris.getText();
            }
            if (jdpMøte.getDate()==null){
                jdpMøte.setBorder(border);
                feil++;
            } else {
                mDato = sdf.format(jdpMøte.getDate());
            }
            if (jdpUtfor.getDate()==null){
                jdpUtfor.setBorder(border);
                feil++;
            } else {
                uDato = sdf.format(jdpMøte.getDate());
            }
            if (txtKommentar.getText().equals("")){
                txtKommentar.setBorder(border);
                feil++;
            } else {
                kommentar=txtKommentar.getText();
            }

            if (feil==0){
                if (radNykunde.isSelected()) {
                    Kontroll.kontroll.nyKunde(kundenr, fornavn, etternavn, firma, postnr, adresse, tlf, epost);
                }
                Kontroll.kontroll.nyBest(bestnr, kundenr, tjeneste, status, pris, kommentar, mDato, uDato);
                JOptionPane.showMessageDialog(null, "Ordre lagt inn", "Suksess", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Kundeinfo eller bestillingsinfo mangler", "Feil", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kundeinfo eller bestillingsinfo mangler", "InfoBox: " + "Feil", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnLagrenyActionPerformed

    private void txtFornavnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFornavnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFornavnActionPerformed

    private void cmbTjenesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTjenesteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTjenesteActionPerformed

    private void txtKundenrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKundenrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKundenrActionPerformed

    private void txtPostnrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPostnrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPostnrActionPerformed
int endreKundenr;
    private void btnKundenr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKundenr1ActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet kunde = Kontroll.kontroll.getKundenr(Integer.parseInt(txtKundenr1.getText()));
            if (kunde.next()){
                endreKundenr = kunde.getInt(1);
                txtKundenr1.setText(String.valueOf(kunde.getInt(1)));
                txtFornavn1.setText(kunde.getString(2));
                txtEtternavn1.setText(kunde.getString(3));
                txtAdresse1.setText(kunde.getString(4));
                txtPostnr1.setText(kunde.getString(5));
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr1.getText()));
                if (resultat.next()){
                    txtPoststed1.setText(resultat.getString(1));
                }
                txtTlf2.setText(kunde.getString(6));
                txtTlf3.setText(kunde.getString(6));
                txtEpost1.setText(kunde.getString(7));
                txtFirma1.setText(kunde.getString(8));
                Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                txtKundenr1.setBorder(border);
                txtTlf2.setBorder(defaultBorder);
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtKundenr.setBorder(border);
                txtTlf2.setBorder(defaultBorder);
                tomKunde();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnKundenr1ActionPerformed

    private void btnTlf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTlf1ActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet kunde = Kontroll.kontroll.getKundetlf(txtTlf2.getText());
            if (kunde.next()){
                endreKundenr = kunde.getInt(1);
                txtKundenr1.setText(String.valueOf(kunde.getInt(1)));
                txtFornavn1.setText(kunde.getString(2));
                txtEtternavn1.setText(kunde.getString(3));
                txtAdresse1.setText(kunde.getString(4));
                txtPostnr1.setText(kunde.getString(5));
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr1.getText()));
                if (resultat.next()){
                    txtPoststed1.setText(resultat.getString(1));
                }
                txtTlf3.setText(kunde.getString(6));
                txtEpost1.setText(kunde.getString(7));
                txtFirma1.setText(kunde.getString(8));
                Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                txtTlf2.setBorder(border);
                txtKundenr1.setBorder(defaultBorder);
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtTlf2.setBorder(border);
                txtKundenr1.setBorder(defaultBorder);
                tomKunde();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnTlf1ActionPerformed

    private void txtFirma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirma1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirma1ActionPerformed

    private void txtPostnr1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPostnr1KeyReleased
        // TODO add your handling code here:
        try {
            if (txtPostnr1.getText().length()==4) {
                ResultSet resultat = Kontroll.kontroll.getPoststed(Integer.parseInt(txtPostnr1.getText()));
                if (resultat.next()){
                    txtPoststed1.setText(resultat.getString(1));
                    Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
                    txtPostnr1.setBorder(border);
                } else {
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    txtPostnr1.setBorder(border);
                }
            } else {
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                txtPostnr1.setBorder(border);
                txtPoststed1.setText("");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txtPostnr1KeyReleased

    private void btnLagrekudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLagrekudeActionPerformed
        // TODO add your handling code here:
        try{
            String fornavn = txtFornavn1.getText();
            String etternavn = txtEtternavn1.getText();
            String adresse = txtAdresse1.getText();
            int postnr = Integer.parseInt(txtPostnr1.getText());
            String tlf = txtTlf3.getText();
            String epost = txtEpost1.getText();
            String firma = txtFirma1.getText();

            Kontroll.kontroll.endreKunde(fornavn, etternavn, adresse, postnr, tlf, epost, firma, endreKundenr);
            JOptionPane.showMessageDialog(null, "Kundeinfo endret", "Suksess", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Kundeinfo ble ikke endret", "Suksess", JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnLagrekudeActionPerformed

    private void txtPeriodetittel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodetittel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodetittel1ActionPerformed

    private void txtPeriodetittel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodetittel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodetittel2ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKlarer;
    private javax.swing.JButton btnKundenr;
    private javax.swing.JButton btnKundenr1;
    private javax.swing.JButton btnLagreendring;
    private javax.swing.JButton btnLagrekude;
    private javax.swing.JButton btnLagreny;
    private javax.swing.JButton btnLagrepdf;
    private javax.swing.JButton btnNy;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnSøk;
    private javax.swing.JButton btnTlf;
    private javax.swing.JButton btnTlf1;
    private javax.swing.JCheckBox chkAnnet;
    private javax.swing.JCheckBox chkHøytrykkrengjøring;
    private javax.swing.JCheckBox chkKjemiskrengjøring;
    private javax.swing.JCheckBox chkMetallisering;
    private javax.swing.JCheckBox chkSand;
    private javax.swing.JCheckBox chkSprøytemaling;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbStatus2;
    private javax.swing.JComboBox<String> cmbTjeneste;
    private org.jdesktop.swingx.JXDatePicker dpFra;
    private org.jdesktop.swingx.JXDatePicker dpTil;
    private javax.swing.ButtonGroup grpKunde;
    private javax.swing.ButtonGroup grpSok;
    private javax.swing.JLabel imgHline;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JLabel imgSetting;
    private javax.swing.JLabel imgUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXDatePicker jdpMøte;
    private org.jdesktop.swingx.JXDatePicker jdpUtfor;
    private javax.swing.JLabel lablChangepass;
    private javax.swing.JLabel lablCreateaccount;
    private javax.swing.JLabel lablLogout;
    private javax.swing.JLabel lablTab1;
    private javax.swing.JLabel lablTab2;
    private javax.swing.JLabel lablTab3;
    private javax.swing.JLabel lablUser;
    private javax.swing.JLabel lblTlfnr2;
    private javax.swing.JPanel pnlKunde;
    private javax.swing.JPanel pnlLeftT1;
    private javax.swing.JPanel pnlLeftT2;
    private javax.swing.JPanel pnlLeftT3;
    private javax.swing.JPanel pnlOvrig;
    private javax.swing.JPanel pnlRightT1;
    private javax.swing.JPanel pnlRightT2;
    private javax.swing.JPanel pnlRightT3;
    private javax.swing.JPanel pnlSetting;
    private javax.swing.JPanel pnlTab1;
    private javax.swing.JPanel pnlTab2;
    private javax.swing.JPanel pnlTab3;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JRadioButton radCombo;
    private javax.swing.JRadioButton radEksistkunde;
    private javax.swing.JRadioButton radNykunde;
    private javax.swing.JRadioButton radOrdrenr;
    private javax.swing.JRadioButton rbAllekunder;
    private javax.swing.JRadioButton rbEnkunde;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tblOrdre;
    private javax.swing.JTable tblResultat;
    private javax.swing.JTextField txtAdresse;
    private javax.swing.JTextField txtAdresse1;
    private javax.swing.JTextField txtAntall;
    private javax.swing.JTextField txtAntalltittel;
    private javax.swing.JTextField txtBestnr;
    private javax.swing.JTextField txtEpost;
    private javax.swing.JTextField txtEpost1;
    private javax.swing.JTextField txtEtternavn;
    private javax.swing.JTextField txtEtternavn1;
    private javax.swing.JTextField txtFirma;
    private javax.swing.JTextField txtFirma1;
    private javax.swing.JTextField txtFornavn;
    private javax.swing.JTextField txtFornavn1;
    private javax.swing.JTextArea txtKommentar;
    private javax.swing.JFormattedTextField txtKundenr;
    private javax.swing.JFormattedTextField txtKundenr1;
    private javax.swing.JTextField txtPeriodetittel;
    private javax.swing.JTextField txtPeriodetittel1;
    private javax.swing.JTextField txtPeriodetittel2;
    private javax.swing.JFormattedTextField txtPostnr;
    private javax.swing.JFormattedTextField txtPostnr1;
    private javax.swing.JTextField txtPoststed;
    private javax.swing.JTextField txtPoststed1;
    private javax.swing.JFormattedTextField txtPris;
    private javax.swing.JFormattedTextField txtSok;
    private javax.swing.JFormattedTextField txtTlf;
    private javax.swing.JFormattedTextField txtTlf1;
    private javax.swing.JFormattedTextField txtTlf2;
    private javax.swing.JFormattedTextField txtTlf3;
    private javax.swing.JTextField txtTlfnr;
    private javax.swing.JTextField txtTypetittel;
    // End of variables declaration//GEN-END:variables
}
