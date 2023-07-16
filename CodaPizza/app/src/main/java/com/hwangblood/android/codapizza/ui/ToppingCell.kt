package com.hwangblood.android.codapizza.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToppingCell(
    topping: Topping,
    placement: ToppingPlacement?,
    modifier: Modifier = Modifier,
    onClickTopping: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClickTopping() }
            .padding(vertical = 4.dp)
            .padding(end = 16.dp)) {
        Checkbox(
            checked = (placement != null),
            onCheckedChange = { onClickTopping() },
        )
        Column(
            modifier = Modifier
                .weight(1f, fill = true)
                .padding(start = 4.dp)
        ) {
            Text(
                text = stringResource(id = topping.toppingName),
                style = MaterialTheme.typography.bodyLarge
            )
            if (placement != null) {
                Text(
                    text = stringResource(id = placement.label),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToppingCellPreviewNotOnPizza() {
    ToppingCell(
        topping = Topping.Pepperoni,
        placement = null,
        modifier = Modifier
            // background color will apply to padding spaces
            .background(Color(0XFFFFF3E0)),
        onClickTopping = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ToppingCellPreviewOnLeftHalf() {
    ToppingCell(topping = Topping.Pepperoni, placement = ToppingPlacement.Left, onClickTopping = {})
}