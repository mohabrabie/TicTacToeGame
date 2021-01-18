/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clintside;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import dbconnection.Player;
import java.io.OutputStream;

/**
 *
 * @author Mohab
 */
public class ClintSide extends Application{
    Socket s;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root = new StackPane();
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void Connect() throws ClassNotFoundException
    {
        Player p = new Player();
        p.setName("from clint");
        p.setEmail("email from clint");
        try {
            s = new Socket("localhost",8000);
            System.out.println("connected");
            //outputStream = new ObjectOutputStream(s.getOutputStream());
            OutputStream os = s.getOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(os);
            System.out.println("sending values");
            //ObjectInputStream inStream = new ObjectInputStream(s.getInputStream());
            
            outStream.writeObject(p);
            
            //p = (Player)inStream.readObject();
            //System.out.println(p.getName());
            //System.out.println(p.getEmail());
        } catch (IOException ex) {
            Logger.getLogger(ClintSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public static void main(String[] args) throws ClassNotFoundException {
        //launch(args);
        ClintSide client = new ClintSide();
        client.Connect();
    }
}
