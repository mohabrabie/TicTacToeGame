package controllers;

import dbconnection.DBMS;
import dbconnection.LoginDB;
import dbconnection.Player;
import dbconnection.SignUpDB;
import javafx.application.Platform;

import java.io.IOException;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 8b2af261506851c4e569647bf71c918f47766f3a
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerHandler {
    private Player player;
    private ManagePlayerConnection playerConn;
    ArrayList<Player> list = null;

    /*public Player stringToPlayer(String msg)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(msg, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }*/
public PlayerHandler(ManagePlayerConnection playerConn) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException
{
    this.playerConn = playerConn;

    new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
            try {
<<<<<<< HEAD
                //Read MSG
                Map<String, Player> elements = playerConn.deserialize();
                player = (Player) elements.values().toArray()[0];
                System.out.println("before if");
                if(elements.keySet().toArray()[0].equals("login")){
                    signInAction(true);
                }
                else if(elements.keySet().toArray()[0].equals("signup")){
                    signUpAction();
                }
                else if(elements.keySet().toArray()[0].equals("forgetPassword")){
                    signInAction(false);
                }else if(elements.keySet().toArray()[0].equals("list")){
                    System.out.println(":::::: Enterd List :::::");
                    list = GetAllPlayers(player);
                    playerConn.serialaizeList("true",list);
                }else if(elements.keySet().toArray()[0].equals("play")) {
                    System.out.println(":::::: Enterd Play Mode :::::");

                }else{
                    break;
                }
                } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                    break;
                }
            }
=======
                while(true){
                    //Read MSG

                    Map<String, Player> elements = playerConn.deserialize();
                    player = (Player) elements.values().toArray()[0];

                    System.out.println("before if");
                    if(elements.keySet().toArray()[0].equals("login")){
                        signInAction(true);
                    }
                    else if(elements.keySet().toArray()[0].equals("signup")){
                        signUpAction();
                    }

                    else if(elements.keySet().toArray()[0].equals("forgetPassword")){
                        signInAction(false);
                    }else if(elements.keySet().toArray()[0].equals("updateProfile")){
                        updateProfileAction();
                    }

            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }   catch (SQLException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

>>>>>>> 8b2af261506851c4e569647bf71c918f47766f3a
    }
    }).start();
}

<<<<<<< HEAD
    public void signUpAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
=======
    public void signUpAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
>>>>>>> 8b2af261506851c4e569647bf71c918f47766f3a
        SignUpDB db = new SignUpDB();
        //db.Connect();
        if (!db.isExist(player)) {
            //insert successfull
            System.out.println("inside isExist");
            boolean bol = false;
            bol = db.newPlayer(player);


            System.out.println("after new player " + bol);

            if(bol)
                playerConn.serialaize("true",player);

            else
                playerConn.serialaize("false",player);

        } else {
            playerConn.serialaize("false",player);
        }

    }
<<<<<<< HEAD
    public void signInAction(boolean loginOrForget) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException {
=======
    public void signInAction(boolean loginOrForget) throws ClassNotFoundException, InstantiationException, IllegalAccessException,IOException, SQLException
    {
>>>>>>> 8b2af261506851c4e569647bf71c918f47766f3a
        LoginDB db = new LoginDB();
        //db.Connect();
        DBMS db1 = new DBMS();
        if(db.isExist(player,loginOrForget))
        {
            player = db.getPlayerData();
            playerConn.serialaize("true",player);

            System.out.println("Found");
        } else {
            playerConn.serialaize("false",player);

        }
    }
<<<<<<< HEAD
    public ArrayList<Player> GetAllPlayers(Player p) {
        DBMS db = new DBMS();
        ArrayList<Player> list = null;
        try {
            list = db.SelectPlayers();
            for (int i = 0; i < list.size(); i++) {
                if (p.getPlayerID() == list.get(i).getPlayerID()) {
                    list.remove(i);
                }
            }
            db.closeConnection();
        } catch (InstantiationException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return list;
=======
    public void updateProfileAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException,IOException, SQLException
    {
        SignUpDB db = new SignUpDB();
        //db.Connect();
        if(db.isExist(player))
        {
            boolean bol = db.updatePlayer(player);
            System.out.println(bol);
            if(bol)
                playerConn.serialaize("true",player);

            else
                playerConn.serialaize("false",player);

        } else {
            playerConn.serialaize("false",player);

        }
>>>>>>> 8b2af261506851c4e569647bf71c918f47766f3a
    }

}
