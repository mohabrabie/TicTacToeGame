/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dbconnection.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import dbconnection.PlayerConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohab
 */
public class PlayWithFriendController implements Initializable {

    @FXML
    private ImageView player1Icone;
    @FXML
    private ImageView player2Icon;
    @FXML
    private ImageView result;
    @FXML
    private Label player1Name;
    @FXML
    private Label resultText;
    @FXML
    private Label option1;
    @FXML
    private Label option2;
    @FXML
    private Label player2Name;
    @FXML
    private Hyperlink player1MainScore;
    @FXML
    private Hyperlink player2MainScore;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private GridPane groundGrid;
    @FXML

    private Button btn0;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button chatBtn;
    @FXML
    private Button leaveBtn;
    @FXML
    private Pane chatRoom;
    @FXML
    private TextArea chatView;
    @FXML
    private TextField message;
    @FXML
    private Button sendBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    GridPane pane = new GridPane();
    private boolean chatOn = false;
    private PlayerConnection connectPlayer;
    private Player player,player2;
    String myOption = " ";
    String friendOption = " ";
    Thread game;
    int flag = 0,win = 0;
    static private ArrayList<Button> buttonList ;
    static private ArrayList<Button> buttonSelected ;

    public void init(Player player1,Player player2,PlayerConnection connectPlayer)
    {
        buttonList = new ArrayList<Button>();
        buttonSelected = new ArrayList<Button>();
        buttonList.add(btn0);
        buttonList.add(btn1);
        buttonList.add(btn2);
        buttonList.add(btn3);
        buttonList.add(btn4);
        buttonList.add(btn5);
        buttonList.add(btn6);
        buttonList.add(btn7);
        buttonList.add(btn8);
        CleanArea();
        System.out.println(player1);
        System.out.println(player2);
        AllowPlay();
        result.setVisible(false);
        resultText.setVisible(false);
        this.connectPlayer = connectPlayer;
        this.player = player1;
        this.player2 = player2;
        RenderData();

        game.start();
        System.out.println(":::::::::::::::: " +buttonSelected);
        System.out.println(":::::::::::::::: " +buttonList);
    }
    public void CleanArea(){
        for(Button Btn : buttonList)
        {
            Btn.setText(" ");
        }
    }
    private void RenderData(){
        Platform.runLater(()->{
            player1Icone.setImage(new Image(getClass().getResourceAsStream("/icons/"+player.getAvatar())));
            player1MainScore.setText(Integer.toString(player.getMain_score()));
            player1Name.setText(player.getName());

            player2Icon.setImage(new Image(getClass().getResourceAsStream("/icons/"+player2.getAvatar())));
            player2MainScore.setText(Integer.toString(player2.getMain_score()));
            player2Name.setText(player2.getName());
        });

    }
    @FXML
    private void leaveMatchButtonPushed(ActionEvent event) throws IOException
    {
        MakeAlertOfLeaving();
        //access the controller and call a method

    }

