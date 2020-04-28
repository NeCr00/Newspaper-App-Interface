/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.vaseis;



import java.sql.*;
import javax.swing.*;

public class dbConnection {
    Connection conn=null;   
    public static Connection ConnectDb() { 
        try{
            Class.forName("com.mysql.jdbc.Driver");         // Εισαγωγή του connector
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "root"); //Eγκαθίδρυση σύνδεσης. Προσοχή καθώς πρέπει να δίνεται τα δικά σας στοιχεία.
            //JOptionPane.showMessageDialog(null, "Connection to MySQL server/CinemaDB Established Successfully!"); //Μήνυμα επιτυχούς σύνδεσης
            return conn;
           }
        catch(Exception e){ // η κλήση της getConnection του DriverManager πετάει throwable για αυτό χρειάζεται η catch
        JOptionPane.showMessageDialog(null,e); // Η οποία θα εκτυπώνει ενα default μήνυμα λάθους.
        return null;
        }
    }
    
}

