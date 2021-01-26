/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.scene.image.ImageView;

/**
 *
 * @author Aya Abdulsamie
 */
public class Player {
    private int playerID;
    private String name;
    private String email;
    private String password;
    private int main_score;
    private int status;
    private String avatar;


    public Player() {
        System.out.println("Hi from default constructor.");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Player(int playerID, String name, String email, String password, int main_score, int status, String avatar) {
        this.playerID = playerID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.main_score = main_score;
        this.status = status;
        this.avatar = avatar;
    }

    public Player(String name, int main_score, int status) {
        this.name = name;
        this.main_score = main_score;
        this.status = status;
    }
    public Player(String name, String email,int main_score,int status)
    {
        this.name = name;
        this.email = email;
        this.main_score = main_score;
        this.status = status;
    }
    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Player(String email) {
        this.email = email;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMain_score() {
        return main_score;
    }

    public void setMain_score(int main_score) {
        this.main_score = main_score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return "Player [playerID=" + playerID + ", name=" + name
                + ", email=" + email + ", password=" + password
                + ", main_score=" + main_score + ", status=" + status
                + ", avatar=" + avatar +  "]";
    }
    public String jsonToString(){

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this);

            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
