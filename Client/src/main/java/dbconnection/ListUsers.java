/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import javafx.scene.image.ImageView;

/**
 *
 * @author Aya Abdulsamie
 */
public class ListUsers {
    private int playerID;
    private String name;
    private String email;
    private String password;
    private int main_score;
    private int status;
    private String avatar;
    private ImageView stat;
    private ImageView avat;

    public ListUsers() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public ListUsers(int playerID, String name, String email, String password, int main_score, int status, String avatar) {
        this.playerID = playerID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.main_score = main_score;
        this.status = status;
        this.avatar = avatar;
    }

    public ListUsers(String name, int main_score, int status) {
        this.name = name;
        this.main_score = main_score;
        this.status = status;
    }
    public ListUsers(String name, String email, int main_score, int status)
    {
        this.name = name;
        this.email = email;
        this.main_score = main_score;
        this.status = status;
    }
    public ListUsers(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public ListUsers(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public ListUsers(String email) {
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
    public void setAvat(ImageView avat) {
        this.avat = avat;
        this.avat.setFitHeight(40);
        this.avat.setFitWidth(40);

    }

    public ImageView getAvat() {
        return avat;
    }

    public ImageView getStat() {
        return stat;
    }
    public void setStat(ImageView stat) {
        this.stat = stat;
        this.stat.setFitHeight(40);
        this.stat.setFitWidth(40);
    }
    public Player mapToPlayer(){
        Player p = new Player();
        p.setMain_score(this.main_score);
        p.setName(this.name);
        p.setEmail(this.email);
        p.setPlayerID(this.playerID);
        p.setAvatar(this.avatar);
        p.setStatus(this.status);
        p.setPassword(this.password);
        return p;
    }
}
