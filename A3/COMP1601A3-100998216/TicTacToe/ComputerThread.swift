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
    init(g:Game){
        game = g
        tempCounter = 0
    }
    func run(ui:ViewController){
        sleep(2000)
        while(game.getResult()==""){
            if(game.place(i: tempCounter, isPlayer: false)){
                ui.update()
                sleep(2000)
            }
            tempCounter+=1
        }
    }
    func findBestMove()->Int{
        let board = game.getGameBoard()
        for _ in board{
            
        }
        return 1;
    }
}
