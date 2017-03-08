//
//  ViewController.swift
//  TicTacToe
//
//  Created by Robin on 2017-03-05.
//
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var result: UILabel!
    var g:Game?
    var ct:ComputerThread?
    
    //9 buttons outlets
    @IBOutlet weak var b1: UIButton!
    @IBOutlet weak var b2: UIButton!
    @IBOutlet weak var b3: UIButton!
    @IBOutlet weak var b4: UIButton!
    @IBOutlet weak var b5: UIButton!
    @IBOutlet weak var b6: UIButton!
    @IBOutlet weak var b7: UIButton!
    @IBOutlet weak var b8: UIButton!
    @IBOutlet weak var b9: UIButton!
    //collection array for buttons
    var buttons:[UIButton]?
    
    
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        setUp()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func Place(_ sender: UIButton) {
        if g!.place(i: sender.tag, isPlayer: true){
            sender.setImage(#imageLiteral(resourceName: "button_x.png"), for: UIControlState.normal)
            update()
        }
    }

    @IBAction func Toggle(_ sender: UIButton) {
        if sender.currentTitle == "Start"{
            sender.setTitle("End Game", for: UIControlState.normal)
            DispatchQueue.global().async{ self.ct!.run(ui: self)}
        }else{
            sender.setTitle("Start", for: UIControlState.normal)
        }
    }
    
    //state true means start game, false means end game
    func changeGameState(state:Bool){
        if(state){
            g = Game()
            ct = ComputerThread(g: g!)
            update()
        }else{
            for i in 0...8{
                let tmpButton = self.view.viewWithTag(i) as? UIButton
                tmpButton!.isEnabled = false
            }
        }
    }
    
    func setUp(){
        g = Game()
        ct = ComputerThread(g: g!)
        //connect all buttons
        buttons = Array(arrayLiteral: b1,b2,b3,b4,b5,b6,b7,b8,b9)
        
    }
    
    func update(){
        var board = g!.getGameBoard()
        for i in 0...8{
            if(board[i] == " "){
                buttons![i].setImage(#imageLiteral(resourceName: "button_empty.png"), for: UIControlState.normal)
            }else if(board[i] == "x"){
                buttons![i].setImage(#imageLiteral(resourceName: "button_x.png"), for: UIControlState.normal)
            }else{
                buttons![i].setImage(#imageLiteral(resourceName: "button_o.png"), for: UIControlState.normal)
            }
        }
    }
    
}

