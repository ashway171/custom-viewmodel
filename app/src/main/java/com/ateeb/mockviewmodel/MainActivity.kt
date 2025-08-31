package com.ateeb.mockviewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.mockviewmodel.databinding.ActivityMainBinding

/**
 * CURRENT STATE: Basic counter app that LOSES state on rotation
 * PURPOSE: Show exactly why we need ViewModel pattern
 *          (onSaveInstanceState() has a size restriction)
 *
 * PROBLEM: Every configuration change creates new Activity instance
 * RESULT: All local variables reset to initial values
 *
 * TEST: Run app, increment counter, rotate screen -> count resets to 0
 * */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // PROBLEM: This variable is recreated every time Activity is recreated
    private var count: Int = 0
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Log to track Activity recreation
        Log.d(TAG, "onCreate: Activity created, count = $count")

        // Display current count (always 0 after rotation)
        binding.counterTv.text = count.toString()

        binding.incrementBtn.setOnClickListener {
            // This works fine until you rotate the screen
            count++
            binding.counterTv.text = count.toString()
            Log.d(TAG, "Count incremented to: $count")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: count was $count")
        Log.d(TAG, "onDestroy: isChangingConfigurations = $isChangingConfigurations")

        // OBSERVATION: This gets called both during rotation AND when finishing
        // We need a way to preserve state during rotation but clean up when finishing
    }

}

/**
 * TESTING INSTRUCTIONS:
 * 1. Run the app
 * 2. Click increment button several times (count goes up)
 * 3. Rotate the screen (Ctrl+F11 in emulator)
 * 4. OBSERVE: Count resets to 0! State is lost!
 * 5. Check logs: You'll see onCreate called again with count = 0
 *
 * THE PROBLEM:
 * Activity instance variables don't survive configuration changes
 *
 * NEXT STEP:
 * We need a class to hold our state separate from the Activity
 */