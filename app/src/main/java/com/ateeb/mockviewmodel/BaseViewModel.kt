package com.ateeb.mockviewmodel

/**
 * EVOLUTION: Make our ViewModel system reusable and type-safe
 *
 * IMPROVEMENTS:
 * 1. Generic base class for any ViewModel type
 * 2. Type-safe retrieval methods
 * 3. Factory pattern for ViewModels with parameters
 * 4. Better error handling
 */

/**
 * BASE VIEWMODEL: Generic foundation for all ViewModels
 *
 * DESIGN PRINCIPLE: Define common ViewModel contract
 * All custom ViewModels extend this for consistent lifecycle management
 */
abstract class BaseViewModel {
    /**
     * CLEANUP CONTRACT: All ViewModels must implement cleanup
     *
     * Called when ViewModel is permanently destroyed
     * Override to release resources, cancel operations, etc.
     */
    abstract fun onCleared()

    /**
     * DEBUGGING: Common instance tracking
     */
    override fun toString(): String {
        return "${javaClass.simpleName}(hashCode=${hashCode()})"
    }
}