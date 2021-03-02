/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dbconnection.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dbconnection.PlayerConnection;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Islam
 */
public class ProfileController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField tfName;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXTextField tfPassword;
    @FXML
    private Button btnSubmit;
    @FXML
    private JFXButton btnBackHome;
    @FXML
    private ImageView imgProfile;
    private boolean isFromPlayingMode;
    private PlayerConnection connectPlayer;
    private Player player;
    private String imgProfilePic;

    public void getData(Player player, boolean flag, PlayerConnection connectPlayer) {
        this.connectPlayer = connectPlayer;
        this.player = player;
        isFromPlayingMode = flag;
        tfName.setText(player.getName());
        tfEmail.setText(player.getEmail());
        tfPassword.setText(player.getPassword());
        imgProfile.setImage(new Image(getClass().getResourceAsStream("/icons/" + player.getAvatar())));
        imgProfilePic = player.getAvatar();
    }

    @FXML
    private void logoutButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Home.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        connectPlayer.closeConnection();
        System.out.println("closed");
        // animation added here
        new SlideInLeft(root).play();
        window.show();

    }

    @FXML
    private void backButtonPushed(ActionEvent event) throws IOException {
        if (isFromPlayingMode) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/MatchGround.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            //access the controller and call a method
            MatchGroundController controller = loader.getController();
            controller.init(player, connectPlayer);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            // animation added here
            new SlideInLeft(root).play();
            window.show();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            //access the controller and call a method
            PlayingModeController controller = loader.getController();
            controller.init(player, connectPlayer);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            // animation added here
            new SlideInLeft(root).play();
            window.show();
        }
    }

    @FXML
    private void saveButtonPushed(ActionEvent event) throws IOException {
        if (!"".equals(tfName.getText())) {
            if (!"".equals(tfPassword.getText())) {
                player = new Player(tfName.getText(), tfEmail.getText(), tfPassword.getText());
                player.setAvatar(imgProfilePic);
                connectPlayer.serialaize("updateProfile", player);

                Map<String, Player> elements = connectPlayer.deserialize();
                System.out.println("bol here :" + elements.keySet().toArray()[0].equals("true"));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Data updated successfully");
                alert.showAndWait();
            } else {
                alertError("Password Wrong", "Password must not be empty!");
            }
        } else {
            alertError("Username Wrong", "Username must not be empty!");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Data updated successfully");
        alert.showAndWait();
    }

    @FXML
    private void changeProfilePicture(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/changeProfilePicture.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private void alertError(String title, String msg) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        //notSavedAlert.showAndWait();
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgProfile.setPickOnBounds(true);

        imgProfile.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxmls/changeProfilePicture.fxml"));
                    Parent root = loader.load();
                    
                    Scene scene = new Scene(root);
                    //This line gets the Stage information
                    ChangeProfilePictureController controller = loader.getController();
                    controller.init(player,connectPlayer);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    
                    window.setScene(scene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public void init(Player player , PlayerConnection connectPlayer) {
        this.connectPlayer = connectPlayer;
        this.player = player;
        tfName.setText(player.getName());
        tfEmail.setText(player.getEmail());
        tfPassword.setText(player.getPassword());
        imgProfilePic = player.getAvatar();
        imgProfile.setImage(new Image(getClass().getResourceAsStream("/icons/" + imgProfilePic)));
    }



}
