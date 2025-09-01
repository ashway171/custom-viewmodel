package com.ateeb.mockviewmodel

import android.app.Application
import android.util.Log

/**
 * ENHANCED APPLICATION: Generic ViewModel storage with type safety
 */
class MainApplication : Application() {
    /**
     * GENERIC STORAGE: Can store any BaseViewModel type
     *
     * VISIBILITY: Public because inline reified functions become part of public API
     * and need access to this property when compiled into calling code
     *
     * For learning purposes: Public is fine since it's just our demo app
     * In production: It's internal and uses non-inline methods
     */
    val viewModelMap = hashMapOf<String, BaseViewModel>()

    override fun onCreate() {
        super.onCreate()
        Log.d("MainApplication", "Application created - Generic ViewModel storage ready")
    }

    /**
     * SIMPLE METHOD: For ViewModels with empty constructors (like our DemoViewModel)
     *
     * USAGE: saveOrGetViewModel<DemoViewModel>("MainActivity")
     *
     * Uses reflection to create instance - requires empty constructor
     */
    inline fun <reified T : BaseViewModel> saveOrGetViewModel(key: String): T {
        Log.d("MainApplication", "saveOrGetViewModel: key='$key', type=${T::class.java.simpleName}")

        return if (viewModelMap.containsKey(key)) {
            // Return existing ViewModel with correct type
            val existingViewModel = viewModelMap[key] as T
            Log.d("MainApplication", "Returning existing: $existingViewModel")
            existingViewModel
        } else {
            // Create new ViewModel using reflection
            val newViewModel = T::class.java.getDeclaredConstructor().newInstance()
            viewModelMap[key] = newViewModel
            Log.d("MainApplication", "Created new: $newViewModel")
            newViewModel
        }
    }

    /**
     * FACTORY METHOD: For ViewModels that need constructor parameters
     *
     * USAGE: saveOrGetViewModel("profile") { UserProfileViewModel("userId123") }
     *
     * @param T The ViewModel type to retrieve
     * @param key Unique identifier
     * @param factory Lambda to create new instance if needed
     * @return Correctly typed ViewModel instance
     */
    inline fun <reified T : BaseViewModel> saveOrGetViewModel(
        key: String,
        factory: () -> T
    ): T {
        Log.d("MainApplication", "saveOrGetViewModel with factory: key='$key', type=${T::class.java.simpleName}")

        return if (viewModelMap.containsKey(key)) {
            // Return existing ViewModel
            val existingViewModel = viewModelMap[key] as T
            Log.d("MainApplication", "Returning existing: $existingViewModel")
            existingViewModel
        } else {
            // Create new ViewModel using factory
            val newViewModel = factory()
            viewModelMap[key] = newViewModel
            Log.d("MainApplication", "Created new: $newViewModel")
            newViewModel
        }
    }

    /**
     * CLEANUP: Enhanced with proper error handling
     */
    fun permanentDestruction(key: String) {
        Log.d("MainApplication", "permanentDestruction: key='$key'")

        val removedViewModel = viewModelMap.remove(key)
        if (removedViewModel != null) {
            Log.d("MainApplication", "Cleaning up: $removedViewModel")

            try {
                removedViewModel.onCleared()
            } catch (e: Exception) {
                Log.e("MainApplication", "Error during ViewModel cleanup", e)
            }
        }

        Log.d("MainApplication", "Remaining ViewModels: ${viewModelMap.keys}")
    }
}