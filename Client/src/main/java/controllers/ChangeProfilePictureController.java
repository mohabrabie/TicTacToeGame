/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import dbconnection.Player;
import dbconnection.PlayerConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import singleMode.Game;

/**
 * FXML Controller class
 *
 * @author Islam
 */
public class ChangeProfilePictureController implements Initializable {

    @FXML
    private Button backButton;

    private PlayerConnection connectPlayer;
    private Player player;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Player player , PlayerConnection connectPlayer){
        this.connectPlayer = connectPlayer;
        this.player = player;
    }

    private void setPicture(Event event,String img) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Profile.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        player.setAvatar(img);
        ProfileController controller = loader.getController();
        controller.init(player,connectPlayer);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Profile.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        ProfileController controller = loader.getController();
        controller.init(player,connectPlayer);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }
    
    @FXML
    private void oneClicked(MouseEvent event) throws IOException {
        setPicture(event,"1.png");
    }

    @FXML
    private void twoClicked(MouseEvent event) throws IOException {
        setPicture(event,"2.png");
    }

    @FXML
    private void threeClicked(MouseEvent event) throws IOException {
        setPicture(event,"3.png");
    }

    @FXML
    private void fourClicked(MouseEvent event) throws IOException {
        setPicture(event,"4.png");
    }

    @FXML
    private void fiveClicked(MouseEvent event) throws IOException {
        setPicture(event,"5.png");
    }

    @FXML
    private void sixClicked(MouseEvent event) throws IOException {
        setPicture(event,"6.png");
    }

    @FXML
    private void sevenClicked(MouseEvent event) throws IOException {
        setPicture(event,"7.png");
    }

    @FXML
    private void eightClicked(MouseEvent event) throws IOException {
        setPicture(event,"8.png");
    }

    @FXML
    private void nineClicked(MouseEvent event) throws IOException {
        setPicture(event,"9.png");
    }

    @FXML
    private void tenClicked(MouseEvent event) throws IOException {
        setPicture(event,"10.png");
    }

    @FXML
    private void elevenClicked(MouseEvent event) throws IOException {
        setPicture(event,"11.png");
    }

    @FXML
    private void twelveClicked(MouseEvent event) throws IOException {
        setPicture(event,"12.png");
    }

    @FXML
    private void thirteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"13.png");
    }

    @FXML
    private void fourteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"14.png");
    }

    @FXML
    private void fifteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"15.png");
    }

    @FXML
    private void sixxteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"16.png");
    }

    @FXML
    private void seventeenClicked(MouseEvent event) throws IOException {
        setPicture(event,"17.png");
    }

    @FXML
    private void eighteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"18.png");
    }

    @FXML
    private void nineteenClicked(MouseEvent event) throws IOException {
        setPicture(event,"19.png");
    }

    @FXML
    private void twentyClicked(MouseEvent event) throws IOException {
        setPicture(event,"20.png");
    }

    @FXML
    private void twentyOneClicked(MouseEvent event) throws IOException {
        setPicture(event,"21.png");
    }

    @FXML
    private void twentyTwoClicked(MouseEvent event) throws IOException {
        setPicture(event,"22.png");
    }

}