    public void MakeAlertOfLeaving()
    {
        Platform.runLater(() -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Leave Game!!!");
            window.setMinWidth(400);
            window.setMaxHeight(300);
            Label label = new Label();
            label.setText("Are You Sure " + player.getName() + " you will Lose You'r Points");
            Button accept = new Button("Ok");
            Button cancel = new Button("Cancel");
            accept.setOnAction(event -> {
                connectPlayer.serialaize("leaveMatch", player);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                PlayingModeController controller = loader.getController();
                controller.init(player, connectPlayer);

                Stage window1 = (Stage) borderPane.getScene().getWindow();

                window1.setScene(scene);
                window1.show();
                window.close();
            });
            cancel.setOnAction(event -> {
                window.close();
            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, accept, cancel);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });

    }
    private void MakeAlertYouWon(){
            Platform.runLater(() -> {
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Game Leaving");
                window.setMinWidth(400);
                window.setMaxHeight(300);
                Label label = new Label();
                label.setText("Sorry But Player " + player2.getName() + " has left the game you Won");
                Button accept = new Button("Ok");
                accept.setOnAction(event -> {
                    Platform.runLater(() -> {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        PlayingModeController controller = loader.getController();
                        controller.init(player, connectPlayer);

                        Stage window1 = (Stage) borderPane.getScene().getWindow();

                        window1.setScene(scene);
                        window1.show();
                        window.close();
                    });
                });
                VBox layout = new VBox(10);
                layout.getChildren().addAll(label, accept);
                layout.setAlignment(Pos.CENTER);
                Scene scene = new Scene(layout);
                window.setScene(scene);
                window.showAndWait();
            });
    }
    @FXML
    private void Chat(ActionEvent event) {
        chatOn = !chatOn;
        chatRoom.setVisible(chatOn);
        chatBtn.setTextFill(Paint.valueOf("#c61a1a"));
    }
    @FXML
    private void SendMsg(ActionEvent event) {
        String msg = message.getText();
        message.clear();
        connectPlayer.serialaize("chat",player);
        connectPlayer.serialaize(msg,player);
    }
    public void AllowPlay(){
        for(Button btn : buttonList)
        {
            btn.setDisable(false);
        }

    }
    public void stopPlay(){
        for(Button btn : buttonList)
        {
            btn.setDisable(true);
        }
    }
    public void DisapleSelected(){
        for(Button btn : buttonSelected)
        {
            btn.setDisable(true);
        }
    }
    public boolean isDigit(String str){
        Pattern pattern = Pattern.compile("\\d");
        return pattern.matcher(str).matches();
    }
    public void makeAlertYouEven()
    {
        Platform.runLater(()->{
            result.setImage(new Image(getClass().getResourceAsStream("/icons/even.gif")));
            result.setVisible(true);
            resultText.setText("You re even");
            resultText.setVisible(true);
            resultText.setTextFill(Paint.valueOf("#7c0006"));
            stopPlay();
            rematch(player);
        });
    }
    public void weHaveWinner()
    {
        Platform.runLater(()->{
            result.setImage(new Image(getClass().getResourceAsStream("/icons/win.gif")));
            result.setVisible(true);
            stopPlay();
            resultText.setText("You Win");
            resultText.setVisible(true);
            resultText.setTextFill(Paint.valueOf("#f6ff00"));

        });
    }
    public void rematch(Player reqPlayer)
    {
        Platform.runLater(() -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Game ended");
            window.setMinWidth(400);
            window.setMaxHeight(300);
            Label label = new Label();
            label.setText("Would u like to play again?");
            Button accept = new Button("Ok");
            Button cancel = new Button("Cancel");
            accept.setOnAction(event -> {
                //connectPlayer.serialaize("rematch",reqPlayer);
                connectPlayer.serialaize("rematch",player);
                window.close();
            });
            cancel.setOnAction(event -> {
                connectPlayer.serialaize("norematch", player);
                window.close();
            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, accept,cancel);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btn0.setOnAction(event -> {
                buttonSelected.add(btn0);
                connectPlayer.serialaize("0",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn1.setOnAction(event -> {
                buttonSelected.add(btn1);
                connectPlayer.serialaize("1",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn2.setOnAction(event -> {
                buttonSelected.add(btn2);
                connectPlayer.serialaize("2",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn3.setOnAction(event -> {
                buttonSelected.add(btn3);
                connectPlayer.serialaize("3",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn4.setOnAction(event -> {
                buttonSelected.add(btn4);
                connectPlayer.serialaize("4",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn5.setOnAction(event -> {
                buttonSelected.add(btn5);
                connectPlayer.serialaize("5",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn6.setOnAction(event -> {
                buttonSelected.add(btn6);
                connectPlayer.serialaize("6",player);
                connectPlayer.serialaize("after",player);
            stopPlay();
        });
        btn7.setOnAction(event -> {
                buttonSelected.add(btn7);
                connectPlayer.serialaize("7",player);
                connectPlayer.serialaize("after",player);
                stopPlay();
        });
        btn8.setOnAction(event -> {
                buttonSelected.add(btn8);
                connectPlayer.serialaize("8",player);
                connectPlayer.serialaize("after",player);
                stopPlay();
        });
        game = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Player> elements = null;
                System.out.println("enterd Thread of Game ::::");
            while(true){
                try {
                    elements = connectPlayer.deserialize();
                    Player myFriend = (Player) elements.values().toArray()[0];
                    if (elements.keySet().toArray()[0].equals("leaveMatch")) {
                        System.out.println("he left u will get alert ::::");
                        MakeAlertYouWon();
                    }else if(elements.keySet().toArray()[0].equals("first")){
                        myOption = "X";
                        friendOption = "O";
                        AllowPlay();
                    }else if(elements.keySet().toArray()[0].equals("second")){
                        myOption = "O";
                        friendOption = "X";
                        stopPlay();
                    }else if(isDigit((String)elements.keySet().toArray()[0])){
                        String move = (String)elements.keySet().toArray()[0];
                        Platform.runLater(()->{
                            if(myFriend.getPlayerID() == player.getPlayerID()){
                                buttonList.get(Integer.parseInt(move)).setText(myOption);
                            }else if(myFriend.getPlayerID() == player2.getPlayerID())
                            {
                                buttonList.get(Integer.parseInt(move)).setText(friendOption);
                            }
                            buttonSelected.add(buttonList.get(Integer.parseInt(move)));
                            System.out.println("selected button size"+ buttonSelected.size());
                            buttonList.get(Integer.parseInt(move)).setDisable(true);
                            if(CheckMe(myOption))
                            {
                                player.setMain_score(player.getMain_score()+5);
                                connectPlayer.serialaize("win",player);
                                System.out.println(player.getName() + " Win ::::::::");
                                weHaveWinner();

                            }
                            else if(isFull())//full
                            {
                                //win = 1;
                                connectPlayer.serialaize("draw",player);
                                makeAlertYouEven();
                            }
                        });
                    }else if(elements.keySet().toArray()[0].equals("after")){
                            AllowPlay();
                            DisapleSelected();

                    }else if(elements.keySet().toArray()[0].equals("win")){

                        System.out.println("you won + "+ player.getName());
                        Platform.runLater(()->{
                            result.setImage(new Image(getClass().getResourceAsStream("/icons/win.gif")));
                            result.setVisible(true);
                            stopPlay();
                            resultText.setText("You Win");
                            resultText.setVisible(true);
                            resultText.setTextFill(Paint.valueOf("#f6ff00"));
                            rematch(player);
                        });
                    }else if(elements.keySet().toArray()[0].equals("lose")){
                        System.out.println("you lose + "+ player.getName());
                        Platform.runLater(()->{
                            player2.setMain_score(player2.getMain_score()+5);
                            result.setImage(new Image(getClass().getResourceAsStream("/icons/lose.gif")));
                            result.setVisible(true);
                            resultText.setText("You lose");
                            resultText.setVisible(true);
                            resultText.setTextFill(Paint.valueOf("#7c0006"));
                            stopPlay();
                            rematch(player);
                        });
                    }else if(elements.keySet().toArray()[0].equals("draw")){
                        System.out.println("you re even + "+ player.getName());
                        Platform.runLater(()->{
                            result.setImage(new Image(getClass().getResourceAsStream("/icons/even.gif")));
                            result.setVisible(true);
                            resultText.setText("no Winner");
                            resultText.setVisible(true);
                            resultText.setTextFill(Paint.valueOf("#111111"));
                            stopPlay();
                        });
                    }else if(elements.keySet().toArray()[0].equals("rematch")){
                        System.out.println("they will start new Game !!!");
                        Platform.runLater(() -> {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/fxmls/PlayWithFriend.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root);
                            PlayWithFriendController controller = loader.getController();
                            controller.init(player,player2, connectPlayer);

                            Stage window1 = (Stage) borderPane.getScene().getWindow();

                            window1.setScene(scene);
                            window1.show();
                        });
                        break;
                    }else if(elements.keySet().toArray()[0].equals("norematch")){
                        System.out.println("they will not start new Game ???");
                        Platform.runLater(() -> {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root);
                            PlayingModeController controller = loader.getController();
                            controller.init(player, connectPlayer);

                            Stage window1 = (Stage) borderPane.getScene().getWindow();

                            window1.setScene(scene);
                            window1.show();
                        });
                    }else if(elements.keySet().toArray()[0].equals("chat")){
                        elements = connectPlayer.deserialize();
                        String msg = (String) elements.keySet().toArray()[0];

                        Platform.runLater(()->{
                            chatView.appendText(msg);
                            if(!chatRoom.isVisible()){
                                chatBtn.setTextFill(Paint.valueOf("#32c41b"));
                            }
                        });
                    }
                } catch (IOException e) {
                    System.out.println("Lost Connection...");
                    //e.printStackTrace();
                    break;
                }
            }
            }
        });

    }
    public boolean CheckMe(String option) {
        int conut = 0;
        for (int j = 0; j < 3; j++) {
            conut = 0;
            for (int i = j; i < 9; i += 3) {
                if (buttonList.get(i).getText() == option) {
                    conut++;
                }
            }
            if (conut == 3) {
                return true;
            }
        }
        conut = 0;
        for (int j = 0; j < 9; j += 3) {
            conut = 0;
            for (int i = j; i < j+3; i++) {
                if (buttonList.get(i).getText() == option) {
                    conut++;
                    System.out.println("horizontal check buttons: "+i+" "+buttonList.get(i).getText());
                }
            }
            System.out.println("horizontal check count: "+conut);
            if (conut == 3) {
                return true;
            }
        }
        if(buttonList.get(0).getText() == option && buttonList.get(8).getText() == option && buttonList.get(4).getText() == option)
        {
            return true;
        }else if(buttonList.get(6).getText() == option && buttonList.get(4).getText() == option && buttonList.get(2).getText() == option)
        {
            return true;
        }
        return false;
    }
    public boolean isFull()
    {
        for(Button Btn : buttonList)
        {
            if(Btn.getText() == " " || Btn.getText() == "")
            {
                return false;
            }
        }
        return true;
    }

}
