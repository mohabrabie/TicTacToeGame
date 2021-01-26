/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Islam
 */
public class SignUpDB {

    static Connection conn;
    private DBMS db;
    private Player player;

    public boolean isExist(Player p) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        db = new DBMS();
        try {
            conn = db.Connect();
            PreparedStatement pins = conn.prepareStatement("Select * From player where email=?");
            pins.setString(1,p.getEmail());
            
            
            ResultSet rs = pins.executeQuery();
            
            // loop through the result set
            if(rs.next())
            {
                player = new Player(rs.getInt("playerID"),rs.getString("name")
                            , rs.getString("email"),rs.getString("password")
                            ,rs.getInt("main_score"),rs.getInt("status"),rs.getString("avatar"));
                return true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("catch signupDB !!");
        }
        //this.closeConnection();
        return false;
    }

    public boolean newPlayer(Player p) throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            PreparedStatement pins = conn.prepareStatement("INSERT INTO player(name,email,password,main_score,status,avatar) VALUES(?,?,?,?,?,?) ");
            pins.setString(1,p.getName());
            pins.setString(2,p.getEmail());
            pins.setString(3,p.getPassword());
            pins.setInt(4,0);
            pins.setInt(5,1);
            pins.setString(6,"1.png");

            int status = pins.executeUpdate();
            
            if(status != 0)
            {
                conn.close();
                db.closeConnection();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        db.closeConnection();
        return false;
    }
    
    public boolean updatePlayer(Player p) throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            PreparedStatement pins = conn.prepareStatement("UPDATE player SET name = ?,password = ? WHERE email = ?");
            pins.setString(1,p.getName());
            pins.setString(2,p.getPassword());
            pins.setString(3,p.getEmail());

            int status = pins.executeUpdate();
            
            if(status != 0)
            {
                conn.close();
                db.closeConnection();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        db.closeConnection();
        return false;
    }
    
    public Player getPlayerData()
     {           
           return player;
     }
    
}
