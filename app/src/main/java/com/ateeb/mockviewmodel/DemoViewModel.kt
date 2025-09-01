package com.ateeb.mockviewmodel

import android.util.Log

/**
 * PROBLEM DISCOVERED: ViewModels accumulate in Application storage forever!
 *
 * NEW CHALLENGE: When should we clean up ViewModels?
 * - NOT during configuration changes (we want to keep them)
 * - YES when Activity is permanently destroyed (back button, finish())
 *
 * THE SOLUTION: isChangingConfigurations flag in onDestroy()
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

    /**
     * CLEANUP METHOD: Called when ViewModel is permanently destroyed
     *
     * This mimics Android ViewModel's onCleared() method
     * Use this for cleanup: cancel coroutines, close resources, etc.
     */
    fun onCleared() {
        Log.d("DemoViewModel", "onCleared: ViewModel destroyed, final count was $count")
        // TODO: In real implementation - cancel jobs, close connections, etc.
    }
}