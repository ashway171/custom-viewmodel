package com.ateeb.mockviewmodel

/**
* BREAKTHROUGH INSIGHT: Application class survives configuration changes!
*
* APPLICATION LIFECYCLE vs ACTIVITY LIFECYCLE:
* Activity: Create -> Destroy -> Create -> Destroy (on rotation)
* Application: Create -> (stays alive throughout) -> Process death
*
* SOLUTION: Store ViewModel instances in Application class
*/
class DemoViewModel {
    /**
     * STATE: Simple counter value
     *
     * This represents any state that should survive configuration changes
     * In real apps: user data, UI state, network responses, etc.
     */
    var count: Int = 0

    /**
     * DEBUGGING: Track instance creation
     *
     * This will help us see when new instances are created vs reused
     * Each ViewModel instance has unique hashCode
     */
    override fun toString(): String {
        return "DemoViewModel(count=$count, hashCode=${hashCode()})"
    }
}