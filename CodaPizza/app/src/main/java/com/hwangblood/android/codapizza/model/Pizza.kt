package com.hwangblood.android.codapizza.model

import android.os.Parcelable
import com.hwangblood.android.codapizza.ui.Topping
import com.hwangblood.android.codapizza.ui.ToppingPlacement
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap()
) : Parcelable {
    val price: Double
        get() = 9.99 + toppings.asSequence().sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                ToppingPlacement.All -> 1.0
            }
        }

    /**
     *  make it easier to add and remove a topping
     */
    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }
}