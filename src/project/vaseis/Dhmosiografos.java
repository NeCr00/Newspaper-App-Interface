/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.vaseis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jason
 */
public class Dhmosiografos extends javax.swing.JFrame {

    /**
     * Creates new form Arxisuntakths
     */
    String user, Efhm;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String email_arxisuntakth;

    public Dhmosiografos() {
        initComponents();
         this.con =dbConnection.ConnectDb();

    }

    public Dhmosiografos(String user) {
        this.con =dbConnection.ConnectDb();
        initComponents();
        this.username.setText(user);
        this.user = user;
        UpdateEfhmerida();
        this.setTitle("ΔΗΜΟΣΙΟΓΡΑΦΟΣ");
        UpdateArtha();
        UpdateCategory();
        UpdateEkdoth();
        UpdateDhmosiografous();
        getEmailArxisuntakth();
        

    }

    private void getEmailArxisuntakth() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT Arxisuntakths.email from Arxisuntakths INNER JOIN Ergazomenos ON Arxisuntakths.email = Ergazomenos.email where Onoma_Efhmeridas ='" + Efhm + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            email_arxisuntakth = (rs.getString("email"));
            System.out.println(email_arxisuntakth);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void UpdateEkdoth() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT Onoma_Ekdoth from Efhmerida where Onoma_Efhmeridas ='" + Efhm + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            ekdoths.setText(rs.getString("Onoma_Ekdoth"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void UpdateDhmosiografous() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = " SELECT Dhmosiografos.email FROM Dhmosiografos INNER JOIN Ergazomenos on Dhmosiografos.email =Ergazomenos.email  Where Onoma_Efhmeridas ='" + Efhm + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            //System.out.println(sql);
            while (rs.next()) {
                if (!rs.getString("email").equals(user)) {
                    suntakths1.addItem(rs.getString("email"));
                    suntakths2.addItem(rs.getString("email"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void UpdateCategory() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT Onoma,Kwdikos from Kathgoria ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                kathgoria.addItem(rs.getString("Kwdikos") + "." + rs.getString("Onoma"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void UpdateEfhmerida() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql2 = "Select Onoma_Efhmeridas from Ergazomenos where email='" + user + "'";
            System.out.println(sql2);
            PreparedStatement pst2 = con.prepareStatement(sql2);
            rs = pst2.executeQuery();
            rs.next();
            Efhm = rs.getString("Onoma_Efhmeridas");
            Efhmerida.setText(Efhm.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void UpdateArtha() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT path_arthrou FROM Ypovalei  INNER JOIN Arthro ON path_arthrou = path WHERE email_suntakth='" + user + "' AND Katastash_Elegxou='TO BE REVISED'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println(sql);
            while (rs.next()) {

                arthra.addItem(rs.getString("path_arthrou"));
            }

            sql = "SELECT path_arthrou FROM Ypovalei  INNER JOIN Arthro ON path_arthrou = path WHERE email_suntakth='" + user + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println(sql);
            while (rs.next()) {

                arthra2.addItem(rs.getString("path_arthrou"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void UpdateStatusArthrou() {

        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "Delete from Ypovalei where path_arthrou =?";
            pst = con.prepareStatement(sql);
            pst.setString(1, arthra.getSelectedItem().toString());
            pst.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void SubmitArthro() {
        String path_arthrou = path.getText();
        String Titlos = titlos.getText();
        int keyCategory = kathgoria.getSelectedIndex() + 1;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "Insert into Arthro values('" + Titlos + "','" + path_arthrou + "','" + perilhpsh.getText() + "'," + Integer.valueOf(selides.getText()) + "," + null + "," + null + "," + null + ",'" + Efhm + "','" + ekdoths.getText() + "'," + keyCategory + ",'" + email_arxisuntakth + "','" + formatter.format(date) + "','NULL')";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

            sql = "Insert into Ypovalei values ('" + path_arthrou + "','" + user + "','" + formatter.format(date) + "')";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

            if (suntakths1.getSelectedIndex() > 0) {
                sql = "Insert into Ypovalei values ('" + path_arthrou + "','" + suntakths1.getSelectedItem().toString() + "','" + formatter.format(date) + "')";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }

            if (suntakths1.getSelectedIndex() > 0) {
                sql = "Insert into Ypovalei values ('" + path_arthrou + "','" + suntakths2.getSelectedItem().toString() + "','" + formatter.format(date) + "')";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }

            String key = kleidia.getText();
            List<String> result = Arrays.asList(key.split("\\s*,\\s*"));

            int i = result.size();
            System.out.println(result);
            i--;
            while (i >= 0 && !result.isEmpty()) {
                sql = "insert into Leksh_Kleidi values('" + result.get(i) + "','" + path_arthrou + "')";
                System.out.println(sql + i);
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                i--;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void ShowDetailsArthro() {
        String path_arthrou = arthra2.getSelectedItem().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "Select Titlos ,Perilhpsh,Plithos_Selidwn,Katastash_elegxou,Seira_Topothetisis,Ar_Fyllou,Kwdikos_kathgorias,Hmeromhnia_Egkrishs,Sxolia_Dior8wshs from Arthro Where path ='" + path_arthrou + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                titlos2.setText(rs.getString("Titlos"));
                path2.setText(path_arthrou);
                perilhpsh2.setText(rs.getString("Perilhpsh"));
                selides2.setText(rs.getString("Plithos_Selidwn"));
                katastash2.setText(rs.getString("Katastash_elegxou"));

                if (rs.getString("Katastash_elegxou").equals("ACCEPTED")) {
                    fyllo2.setText(rs.getString("Ar_Fyllou"));
                } else {
                    fyllo2.setText("NULL");
                }

            }

            sql = "Select Onoma from Arthro INNER JOIN Kathgoria on kwdikos_kathgorias = kwdikos where path='" + path_arthrou + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                kathgoria2.setText(rs.getString("Onoma"));
            }

            sql = "Select Leksh from leksh_kleidi  INNER JOIN Arthro on path_arthrou=Arthro.path where path_arthrou ='" + path_arthrou + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString("Leksh"));
            }
            kleidia2.setText(result.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Efhmerida = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        arthra = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        titlos = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        perilhpsh = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        kathgoria = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        kleidia = new javax.swing.JTextField();
        logout = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        selides = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ekdoths = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        suntakths1 = new javax.swing.JComboBox<>();
        suntakths2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        arthra2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        path2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        titlos2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        selides2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        fyllo2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        perilhpsh2 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        katastash2 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        kathgoria2 = new javax.swing.JLabel();
        kleidia2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        jLabel14.setText("jLabel14");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Χρήστης :");

        username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Εφημερίδα :");

        Efhmerida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel3.setText("Επιλογή 'Αρθρου  για Αναθεώρηση:");

        arthra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jButton1.setText("Διαγραφή Άρθρου");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Καταχώρηση  Νέου 'Αρθου");

        jLabel7.setText("Path :");

        jLabel8.setText("Τίτλος :");

        titlos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titlosActionPerformed(evt);
            }
        });

        jLabel9.setText("Περίληψη :");

        perilhpsh.setColumns(20);
        perilhpsh.setRows(5);
        jScrollPane2.setViewportView(perilhpsh);

        jLabel12.setText("Καρηγορια :");

        jLabel13.setText("Λέξεις κλειδιά :");

        logout.setText("Log out");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        jButton2.setText("Υποβολή Άρθρου");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel17.setText("Σελίδες :");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setText("Εκδότης :");

        ekdoths.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel20.setText("Συντάκτης Νο1 :");

        jLabel21.setText("Συντάκτης Νο2 :");

        suntakths1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        suntakths2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel4.setText("Διαγράφει το Επιλεγμένο άρθρο και Απαιτείται η  ξανά η διαδικασία καταχώρης  νέου 'Αρθρου !");

        jLabel5.setText("Προσοχή !");

        jLabel10.setText("Επιλογή Άρθρου :");

        arthra2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("Προβολή Στοιχείων Αρθρου ");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setText("Άρθρα Για Αναθεώρηση");

        jButton3.setText("Προβολή Στοιχείων");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel16.setText("Path :");

        jLabel19.setText("Τίτλος :");

        jLabel22.setText("Σελίδες :");

        jLabel23.setText("Αρ. Φύλλου :");

        jLabel25.setText("Περίληψη :");

        jLabel26.setText("Κατάσταση 'Αρθου :");

        katastash2.setText("NULL");

        jLabel27.setText("Λέξεις κλειδία :");

        jLabel28.setText("Κατηγορία :");

        jButton4.setText("Refresh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(kleidia, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(102, 102, 102)
                                                        .addComponent(jButton2))
                                                    .addComponent(kathgoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(selides, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(titlos, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(suntakths2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(suntakths1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel6))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(391, 391, 391)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(arthra, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel16))))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(arthra2, 0, 331, Short.MAX_VALUE)
                                .addComponent(path2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(titlos2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(selides2)
                            .addComponent(fyllo2)
                            .addComponent(perilhpsh2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(katastash2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kleidia2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kathgoria2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ekdoths, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(578, 578, 578)
                                        .addComponent(jLabel11))
                                    .addComponent(Efhmerida, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(Efhmerida, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel11))
                            .addComponent(ekdoths, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logout)
                        .addGap(28, 28, 28)
                        .addComponent(jButton4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(arthra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(arthra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(path2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(titlos2))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(titlos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(selides2))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(fyllo2))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(perilhpsh2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(suntakths1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(katastash2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(suntakths2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(kathgoria2))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(selides, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(kathgoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(kleidia2))
                        .addGap(35, 35, 35)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(kleidia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        UpdateStatusArthrou();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SubmitArthro();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void titlosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titlosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titlosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ShowDetailsArthro();    // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        this.UpdateEfhmerida();
        this.UpdateArtha();
        this.UpdateCategory();
        this.UpdateEkdoth();
        this.UpdateDhmosiografous();
        this.getEmailArxisuntakth();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Authentication Auth = new Authentication();
        Auth.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

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
            java.util.logging.Logger.getLogger(Dhmosiografos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dhmosiografos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dhmosiografos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dhmosiografos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dhmosiografos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Efhmerida;
    private javax.swing.JComboBox<String> arthra;
    private javax.swing.JComboBox<String> arthra2;
    private javax.swing.JLabel ekdoths;
    private javax.swing.JLabel fyllo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel katastash2;
    private javax.swing.JComboBox<String> kathgoria;
    private javax.swing.JLabel kathgoria2;
    private javax.swing.JTextField kleidia;
    private javax.swing.JLabel kleidia2;
    private javax.swing.JButton logout;
    private javax.swing.JTextField path;
    private javax.swing.JLabel path2;
    private javax.swing.JTextArea perilhpsh;
    private javax.swing.JLabel perilhpsh2;
    private javax.swing.JTextField selides;
    private javax.swing.JLabel selides2;
    private javax.swing.JComboBox<String> suntakths1;
    private javax.swing.JComboBox<String> suntakths2;
    private javax.swing.JTextField titlos;
    private javax.swing.JLabel titlos2;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
