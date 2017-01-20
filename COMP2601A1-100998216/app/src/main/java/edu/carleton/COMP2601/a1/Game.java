package edu.carleton.COMP2601.a1;

/**
 * Created by Luo on 2017-01-18.
 */

public class Game {
    protected char[] gameBoard;
    protected boolean playerTurn;
    protected int placed;
    public Game(){
        this.gameBoard = new char[9];
        for(char a : gameBoard){
            a = ' ';
        }
        this.playerTurn = true;
        this.placed = 0;
    }
    //places a square for a person
    synchronized public boolean place(int i, boolean isPlayer){
        if(checkSquare(i)) {
            if (playerTurn) {
                gameBoard[i] = 'x';
                playerTurn = false;
            } else {
                if(isPlayer){
                    //unauthorized, player cannot play for computer
                    return false;
                }
                gameBoard[i] = 'o';
                playerTurn = true;
            }
            placed++;
            return true;
        }else{
            //cannot place on already placed tile
            return false;
        }
    }
    //check to see if square it empty
    protected boolean checkSquare(int i){
        if(gameBoard[i] == ' '){
            return true;
        }else{
            return false;
        }
    }
    //checks if the game is over
    public boolean checkEnd(int n){
        if(placed == 9){
            return true;
        }
        if(n%2==0){
            //check diagonal
            if(gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]){
                return true;
            }else if(gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]){
                return true;
            }
        }
        //check horizontal and vertical
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if(gameBoard[(3*i)+j]==gameBoard[(3*i)+j+1]&&gameBoard[(3*i)+j+1]==gameBoard[(3*i)+j+2]){
                    return true;
                }else if(gameBoard[(3*j)+i]==gameBoard[(3*j)+i+1]&&gameBoard[(3*j)+i+1]==gameBoard[(3*j)+i+2]){
                    return true;
                }
            }
        }
        return false;
    }
}
