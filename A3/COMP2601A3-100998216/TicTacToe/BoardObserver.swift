//
//  BoardObserver.swift
//  COMP2601A3-100998216
//
//  Created by Robin on 2017-03-12.
//  Copyright © 2017 Robin. All rights reserved.
//

import Foundation

class BoardObserver : Observer{
    
    var controller:ViewController
    
    init(controller:ViewController, game:Game){
        self.controller = controller
        game.attachObserver(observer: self)
    }
    
    func update() {
        controller.update()
    }
}
