package controllers;

import dbconnection.DBMS;
import dbconnection.LoginDB;
import dbconnection.Player;
import dbconnection.SignUpDB;
import javafx.application.Platform;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerHandler {
    private Player player;
    private ManagePlayerConnection playerConn;
    ArrayList<Player> list = null;

    public PlayerHandler(ManagePlayerConnection playerConn) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this.playerConn = playerConn;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //Read MSG

                            Map<String, Player> elements ;
                            if((elements = playerConn.deserialize()) != null) {
                                player = (Player) elements.values().toArray()[0];
                                //System.out.println("before if");
                                if (elements.keySet().toArray()[0].equals("login")) {
                                    signInAction(true,1);
                                } else if (elements.keySet().toArray()[0].equals("signup")) {
                                    signUpAction();
                                } else if (elements.keySet().toArray()[0].equals("forgetPassword")) {
                                    signInAction(false,1);
                                }else if (elements.keySet().toArray()[0].equals("logout")) {
                                    signInAction(false, 0);
                                }else if (elements.keySet().toArray()[0].equals("updateProfile")) {
                                    updateProfileAction();
                                } else if (elements.keySet().toArray()[0].equals("list")) {
                                    System.out.println(":::::: Enterd List :::::");
                                    list = GetAllPlayers(player);
                                    playerConn.serialaizeList("true", list);
                                } else if (elements.keySet().toArray()[0].equals("play")) {
                                    System.out.println(":::::: Enterd Play Mode :::::");

                                } else {
                                    break;
                                }
                            }else{
                                System.out.println("Connection is lost");
                                break;
                            }
                    } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
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


            //System.out.println("after new player " + bol);

            if (bol)
                playerConn.serialaize("true", player);

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

            System.out.println("Found");
        } else {
            playerConn.serialaize("false", player);

        }
    }

    public ArrayList<Player> GetAllPlayers(Player p) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        DBMS db1 = new DBMS();
        ArrayList<Player> list = null;

        list = db1.SelectPlayers();
        for (int i = 0; i < list.size(); i++) {
            if (p.getPlayerID() == list.get(i).getPlayerID()) {
                list.remove(i);
            }
        }
        db1.closeConnection();

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

        }

    }

    }

