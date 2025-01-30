package com.vlad.grpcdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vlad.grpcdemo.chat.ChatScreen
import com.vlad.grpcdemo.landing.LandingScreen
import com.vlad.grpcdemo.stock.StockScreen
import com.vlad.grpcdemo.ui.theme.AndroidgrpcdemoTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val grpcClient by inject<GrpcClient>()
    override fun onStart() {
        grpcClient.init()
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidgrpcdemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    AppNavigation()
                }
            }
        }
    }

    override fun onDestroy() {
        grpcClient.shutdown()
        super.onDestroy()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(navController)
        }
        composable(Screen.Chat.route) {
            ChatScreen(navController)
        }
        composable(Screen.Stock.route) {
            StockScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    data object Landing : Screen("landing")
    data object Chat : Screen("chat")
    data object Stock : Screen("stock")
}
