package com.ateeb.mockviewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.mockviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // IMPROVEMENT: Now using dedicated ViewModel class
    private lateinit var viewModel: DemoViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * STILL THE PROBLEM: Creating new ViewModel instance every onCreate()
         *
         * WHAT HAPPENS:
         * - First launch: New ViewModel created
         * - After rotation: NEW ViewModel created (different hashCode)
         * - State lost because we get a fresh ViewModel instance
         *
         * IMPROVEMENT: Code is now organized, but same fundamental issue
         */
        viewModel = DemoViewModel()
        Log.d(TAG, "onCreate: Created ViewModel: $viewModel")

        // Display count from ViewModel (still resets to 0 on rotation)
        binding.counterTv.text = viewModel.count.toString()

        binding.incrementBtn.setOnClickListener {
            // State management now properly in ViewModel
            viewModel.count++
            binding.counterTv.text = viewModel.count.toString()
            Log.d(TAG, "ViewModel count: ${viewModel.count}")
        }
    }
}

/**
 * TESTING RESULTS:
 *
 * BEHAVIOR: Still loses state on rotation (same problem as before)
 *
 * LOGS SHOW:
 * 1. First launch: ViewModel created with hashCode X
 * 2. Increment counter to 5
 * 3. Rotate screen:
 *    - onCreate called again
 *    - NEW ViewModel created with different hashCode Y
 *    - Count back to 0
 *
 * PROGRESS: Better code organization (Can decouple states and business logic)
 * REMAINING ISSUE: ViewModel instance doesn't survive Activity recreation
 *
 * NEXT CHALLENGE: Where can we store the ViewModel instance so it survives
 * Activity recreation but still gets cleaned up appropriately?
 *
 * INSIGHT NEEDED: We need external storage that has the right lifecycle!
 */