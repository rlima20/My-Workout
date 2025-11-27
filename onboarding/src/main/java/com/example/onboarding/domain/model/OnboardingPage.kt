package com.example.onboarding.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.onboarding.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingPage(
    val title: String? = null,
    val description: String? = null,
    val titleColor: Int = R.color.text_color,
    val descriptionColor: Int = R.color.text_color,
    val dotColor: Int = R.color.black,
    val backgroundColor: Int = R.color.white,
    val nextButtonColor: Int = R.color.button_color,
    val skipButtonColor: Int = R.color.white,
    @DrawableRes val image: Int,
): Parcelable