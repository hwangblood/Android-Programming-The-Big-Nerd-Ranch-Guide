package com.hwangblood.android.codapizza.model

import androidx.annotation.StringRes
import com.hwangblood.android.codapizza.R

enum class PizzaSize(
    @StringRes val labelResId: Int,
    val extraCost: Double
) {
    Small(R.string.pizza_size_small, 0.0),
    Medium(R.string.pizza_size_medium, 2.0),
    Large(R.string.pizza_size_large, 3.0),
    ExtraLarge(R.string.pizza_size_extra_large, 5.0)
}
