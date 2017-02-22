package edu.carleton.COMP2601;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luo on 2017-02-18.
 */

public class Game {
    /*Robin Luo 100998216
  Michael Kameoka 100980710 */
    private char[] gameBoard;
    private boolean playerTurn;
    private int placed;
    private String winner;
    private String lastMove;
    private String player1, player2;
    Set<Integer> numSet;
    public Game(String p1, String p2){
        this.gameBoard = new char[9];
        for(int i=0;i<gameBoard.length;i++){
            //initialize every cell at ' '
            gameBoard[i] = ' ';
        }
        player1 = p1;
        player2 = p2;
        this.playerTurn = true;
        this.placed = 0;
        this.winner="";
        this.lastMove="";
        this.numSet = new HashSet<Integer>();
    }
    //get the x or o to place AFTER place have been called
    public char getRecentSymbol(){
        if(playerTurn){
            return 'o';
        }
        return 'x';
    }
    //get the symbol that was just placed
    public char getPreviousSymbol(){
        if(playerTurn){
            return 'o';
        }
        return 'x';
    }
    public String getLastMove(){return this.lastMove;}
    public String getResult(){return  this.winner;}
    //places a square for a person
    //turn will be false for p2 and true for p1
    synchronized public boolean place(int i, String name){
        boolean turn = false;
        if(player1.equals(name)){
            turn = true;
        }
        if(turn != playerTurn){
            //unauthorized it is not that player's turn
            return false;
        }
        if(checkSquare(i)) {
            if (playerTurn) {
                gameBoard[i] = 'x';
                playerTurn = false;
            } else {
                gameBoard[i] = 'o';
                playerTurn = true;
            }
            placed++;
            checkEnd(i);
            return true;
        }else{
            //cannot place on already placed tile
            return false;
        }
    }
    //check to see if square it empty
    //double security in this case since Main Activity should have already disabled it
    private boolean checkSquare(int i){
        if(gameBoard[i] == ' ' && winner.equals("")){
            return true;
        }else{
            return false;
        }
    }
    //checks if the game is over
    private void checkEnd(int n){
        //9 moves are made. The game is over
        if(placed == 9){
            this.winner="No One";
        }
        if(n%2==0){
            //check diagonal
            if(gameBoard[4]!=' ') {
                if (gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) {
                    if (gameBoard[4] == 'x')
                        this.winner = player1;
                    else
                        this.winner = player2;
                } else if (gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]) {
                    if (gameBoard[4] == 'x')
                        this.winner = player1;
                    else
                        this.winner = player2;
                }
            }
        }
        //check horizontal and vertical
        //find first cell in the column
        int vertical = n%3;
        //find first cell in the row
        int horizontal = (n/3)*3;
        if(gameBoard[vertical]==gameBoard[vertical+3]&&gameBoard[vertical+3]==gameBoard[vertical+6]){
            if(gameBoard[vertical]=='x')
                this.winner=player1;
            else
                this.winner=player2;
        }else if(gameBoard[horizontal]==gameBoard[horizontal+1]&&gameBoard[horizontal+1]==gameBoard[horizontal+2]){
            if(gameBoard[horizontal]=='x')
                this.winner=player1;
            else
                this.winner=player2;
        }
    }
}
