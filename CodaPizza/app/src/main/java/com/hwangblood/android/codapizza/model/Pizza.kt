package com.hwangblood.android.codapizza.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A pizza defaults to small size and has no topping
 */
@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val size: PizzaSize = PizzaSize.Small,
) : Parcelable {
    val price: Double
        get() = 9.99 + toppings.asSequence().sumOf { (_, toppingPlacement) ->
            when (toppingPlacement) {
                ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                ToppingPlacement.All -> 1.0
            }
        } + size.extraCost

    /**
     *  make it easier to add and remove a topping
     */
    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }, size = size
        )
    }


    /**
     *  make it easier to change the size of pizza
     */
    fun withSize(size: PizzaSize): Pizza {
        return copy(
            size = size
        )
    }


}