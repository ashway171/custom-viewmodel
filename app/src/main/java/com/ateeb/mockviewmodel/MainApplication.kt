package com.ateeb.mockviewmodel

import android.app.Application
import android.util.Log

/**
 * OUR APPLICATION: ViewModel storage manager
 *
 * WHY APPLICATION CLASS?
 * 1. Created once per app process
 * 2. Survives ALL configuration changes
 * 3. Accessible from any Activity via getApplication()
 * 4. Perfect lifecycle for ViewModel storage
 */
class MainApplication : Application() {
    /**
     * STORAGE MECHANISM: HashMap with String keys
     *
     * DESIGN CHOICES:
     * - HashMap since we want to store one viewmodel instance of DemoViewModel per activity
     * - String keys for uniqueness factor (Activity name, custom identifiers)
     */
    private val viewModelMap = hashMapOf<String, DemoViewModel>()

    override fun onCreate() {
        super.onCreate()
        Log.d("DemoApplication", "Application onCreate - ViewModel storage ready")
        Log.d("DemoApplication", "This should only be called ONCE per app launch")
    }

    /**
     * CORE METHOD: Retrieve or create ViewModel instance (if it already exists)
     *
     * THIS IS THE CRUX! Replicates ViewModelProvider.get() behavior:
     * 1. Look for existing instance by key
     * 2. If found: return it (state preserved!)
     * 3. If not found: create new one and store it
     *
     * @param key Unique identifier for ViewModel
     * @return ViewModel instance (existing or newly created)
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
}