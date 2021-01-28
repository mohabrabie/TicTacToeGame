package controllers;

import dbconnection.DBMS;
import dbconnection.LoginDB;
import dbconnection.Player;
import dbconnection.SignUpDB;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerHandler {
    private Player player;
    private Player thisPlayer;
    private ManagePlayerConnection playerConn;
    ArrayList<Player> list = null;
    static Map<Integer,ManagePlayerConnection> onlinePlayers = new HashMap<Integer,ManagePlayerConnection>();

    public PlayerHandler(ManagePlayerConnection playerConn) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this.playerConn = playerConn;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //Read MSG
                        Map<String, Player> elements = playerConn.deserialize();
                        player = (Player) elements.values().toArray()[0];
                        System.out.println("before if");
                        if (elements.keySet().toArray()[0].equals("login")) {
                            signInAction(true,1);
                        } else if (elements.keySet().toArray()[0].equals("signup")) {
                            signUpAction();
                        } else if (elements.keySet().toArray()[0].equals("forgetPassword")) {
                            signInAction(false,1);
                        } else if (elements.keySet().toArray()[0].equals("list"))
                        {
                            System.out.println(":::::: Enterd List :::::");
                            list = GetAllPlayers(player);
                            playerConn.serialaizeList("true", list);
                            thisPlayer = player;
                        }else if (elements.keySet().toArray()[0].equals("logout")) {
                            signInAction(false, 0);
                        }else if (elements.keySet().toArray()[0].equals("updateProfile")) {
                            updateProfileAction();
                        } else if (elements.keySet().toArray()[0].equals("play")) {
                            System.out.println(":::::: Enterd Play Mode :::::");
                            System.out.println(PlayerHandler.onlinePlayers);
                            Player myFriend = player;
                            if(FindMyFriend(myFriend))
                            {
                                System.out.println("Match between " + thisPlayer.getName() +" and " + myFriend.getName());
                                System.out.println(PlayerHandler.onlinePlayers.get(myFriend.getPlayerID()));
                                PlayerHandler.onlinePlayers.get(myFriend.getPlayerID()).serialaize("play",thisPlayer);
                                System.out.println("Data Sent....to " + myFriend.getName());
                            }else{
                                System.out.println("Sorry he/she is offline");
                                playerConn.serialaize("offline", player);
                            }
                            //GetAnswer from Player
                        }else if(elements.keySet().toArray()[0].equals("yes")){
                            System.out.println("sedning yes to "+player.getName());
                            PlayerHandler.onlinePlayers.get(player.getPlayerID()).serialaize("yes",thisPlayer);
                            System.out.println("she accepted");
                        }else if(elements.keySet().toArray()[0].equals("no")){
                            System.out.println("sedning no to "+player.getName());
                            PlayerHandler.onlinePlayers.get(player.getPlayerID()).serialaize("no",thisPlayer);
                            System.out.println("she did not accepted");
                        }
                        else {
                            playerConn.closeConnection();
                            PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                            break;
                        }
                    } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                        PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                        playerConn.closeConnection();
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

    public void signUpAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        SignUpDB db = new SignUpDB();
        //db.Connect();
        if (!db.isExist(player)) {
            //insert successfull
            //System.out.println("inside isExist");
            boolean bol = false;
            bol = db.newPlayer(player);
            System.out.println("after new player " + bol);
            if (bol) {
                playerConn.serialaize("true", player);
                thisPlayer = player;
            }
            else
                playerConn.serialaize("false", player);
        } else {
            playerConn.serialaize("false", player);
        }

    }

    public void signInAction(boolean loginOrForget,int status) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SQLException {
        LoginDB db = new LoginDB();

        if (db.isExist(player, loginOrForget))
        {
            boolean isUpdated = db.updateStatus(status);
            if(isUpdated)
                System.out.println("Status is updated");
            else
                System.out.println("Error updating the status");
            player = db.getPlayerData();
            playerConn.serialaize("true", player);
            PlayerHandler.onlinePlayers.put(player.getPlayerID(),playerConn);
        } else {
            playerConn.serialaize("false", player);
        }
    }

    public ArrayList<Player> GetAllPlayers(Player p) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        DBMS db1 = new DBMS();
        ArrayList<Player> list = null;
        try {
            list = db1.SelectPlayers();
            for (int i = 0; i < list.size(); i++) {
                if (p.getPlayerID() == list.get(i).getPlayerID()) {
                    list.remove(i);
                }
            }
            db1.closeConnection();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void updateProfileAction() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        SignUpDB db = new SignUpDB();
        //db.Connect();
        if (db.isExist(player)) {
            boolean bol = db.updatePlayer(player);
            //System.out.println(bol);
            if (bol)
                playerConn.serialaize("true", player);

            else
                playerConn.serialaize("false", player);

        } else {
            playerConn.serialaize("false", player);

        }}
        public boolean FindMyFriend(Player player){
            System.out.println("Play with :::: "+player);
            for(int id :  PlayerHandler.onlinePlayers.keySet())
            {
                if(id == player.getPlayerID())
                {
                    System.out.println("yes I found him ");
                    return true;
                }
            }
            return false;
        }
    }
