package com.vpdevs.calaulator

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vpdevs.calaulator.scrren.Calculator
import com.vpdevs.calaulator.scrren.CurrencyCalculator


enum class CalculatorAppScreen {
    Currency_Calculator,
    Calculator
}

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = CalculatorAppScreen.Calculator.name,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = CalculatorAppScreen.Calculator.name
            ) {
                Calculator()
            }

            composable(
                route = CalculatorAppScreen.Currency_Calculator.name
            ) {
                CurrencyCalculator()
            }
        }
    }

}