package com.hwangblood.android.codapizza

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.hwangblood.android.codapizza.ui.PizzaBuilderScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaBuilderScreen()
        }
    }
}

