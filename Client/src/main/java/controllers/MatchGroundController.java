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
import dbconnection.ListUsers;
import dbconnection.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import dbconnection.PlayerConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
public class MatchGroundController implements Initializable {

    @FXML
    private Label appName;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label name;
    @FXML
    private Label textLoad;
    @FXML
    private Label score;
    @FXML
    private ImageView icon;
    @FXML
    private ImageView load;
    @FXML
    private Button backBtn;
    @FXML
    BorderPane mainPane;
    @FXML
    private Button matchBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private TableView<ListUsers> table ;
    @FXML
    private TableColumn<ListUsers, String> nameCol ;
    @FXML
    private TableColumn<ListUsers, Integer> scoreCol ;
    @FXML
    private TableColumn<ListUsers, ImageView> statusCol ;
    @FXML
    private TableColumn<ListUsers, ImageView> avatarCol ;
    @FXML
    private BorderPane pane1;
    @FXML
    private ImageView reloadImage;
    @FXML
    private Label notifyText;
    @FXML
    private ImageView notifyImage;
    @FXML
    private TextArea chatView;
    @FXML
    private TextField message;
    @FXML
    private Pane chatRoom;
    private PlayerConnection connectPlayer;
    private Player player,myFriend;
    private boolean chatOn = false;
    ArrayList<Player> list;
    Thread request = null;
    Map<String, Player> elements;
    ObservableList<ListUsers> tableData;
    boolean onGame = false;

    @FXML
    private void ImageReload(ActionEvent event) throws IOException
    {
        connectPlayer.serialaize("list",player);
        Platform.runLater(()->{
            notifyText.setVisible(false);
            notifyImage.setVisible(false);
        });
    }


    public void init(Player player,PlayerConnection connectPlayer)
    {
        this.connectPlayer = connectPlayer;
        this.player = player;
        if(!request.isAlive()){
            System.out.println("new Thread is Up now !");
            request.start();
        }else{
            System.out.println("Thread is up already");
        }
        RenderData();

        System.out.println(tableData);
    }
    
    @FXML
    public void playMatch(ActionEvent event) throws IOException
    {
        System.out.println(event);

        //get selected player from list
        ListUsers selectedUser = table.getSelectionModel().getSelectedItem();
        System.out.println("play with : "+selectedUser.getName());
        //making user wait
        Platform.runLater(()->{
            textLoad.setText("Waiting other Player");
            load.setVisible(true);
            table.setOpacity(.3);
            matchBtn.setDisable(true);
            backBtn.setDisable(true);
            textLoad.setVisible(true);
        });
        //sending selected user to play with.
        connectPlayer.serialaize("play",selectedUser.mapToPlayer());

    }
    @FXML
    public void profileButtonPushed(ActionEvent event) throws IOException
    {
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Profile.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        connectPlayer.serialaize("non",player);
        //access the controller and call a method
        ProfileController controller = loader.getController();
       controller.getData(player,true,connectPlayer);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        // animation added here
        new SlideInDown(root).play();
        window.show();
    }
    @FXML
    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
        Scene scene = new Scene(root);
        connectPlayer.serialaize("logout",player);
        connectPlayer.serialaize("non",player);
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        System.out.println(window);
        window.setScene(scene);


