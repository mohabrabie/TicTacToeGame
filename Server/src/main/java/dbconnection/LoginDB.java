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

    static Connection conn;
    private DBMS db ;
    private Player player;

    public boolean isExist(Player p,boolean loginOrforget) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        db = new DBMS();
        try {
            conn = db.Connect();
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
                pins.close();
                conn.close();
                db.closeConnection();
                return true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("catch login DB !!");
        }
        conn.close();
        db.closeConnection();
        return false;
    }
    public boolean updateStatus(int flag) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        db = new DBMS();
        conn = db.Connect();
        player = getPlayerData();
        PreparedStatement pins = conn.prepareStatement("UPDATE player SET status = ? WHERE email = ?");
        pins.setInt(1,flag);
        pins.setString(2,player.getEmail());

        int status = pins.executeUpdate();

        if(status != 0)
        {
            //player = getPlayerData();
            System.out.println("status updated");
            conn.close();
            db.closeConnection();
            return true;
        }
        pins.close();
        conn.close();
        db.closeConnection();
        return false;
    }
    public Player getPlayerData()
    {
        return player;
    }
}
