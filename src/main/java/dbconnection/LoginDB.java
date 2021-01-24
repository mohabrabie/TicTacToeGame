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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aya Abdulsamie
 */
public class LoginDB {
    static Connection conn = null;
    static String url = "jdbc:sqlite:src\\main\\java\\dbconnection\\TicTacToeDB.db";

    ResultSet rs;
    PreparedStatement ps;
    Player player;
     //connecting to DB 
    public Connection Connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(conn);
        return conn;
        
    }
     public boolean isExist(Player p,boolean loginOrforget) throws ClassNotFoundException, IllegalAccessException, InstantiationException {     
        try {
            conn = this.Connect();
             PreparedStatement pins;
            if(loginOrforget){
                    pins = conn.prepareStatement("Select * From player where email=? and password=?");
                   pins.setString(1,p.getEmail());
                   pins.setString(2,p.getPassword());
            }
            else{
                pins = conn.prepareStatement("Select * From player where email=?");
                 pins.setString(1,p.getEmail());
            }
       
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
            System.out.println("catch login DB !!");
        }
        return false;
    }
     public Player getPlayerData()
     {           
           return player;
     }
    public void closeConnection()
    {
        try {
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
