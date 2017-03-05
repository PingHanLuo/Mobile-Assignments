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
    static var g:Game = Game()
    
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
        if ViewController.g.place(i: sender.tag, isPlayer: true){
            sender.setImage(#imageLiteral(resourceName: "button_x.png"), for: UIControlState.normal)
        }
    }

    @IBAction func Toggle(_ sender: UIButton) {
        if sender.currentTitle == "Start"{
            sender.setTitle("End Game", for: UIControlState.normal)
        }else{
            sender.setTitle("Start", for: UIControlState.normal)
        }
    }
    
    func setUp(){
        
    }
    
}

