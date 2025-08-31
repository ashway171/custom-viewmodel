package com.ateeb.mockviewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.mockviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: DemoViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * THE FIX: Get ViewModel from Application storage instead of creating new
         *
         * EVERYTHING HAPPENS HERE:
         * - First launch: Application creates new ViewModel, stores it with key
         * - After rotation: Application returns SAME ViewModel instance
         * - State preserved across Activity recreation!
         */
        viewModel = (application as MainApplication).saveOrGetViewModel("MainActivity")
        Log.d(TAG, "onCreate: Retrieved ViewModel: $viewModel")

        /**
         * STATE RESTORATION: Now displays preserved count!
         *
         * Before: Always showed 0 after rotation
         * After: Shows actual count value preserved from before rotation
         */
        binding.counterTv.text = viewModel.count.toString()
        Log.d(TAG, "onCreate: Displaying preserved count: ${viewModel.count}")

        binding.incrementBtn.setOnClickListener {
            viewModel.count++
            binding.counterTv.text = viewModel.count.toString()
            Log.d(TAG, "Count incremented: ${viewModel.count}")
        }
    }
}
/**
 * TESTING RESULTS - SUCCESS!
 *
 * ROTATION TEST:
 * 1. Launch app: Application.onCreate() -> MainActivity.onCreate()
 * 2. Increment counter to 5
 * 3. Rotate screen:
 *    - MainActivity destroyed and recreated
 *    - SAME ViewModel instance retrieved (same hashCode!)
 *    - Count still shows 5! STATE PRESERVED!
 *
 * LOG EVIDENCE:
 * Before rotation: DemoViewModel(count=5, hashCode=12345)
 * After rotation:  DemoViewModel(count=5, hashCode=12345) <- SAME INSTANCE!
 *
 * NEW PROBLEM:
 * ViewModels now accumulate in Application storage forever!
 * Memory leak potential - when should we clean them up?
 *
 * NEXT CHALLENGE: Detect when Activity is permanently destroyed vs temporarily destroyed
 */