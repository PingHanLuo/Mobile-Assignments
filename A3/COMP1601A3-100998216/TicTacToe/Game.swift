//
//  Game.swift
//  COMP1601A3-100998216
//
//  Created by Robin on 2017-03-05.
//
//

import Foundation

class Game {
    private var gameBoard:[Character]
    private var playerTurn:Bool
    private var placed:Int
    private var winner:String
    private var lastMove:String
    init() {
        gameBoard = []
        for i in 0...8{
            gameBoard.insert(" ", at: i)
        }
        playerTurn = true
        placed = 0
        winner = ""
        lastMove = ""
    }
    
    func getGameBoard()->[Character]{
        return gameBoard
    }
    
    func getRecentSymbol()->Character{
        if(playerTurn){
            return "o"
        }
        return "x"
    }
    func getLastMove()->String{return lastMove}
    func getResult()->String{return winner}
    
    func place(i:Int,isPlayer:Bool)->Bool{
        if(checkSquare(i: i)) {
            if (playerTurn) {
                gameBoard[i] = "x"
                playerTurn = false
            } else {
                if(isPlayer){
                    //unauthorized, player cannot play for computer
                    return false
                }
                gameBoard[i] = "o"
                playerTurn = true
            }
            placed += 1
            checkEnd(n: i)
            return true
        }else{
            //cannot place on already placed tile
            return false
        }
    }
    
    private func checkSquare(i:Int)->Bool{
        if(gameBoard[i] == " " && winner == ""){
            return true
        }else{
            return false
        }
    }
    
    private func checkEnd(n:Int){
    //9 moves are made. The game is over
        if(placed == 9){
            winner="No one"
        }
        if(n%2 == 0){
            //check diagonal
            if(gameBoard[4] != " ") {
                if (gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) {
                    if (gameBoard[4] == "x"){
                        winner = "You"
                    }else{
                        winner = "Computer"
                    }
                }else if (gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]) {
                    if (gameBoard[4] == "x"){
                        winner = "You"
                    }else{
                        winner = "Computer"
                    }
                }
            }
        }
        //check horizontal and vertical
        //find first cell in the column
        let vertical:Int = n%3
        //find first cell in the row
        let horizontal:Int = (n/3)*3
        if(gameBoard[vertical] == gameBoard[vertical+3] && gameBoard[vertical+3] == gameBoard[vertical+6]){
            if(gameBoard[vertical] == "x"){
                winner = "You"
            }else{
                winner = "Computer"
            }
        }else if(gameBoard[horizontal] == gameBoard[horizontal+1] && gameBoard[horizontal+1] == gameBoard[horizontal+2]){
            if(gameBoard[horizontal] == "x"){
                winner = "You"
            }else{
                winner = "Computer"
            }
        }
    }
}
