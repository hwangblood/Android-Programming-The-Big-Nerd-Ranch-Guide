package com.hwangblood.android.codapizza.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwangblood.android.codapizza.R
import com.hwangblood.android.codapizza.model.Pizza
import com.hwangblood.android.codapizza.model.PizzaSize
import com.hwangblood.android.codapizza.model.Topping
import java.text.NumberFormat

@Preview(showBackground = true)
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    // state is stored in the savedInstanceState bundle
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }

    Column(modifier = modifier) {
        ToppingsList(
            pizza = pizza,
            onEditPizza = { pizza = it },
            modifier = modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )
        OrderButton(
            pizza = pizza, modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun ToppingsList(
    pizza: Pizza, onEditPizza: (Pizza) -> Unit, modifier: Modifier = Modifier
) {
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }

    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(topping = topping, onSetToppingPlacement = { placement ->
            onEditPizza(pizza.withTopping(topping, placement))
        }, onDismissRequest = {
            toppingBeingAdded = null
        })
    }

    SizeSelection(
        pizzaSize = pizza.size,
        onSelectSize = { size ->
            onEditPizza(pizza.withSize(size))
        },
    )

    LazyColumn(modifier = modifier) {
        items(Topping.values()) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    toppingBeingAdded = topping
                },
            )
        }
    }
}


@Composable
private fun SizeSelection(
    modifier: Modifier = Modifier, pizzaSize: PizzaSize, onSelectSize: (PizzaSize) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val onToggle = { expanded = expanded.not() }

    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
                .clickable(onClick = onToggle)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Text(
                    pizzaSize.name, modifier = Modifier.weight(1f, true)
                )
                IconButton(onClick = onToggle) {
                    Icon(
                        imageVector = if (expanded) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = "More",
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = onToggle,
            ) {
                PizzaSize.values().forEachIndexed { _, size ->
                    DropdownMenuItem(
                        enabled = pizzaSize != size,
                        text = {
                            val sizeName = stringResource(id = size.labelResId)
                            Text(
                                text = stringResource(
                                    id = R.string.pizza_size_selection_label,
                                    sizeName,
                                    size.extraCost
                                )
                            )
                        },
                        onClick = {
                            onSelectSize(size)
                            onToggle()
                        },
                    )
                }
            }
        }
    }

}

@Composable
private fun OrderButton(
    pizza: Pizza, modifier: Modifier = Modifier
) {
    Button(modifier = modifier, onClick = {
        /* TODO */
    }) {
        val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button, price).toUpperCase(Locale.current)
        )
    }
}