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
    public static int seq = 0;
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

    public boolean addNewGame(int p1_id,int p2_id,int p1_score,int p2_score)
    {
        try {
            Connection conn = this.Connect();
            PreparedStatement pins = conn.prepareStatement("INSERT INTO game(p1_ID,p2_ID,p1_score,p2_score) VALUES(?,?,?,?) ");
            pins.setInt(1,p1_id);
            pins.setInt(2,p2_id);
            pins.setInt(3,p1_score);
            pins.setInt(4,p2_score);

            int status = pins.executeUpdate();
            if(status != 0)
            {
                conn.close();
                pins.close();
                System.out.println("game added");
                return true;
            }else{
                conn.close();
                pins.close();
            }
        } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateGameResults(int gID,int p1_score,int p2_score)
    {
        try {
            Connection conn = this.Connect();
            PreparedStatement pins = conn.prepareStatement("UPDATE game SET p1_score = ? ,p2_score = ? WHERE gameID = ?");
            pins.setInt(1,p1_score);
            pins.setInt(2,p2_score);
            pins.setInt(3,gID);

            int status = pins.executeUpdate();
            if(status != 0)
            {
                conn.close();
                pins.close();
                return true;
            }else{
                conn.close();
                pins.close();
            }
            } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        return false;
    }

    public Game SelectGame(int player1Id,int player2Id)
    {
        try {
            Connection conn = this.Connect();
            PreparedStatement pins;
            pins = conn.prepareStatement("Select * From game where p1_ID=? and p2_ID=?");
            pins.setInt(1, player1Id);
            pins.setInt(2, player2Id);

            ResultSet rs1 = pins.executeQuery();
            // loop through the result set
            if(rs1.next())
            {
                Game game= new Game(rs1.getInt("gameID"),rs1.getInt("p1_ID")
                        , rs1.getInt("p2_ID"),rs1.getInt("p1_score")
                        ,rs1.getInt("p2_score"));
                conn.close();
                pins.close();
                System.out.println("in order");
                return game;
            }
            pins = conn.prepareStatement("Select * From game where p1_ID=? and p2_ID=?");
            pins.setInt(1, player2Id);
            pins.setInt(2, player1Id);
            rs1 = pins.executeQuery();

            if(rs1.next())
            {
                Game game= new Game(rs1.getInt("gameID"),rs1.getInt("p1_ID")
                        , rs1.getInt("p2_ID"),rs1.getInt("p1_score")
                        ,rs1.getInt("p2_score"));
                conn.close();
                pins.close();
                System.out.println("in order");
                return game;
            }
            else
            {
                conn.close();
                pins.close();
            }

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("catch DB !!");
        }

        return null;
    }
    public boolean updateMainScores(int p_ID,int p_score)
    {
        try {
            Connection conn = this.Connect();
            PreparedStatement pins = conn.prepareStatement("UPDATE player SET main_score = ? WHERE playerID = ?");
            pins.setInt(1,p_score);
            pins.setInt(2,p_ID);

            int status = pins.executeUpdate();
            if(status != 0)
            {
                conn.close();
                pins.close();
                System.out.println("main scores");
                return true;
            }else{
                conn.close();
                pins.close();
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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