        connectPlayer.closeConnection();
        System.out.println("closed");
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }
    @FXML
    public void backButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        connectPlayer.serialaize("non",player);
        //access the controller and call a method
        PlayingModeController controller = loader.getController();
        controller.init(player,connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        // animation added here
        new SlideInLeft(root).play();
        window.show();
    }
    //here we view users on table
    public ObservableList<ListUsers> ViewTable(ArrayList<Player> allPlayers){
        ObservableList<ListUsers> Data = FXCollections.observableArrayList();
        ListUsers user;
        for(Player p : allPlayers) {
            user = new ListUsers(p.getPlayerID(),p.getName(), p.getEmail(),p.getPassword(),p.getMain_score(), p.getStatus(),p.getAvatar());
            if(p.getStatus() == 1)
            {
                user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/on.png"))));
            }else{
                user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/off.png"))));
            }
            user.setAvat(new ImageView(new Image(getClass().getResourceAsStream("/icons/"+p.getAvatar()))));
            Data.add(user);
        }
        return Data;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set up columns
        nameCol.setCellValueFactory(new PropertyValueFactory<ListUsers,String>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<ListUsers,Integer>("main_score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<ListUsers,ImageView>("stat"));
        avatarCol.setCellValueFactory(new PropertyValueFactory<ListUsers,ImageView>("avat"));

        request = new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Player> elements = null;

                System.out.println("enterd Thread of Requests ::::");
                while(!onGame){
                try {
                    if((elements = connectPlayer.deserialize()) != null) {
                        if (elements.keySet().toArray()[0].equals("play")) {
                            Player myFriend = (Player) elements.values().toArray()[0];
                            System.out.println(myFriend.getName() + " wanna play :::");
                            MakeAlert(myFriend);
                            //access the controller and call a method
                        } else if (elements.keySet().toArray()[0].equals("yes")) {
                            Player myFriend = (Player) elements.values().toArray()[0];
                            load.setVisible(false);
                            table.setOpacity(.7);
                            matchBtn.setDisable(false);
                            textLoad.setVisible(false);
                            backBtn.setDisable(false);
                            System.out.println("loading the Game ......");
                            //onGame = true;
                            //connectPlayer.serialaize("non",player);

                            OpenGame(player, myFriend, connectPlayer);
                            System.out.println("main loop break!!!");
                            break;
                        } else if (elements.keySet().toArray()[0].equals("no")) {
                            Player myFriend = (Player) elements.values().toArray()[0];
                            System.out.println("she said no and u are normal now :::::");
                            load.setVisible(false);
                            table.setOpacity(.7);
                            matchBtn.setDisable(false);
                            textLoad.setVisible(false);
                            backBtn.setDisable(false);
                        } else if (elements.keySet().toArray()[0].equals("offline")) {
                            Player myFriend = (Player) elements.values().toArray()[0];
                            System.out.println("offline person :::::");
                            Platform.runLater(() -> {
                                textLoad.setText(myFriend.getName() + " is offline or busy");
                                textLoad.setVisible(true);
                                load.setVisible(false);
                            });
                            Thread.sleep(2000);
                            table.setOpacity(.7);
                            matchBtn.setDisable(false);
                            textLoad.setVisible(false);
                            backBtn.setDisable(false);
                        } else if (elements.keySet().toArray()[0].equals("list")) {
                            System.out.println("should print list");
                            //getting all Users from Server
                            Map<String, ArrayList<Player>> data = connectPlayer.deserializeList();
                            list = (ArrayList<Player>) data.values().toArray()[0];
                            //here you will send the function List of players
                            tableData = ViewTable(list);
                            table.setItems(tableData);
                        }else if (elements.keySet().toArray()[0].equals("notify")) {
                            Player myFriend = (Player) elements.values().toArray()[0];
                            System.out.println("notify");
                            Platform.runLater(()->{
                                notifyText.setText(myFriend.getName()+" is Online");
                                notifyImage.setImage(new Image(getClass().getResourceAsStream("/icons/"+myFriend.getAvatar())));
                                notifyText.setVisible(true);
                                notifyImage.setVisible(true);
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                        }else if(elements.keySet().toArray()[0].equals("globalChat")){
                            elements = connectPlayer.deserialize();
                            String msg = (String) elements.keySet().toArray()[0];
                            Platform.runLater(()->{
                                chatView.appendText(msg);
                                if(!chatRoom.isVisible()){
                                    chatBtn.setTextFill(Paint.valueOf("#32c41b"));
                                }
                            });


                        } else if (elements.keySet().toArray()[0].equals("non")) {
                            break;
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println(elements);
                    break;
                }
                }
            }
        });
    }
    @FXML
    private void SendMsg(ActionEvent event) {
        String msg = message.getText();
        message.clear();
        connectPlayer.serialaize("globalChat",player);
        connectPlayer.serialaize(msg,player);
    }
    @FXML
    private void GlobalChat(ActionEvent event) {
        chatOn = !chatOn;
        chatRoom.setVisible(chatOn);
        chatBtn.setTextFill(Paint.valueOf("#c61a1a"));
    }
    public void OpenGame(Player player,Player myFriend,PlayerConnection connectPlayer){
        Platform.runLater(()->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/PlayWithFriend.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage  window = (Stage)mainPane.getScene().getWindow();
            PlayWithFriendController controller = loader.getController();
            controller.init(player,myFriend,connectPlayer);
            window.setScene(scene);
            window.show();
        });
    }
    public void RenderData(){
        //Render Player Data on Scene
        icon.setImage(new Image(getClass().getResourceAsStream("/icons/"+player.getAvatar())));
        score.setText(Integer.toString(player.getMain_score()));
        name.setText(player.getName());
        //requesting all users from server
        connectPlayer.serialaize("list",player);


    }
    public void MakeAlert(Player friend)
    {
        Platform.runLater(() -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Game Request");
            window.setMinWidth(400);
            window.setMaxHeight(300);
            Label label = new Label();
            label.setText(friend.getName()+ " wanna play with You ! ");
            Button accept = new Button("Accept");
            Button cancel = new Button("Cancel");
            accept.setOnAction(event -> {
                System.out.println("I accept");
                connectPlayer.serialaize("yes",friend);
                window.close();
            });
            cancel.setOnAction(event -> {
                System.out.println("I cancel");
                connectPlayer.serialaize("no",friend);
                window.close();
            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(label,accept,cancel);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
    }
}
