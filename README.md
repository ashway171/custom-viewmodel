[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)

# Custom ViewModel Implementation 

> **This repository demonstrates the journey of creating a custom ViewModel implementation from scratch, showcasing the exact problems that Android's ViewModel architecture component solves**.

<img width="1582" height="1047" alt="ViewModel Create Banner Tiny" src="https://github.com/user-attachments/assets/8c2baa5a-8c90-4111-a5e1-287dd636f7cb" />

## Project Purpose

Instead of just using the built-in `androidx.lifecycle.ViewModel`, this project walks you through building your own ViewModel implementation to understand:

- **Configuration Change Survival**: How data persists through device rotations
- **UI Controller Separation**: Clean separation of concerns between UI and business logic
- **Memory Management**: Preventing memory leaks by clearing viewModel instance when necessary
- **Lifecycle Awareness**: Understanding when and how ViewModels should be created and destroyed

## Getting Started

### Prerequisites

- Android Studio: Any recent version would suffice
- Minimum SDK: API 24 (Android 7.0)
- Kotlin knowledge
- Basic understanding of Android lifecycle

### Clone Instructions

```bash
# Clone the repository
git clone https://github.com/ashway171/custom-viewmodel.git

# Navigate to the project directory
cd custom-viewmodel

# Open in Android Studio
# File -> Open -> Select the cloned directory
```


## Project Structure

```
app/
├── src/main/java/com/ateeb/mockviewmodel/
│   ├── viewmodel/
│   │   ├── BaseViewModel.kt           
│   │   ├── DemoViewModel.kt           
│   │   └── UserViewModel.kt           
│   ├── ui/
│   │   └── MainActivity.kt            
│   │   
│   └── MainApplication.kt
│      
└── README.md
```


## Learning Path: Follow the Commit History

This project is coded as a step-by-step learning experience. **The real value lies in following the commit history** to understand the thought process behind each implementation decision.

### Observation Steps

```bash
# Workflow to follow along
git log --oneline

# Start from the beginning
git checkout <first-commit-hash>

# Run the app, observe the problems
# Read the commit message and code

# Move to next commit
git checkout <next-commit-hash>

# Compare the changes
```


### How to Navigate the Learning Journey

1. **Start Fresh**: Begin from the first commit to see the initial problem
2. **Step by Step**: Move through each commit chronologically
3. **Understand the Why**: Each commit addresses a specific problem or adds a crucial feature
4. **Compare Solutions**: See how each iteration improves upon the previous one
5. **Attention to Comments**: Pay extreme attention to comments since they're a reflection of the thought-process


### Expected Learning Progression

As you follow the commits, this will be the typical journey:

#### Phase 1: The Problem Recognition
- **Initial Setup**: Basic Activity with business logic (imitated by a counter variable) mixed with UI code
- **The Rotation Problem**: Data loss during configuration changes

#### Phase 2: First Solution - ViewModel class (Bifurcate UI from Business Logic)
- **Separation of Concerns (SOC)**: Maintaining the business logic in the viewmodel class
- **Configuration Changes**: Although the class caters to SOC, the viewmodel instance is recreated on cofig changes resulting in data loss 

#### Phase 3: Lifecycle Thought-Process
- **Leveraging Application Class**: Since we want some place to hold viewmodel that has a lifecycle more than Activity
- **Recreation Tracking**: Implementing a HashMap to track the viewmodels 
- **Access**: ViewModels won't be created in the activity in onCreate(), instead accessed from Application class method that checks and then returns an instance 
- **Scope Management**: Handling ViewModel lifecycle properly still remains

#### Phase 4: Cleanup Mechanism
- **Avoiding Memory Leaks**: ViewModels are destroyed when Activity is permanently destroyed
- **Destruction**: Looking at activity flags for viewModel cleanup

#### Phase 5: BaseViewModel Generic Impl
- **Design Pattern**: Every new viewmodel extends this class 
- **ViewModel Types**: Taking into consideration, the nature of both parametrized and non-parametrized constructors in viewmodels



## Running the Demo

1. **Clone the repository** (see instructions above)
2. **Open in Android Studio**
3. **Build and run** the project
4. **Test scenarios**:
   - Rotate the device to test configuration change handling
   - Test with process kill switch

---

### If this project helped you, show love ❤️ by putting a ⭐ on this project 
