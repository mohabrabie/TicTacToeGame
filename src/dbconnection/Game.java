/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

/**
 *
 * @author Aya Abdulsamie
 */

public class Game {
     private int gameID;
     private int p1_ID;
     private int p2_ID;
     private int p1_score;
     private int p2_score;

    public Game(int gameID, int p1_ID, int p2_ID, int p1_score, int p2_score) {
        this.gameID = gameID;
        this.p1_ID = p1_ID;
        this.p2_ID = p2_ID;
        this.p1_score = p1_score;
        this.p2_score = p2_score;

    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getP1_ID() {
        return p1_ID;
    }

    public void setP1_ID(int p1_ID) {
        this.p1_ID = p1_ID;
    }

    public int getP2_ID() {
        return p2_ID;
    }

    public void setP2_ID(int p2_ID) {
        this.p2_ID = p2_ID;
    }

    public int getP1_score() {
        return p1_score;
    }

    public void setP1_score(int p1_score) {
        this.p1_score = p1_score;
    }

    public int getP2_score() {
        return p2_score;
    }

    public void setP2_score(int p2_score) {
        this.p2_score = p2_score;
    }
   
}
