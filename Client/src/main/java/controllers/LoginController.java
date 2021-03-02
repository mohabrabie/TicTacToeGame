/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import animatefx.animation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import controllers.PlayingModeController;
import dbconnection.Player;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Hyperlink;
import dbconnection.PlayerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aya Abdulsamie
 */
public class LoginController implements Initializable {

    @FXML
    private Label labelEmail;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button signinbtn;
    @FXML
    private Hyperlink forgetpassbtn;
    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXButton signupbtn;

    private Player player;
    private PlayerConnection connectPlayer;


    public void connectPlayer(PlayerConnection connectPlayer) throws IOException
    {
        this.connectPlayer = connectPlayer;
    }
    @FXML
    private void signInButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        //Vlidate entered email pattern and password
        if(validateEmailPattern(tfEmail.getText()))
        {
              if(!tfPassword.getText().trim().isEmpty())
              {
                      player = new Player(tfEmail.getText(), tfPassword.getText());
                      connectPlayer.serialaize("login",player);

                      Map<String, Player> elements = connectPlayer.deserialize();
                      System.out.println(elements.values().toArray()[0]);

                      if(elements.keySet().toArray()[0].equals("true"))
                      {
                          System.out.println("it works!");
                          player = (Player) elements.values().toArray()[0];
                          moveToPlayingModeOptions(event);
                      }
                    else
                    {
                        alertError("Invalid","Wrong email or password");
                        clearNodes();
                    }
                 }
              else
              {
                  alertError("Invalid password","Password field can't be empty!");
                   clearNodes();
              }
        }
        else
        {
                alertError("Invalid email","Invalid email pattern");
                 clearNodes();
        }
    }
   
    @FXML
    private void forgetPasswordButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/ForgetPass.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        ForgetPassController controller = loader.getController();
        controller.init(connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);

        // animation added here
        new SlideInRight(root).play();

        window.show();
    }

    @FXML
    private void backButtonPushed(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
        Scene scene = new Scene(root);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        connectPlayer.serialaize("logout",player);
        Map<String, Player> elements = connectPlayer.deserialize();
        System.out.println(elements.values().toArray()[0]);

        connectPlayer.closeConnection();
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }

    @FXML
    private void signUpButtonPushed(ActionEvent event) throws IOException
    {
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
    
     private void moveToPlayingModeOptions(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        PlayingModeController controller = loader.getController();
        controller.init(player,connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInRight(root).play();
        window.show();
    }
      private boolean validateEmailPattern(String email)
    {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
   
   private void clearNodes()
   {
        tfEmail.clear();
        tfPassword.clear();
   }
      
   private void alertError(String title , String msg)
   {
          Alert alert ;
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle(title);
           alert.setContentText(msg);
           alert.showAndWait();
   }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //new LoginController();
    }

}
