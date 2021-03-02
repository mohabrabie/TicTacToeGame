/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import dbconnection.Player;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import dbconnection.PlayerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aya Abdulsamie
 */
public class PlayingModeController implements Initializable {

    @FXML
    private AnchorPane home;
    @FXML
    private Button login;
    @FXML
    private Button singlemodebtn;
    @FXML
    private Button logoutbtn;
    @FXML
    private Button profilebtn;
    private PlayerConnection connectPlayer;
    private Player p;


    public void init(Player player,PlayerConnection connectPlayer)
    {
        this.connectPlayer = connectPlayer;
        this.p = player;
        profilebtn.setText(p.getName());
    }
    @FXML
    private void multiModeButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/MatchGround.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        MatchGroundController controller = loader.getController();
        controller.init(p,connectPlayer);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }

    @FXML
    private void singleModeButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Levels.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        LevelsController controller = loader.getController();
        controller.init(p,connectPlayer);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }

    @FXML
    private void logoutButtonPushed(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
        Scene scene = new Scene(root);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        connectPlayer.serialaize("logout",p);
       // Map<String, Player> elements = connectPlayer.deserialize();
        //System.out.println(elements.values().toArray()[0]);

        connectPlayer.closeConnection();
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }

    @FXML
    private void profileButtonPushed(ActionEvent event) throws IOException
    {
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Profile.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        ProfileController controller = loader.getController();
        controller.getData(p,false,connectPlayer);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        // animation added here
        new SlideInDown(root).play();
        window.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //profilebtn.setStyle("-fx-text-fill: white");
    }
    
}
