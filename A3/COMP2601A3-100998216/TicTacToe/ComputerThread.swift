//
//  ComputerThread.swift
//  COMP1601A3-100998216
//
//  Created by Robin on 2017-03-08.
//  Copyright © 2017 Robin. All rights reserved.
//

import Foundation
class ComputerThread{
    var game:Game
    var running:Bool
    init(g:Game){
        game = g
        running = true;
    }
    
    func stop(){
        running = false
    }
    
    func run(ui:ViewController){
        sleep(2)
        while(running){
            let move:Int = findBestMove()
            if(game.place(i: move, isPlayer: false)){
                if(game.getPlayerTurn()){
                    ui.compPlace(index: move, forPlayer: false)
                }else{
                    ui.compPlace(index: move, forPlayer: true)
                }
                ui.checkEnd()
                sleep(2)
            }
            if(game.getResult() != ""){
                running = false
            }
        }
    }
    func findBestMove()->Int{
        //this is the array of the board of [Character]
        let board = game.getGameBoard()
        let turn = game.getPlayerTurn()
        var isEmpty:Bool = true;
        var emptySpaces:Int = 9;
        var temp:Int = 0;
        //loop through the board to see what pieces there are
        for i in 0...8{
            
            if(board[i] != " "){
                emptySpaces = emptySpaces-1
                isEmpty = false;
            }
        }
        //make the optimal move given the situation. Check for 2 in a row for O? then for X?
        //Empty board (first move)
        if(isEmpty == true){
            return 0
        }
        //Comp's first move playing center
        if(emptySpaces == 8 && turn == false){
            if(board[4] != " "){
                return 0 //if X in mid then go corner
            }
            else{ //go middle
                return 4
            }
        }
        
        //check for 2 in a row to win
        //can only be possible on 5th turn or later
        if(emptySpaces < 6){
            if(turn == true){
                //diagonal check
                if(board[0] == "x" && board[0] == board[4] && board[8] == " "){
                    return 8
                }
                else if(board[8] == "x" && board[8] == board[4] && board[0] == " "){
                    return 0
                }
                else if(board[2] == "x" && board[2] == board[4] && board[6] == " "){
                    return 6
                }
                else if(board[6] == "x" && board[6] == board[4] && board[2] == " "){
                    return 2
                }
                //horizontal check
                temp = 0;
                while(temp < 8){
                    if((temp+1)%3 == 0){ //right side piece
                        if(board[temp] == "x" && board[temp] == board[temp-2] && board[temp-1] == " "){
                            return temp-1;
                        }
                    }
                    else if((temp+3)%3 == 0){ //left side
                        if(board[temp] == "x" && board[temp] == board[temp+1] && board[temp+2] == " "){
                            return temp+2
                        }
                    }
                    else if((temp+2)%3 == 0){ //middle
                        if(board[temp] == "x" && board[temp] == board[temp+1] && board[temp-1] == " "){
                            return temp-1
                        }
                    }
                    temp = temp+1
                }
                //vertical check
                temp = 0;
                while(temp < 3){
                    if(board[temp] == "x" && board[temp] == board[temp+3] && board[temp+6] == " "){
                        return temp+6
                    }
                    else if(board[temp] == "x" && board[temp] == board[temp+6] && board[temp+3] == " "){
                        return temp+3
                    }
                    else if(board[temp+3] == "x" && board[temp+3] == board[temp+6] && board[temp] == " "){
                        return temp
                    }
                    temp = temp + 1
                }
            }
                //circle's turn to check for 2 in a row
            else if(turn == false){
                //diagonal check
                if(board[0] == "o" && board[0] == board[4] && board[8] == " "){
                    return 8
                }
                else if(board[8] == "o" && board[8] == board[4] && board[0] == " "){
                    return 0
                }
                else if(board[2] == "o" && board[2] == board[4] && board[6] == " "){
                    return 6
                }
                else if(board[6] == "o" && board[6] == board[4] && board[2] == " "){
                    return 2
                }
                //horizontal check
                temp = 0;
                while(temp < 8){
                    if((temp+1)%3 == 0){ //right side piece
                        if(board[temp] == "o" && board[temp] == board[temp-2] && board[temp-1] == " "){
                            return temp-1;
                        }
                    }
                    else if((temp+3)%3 == 0){ //left side
                        if(board[temp] == "o" && board[temp] == board[temp+1] && board[temp+2] == " "){
                            return temp+2
                        }
                    }
                    else if((temp+2)%3 == 0){ //middle
                        if(board[temp] == "o" && board[temp] == board[temp+1] && board[temp-1] == " "){
                            return temp-1
                        }
                    }
                    temp = temp+1
                }
                //vertical check
                temp = 0;
                while(temp < 3){
                    if(board[temp] == "o" && board[temp] == board[temp+3] && board[temp+6] == " "){
                        return temp+6
                    }
                    else if(board[temp] == "o" && board[temp] == board[temp+6] && board[temp+3] == " "){
                        return temp+3
                    }
                    else if(board[temp+3] == "o" && board[temp+3] == board[temp+6] && board[temp] == " "){
                        return temp
                    }
                    temp = temp + 1
                }
            }
        }
        //check for a regular block
        //only possible on 3rd turn or later
        if(emptySpaces < 7){
            //diagonal check
            if(board[0] != " " && board[0] == board[4] && board[8] == " "){
                return 8
            }
            else if(board[8] != " " && board[8] == board[4] && board[0] == " "){
                return 0
            }
            else if(board[2] != " " && board[2] == board[4] && board[6] == " "){
                return 6
            }
            else if(board[6] != " " && board[6] == board[4] && board[2] == " "){
                return 2
            }
            //horizontal check
            temp = 0;
            while(temp < 8){
                if((temp+1)%3 == 0){ //right side piece
                    if(board[temp] != " " && board[temp] == board[temp-2] && board[temp-1] == " "){
                        return temp-1;
                    }
                }
                else if((temp+3)%3 == 0){ //left side
                    if(board[temp] != " " && board[temp] == board[temp+1] && board[temp+2] == " "){
                        return temp+2
                    }
                }
                else if((temp+2)%3 == 0){ //middle
                    if(board[temp] != " " && board[temp] == board[temp+1] && board[temp-1] == " "){
                        return temp-1
                    }
                }
                temp = temp+1
            }
            //vertical check
            temp = 0;
            while(temp < 3){
                if(board[temp] != " " && board[temp] == board[temp+3] && board[temp+6] == " "){
                    return temp+6
                }
                else if(board[temp] != " " && board[temp] == board[temp+6] && board[temp+3] == " "){
                    return temp+3
                }
                else if(board[temp+3] != " " && board[temp+3] == board[temp+6] && board[temp] == " "){
                    return temp
                }
                temp = temp + 1
            }
        }
        
        //Forking and blocking forks
        //TO DO
        
        //Playing in opposite corner
        //TO DO
        
        //playing corner
        if(board[0] == " "){
            return 0;
        }
        else if(board[8] == " "){
            return 8;
        }
        else if(board[2] == " "){
            return 2;
        }
        else if(board[6] == " "){
            return 6;
        }
            //playing side
        else if(board[1] == " "){
            return 1;
        }
        else if(board[7] == " "){
            return 7;
        }
        else if(board[3] == " "){
            return 3;
        }
        else if(board[5] == " "){
            return 5;
        }
        return 4;
    }
}
