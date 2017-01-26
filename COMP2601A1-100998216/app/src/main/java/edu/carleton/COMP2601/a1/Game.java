package edu.carleton.COMP2601.a1;

/**
 * Created by Luo on 2017-01-18.
 */
//git status - checks which files are new
public class Game {
    private char[] gameBoard;
    private boolean playerTurn;
    private int placed;
    private String winner;
    private String lastMove;
    public Game(){
        this.gameBoard = new char[9];
        for(int i=0;i<gameBoard.length;i++){
            //initialize every cell at ' '
            gameBoard[i] = ' ';
        }
        this.playerTurn = true;
        this.placed = 0;
        this.winner="";
        this.lastMove="";
    }
    public String getLastMove(){return this.lastMove;}
    public String getResult(){return  this.winner;}
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
        if(gameBoard[i] == ' '){
            return true;
        }else{
            return false;
        }
    }
    //checks if the game is over
    private void checkEnd(int n){
        if(n%2==0){
            //check diagonal
            if(gameBoard[4]!=' ') {
                if (gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) {
                    if (gameBoard[4] == 'x')
                        this.winner = "You";
                    else
                        this.winner = "Computer";
                } else if (gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]) {
                    if (gameBoard[4] == 'x')
                        this.winner = "You";
                    else
                        this.winner = "Computer";
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
                this.winner="You";
            else
                this.winner="Computer";
        }else if(gameBoard[horizontal]==gameBoard[horizontal+1]&&gameBoard[horizontal+1]==gameBoard[horizontal+2]){
            if(gameBoard[horizontal]=='x')
                this.winner="You";
            else
                this.winner="Computer";
        }
        //9 moves are made. The game is over
        if(placed == 9){
            this.winner="No one";
        }
    }
}
