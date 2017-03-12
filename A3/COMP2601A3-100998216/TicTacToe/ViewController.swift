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
    var bo:BoardObserver?
    
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
    @IBOutlet weak var start: UIButton!
    
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
            result.text = "Button " + sender.tag.description + " pressed."
            checkEnd()
        }
    }

    @IBAction func Toggle(_ sender: UIButton) {
        if sender.currentTitle == "Start"{
            sender.setTitle("Running", for: UIControlState.normal)
            changeGameState(state: true)
        }else{
            ct!.stop()
            changeGameState(state: false)
            result.text = "Game ended."
        }
    }
    
    //state true means start game, false means end game
    func changeGameState(state:Bool){
        if(state){
            g = Game()
            bo = BoardObserver(controller: self,game: g!)
            ct = ComputerThread(g: g!)
            for i in 0...8{
                buttons![i].isEnabled = true
            }
            result.text = ""
            DispatchQueue.global().async{ self.ct!.run(ui: self)}
        }else{
            for i in 0...8{
                buttons![i].isEnabled = false
            }
            start.setTitle("Start", for: UIControlState.normal)
        }
    }
    
    func setUp(){
        g = Game()
        bo = BoardObserver(controller: self, game: g!)
        ct = ComputerThread(g: g!)
        //connect all buttons
        buttons = Array(arrayLiteral: b1,b2,b3,b4,b5,b6,b7,b8,b9)
    }
    
    func update(){
        var board = g!.getGameBoard()
        DispatchQueue.main.async {
            for i in 0...8{
                if(board[i] == " "){
                    self.buttons![i].setImage(#imageLiteral(resourceName: "button_empty.png"), for: UIControlState.normal)
                }else if(board[i] == "x"){
                    self.buttons![i].setImage(#imageLiteral(resourceName: "button_x"), for: UIControlState.normal)
                }else{
                    self.buttons![i].setImage(#imageLiteral(resourceName: "button_o.png"), for: UIControlState.normal)
                }
            }
        }
    }
    
    func compPlace(index:Int, forPlayer:Bool){
        DispatchQueue.main.async {
            self.result.text = "Button " + index.description + " pressed."
        }
    }
    
    func checkEnd(){
        DispatchQueue.main.async {
            if(self.g!.getResult() != ""){
                self.result.text = "Game is over. " + self.g!.getResult() + " won!"
                self.changeGameState(state: false)
            }
        }
    }
}

