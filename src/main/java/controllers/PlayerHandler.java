package controllers;

import dbconnection.*;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class PlayerHandler {
    private Player player;
    private Player thisPlayer;
    private ManagePlayerConnection playerConn;
    ArrayList<Player> list = null;
    static Map<Integer,ManagePlayerConnection> onlinePlayers = new HashMap<Integer,ManagePlayerConnection>();
    static Map<Integer, Game> onGame = new HashMap<Integer,Game>();//Integer / Game
    static private Player player1,player2;
    static private ManagePlayerConnection player1Talk,player2Talk;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    boolean gameOn = false;
    int p1_score = 0,p2_score = 0;

    public PlayerHandler(ManagePlayerConnection playerConn) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this.playerConn = playerConn;
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (!gameOn) {
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
                            playerConn.serialaize("list", player);
                            playerConn.serialaizeList("true", list);
                            thisPlayer = player;
                        }else if (elements.keySet().toArray()[0].equals("logout")) {
                            signInAction(false, 0);
                            PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                            playerConn.closeConnection();
                        }else if (elements.keySet().toArray()[0].equals("updateProfile")) {
                            updateProfileAction();
                        } else if (elements.keySet().toArray()[0].equals("play")) {
                            SendPlay();
                            p1_score = p2_score =0;
                            //GetAnswer from Player
                        }else if(elements.keySet().toArray()[0].equals("yes")){
                            //gameOn = true;
                            System.out.println("sedning yes to "+player.getName());
                            System.out.println("sedning yes to "+thisPlayer.getName());
                            PlayerHandler.onlinePlayers.get(player.getPlayerID()).serialaize("yes",thisPlayer);//sending player who accepted
                            PlayerHandler.onlinePlayers.get(thisPlayer.getPlayerID()).serialaize("yes",player);
                            player2 = thisPlayer; //this player is X
                            player1 = player; // this Player is O
                            p1_score = p2_score =0;
                            player2Talk = PlayerHandler.onlinePlayers.get(thisPlayer.getPlayerID());
                            player1Talk = PlayerHandler.onlinePlayers.get(player.getPlayerID());
                            //onGame.put(player1.getPlayerID(),player2.getPlayerID());
                            System.out.println(player1.getName() + " player1 :::::::");
                            System.out.println(player2.getName() + " player2 :::::::");
                            player1Talk.serialaize("first",player1);
                            player2Talk.serialaize("second",player2);
                        }else if(elements.keySet().toArray()[0].equals("no")){
                            System.out.println("sedning no to "+player.getName());
                            PlayerHandler.onlinePlayers.get(player.getPlayerID()).serialaize("no",thisPlayer);//sending player who didn't accept
                        }if (elements.keySet().toArray()[0].equals("leaveMatch")) {
                            if(player.getPlayerID() == player1.getPlayerID())
                            {
                                System.out.println("player1 is "+player1.getName());
                                p2_score++;
                                p1_score-=5;
                                updateGameStatus(player2,player1);
                                player2Talk.serialaize("leaveMatch",player2);
                            }else{
                                System.out.println("player2 is "+player2.getName());
                                p1_score++;
                                p2_score-=5;
                                updateGameStatus(player1,player2);
                                player1Talk.serialaize("leaveMatch",player1);
                            }
                            PlayerHandler.onGame.remove(player.getPlayerID());
                        }
                        else if(elements.keySet().toArray()[0].equals("non")){
                            playerConn.serialaize("non", player);
                        }else if(elements.keySet().toArray()[0].equals("after")){
                            if(player.getPlayerID() == player1.getPlayerID())
                            {
                                player2Talk.serialaize("after",player2);
                            }else if(player.getPlayerID() == player2.getPlayerID()){
                                player1Talk.serialaize("after",player1);
                            }
                        }else if(elements.keySet().toArray()[0].equals("win")){
                            if(player.getPlayerID() == player1.getPlayerID())
                            {
                                p1_score++;
                                p2_score-=5;
                                updateGameStatus(player1,player2);
                                player1Talk.serialaize("win",player1);
                                player2Talk.serialaize("lose",player2);
                            }else if(player.getPlayerID() == player2.getPlayerID()){
                                p2_score++;
                                p1_score-=5;
                                updateGameStatus(player2,player1);
                                player1Talk.serialaize("lose",player1);
                                player2Talk.serialaize("win",player2);
                            }
                        }else if(elements.keySet().toArray()[0].equals("draw")){
                            player1Talk.serialaize("even",player1);
                            player2Talk.serialaize("even",player2);
                        }else if(elements.keySet().toArray()[0].equals("rematch")){
                            player2Talk.serialaize("even",player2);
                        }
                        else if(isDigit((String)elements.keySet().toArray()[0]))
                        {
                            String move = (String)elements.keySet().toArray()[0];
                            player2Talk.serialaize(move,player);
                            player1Talk.serialaize(move,player);
                        }
                    } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                        //e.printStackTrace();
                        try {
                            signInAction(false, 0);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IOException | SQLException e1) {
                            //e1.printStackTrace();
                            System.out.println("something went wrong!");
                        }
                        PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                        playerConn.closeConnection();
                        break;
                    }
                }
            }
        }).start();
    }
    public boolean isDigit(String str){
        Pattern pattern = Pattern.compile("\\d");
        return pattern.matcher(str).matches();
    }
    public void SendPlay()
    {
        System.out.println(":::::: Enterd Play Mode :::::");
        System.out.println(PlayerHandler.onlinePlayers);
        Player myFriend = player;
        if(FindMyFriendOnline(myFriend) && FindMyFriendOnGame(myFriend))
        {
            System.out.println("Match between " + thisPlayer.getName() +" and " + myFriend.getName());
            System.out.println(PlayerHandler.onlinePlayers.get(myFriend.getPlayerID()));
            PlayerHandler.onlinePlayers.get(myFriend.getPlayerID()).serialaize("play",thisPlayer);
            System.out.println("Data Sent....to " + myFriend.getName());
        }else{
            System.out.println("Sorry he/she is offline or in a Game");
            playerConn.serialaize("offline", player);
        }
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
        public boolean FindMyFriendOnline(Player player){
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
    public boolean FindMyFriendOnGame(Player player){
        System.out.println("Play with :::: "+player);
        for(int id :  onGame.keySet())
        {
            if((id == player.getPlayerID()))
            {
                System.out.println("he is in a Game");
                return false;
            }
        }
        for(int id :  onGame.keySet())
        {
            if((id == player.getPlayerID()))
            {
                System.out.println("he is in a Game");
                return false;
            }
        }
        return true;
    }
    public void updateGameStatus(Player winner,Player defeated)
    {
        DBMS db = new DBMS();
        Game game;
        if ((game = db.SelectGame(winner.getPlayerID(), defeated.getPlayerID())) == null) {
            db.addNewGame(winner.getPlayerID(), defeated.getPlayerID(), p1_score, p2_score);
        }
        else {
            if(winner.getPlayerID() == game.getP1_ID() && player1.getPlayerID() == game.getP1_ID()) {
                db.updateGameResults(game.getGameID(), game.getP1_score() + 1, game.getP2_score() - 5);
            }else{
                db.updateGameResults(game.getGameID(), game.getP1_score() - 5, game.getP2_score() + 1);
            }
            db.updateMainScores(winner.getPlayerID(), winner.getMain_score() + 1);
            db.updateMainScores(defeated.getPlayerID(), defeated.getMain_score() - 5);
        }
    }
}
