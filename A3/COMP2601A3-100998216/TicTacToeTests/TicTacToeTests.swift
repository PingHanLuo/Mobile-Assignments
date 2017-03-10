//
//  TicTacToeTests.swift
//  TicTacToeTests
//
//  Created by Robin on 2017-03-05.
//  Copyright © 2017 Robin. All rights reserved.
//

import XCTest
@testable import TicTacToe

class TicTacToeTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testGame(){
        let g:Game = Game()
        assert(g.place(i: 0, isPlayer: true))
        XCTAssertFalse(g.getPlayerTurn())
        XCTAssertFalse(g.place(i: 1, isPlayer: true))
        XCTAssertFalse(g.getPlayerTurn())
        assert(g.place(i: 1, isPlayer: false))
        assert(g.getPlayerTurn())
        assert(g.place(i: 6, isPlayer: false))
        XCTAssertFalse(g.getPlayerTurn())
        assert(g.place(i: 7, isPlayer: false))
        assert(g.place(i: 3, isPlayer: true))
        assert(g.getResult() == "You")
    }
    
}
