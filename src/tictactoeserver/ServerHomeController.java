/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import dbconnection.DBMS;
import dbconnection.Player;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohab
 */
public class ServerHomeController implements Initializable {
    @FXML
    private Button start;
    @FXML
    private TextArea taLog;
    @FXML
    private Button stop;
    @FXML
    private Button listUsers;
    @FXML
    private TableView<Player> table = new TableView();
    @FXML
    private TableColumn<Player, String> nameCol;
    @FXML
    private TableColumn<Player, String> emailCol ;
    @FXML
    private TableColumn<Player, Integer> scoreCol ;
    @FXML
    private TableColumn<Player, ImageView> statusCol ;
    @FXML
    private TableColumn<Player, ImageView> avatarCol ;
    
    private ServerSocket serverSocket;
    
    private boolean ServerOn = false;
    private int sessionNo = 1;
    Thread main;
    boolean tableOn = false;
    DBMS db = new DBMS();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //set up columns
        nameCol.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Player,String>("email"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player,Integer>("main_score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Player,ImageView>("stat"));
        avatarCol.setCellValueFactory(new PropertyValueFactory<Player,ImageView>("avat"));
    }    
    
    public void ViewUsersAction(ActionEvent event) throws IOException
    {
        if(tableOn == true)
        {
            tableOn = false;
        }else{
            tableOn = true;
        }
            table.setVisible(tableOn);
        try {
            table.setItems(db.ViewForServer());
        } catch (InstantiationException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartServer(ActionEvent event)
    {
        ServerOn = true;
        start.setDisable(ServerOn);
        if(main == null)
        {
        main = new Thread( new Runnable() {
            @Override
            public void run() {
                try 
                {
                    // Create a server socket
                    serverSocket = new ServerSocket(8000);
                    
                    //start.disableProperty();
                    Platform.runLater(() -> taLog.appendText(new Date() + ": Server started at socket 8000\n")); 
                    
                    // Ready to create a session for every two players
                    while (ServerOn)
                    {
                        Platform.runLater(() -> taLog.appendText(new Date() + ": Wait for players to join session " + sessionNo + '\n'));
                        
                        // Connect to player 1
                        Socket s = serverSocket.accept();
                        Platform.runLater(() ->
                        {
                            taLog.appendText(new Date() + "Socket Number  "+ s +" joind and session number : "+ sessionNo + '\n');
                            taLog.appendText("Player 1's IP address" + s.getInetAddress().getHostAddress() + '\n');
                        });
                        
                        // Notify that the player is Player 1
                        //new DataOutputStream(player1.getOutputStream()).writeInt(1);
                        ObjectOutputStream outStream = new ObjectOutputStream(s.getOutputStream());
                        ObjectInputStream inStream = new ObjectInputStream(s.getInputStream());
                        try {
                            Player p = (Player) inStream.readObject();
                            //System.out.println(p.getName());
                            //System.out.println(p.getEmail());
                            //p.setName("from Server");
                            //p.setEmail("email from server");
                            //System.out.println(p.getName());
                            //System.out.println(p.getEmail());
                            // Connect to player 2
                            //Socket player2 = serverSocket.accept();
                            
                            //Platform.runLater(() ->
                            //{
                            //    taLog.appendText(new Date() + ": Player 2 joined session " + sessionNo + '\n');
                            //   taLog.appendText("Player 2's IP address" + player2.getInetAddress().getHostAddress() + '\n');
                            //});

                            // Notify that the player is Player 2
                            //new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
                            
                            // Display this session and increment session number
                            //Platform.runLater(() ->
                            //taLog.appendText(new Date() + ": Start a thread for session " + sessionNo++ + '\n'));
                            // Launch a new thread for this session of two players
                            //new Thread(new HandleASession(player1, player2)).start();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    serverSocket.close();
                }
                catch(IOException ex) 
                {
                    ex.printStackTrace();
                }
            }            
        });
        
        main.start();
        }else{
        main.resume();
    }
    }
    public void StopServer(ActionEvent event) 
    {
        ServerOn = false;
        start.setDisable(ServerOn);
        main.stop();
        
    }
    
}