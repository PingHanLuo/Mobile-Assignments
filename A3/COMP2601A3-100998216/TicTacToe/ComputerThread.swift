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
    var tempCounter:Int
    var running:Bool
    init(g:Game){
        game = g
        running = true;
        tempCounter = 0
    }
    
    func stop(){
        running = false
    }
    
    func run(ui:ViewController){
        sleep(2)
        while(running){
            if(game.place(i: tempCounter, isPlayer: false)){
                if(game.getPlayerTurn()){
                    ui.compPlace(index: tempCounter, forPlayer: false)
                }else{
                    ui.compPlace(index: tempCounter, forPlayer: true)
                }
                ui.checkEnd()
                sleep(2)
            }
            tempCounter+=1
            if(game.getResult() != ""){
                running = false
            }
        }
    }
    func findBestMove()->Int{
        //this is the array of the board of [Character]
        let board = game.getGameBoard()
        //loop through the board to see what pieces there are
        for _ in board{
            
        }
        //make the optimal move given the situation. Check for 2 in a row for O? then for X?
        return 1;
    }
}
