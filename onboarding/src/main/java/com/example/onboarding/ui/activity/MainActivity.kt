package com.example.onboarding.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.onboarding.R
import com.example.onboarding.viewmodel.OnboardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by viewModel()

    private val onboardingPages = listOf(
        OnboardingPage(
            image = R.drawable.ic_launcher_background,
            title = "Bem-vindo!",
            description = "Conheça as funcionalidades incríveis do nosso app."
        ),
        OnboardingPage(
            image = R.drawable.ic_launcher_background,
            title = "Organize tudo",
            description = "Tenha controle do seu progresso de forma simples."
        ),
        OnboardingPage(
            image = R.drawable.ic_launcher_background,
            title = "Comece agora",
            description = "Vamos iniciar sua jornada!"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PagerWithCustomIndicator(
                pages = onboardingPages,
                onFinished = {
                    // TODO: Navegar para a home ou salvar no DataStore
                }
            )
        }
    }
}

data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

@Composable
fun DotIndicator(
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(if (isSelected) 12.dp else 8.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.3f))
    )
}

@Composable
fun PagerWithCustomIndicator(
    pages: List<OnboardingPage>,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
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

            TextButton(
                onClick = onFinished
            ) {
                Text("Pular")
            }

            androidx.compose.material3.Button(
                onClick = {
                    if (pagerState.currentPage < pages.lastIndex) {
                        // go to next page
//                        LaunchedEffect(Unit) {
//                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                        }
                    } else {
                        onFinished()
                    }
                }
            ) {
                androidx.compose.material3.Text(
                    if (pagerState.currentPage == pages.lastIndex) "Começar" else "Próximo"
                )
            }
        }
    }
}

@Composable
fun PagerScreen(
    page: OnboardingPage
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = page.title,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.description,
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
    }
}

