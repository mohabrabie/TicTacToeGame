package com.tictactoe.tictactoeclient;

import animatefx.animation.*;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("TicTacToe JavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        new FadeIn(root).play();

        stage.setOnCloseRequest(e -> {
            try{
                super.stop();
            }catch(Exception ee){
                ee.getMessage();
            }
            Platform.exit();
            System.exit(0);
        });

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
