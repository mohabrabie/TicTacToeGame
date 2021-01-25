package controllers;

import dbconnection.LoginDB;
import dbconnection.Player;
import dbconnection.SignUpDB;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


public class PlayerHandler {
    private Player player;
    private ManagePlayerConnection playerConn;

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
            try {
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
        }

    }
    }).start();
}

    public void signUpAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        SignUpDB db = new SignUpDB();
        //db.Connect();
        if (!db.isExist(player)) {
            //insert successfull
            System.out.println("inside isExist");
            boolean bol = db.newPlayer(player);

            System.out.println("after new player " + bol);

            if(bol)
                playerConn.serialaize("true",player);

            else
                playerConn.serialaize("false",player);

        } else {
            playerConn.serialaize("false",player);
        }

    }
    public void signInAction(boolean loginOrForget) throws ClassNotFoundException, InstantiationException, IllegalAccessException,IOException
    {
        LoginDB db = new LoginDB();
        //db.Connect();
        if(db.isExist(player,loginOrForget))
        {
            player = db.getPlayerData();
            playerConn.serialaize("true",player);

            System.out.println("Found");
        } else {
            playerConn.serialaize("false",player);

        }
    }
    public void updateProfileAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException,IOException
    {
        SignUpDB db = new SignUpDB();
        //db.Connect();
        if(db.isExist(player))
        {
            boolean bol = db.updatePlayer(player);
            if(bol)
                playerConn.serialaize("true",player);

            else
                playerConn.serialaize("false",player);

        } else {
            playerConn.serialaize("false",player);

        }
    }

}
