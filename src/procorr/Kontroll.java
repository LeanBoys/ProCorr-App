/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procorr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Kontroll {
    public static Kontroll kontroll = new Kontroll();
    Connection conn = null;
    Statement stat;
    PreparedStatement prepStat;    //Hashed pass contains single quote, can't do statement
    ResultSet resultat;
        
    private Kontroll(){};
    
    public void startConnection() throws Exception {
    String DBbruker = "root";
    String DBpassord = "root";
    try {
        String url = "jdbc:mysql://localhost:3306/procorr";
        conn = DriverManager.getConnection(url,DBbruker,DBpassord);
        stat = conn.createStatement();
        } catch (SQLException e) {
            throw new Exception("Kan ikke koble til databasen");
        }
    }
    
    public void loginCorrect(String username, char[] password) throws Exception {
        String pass = new String(password);
        String encPass = getSHA512(pass);
        prepStat = conn.prepareStatement("select * from bruker where (brukernavn=? AND passord=?)");
        prepStat.setString(1, username);
        prepStat.setString(2, encPass);
        resultat = prepStat.executeQuery();

        if(!resultat.next()) {
            throw new Exception("Feil login");
        }       
    } 
    
    //Lag encrypted pass   
    public static String getSHA512(String pass) {
        String salt = "procorr_2017";
        for (int i = 0; i < 100000; i++) {
            pass = SHA512once(pass+salt);
        }
        return SHA512once(pass);
    }
    
    private static String SHA512once(String toHash) {
        MessageDigest md;
        String message = toHash;
        try {
            md= MessageDigest.getInstance("SHA-512");

            md.update(message.getBytes());
            byte[] mb = md.digest();
            String out = "";
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
            return(out);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return "error";
    }
        
    public ResultSet hentOrdre (String type, String criteria) throws Exception {
       resultat = null;
       if (type.equals("status")) {
           //must select count by itself for mysql to not group the results
           prepStat = conn.prepareStatement("select (select count(*) from bestilling where status=?), bestillingnr, kundenr from bestilling where status=?");
           prepStat.setString(1, criteria);
           prepStat.setString(2, criteria);
           resultat = prepStat.executeQuery();
       } else {
           int nr = Integer.parseInt(criteria);
           System.out.println(criteria);
           prepStat = conn.prepareStatement("select (select count(*) from bestilling where bestillingnr=?), bestillingnr, kundenr from bestilling where bestillingnr=?");
           prepStat.setInt(1, nr);
           prepStat.setInt(2, nr);
           resultat = prepStat.executeQuery();
       }
       return resultat;
    }
    
    public ResultSet enOrdre (String ordreNr) throws Exception{
        resultat=null;
        try{
            resultat= stat.executeQuery("select * from bestilling where bestillingnr="+ordreNr);
        }catch (SQLException e){
            throw new Exception("Finner ikke valgt ordre"+e.getMessage());
        }
        return resultat;
    }
    
    public void lagre(int bestnr, int tjeneste, String status, String pris, String moteDato, String utforDato, String kommentar) throws Exception{
        prepStat = conn.prepareStatement("UPDATE bestilling "
                + "SET tjenestenr=?, status=?, pris=?, motedato=?, utforelsedato=?, kommentar=? "
                + "WHERE bestillingnr=?;");
        prepStat.setInt(1, tjeneste);
        prepStat.setString(2, status);
        prepStat.setString(3, pris);
        prepStat.setString(4, moteDato);
        prepStat.setString(5, utforDato);
        prepStat.setString(6, kommentar);
        prepStat.setInt(7, bestnr);
        prepStat.executeUpdate();
    }
    
    public ResultSet getKundenr(int kundenr) throws Exception{
        prepStat = conn.prepareStatement("select * from kunde where kundenr=?");
        prepStat.setInt(1, kundenr);
        resultat = prepStat.executeQuery();
        return resultat;
    }
    
    public ResultSet getKundetlf(String tlf)throws Exception{
        prepStat = conn.prepareStatement("select * from kunde where tlf=?");
        prepStat.setString(1, tlf);
        resultat = prepStat.executeQuery();
        return resultat;    
    }
    
    public ResultSet hentTjenester()throws Exception{
        prepStat = conn.prepareStatement("select tjeneste from tjeneste");
        resultat = prepStat.executeQuery();
        return resultat;
    }
    
    public int getMaxBest() throws Exception{
        int max = 0;
        prepStat = conn.prepareStatement("select max(bestillingnr) from bestilling");
        resultat = prepStat.executeQuery();
        if (resultat.next()){
            max = resultat.getInt(1);
        }
        return max;
    }
    
    public int getMaxKunde() throws Exception {
        int max=0;
        prepStat = conn.prepareStatement("select max(kundenr) from kunde");
        resultat = prepStat.executeQuery();
        if (resultat.next()) {
            max=resultat.getInt(1);
        }
        return max;
    }
    
    public void nyKunde(int kundenr, String fornavn, String etternavn, String firma, int postnr, String adresse, String tlf, String epost) throws Exception{
        prepStat = conn.prepareStatement("insert into kunde (kundenr, fornavn, etternavn, firma, postnr, adresse, tlf, ePost) "
                + "values (?, ?,?,?,?,?,?,?)");
        prepStat.setInt(1, kundenr);
        prepStat.setString(2, fornavn);
        prepStat.setString(3, etternavn);
        prepStat.setString(4, firma);
        prepStat.setInt(5, postnr);
        prepStat.setString(6, adresse);
        prepStat.setString(7, tlf);
        prepStat.setString(8, epost);
        
        prepStat.execute();
    }
    public void nyBest(int bestnr, int kundenr, int tjeneste, String status, String pris, String kommentar, String mDato, String uDato) throws Exception{
        prepStat = conn.prepareStatement("insert into bestilling "
                + "values (?,?,?,?,?,?,?,?)");
        prepStat.setInt(1, bestnr);
        prepStat.setInt(2, kundenr);
        prepStat.setInt(3, tjeneste);
        prepStat.setString(4, status);
        prepStat.setString(5, pris);
        prepStat.setString(6, kommentar);
        prepStat.setString(7, mDato);
        prepStat.setString(8, uDato);
        
        prepStat.execute();
    }
    
    public ResultSet getPoststed(int postnr) throws Exception {
        prepStat = conn.prepareStatement("select poststed from postkatalog where postnr=?");
        prepStat.setInt(1, postnr);
        resultat = prepStat.executeQuery();
        return resultat; 
    }
    
    public void fyllTabellen(JTable table, String Query) {
        try {
            resultat = stat.executeQuery(Query);

            while(table.getRowCount() > 0) {
                System.out.println("poop");
                ((DefaultTableModel) table.getModel()).removeRow(0);
            }
            
            int columns = resultat.getMetaData().getColumnCount();
            while(resultat.next()) {  
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {  
                    row[i - 1] = resultat.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(resultat.getRow()-1,row);
            }
        }
        catch(SQLException e) {
        }
    }
        
    public void endreKunde(String fornavn, String etternavn, String adresse, int postnr, String tlf, String epost, String firma, int kundenr) throws Exception{
        prepStat = conn.prepareStatement("UPDATE kunde "
                + "SET fornavn=?, etternavn=?, adresse=?, postnr=?, tlf=?, ePost=?, firma=? "
                + "WHERE kundenr=?;");
        prepStat.setString(1, fornavn);
        prepStat.setString(2, etternavn);
        prepStat.setString(3, adresse);
        prepStat.setInt(4, postnr);
        prepStat.setString(5, tlf);
        prepStat.setString(6, epost);
        prepStat.setString(7, firma);
        prepStat.setInt(8, kundenr);
        prepStat.executeUpdate();
    }
}
