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
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jason
 */
public class Arxisuntakths extends javax.swing.JFrame {

    String user, Efhm;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public Arxisuntakths() {
        initComponents();
        this.con = dbConnection.ConnectDb();

    }

    public Arxisuntakths(String user) {
        this.con = dbConnection.ConnectDb();
        initComponents();
        this.username.setText(user);
        this.user = user;
        UpdateEfhmerida();
        this.setTitle("ΑΡΧΙΣΥΝΤΑΚΤΗΣ");
        combobox();
        UpdateCategory();
        UpdateEkdoth();
        UpdateDhmosiografous();
        updateArFyllou();

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
                kathgoria1.addItem(rs.getString("Kwdikos") + "." + rs.getString("Onoma"));
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

    private void combobox() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT path_arthrou FROM Ypovalei INNER JOIN Ergazomenos ON email_suntakth = Ergazomenos.email WHERE Ergazomenos.Onoma_Efhmeridas='" + Efhm + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println(sql);
            while (rs.next()) {

                artha.addItem(rs.getString("path_arthrou"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void updateArFyllou(){
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT Ar_Fyllou FROM Fyllo  WHERE Onoma_Efhmeridas='" + Efhm + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println(sql);
            while (rs.next()) {

                Arfyllou.addItem(rs.getString("Ar_Fyllou"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    

    private void UpdateStatusArthrou() {
        String path = artha.getSelectedItem().toString();
        String status;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            if (Accept.isSelected()) {
                status = "ACCEPTED";

            } else if (revise.isSelected()) {
                status = "TO BE REVISED";
            } else {

                status = "REJECTED";
            }

            String sql = "update Arthro set Katastash_Elegxou=?,Seira_Topothetisis=?,Sxolia_Dior8wshs=? ,Hmeromhnia_Egkrishs=?,Ar_Fyllou=?  Where path='" + artha.getSelectedItem().toString() + "'";
            pst = con.prepareStatement(sql);
            pst.setString(1, status);

            if (seira1.getText().equals("NULL")) {
                pst.setNull(2, java.sql.Types.INTEGER);
            } else {
                pst.setInt(2, Integer.valueOf(seira1.getText()));
            }

            pst.setString(3, comment.getText().toString());

            if (Accept.isSelected()) {
                pst.setString(4, formatter.format(date).toString());
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }
            System.out.println(formatter.format(date).toString());
            pst.setInt(5,Integer.valueOf(Arfyllou.getSelectedItem().toString()));
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
            String sql = "Insert into Arthro values('" + Titlos + "','" + path_arthrou + "','" + perilhpsh.getText() + "'," + Integer.valueOf(selides.getText()) + ",'ACCEPTED'," + Integer.valueOf(seira2.getText()) + "," + Integer.valueOf(Fyllo.getText()) + ",'" + Efhm + "','" + ekdoths.getText() + "'," + keyCategory + ",'" + user + "','" + formatter.format(date) + "','NULL')";
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

    private void SubmitCategory() {
        int parentcat;
        String category = newcat.getText();

        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");

            String sql = "Insert into Kathgoria values(?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setNull(1, java.sql.Types.INTEGER);
            pst.setString(2, category);
            pst.setString(3, perigrafh.getText());
            if (kathgoria1.getSelectedIndex() > 0) {
                parentcat = kathgoria1.getSelectedIndex();
                pst.setInt(4, parentcat);
            } else {
                pst.setNull(4, java.sql.Types.INTEGER);
            }

            pst.executeUpdate();

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

        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Efhmerida = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        artha = new javax.swing.JComboBox<>();
        Accept = new javax.swing.JCheckBox();
        revise = new javax.swing.JCheckBox();
        reject = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        comment = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        seira1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        titlos = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        perilhpsh = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        seira2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        kathgoria = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        kleidia = new javax.swing.JTextField();
        logout = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        newcat = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Fyllo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        selides = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ekdoths = new javax.swing.JLabel();
        kathgoria1 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        perigrafh = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        suntakths1 = new javax.swing.JComboBox<>();
        suntakths2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        Arfyllou = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Χρήστης :");

        username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Εφημερίδα :");

        Efhmerida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel3.setText("Επιλογή 'Αρθρου :");

        Accept.setText("Accepted");

        revise.setText("Revise");

        reject.setText("Reject");

        jButton1.setText("Καταχώρηση");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        comment.setColumns(20);
        comment.setRows(5);
        jScrollPane1.setViewportView(comment);

        jLabel4.setText("Σχόλια ");

        jLabel5.setText("Σειρά Τοποθέτησης :");

        seira1.setText("NULL");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Καταχώρηση 'Αρθου");

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

        jLabel10.setText("Σειρά :");

        jLabel11.setText("Αρ. Φύλλου :");

        jLabel12.setText("Καρηγορια :");

        jLabel13.setText("Λέξεις κλειδιά :");

        logout.setText("Log out");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setText("Δημιουργια Νεας Κατηγορίας");

        jLabel15.setText("Νέα κατηγορία :");

        jLabel16.setText("Γονική Κατηγορία :");

        jButton2.setText("Υποβολή Άρθρου");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Υποβολή Κατηγορίας");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Σελίδες :");

        jLabel18.setText("Εκδότης :");

        kathgoria1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel19.setText("Περιγραφή :");

        perigrafh.setColumns(20);
        perigrafh.setRows(5);
        jScrollPane3.setViewportView(perigrafh);

        jLabel20.setText("Συντάκτης Νο1 :");

        jLabel21.setText("Συντάκτης Νο2 :");

        suntakths1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        suntakths2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jButton4.setText("Refresh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel22.setText("Αρ. Φύλλου :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(Efhmerida, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(37, 37, 37)
                                .addComponent(artha, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel4)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12))
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel18))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(titlos, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ekdoths, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(selides, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(seira2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(78, 78, 78)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel21)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(suntakths2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel20)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(suntakths1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(270, 270, 270)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel16)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(70, 70, 70)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(newcat, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(kathgoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(253, 253, 253)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton3)
                                                .addGap(55, 55, 55))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(kleidia, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(102, 102, 102)
                                                .addComponent(jButton2))
                                            .addComponent(Fyllo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(kathgoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Accept)
                                    .addComponent(revise)
                                    .addComponent(reject))
                                .addGap(173, 173, 173)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel22))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(seira1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                    .addComponent(Arfyllou, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(170, 170, 170)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(177, 177, 177))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                        .addGap(66, 66, 66))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(Efhmerida, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(artha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Accept)
                            .addComponent(jLabel5)
                            .addComponent(seira1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(revise)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reject))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(Arfyllou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(logout)
                        .addGap(37, 37, 37)
                        .addComponent(jButton4)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel14)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(newcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(kathgoria1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel19)))
                                .addGap(45, 45, 45)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ekdoths, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(titlos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selides, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel20)
                                    .addComponent(suntakths1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(seira2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)
                                    .addComponent(suntakths2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(Fyllo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(kathgoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(kleidia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2))))))
                .addContainerGap(76, Short.MAX_VALUE))
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        SubmitCategory();
        perigrafh.setText(null);
        newcat.setText(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void titlosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titlosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titlosActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Authentication Auth = new Authentication();
        Auth.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

     

        this.UpdateEfhmerida();
        this.combobox();
        this.UpdateCategory();
        this.UpdateEkdoth();
        this.UpdateDhmosiografous();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Arxisuntakths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arxisuntakths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arxisuntakths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arxisuntakths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Arxisuntakths().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Accept;
    private javax.swing.JComboBox<String> Arfyllou;
    private javax.swing.JLabel Efhmerida;
    private javax.swing.JTextField Fyllo;
    private javax.swing.JComboBox<String> artha;
    private javax.swing.JTextArea comment;
    private javax.swing.JLabel ekdoths;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> kathgoria;
    private javax.swing.JComboBox<String> kathgoria1;
    private javax.swing.JTextField kleidia;
    private javax.swing.JButton logout;
    private javax.swing.JTextField newcat;
    private javax.swing.JTextField path;
    private javax.swing.JTextArea perigrafh;
    private javax.swing.JTextArea perilhpsh;
    private javax.swing.JCheckBox reject;
    private javax.swing.JCheckBox revise;
    private javax.swing.JTextField seira1;
    private javax.swing.JTextField seira2;
    private javax.swing.JTextField selides;
    private javax.swing.JComboBox<String> suntakths1;
    private javax.swing.JComboBox<String> suntakths2;
    private javax.swing.JTextField titlos;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
