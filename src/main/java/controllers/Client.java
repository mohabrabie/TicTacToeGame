package controllers;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.Player;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author Aya Abdulsamie
 */
public class Client{
    Socket s;

    public static void main(String[] args)
    {
        new Client();
    }
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
    public Client() {
        try {
            Player player = new Player("aya", "aya@gmail.com", 0, 1);

            s = new Socket("127.0.0.1", 5005);
            System.out.println("connected");
            DataInputStream dis = new DataInputStream(s.getInputStream());
            PrintStream ps = new PrintStream(s.getOutputStream());


            try {
                //send MSG
                String json = player.jsonToString();
                ps.println(json);

                player = new Player("aya", "aya@gmail.com", 0, 1);
                //Read MSG
                String msg = dis.readLine();
                player  = stringToPlayer(msg);
                System.out.println(msg);
                System.out.println(player.getName());


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //String replyMsg = dis.readLine();
            //System.out.println(replyMsg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

