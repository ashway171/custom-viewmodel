package com.ateeb.mockviewmodel.viewmodel

import android.util.Log

/**
 * USER PROFILE VIEWMODEL: Example of different ViewModel type
 *
 * Shows how our generic system handles multiple ViewModel types
 */
class UserViewModel(private val userId: Int) : BaseViewModel() {

    var userName: String = "Loading..."
    var isLoading: Boolean = true

    init {
        Log.d("UserProfileViewModel", "Created for userId: $userId")
    }

    override fun onCleared() {
        Log.d("UserProfileViewModel", "onCleared: UserProfile ViewModel destroyed")
        // Cancel network requests, clear user data, etc.
    }

    override fun toString(): String {
        return "UserProfileViewModel(userId=$userId, userName=$userName, hashCode=${hashCode()})"
    }
}