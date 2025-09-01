package com.ateeb.mockviewmodel.viewmodel

import android.util.Log

class DemoViewModel : BaseViewModel(){
    /**
     * STATE: Counter that survives configuration changes
     */
    var count: Int = 0

    /**
     * CLEANUP IMPLEMENTATION: What to do when destroyed
     */
    override fun onCleared() {
        Log.d("DemoViewModel", "onCleared: Final count was $count")
        // Real implementation: cancel coroutines, close DB, etc.
    }

    /**
     * ENHANCED DEBUGGING: Include state in toString
     */
    override fun toString(): String {
        return "DemoViewModel(count=$count, hashCode=${hashCode()})"
    }
}