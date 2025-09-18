package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.myworkout.presentation.ui.components.commons.SplashComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }

        setContent {
            SplashComponent()
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashComponent()
}