/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import dbconnection.Player;
import dbconnection.PlayerConnection;
import singleMode.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class LevelsController implements Initializable {

    @FXML
    private Button mediumButton;
    @FXML
    private Button easyButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button backButton;
    
    
    private Player p;
    private PlayerConnection connectPlayer;

//    private Game g;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void init(Player player,PlayerConnection connectPlayer)
    {
        p = player;
        this.connectPlayer = connectPlayer;

    }
    
    @FXML
    private void backButtonAction(ActionEvent event)  throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            //access the controller and call a method
            PlayingModeController controller = loader.getController();
            controller.init(p,connectPlayer);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            // animation added here
            new SlideInLeft(root).play();
            window.show();
    }
    
    
    @FXML
    private void easyButtonAction(ActionEvent event) throws IOException 
    {
        Game.setLevel(1);
        nextPage(event);
    }
    
    @FXML
    private void mediumButtonAction(ActionEvent event) throws IOException 
    {
        Game.setLevel(2);
        nextPage(event);
    }
    
    @FXML
    private void hardButtonAction(ActionEvent event) throws IOException 
    {
        Game.setLevel(3);
        nextPage(event);
    }

    private void nextPage(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/SinglePlayer.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        SinglePlayerController controller = loader.getController();
        controller.init(p,connectPlayer);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }

    

}
