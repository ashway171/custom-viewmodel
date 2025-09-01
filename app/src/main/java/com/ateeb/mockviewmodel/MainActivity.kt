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
         * ENHANCED RETRIEVAL: Using generic method
         *
         * Options:
         * 1. With factory: saveOrGetViewModel("key") { DemoViewModel() }
         * 2. Simple: saveOrGetViewModel<DemoViewModel>("key")
         */
        viewModel = (application as MainApplication).saveOrGetViewModel<DemoViewModel>("MainActivity")
        Log.d(TAG, "onCreate: Retrieved ViewModel: $viewModel")

        /**
         * STATE RESTORATION: displays preserved count!
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

        // Permanent Destruction of activity
        finishMainActivity()
    }
    /**
     * Usually RAM stores the instance of the last activity on the backstack
     * When you press the back button that closes the app..
     * Thus not calling onDestroy() and just onPause() and onStop()
     *
     * Here, when the back button is pressed from the MainActivity,
     * it may restore the counter since onDestroy() was NEVER CALLED
     *
     * So we will explicitly finish() the MainActivity to register permanent destruction
     */
    private fun finishMainActivity(){
        binding.permanentDestroyBtn.setOnClickListener{
            finish()
        }
    }

    /**
     * LIFECYCLE MANAGEMENT: The critical cleanup decision
     *
     * KEY INSIGHT: isChangingConfigurations flag
     *
     * Android sets this flag to tell us WHY onDestroy() was called:
     *
     * isChangingConfigurations = true:
     * - Screen rotation, language change, dark mode toggle, etc.
     * - Activity will be recreated immediately
     * - We WANT to keep ViewModel alive for the new Activity instance
     *
     * isChangingConfigurations = false:
     * - Back button pressed, finish() called, system kill
     * - Activity will NOT be recreated
     * - We SHOULD clean up ViewModel to prevent memory leak
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: isChangingConfigurations = $isChangingConfigurations")

        if (!isChangingConfigurations) {
            /**
             * PERMANENT DESTRUCTION: Clean up ViewModel
             *
             * SCENARIOS:
             * - User pressed back button
             * - finish() called programmatically
             * - System permanently killed Activity
             *
             * ACTION: Tell Application to remove ViewModel from storage
             */
            Log.d(TAG, "onDestroy: Activity finishing permanently - cleaning up ViewModel")
            (application as MainApplication).permanentDestruction("MainActivity")
        } else {
            /**
             * TEMPORARY DESTRUCTION: Keep ViewModel alive
             *
             * SCENARIOS:
             * - Screen rotation
             * - Configuration changes
             *
             * ACTION: Do nothing - let ViewModel stay in Application storage
             */
            Log.d(TAG, "onDestroy: Configuration change - keeping ViewModel alive")
        }
    }
}

/**
 * COMPLETE SOLUTION TESTING:
 *
 * TEST 1 - Configuration Changes:
 * 1. Increment counter to 5
 * 2. Rotate screen multiple times
 * 3. RESULT: Count preserved through all rotations
 * 4. LOGS: Same ViewModel hashCode, isChangingConfigurations = true
 *
 * TEST 2 - Permanent Destruction:
 * 1. Increment counter to 3
 * 2. Press back button
 * 3. RESULT: onCleared() called, ViewModel removed from storage
 * 4. LOGS: isChangingConfigurations = false, cleanup performed
 * 5. Re-launch: Fresh ViewModel created (count = 0)
 *
 * TEST 3 - Memory Management:
 * 1. Multiple Activity launches and finishes
 * 2. RESULT: No ViewModel accumulation in storage
 * 3. LOGS: ViewModels properly cleaned up each time
 *
 * ACHIEVEMENT: Custom ViewModel that works like the real thing
 *
 * NEXT POSSIBLE ENHANCEMENTS:
 * - Weak references for additional memory safety
 * - Multiple ViewModel support per Activity (Tweaking the store key)
 * - Integration with dependency injection
 * - Coroutine scope management
 */