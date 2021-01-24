package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.Player;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class PlayerHandler {
    private DataInputStream dis;
    private PrintStream ps;
    private Player player;

    public Player stringToPlayer(String msg)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(msg, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public PlayerHandler(Socket s){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true){
                            dis = new DataInputStream(s.getInputStream());
                            ps = new PrintStream(s.getOutputStream());

                            //Read MSG
                            String msg = dis.readLine();
                            player  = stringToPlayer(msg);
                            System.out.println(msg);
                            System.out.println(player.getName());

                            //System.out.println(msg);

                            //send Object
                            player = new Player("mohab", "mohab@gmail.com", 0, 5);
                            String json = player.jsonToString();
                            ps.println(json);

                            //ether login or SignUP
                            //System.out.println(player.getEmail() + " " + player.getName()+" "+player.getPassword());
                            //Platform.runLater(() -> taLog.appendText(new Date() + " Player : " +player.getName()+" Login" + '\n'));

                            //Platform.runLater(() -> taLog.appendText(new Date() + " Player : " +player.getName()+" SignedUp" + '\n'));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
    }
}
