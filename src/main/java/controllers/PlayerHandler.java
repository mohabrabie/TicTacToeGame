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
    static Map<Integer, PlayerGame> playerGame = new HashMap<Integer, PlayerGame>(); // player1,player2
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    boolean gameOn = false;
    int p1_score = 0,p2_score = 0;
    private Player player1,player2 = null;
    static private int token=0;
    private ManagePlayerConnection player1Talk,player2Talk = null;
    public PlayerHandler(ManagePlayerConnection playerConn) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this.playerConn = playerConn;
        new Thread(new Runnable() {

            @Override
            public void run() {
                player1 = null;
                player2 = null;
                player1Talk = null;
                player2Talk = null;
                System.out.println(":::::::::::::::::::::::NEW Thread ::::::::::::::::::::::::::::::::::");
                System.out.println(onlinePlayers);
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
                            //GetAnswer from Player
                        }else if(elements.keySet().toArray()[0].equals("yes")){
                            //gameOn = true;
                            System.out.println("sedning yes to "+player.getName());
                            System.out.println("sedning yes to "+thisPlayer.getName());
                            PlayerHandler.onlinePlayers.get(player.getPlayerID()).serialaize("yes",thisPlayer);//sending player who accepted
                            PlayerHandler.onlinePlayers.get(thisPlayer.getPlayerID()).serialaize("yes",player);
                            player2 = thisPlayer; //this player is X
                            player1 = player; // this Player is O
                            PlayerGame pg = new PlayerGame(player2,player1);
                            playerGame.put(player1.getPlayerID(),pg);
                            playerGame.put(player2.getPlayerID(),pg);
                            System.out.println("player1 id = "+player1.getPlayerID());
                            System.out.println("player2 id = "+player2.getPlayerID());
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
                            if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID())
                            {
                                System.out.println("player1 is "+playerGame.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).getPlayer1().getName());
                                updateGameStatus(playerGame.get(player.getPlayerID()).getPlayer2(),playerGame.get(player.getPlayerID()).getPlayer1());

                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("leaveMatch",playerGame.get(player.getPlayerID()).getPlayer2());
                            }else{
                                System.out.println("player2 is "+playerGame.get(player.getPlayerID()).getPlayer2().getName());
                                updateGameStatus(playerGame.get(player.getPlayerID()).getPlayer1(),playerGame.get(player.getPlayerID()).getPlayer2());
                                player1Talk.serialaize("leaveMatch",playerGame.get(player.getPlayerID()).getPlayer1());
                            }
                            PlayerHandler.onGame.remove(player.getPlayerID());
                            System.out.println("player game size map before removing player1 :"+playerGame.size());
                            playerGame.remove(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID());
                            System.out.println("player game map size after removing player1 :"+playerGame.size());
                            playerGame.remove(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID());
                            System.out.println("player game  :"+playerGame.get(player.getPlayerID()));

                        }
                        else if(elements.keySet().toArray()[0].equals("non")){
                            playerConn.serialaize("non", player);
                        }else if(elements.keySet().toArray()[0].equals("after")){
                            if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID())
                            {
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("after",playerGame.get(player.getPlayerID()).getPlayer2());
                            }else if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()){
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("after",playerGame.get(player.getPlayerID()).getPlayer1());
                            }
                        }else if(elements.keySet().toArray()[0].equals("win")){
                            if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID())
                            {
                                p1_score++;
                                p2_score-=5;
                                updateGameStatus(playerGame.get(player.getPlayerID()).getPlayer1(),playerGame.get(player.getPlayerID()).getPlayer2());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("win",playerGame.get(player.getPlayerID()).getPlayer1());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("lose",playerGame.get(player.getPlayerID()).getPlayer2());
                            }else if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()){
                                p2_score++;
                                p1_score-=5;
                                updateGameStatus(playerGame.get(player.getPlayerID()).getPlayer2(),playerGame.get(player.getPlayerID()).getPlayer1());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("lose",playerGame.get(player.getPlayerID()).getPlayer1());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("win",playerGame.get(player.getPlayerID()).getPlayer2());
                            }
                        }else if(elements.keySet().toArray()[0].equals("draw")){
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("draw",playerGame.get(player.getPlayerID()).getPlayer1());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("draw",playerGame.get(player.getPlayerID()).getPlayer2());

                        }else if(elements.keySet().toArray()[0].equals("chat")){
                            String msg;
                            if(player.getPlayerID() == playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID())
                            {
                                elements = onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).deserialize();
                                msg = (String) elements.keySet().toArray()[0];
                                msg = player.getName()+" : " + msg + "\n";

                            }else{
                                elements = onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).deserialize();
                                msg = (String) elements.keySet().toArray()[0];
                                msg = player.getName()+" : " + msg + "\n";
                            }
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("chat",playerGame.get(player.getPlayerID()).getPlayer1());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize(msg,playerGame.get(player.getPlayerID()).getPlayer1());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("chat",playerGame.get(player.getPlayerID()).getPlayer2());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize(msg,playerGame.get(player.getPlayerID()).getPlayer2());
                        }else if(elements.keySet().toArray()[0].equals("globalChat")){
                            String msg;
                            elements = onlinePlayers.get(player.getPlayerID()).deserialize();
                            msg = (String) elements.keySet().toArray()[0];
                            msg = player.getName()+" : " + msg + "\n";
                            SendMsgToAll(msg);
                        }else if(isDigit((String)elements.keySet().toArray()[0]))
                        {
                            String move = (String)elements.keySet().toArray()[0];
                            System.out.println("sending "+move+" to "+playerGame.get(player.getPlayerID()).getPlayer2().getName());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize(move,player);
                            System.out.println("sending "+move+" to "+playerGame.get(player.getPlayerID()).getPlayer1().getName());
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize(move,player);
                        }else if(elements.keySet().toArray()[0].equals("rematch")){
                            token++;
                            System.out.println("Token :::::::::::::: "+token);
                            if(token == 2) {
                                System.out.println("Token :::::::::::::: inside if"+token);
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("rematch", playerGame.get(player.getPlayerID()).getPlayer2());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("rematch", playerGame.get(player.getPlayerID()).getPlayer1());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("first", playerGame.get(player.getPlayerID()).getPlayer2());
                                onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("second", playerGame.get(player.getPlayerID()).getPlayer1());
                            }
                            if(token == 2)
                            {
                                token = 0;
                            }
                        }else if(elements.keySet().toArray()[0].equals("norematch")){
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID()).serialaize("norematch", player);
                            onlinePlayers.get(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID()).serialaize("norematch", player);
                            int x,y;
                            x = playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID();
                            y = playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID();
                            PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                            System.out.println("player game size map before removing player1 :"+playerGame.size());
                            playerGame.remove(x);
                            System.out.println("player game map size after removing player1 :"+playerGame.size());
                            playerGame.remove(y);
                            System.out.println("player game size map before removing player2 :"+playerGame.size());
                        } } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                        //e.printStackTrace();
                        try {
                            signInAction(false, 0);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IOException | SQLException e1) {
                            //e1.printStackTrace();
                            System.out.println("something went wrong!");
                        }
                        PlayerHandler.onlinePlayers.remove(player.getPlayerID());
                        if(playerGame.size() != 0) {
                            System.out.println("player game size map before removing player1 :" + playerGame.size());
                            playerGame.remove(playerGame.get(player.getPlayerID()).getPlayer1().getPlayerID());
                            System.out.println("player game map size after removing player1 :" + playerGame.size());
                            playerGame.remove(playerGame.get(player.getPlayerID()).getPlayer2().getPlayerID());
                            System.out.println("player game size map before removing player2 :" + playerGame.size());
                        }playerConn.closeConnection();
                        break;
                    }
                }
            }
        }).start();
    }
    public void SendMsgToAll(String msg)
    {
        for(Integer x : onlinePlayers.keySet())
        {
            onlinePlayers.get(x).serialaize("globalChat",player);
            onlinePlayers.get(x).serialaize(msg,player);
        }
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
        if(FindMyFriendOnline(myFriend) && !FindMyFriendOnGame(myFriend))
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
            NotifyAll(player);
            PlayerHandler.onlinePlayers.put(player.getPlayerID(),playerConn);


        } else {
            playerConn.serialaize("false", player);
        }
    }
    public void NotifyAll(Player p)
    {
        for(Integer x : onlinePlayers.keySet())
        {
            System.out.println("sending notify to ID "+x);
            onlinePlayers.get(x).serialaize("notify",p);
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
        for(int id :  playerGame.keySet())
        {
            if((id == player.getPlayerID()))
            {
                System.out.println("he is in a Game");
                return true;
            }

        }
        return false;
    }
    public void updateGameStatus(Player winner,Player defeated)
    {
        DBMS db = new DBMS();
        db.updateMainScores(winner.getPlayerID(), winner.getMain_score() + 5);
        //db.updateMainScores(defeated.getPlayerID(), defeated.getMain_score() - 5);
    }
}
