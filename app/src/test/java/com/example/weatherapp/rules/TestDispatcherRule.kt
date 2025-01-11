package com.example.weatherapp.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// JUnit test rule
// TestRule provides a way to control the environment under which a test is run
// TestWatcher enables you to take actions on different execution phases of a test
class TestDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()): TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}