package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
            finish()
        }, 1000)

        setContent {
            SplashComponent()
        }
    }

    @Composable
    private fun SplashComponent() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.splash_screen_color)),
            color = colorResource(id = R.color.splash_screen_color),
        ) {
            Box(
                contentAlignment = androidx.compose.ui.Alignment.Center,
                modifier = Modifier.size(500.dp),
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(244.dp)
                        .background(color = colorResource(id = R.color.splash_screen_color)),
                    painter = painterResource(id = R.drawable.gym_time),
                    contentDescription = null,
                )
            }
        }
    }
}