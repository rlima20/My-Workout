package com.example.onboarding.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.onboarding.domain.model.OnboardingPage

@Composable
fun PagerWithCustomIndicator(
    pages: List<OnboardingPage>,
    modifier: Modifier = Modifier,
    nextButtonText: Pair<String, String>,
    skipButtonText: String,
    showSkipButton: Boolean,
    finishButtonText: String,
    onFinished: () -> Unit,
    onNextPage: () -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            PagerScreen(page = pages[pageIndex])
        }

        // Indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                DotIndicator(isSelected = pagerState.currentPage == index)
            }
        }

        // Bottom buttons
        Row(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // SkipButton
            if (showSkipButton) {
                TextButton(
                    onClick = { onFinished() },
                    content = { Text(text = skipButtonText) }
                )
            }

            // NextButton
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.lastIndex) onNextPage()
                    else onFinished()
                },
                content = {
                    Text(
                        if (pagerState.currentPage == pages.lastIndex) nextButtonText.first
                        else nextButtonText.second
                    )
                }
            )
        }
    }
}