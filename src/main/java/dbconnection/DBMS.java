/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;



import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Aya Abdulsamie
 */
public class DBMS {
    static Connection conn = null;
    static String url = "jdbc:sqlite:src\\main\\java\\dbconnection\\TicTacToeDB.db";

    ResultSet rs,rsgame;
    PreparedStatement ps,psgame;
     //this method to connect to DB only
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
    //this method to select 1 player on future
    public ArrayList<Player> SelectPlayers() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        ArrayList<Player> Data = new ArrayList();
        String sql = "select * from player";
        Statement stmt = null;
        try {
            Connection conn = this.Connect();
            stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                Player p = new Player(rs.getInt("playerID"),rs.getString("name")
                , rs.getString("email"),rs.getString("password")
                ,rs.getInt("main_score"),rs.getInt("status"),rs.getString("avatar"));
                Data.add(p);
                System.out.println(rs.getInt("playerID") + "\t" +
                rs.getString("name") + "\t" +
                rs.getString("email") + "\t" +
                rs.getString("password") + "\t" +
                rs.getInt("main_score") + "\t" +
                rs.getString("status") + "\t" +
                rs.getString("avatar") + "\t" 
                );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
                stmt.close();
                this.closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return Data;
    }
    public ObservableList<ListUsers> ViewForServer() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        ObservableList<ListUsers> Data = FXCollections.observableArrayList();
        ListUsers user;
        Statement stmt = null;
        String sql = "select * from player";
        try{
            // loop through the result set
            Connection conn = this.Connect();
            stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                user = new ListUsers(rs.getString("name"), rs.getString("email"),
                        rs.getInt("main_score"),rs.getInt("status"));
                if(rs.getInt("status") == 1)
                {//..\icons\icon9.png
                    user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/on.png"))));
                }else
                {
                    user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/off.png"))));
                }
                user.setAvat(new ImageView(new Image(getClass().getResourceAsStream("/icons/"+rs.getString("avatar")))));
                Data.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.closeConnection();
        }
        return Data;
    }
    public void addNewPlayer(String pName,String pEmail,String password,int pScore,String pStatus)
    {
        try {
            PreparedStatement pins = conn.prepareStatement("INSERT INTO player(name,email,password,main_score,status) VALUES(?,?,?,?,?) ");
            pins.setString(1,pName);
            pins.setString(2,pEmail);
            pins.setString(3,password);
            pins.setInt(4,pScore);
            pins.setString(5,pStatus);
       
            int status = pins.executeUpdate();
            rs = ps.executeQuery();
            
            if(status != 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Account has been created successfully");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error creating account");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updatePlayerData(String pName,String pEmail,String password,int pScore,String pStatus,String pAvatar)
    {
        try {
        PreparedStatement pup = conn.prepareStatement("UPDATE player  SET name = ? , password = ?,main_score = ? ,status = ?,avatar=? WHERE email = ?");
        pup.setString(1,pName);
        pup.setString(2,password);
        pup.setInt(3,pScore);
        pup.setString(4,pStatus);
        pup.setString(5,pAvatar);
        pup.setString(6,pEmail);
        
        int status = pup.executeUpdate();
        rs = ps.executeQuery();
        
        if(status != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Data updated Succefully");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Error updating data");
            alert.showAndWait();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletePlayer(String pEmail)
    {
        try {
            PreparedStatement pdel = conn.prepareStatement("DELETE FROM playre WHERE email=?");
            pdel.setString(1,pEmail);
            
            int status = pdel.executeUpdate();
            rs = ps.executeQuery();
            
            if(status != 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Account has been deleted succefully");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error deleting the account");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addNewGame(int p1_id,int p2_id,int p1_score,int p2_score)
    {
        try {
            PreparedStatement pins = conn.prepareStatement("INSERT INTO games(play1ID,player2ID,playr1Score,player2Score) VALUES(?,?,?,?) ");
            pins.setInt(1,p1_id);
            pins.setInt(2,p2_id);
            pins.setInt(3,p1_score);
            pins.setInt(4,p2_score);
       
            int status = pins.executeUpdate();
            rsgame = psgame.executeQuery();
            
            if(status != 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Start playing!");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Error establishing new game");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateGameResults(int gID,int p1_score,int p2_score)
    {
        try {
            //play1ID,player2ID,playr1Score,player2Score
        PreparedStatement pup = conn.prepareStatement("UPDATE games SET playr1Score = ? ,player2Score = ? WHERE gameID = ?");
        pup.setInt(1,gID);
        pup.setInt(2,p1_score);
        pup.setInt(3,p2_score);
  
        int status = pup.executeUpdate();
        rsgame = psgame.executeQuery();
        
        if(status != 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Game's scores was Updated Succefully");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Error updating game's scores data");
            alert.showAndWait();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
