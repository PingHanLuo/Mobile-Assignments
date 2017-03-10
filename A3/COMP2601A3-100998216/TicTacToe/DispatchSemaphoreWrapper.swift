//
//  DispatchSemaphoreWrapper.swift
//  BankAccountClosure
//
//  Created by Tony White on 2017-03-05.
//  Copyright © 2017 Tony White. All rights reserved.
//

import Foundation

// The sync func below is used to synchronize the code in 'execute'.
// This means that the code is thread safe. By using a generic we
// ensure that the return type of the closure can be (almost) anything.
// By providing a 'throw' statement we ensure that any error occurring
// within the closure can be propagated to the containing scope and 
// handled there. The 'defer' is present in order to provide guarantees
// on deadlock; i.e., we will ALWAYS release any waiting threads when the 
// 'sync' method completes.

public struct DispatchSemaphoreWrapper {
    let s = DispatchSemaphore(value: 1)
    
    init() {}
    
    func sync<R>(execute: () throws -> R) rethrows -> R {
        _ = s.wait(timeout: DispatchTime.distantFuture)
        defer { s.signal() }
        return try execute()
    }
}
