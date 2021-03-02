/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import dbconnection.PlayerConnection;
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
 *
 * @author Aya Abdulsamie
 */
public class HomeController  implements Initializable {
     @FXML
    private Button loginButton;
     @FXML
     private Button singupButton;
    private PlayerConnection connectPlayer;

    private void startConnection() throws IOException
    {
        this.connectPlayer = new PlayerConnection();
        this.connectPlayer.startConnection();
    }
    @FXML
    private void loginButtonPushed(ActionEvent event) throws IOException
    {
        startConnection();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        LoginController controller = loader.getController();
        controller.connectPlayer(connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }
    @FXML
     private void signupButtonPushed(ActionEvent event) throws IOException
    {
        startConnection();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/SignUp.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        SignUpController controller = loader.getController();
        controller.connectPlayer(connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
}
