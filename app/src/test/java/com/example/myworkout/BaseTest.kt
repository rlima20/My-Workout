package com.example.myworkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val testDispatcher = Dispatchers
}