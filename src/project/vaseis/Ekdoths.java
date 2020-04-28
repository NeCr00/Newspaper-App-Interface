/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.vaseis;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Jason
 */
public class Ekdoths extends javax.swing.JFrame {

    /**
     * Creates new form Ekdoths
     *
     * @param user
     */
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String user, Onoma_Ekdoth;

    public Ekdoths() {
        initComponents();
       this.con =dbConnection.ConnectDb();

    }

    public Ekdoths(String user, String Onoma_Ekdoth) {
        this.con =dbConnection.ConnectDb();
        initComponents();
        combobox(user);
        this.username.setText(user);
        SetDhmosiografousCombobox();
        this.Onoma_Ekdoth = Onoma_Ekdoth;
          this.setTitle("ΕΚΔΟΤΗΣ");
    

    }

    private void combobox(String user) {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "SELECT Onoma_Efhmeridas FROM Efhmerida INNER JOIN Ekdoths ON Onoma_Ek = Onoma_Ekdoth WHERE Ekdoths.email='" + user + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            //System.out.println(sql);
            while (rs.next()) {

                Efhmerides.addItem(rs.getString("Onoma_Efhmeridas"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void SetCopies() {
        int Fyllo = Integer.valueOf(ar_fyllou.getText());
        int Antitypa = Integer.valueOf(ar_antitypou.getText());
        String Efhmerida = Efhmerides.getSelectedItem().toString();
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "update Fyllo set Antitypa =? where Onoma_Efhmeridas =? AND Ar_Fyllou =?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, Antitypa);
            pst.setString(2, Efhmerida);
            pst.setInt(3, Fyllo);
            pst.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void SetDhmosiografousCombobox() {
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = " SELECT Dhmosiografos.email FROM Dhmosiografos INNER JOIN Ergazomenos on Dhmosiografos.email =Ergazomenos.email  Where Onoma_Efhmeridas ='" + Efhmerides.getSelectedItem() + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            //System.out.println(sql);
            while (rs.next()) {

                dhmosiografoi.addItem(rs.getString("email"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void SetArxisuntakth() {

        String Arxisuntakths = dhmosiografoi.getSelectedItem().toString();
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "insert into Arxisuntakths values('" + Arxisuntakths + "','" + Onoma_Ekdoth + "')";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void printStats() {

        
        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root");
            String sql = "Select sum(Antitypa) as Antitypa, sum(Den_poulithikan) as Den_poulithikan from Fyllo Where Onoma_Ekdoth ='"+Onoma_Ekdoth+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println(rs);
            rs.next();
            int fylla =Integer.valueOf(rs.getString("Antitypa"));
            int den_poulithikan =Integer.valueOf(rs.getString("Den_poulithikan"));
            jLabel14.setText(Integer.toString(fylla-den_poulithikan));
            jLabel15.setText(rs.getString("Den_poulithikan"));
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ar_fyllou = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ar_antitypou = new javax.swing.JTextField();
        apply1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dhmosiografoi = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        apply2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        emfanise = new javax.swing.JButton();
        pwlhseis = new javax.swing.JLabel();
        Efhmerides = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Χρήστης :");

        username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel3.setText("Επέλεξε μία Εφημερίδα");

        edit.setText("Επεξεργασία");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        jLabel5.setText("Επεξεργασία Στοιχείων Εφημερίδας ");

        jLabel6.setText("Αριθμός Φύλλου");

        jLabel7.setText("Αριθμός Αντίτυπων");

        apply1.setText("Εφαρμογή");
        apply1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apply1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Καθορισμός Αριθμού Αντίτυπων Φύλλου Εφημερίδας");

        jLabel9.setText("Όρισε Αρχισυντάκτη στην Εφημερίδα ");

        jLabel10.setText("Επιλογή Διαθέσιμου Αρχισυντάκτη:");

        apply2.setText("Εφαρμογή");
        apply2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apply2ActionPerformed(evt);
            }
        });

        jLabel11.setText("Εμφάνιση πωλήσεων  Φύλλων");

        emfanise.setText("Εμφάνισε");
        emfanise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emfaniseActionPerformed(evt);
            }
        });

        jLabel12.setText("Φύλλα που πουληθηκαν :");

        jLabel13.setText("Φύλλα που επιστράφηκαν :");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pwlhseis, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ar_fyllou, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(ar_antitypou, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Efhmerides, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 340, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(apply1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apply2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emfanise, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(dhmosiografoi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel12)
                        .addGap(116, 116, 116)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(226, 226, 226)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(44, 44, 44))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(edit))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ar_fyllou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(ar_antitypou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apply1))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(dhmosiografoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apply2))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pwlhseis, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emfanise)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(Efhmerides, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        Edit Edit = new Edit(this.username.getText(), Efhmerides.getSelectedItem().toString());
        Edit.setVisible(true);
        
    }//GEN-LAST:event_editActionPerformed

    private void apply2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apply2ActionPerformed
        // TODO add your handling code here:
        SetArxisuntakth();
    }//GEN-LAST:event_apply2ActionPerformed

    private void emfaniseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emfaniseActionPerformed
        // TODO add your handling code here:
        printStats();
    }//GEN-LAST:event_emfaniseActionPerformed

    private void apply1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apply1ActionPerformed

        SetCopies();
    }//GEN-LAST:event_apply1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Authentication Auth = new Authentication();
        Auth.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         
    this.con =dbConnection.ConnectDb();
      
        this.combobox(user);
       
        SetDhmosiografousCombobox();
       
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Ekdoths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ekdoths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ekdoths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ekdoths.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Ekdoths().setVisible(true);
                //new Edit().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Efhmerides;
    private javax.swing.JButton apply1;
    private javax.swing.JButton apply2;
    private javax.swing.JTextField ar_antitypou;
    private javax.swing.JTextField ar_fyllou;
    private javax.swing.JComboBox<String> dhmosiografoi;
    private javax.swing.JButton edit;
    private javax.swing.JButton emfanise;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel pwlhseis;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
