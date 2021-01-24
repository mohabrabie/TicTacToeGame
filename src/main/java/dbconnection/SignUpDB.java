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
    static Connection conn = null;
    List<Player> Data = new ArrayList();
    static String url = "jdbc:sqlite:src\\main\\java\\dbconnection\\TicTacToeDB.db";
    DBMS db = new DBMS();
    ResultSet rs,rsgame;
    PreparedStatement ps,psgame;
    Player player;
     //this method to connect to DB only
    public Connection Connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        //../icons/icon9.png
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(conn);
        return conn;
        
    }
    
    public void closeConnection()
    {
        try {
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public boolean isExist(Player p) throws ClassNotFoundException, IllegalAccessException, InstantiationException {     
        try {
            conn = this.Connect();
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

    public boolean newPlayer(Player p) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            PreparedStatement pins = conn.prepareStatement("INSERT INTO player(name,email,password) VALUES(?,?,?) ");
            pins.setString(1,p.getName());
            pins.setString(2,p.getEmail());
            pins.setString(3,p.getPassword());
       
            int status = pins.executeUpdate();
            
            if(status != 0)
            {
                //this.closeConnection();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeConnection();
        return false;
    }
    public Player getPlayerData()
     {           
           return player;
     }
    
}
