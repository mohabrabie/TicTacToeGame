/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleMode;


import animatefx.animation.BounceIn;
import animatefx.animation.Flash;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ahmed
 */
public class Square  
{
    private AnchorPane containerOfX;
    private AnchorPane containerOfO;
    
    
    private Value value = Value.EMPTY;
    private boolean isWinnerSquare = false;
    
    public enum Value
    {
        X, O, EMPTY
    }

    public Square(AnchorPane containerOfX, AnchorPane containerOfO) 
    {
        this.containerOfX = containerOfX;
        this.containerOfO = containerOfO;
    }
    
    
    public void loadSquare()
    {
        value = Value.EMPTY;
        displaySquare();
    }
    
    
    public void displaySquare()
    {
        switch(value)
        {
            case O:
                containerOfX.setVisible(false);
                containerOfO.setVisible(true);
                new BounceIn(containerOfO).play();
                break;
            case X:
                containerOfO.setVisible(false);
                containerOfX.setVisible(true);
                new BounceIn(containerOfX).play();
                break;
            default:
                containerOfO.setVisible(false);
                containerOfX.setVisible(false);
        }
    }

    public Value getValue() 
    {
        return value;
    }

    public void setValue(Value value) 
    {
        this.value = value;
        displaySquare();
    }
    
    public void makeAnimation()
    {
       if(isWinnerSquare) {
            if(value == Value.O)
            {
                new Flash(containerOfO).setCycleCount(2).play();
            }
            else if(value == Value.X)
               {
                new Flash(containerOfX).setCycleCount(2).play();
            }
            isWinnerSquare = false;
        }
    }

    

    public boolean isIsWinnerSquare() 
    {
        return isWinnerSquare;
    }

    public void setIsWinnerSquare(boolean isWinnerSquare) 
    {
        this.isWinnerSquare = isWinnerSquare;
        makeAnimation();
    }  
    
}
