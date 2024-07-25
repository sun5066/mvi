package com.sun5066.mvi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sun5066.core.mvi.android.collectAsContainerStateWithLifecycle
import com.sun5066.core.mvi.android.collectContainerSideEffect
import com.sun5066.mvi.ui.theme.MviTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MviTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.collectAsContainerStateWithLifecycle()

    viewModel.collectContainerSideEffect {sideEffect ->
        when (sideEffect) {
            is MainSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(state) {
        Log.d("123123", "state: $state")
    }

    Text(
        text = "Count: ${state.count}",
        modifier = modifier.clickable { viewModel.increment() }
    )
}