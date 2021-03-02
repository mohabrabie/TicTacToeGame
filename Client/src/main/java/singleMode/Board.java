/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleMode;

import static singleMode.MiniMaxAlgo.Move;
import java.util.Random;

/**
 *
 * @author ahmed
 */
public class Board 
{
    private static int size = 3;
    private Square[][] board;
   
    private int lineWinner;
    
    public static int size() 
    {
        return size;
    }

    public Board(Square[][] board) 
    {
        this.board = board;
    }
    
    public void loadBoard() 
    {
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                board[i][j].loadSquare();
            }
        }
    }
    
    public boolean makeValue(int i, int j)
    {
        if(board[i][j].getValue() == Square.Value.EMPTY)
        {
            board[i][j].setValue(Square.Value.X);
            return true;
        }
        return false;
    }
    
    
    // Easy Level
    public void putRandomValue()
    {
        int[][] mat=new int[size*size][2];
        int k = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(board[i][j].getValue() == Square.Value.EMPTY)
                {
                    mat[k][0]=i;
                    mat[k][1]=j;
                    k++;
                }   
            }
        }
        if(k>0)
        {
            Random r = new Random();
            int m=(int)(k*r.nextDouble())%k;
            board[mat[m][0]][mat[m][1]].setValue(Square.Value.O);
        }

    }
    
    public void hardLevel()
    {
        char AlgoBoard[][] = new char[size][size] ;
        char x = 'x';
        char o ='o';
        char empty = '_';
        
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                if(board[i][j].getValue() == Square.Value.X)
                {
                    AlgoBoard[i][j]= x;
                }
                else if(board[i][j].getValue() == Square.Value.O)
                {
                    AlgoBoard[i][j]= o;
                }
                
                else if(board[i][j].getValue() == Square.Value.EMPTY)
                {
                    AlgoBoard[i][j]= empty;
                }
            }
        }
        
        Move bestMove = MiniMaxAlgo.findBestMove(AlgoBoard);
        int row = bestMove.row;
        int col = bestMove.col;
        
        board[row][col].setValue(Square.Value.O);
        
    }
    
    public void mediumLevel()
    {
        //Checking The first row
        if(board[0][1].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[0][2].getValue() == Square.Value.X)
        {
            board[0][1].setValue(Square.Value.O);
        }
        
        //Checking The second row
        else if(board[0][1].getValue() == Square.Value.EMPTY && board[1][0].getValue() == Square.Value.X && board[1][2].getValue() == Square.Value.X)
        {
            board[0][1].setValue(Square.Value.O);
        }
        
        //Checking The third row
        else if(board[2][1].getValue() == Square.Value.EMPTY && board[2][0].getValue() == Square.Value.X && board[2][2].getValue() == Square.Value.X)
        {
            board[2][1].setValue(Square.Value.O);
        }
        
        //Checking the first Column
        else if(board[0][1].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[2][0].getValue() == Square.Value.X)
        {
            board[0][1].setValue(Square.Value.O);
        }
        
        //Checking the second Column
        else if(board[1][1].getValue() == Square.Value.EMPTY && board[0][1].getValue() == Square.Value.X && board[2][1].getValue() == Square.Value.X)
        {
            board[1][1].setValue(Square.Value.O);
        }
        
        //Checking the third Column
        else if(board[1][2].getValue() == Square.Value.EMPTY && board[0][2].getValue() == Square.Value.X && board[2][2].getValue() == Square.Value.X)
        {
            board[1][2].setValue(Square.Value.O);
        }
        
        //Checking /
        else if( board[1][1].getValue() == Square.Value.EMPTY && board[0][2].getValue() == Square.Value.X && board[2][0].getValue() == Square.Value.X)
        {
            board[1][1].setValue(Square.Value.O);
        }
        //Checking \
        else if(board[1][1].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[2][2].getValue() == Square.Value.X)
        {
            board[1][1].setValue(Square.Value.O);
        }
        
        //****************Checking after-before *****************
        
        //Checking first row from right
        else if(board[0][0].getValue() == Square.Value.EMPTY && board[0][2].getValue() == Square.Value.X && board[0][1].getValue() == Square.Value.X)
        {
            board[0][0].setValue(Square.Value.O);
        }
        //Checking second row from right
        else if(board[1][0].getValue() == Square.Value.EMPTY && board[2][1].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[1][0].setValue(Square.Value.O);
        }
        //Checking third row from right
        else if(board[2][0].getValue() == Square.Value.EMPTY && board[2][2].getValue() == Square.Value.X && board[2][1].getValue() == Square.Value.X)
        {
            board[2][0].setValue(Square.Value.O);
        }
        
        //Checking first row from left
        else if(board[0][2].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[0][1].getValue() == Square.Value.X)
        {
            board[0][2].setValue(Square.Value.O);
        }
        //Checking second row from left
        else if(board[1][2].getValue() == Square.Value.EMPTY && board[1][0].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[1][2].setValue(Square.Value.O);
        }
        //Checking third row from left
        else if(board[2][2].getValue() == Square.Value.EMPTY && board[2][0].getValue() == Square.Value.X && board[2][1].getValue() == Square.Value.X)
        {
            board[2][2].setValue(Square.Value.O);
        }
        
        //Checking the first col from up down
        else if(board[2][0].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[1][0].getValue() == Square.Value.X)
        {
            board[2][0].setValue(Square.Value.O);
        }
        //Checking the second col from up down
        else if(board[2][1].getValue() == Square.Value.EMPTY && board[0][1].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[2][1].setValue(Square.Value.O);
        }
        //Checking the third col from up down
        else if(board[2][2].getValue() == Square.Value.EMPTY && board[0][2].getValue() == Square.Value.X && board[1][2].getValue() == Square.Value.X)
        {
            board[2][2].setValue(Square.Value.O);
        }
        
        //Checking the first col from down up
        else if(board[0][0].getValue() == Square.Value.EMPTY && board[2][0].getValue() == Square.Value.X && board[1][0].getValue() == Square.Value.X)
        {
            board[0][0].setValue(Square.Value.O);
        }
        //Checking the second col from down up
        else if(board[0][1].getValue() == Square.Value.EMPTY && board[2][1].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[0][1].setValue(Square.Value.O);
        }
        //Checking the third col from down up
        else if(board[0][2].getValue() == Square.Value.EMPTY && board[2][2].getValue() == Square.Value.X && board[2][1].getValue() == Square.Value.X)
        {
            board[0][2].setValue(Square.Value.O);
        }
        
        //Checking \ before-after - up down
         else if(board[2][2].getValue() == Square.Value.EMPTY && board[0][0].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[2][2].setValue(Square.Value.O);
        }
          //Checking \ before-after -  down up
          else if(board[0][0].getValue() == Square.Value.EMPTY && board[2][2].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[0][0].setValue(Square.Value.O);
        }
          
          //Checking / before-after - up down
           else if(board[2][0].getValue() == Square.Value.EMPTY && board[0][2].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[2][0].setValue(Square.Value.O);
        }
          else if(board[0][2].getValue() == Square.Value.EMPTY && board[2][0].getValue() == Square.Value.X && board[1][1].getValue() == Square.Value.X)
        {
            board[0][2].setValue(Square.Value.O);
        }
        
        
        else
        {
            putRandomValue();
        }

    }
    
    public boolean isWinner(Square.Value val)
    {
        boolean youWin = true;
        
        // Checking every row
        for(int i = 0; i < size; i++)
        {
            youWin = true;
            for(int j = 0; j < size; j++)
            {
                if(board[i][j].getValue() != val)
                {
                    youWin = false;
                    break;
                }
            }
            if(youWin)
            {
                lineWinner = i;
                return true;
            }
        }
        
        // Checking every Column
        for(int j = 0; j < size; j++)
        {
            youWin = true;
            for(int i = 0; i < size; i++)
            {
                if(board[i][j].getValue() != val)
                {
                    youWin = false;
                    break;
                }
            }
            if(youWin)
            {
                lineWinner = size+j;
                return true;
            }
        }
        
        // Checking this cells \
        youWin = true;
        for(int i = 0; i < size; i++)
        {
            if(board[i][i].getValue() != val)
            {
                youWin = false;
                break;
            }
        }
        if(youWin)
        {
            lineWinner=size+size;
            return true;
        }
        
        // Checking this cells /
        youWin = true;
        for(int i = 0; i < size; i++)
        {
            if(board[size-1-i][i].getValue()!=val)
            {
                youWin = false;
                break;
            }
        }
        if(youWin)
        {
            lineWinner = size + size + 1;
            return true;
        }
        return false;
    }
    
    
    public boolean isFull()
    {
        for(int i=0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(board[i][j].getValue() == Square.Value.EMPTY)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void makeAnimationWinner()
    {
        if(lineWinner < size)
        {
            for(int j = 0; j < size; j++)
            {
                board[lineWinner][j].setIsWinnerSquare(true);
            }
        }
        else if(lineWinner < 2*size)
        {
            for(int i = 0; i < size; i++)
            {
                board[i][lineWinner-size].setIsWinnerSquare(true);
            }
        }
        else if(lineWinner == 2*size)
        {
            for(int i = 0; i < size; i++)
            {
                board[i][i].setIsWinnerSquare(true);
            }
        }
        else if(lineWinner == 2*size+1)
        {
            for(int i = 0; i < size; i++)
            {
                board[size-1-i][i].setIsWinnerSquare(true);
            }
        }
    }
}