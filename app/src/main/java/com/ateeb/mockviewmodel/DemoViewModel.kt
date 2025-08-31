package com.ateeb.mockviewmodel

/**
 * THOUGHT PROCESS: Separate state management from Activity
 * GOAL: Move state into dedicated class for better organization and state mgmt
 *
 * CURRENT LIMITATION: Still creating new ViewModel in onCreate()
 * RESULT: Still loses state on rotation, but code is better structured
 */

/**
 * CUSTOM VIEWMODEL: Simple state holder class
 *
 * DESIGN DECISIONS:
 * - Keep it minimal - just hold the state we need
 * - Public properties for direct access (focusing on structure, not encapsulation)
 * - Add toString() for debugging instance identity
 *
 * WHAT'S MISSING: Persistence mechanism!
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