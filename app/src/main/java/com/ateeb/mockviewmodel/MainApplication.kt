package com.ateeb.mockviewmodel

import android.app.Application
import android.util.Log


class MainApplication : Application() {
    /**
     * VIEWMODEL STORAGE: Application-scoped HashMap
     *
     * This is our version of ViewModelStore
     */
    private val viewModelMap = hashMapOf<String, DemoViewModel>()

    override fun onCreate() {
        super.onCreate()
        Log.d("DemoApplication", "Application onCreate - ViewModel storage ready")
        Log.d("DemoApplication", "This should only be called ONCE per app launch")
    }

    /**
     * INSTANCE MANAGEMENT: Get or create ViewModel
     *
     * Our implementation of ViewModelProvider.get() logic
     */
    fun saveOrGetViewModel(key: String): DemoViewModel {
        Log.d("DemoApplication", "saveOrGetViewModel called with key: '$key'")

        return if (viewModelMap.containsKey(key)) {
            // CONFIGURATION CHANGE SCENARIO: ViewModel already exists
            val existingViewModel = viewModelMap[key]!!
            Log.d("DemoApplication", "Found existing ViewModel: $existingViewModel")
            existingViewModel
        } else {
            // FIRST CREATION SCENARIO: No ViewModel exists yet
            val newViewModel = DemoViewModel()
            viewModelMap[key] = newViewModel
            Log.d("DemoApplication", "Created and stored new ViewModel: $newViewModel")
            newViewModel
        }
    }

    /**
     * CLEANUP: Remove ViewModel when Activity permanently destroyed
     *
     * CRITICAL: Only call this for permanent destruction!
     * NOT for configuration changes!
     *
     * This prevents memory leaks by cleaning up unused ViewModels
     */
    fun permanentDestruction(key: String) {
        Log.d("DemoApplication", "permanentDestruction: key='$key'")

        val removedViewModel = viewModelMap.remove(key)
        if (removedViewModel != null) {
            Log.d("DemoApplication", "Cleaning up ViewModel: $removedViewModel")
            removedViewModel.onCleared()
        } else {
            Log.w("DemoApplication", "No ViewModel found for key: '$key'")
        }

        Log.d("DemoApplication", "Remaining ViewModels: ${viewModelMap.keys}")
    }
}