package com.felipepalma14.cotajusta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.felipepalma14.cotajusta.ui.home.HomeFiiViewModel
import com.felipepalma14.cotajusta.ui.home.HomeScreen
import com.felipepalma14.cotajusta.ui.theme.CotaJustaTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: HomeFiiViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            CotaJustaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onEvent = viewModel::onEvent
                    ){

                    }
                }
            }
        }
    }
}
