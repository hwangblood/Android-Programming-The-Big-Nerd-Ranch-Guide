package com.hwangblood.android.codapizza.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hwangblood.android.codapizza.R
import com.hwangblood.android.codapizza.model.Topping
import com.hwangblood.android.codapizza.model.ToppingPlacement

@Preview(showBackground = true)
@Composable
fun ToppingPlacementDialogPreview() {
    ToppingPlacementDialog(
        topping = Topping.Pepperoni,
        onSetToppingPlacement = { },
        onDismissRequest = { },
    )
}

@Composable
fun ToppingPlacementDialog(
    topping: Topping,
    onSetToppingPlacement: (placement: ToppingPlacement?) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column {
                val toppingName = stringResource(topping.toppingName)
                Text(
                    text = stringResource(R.string.placement_prompt, toppingName),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp)
                )

                ToppingPlacement.values().forEach { placement ->
                    ToppingPlacementOption(placementName = placement.label, onClick = {
                        onSetToppingPlacement(placement)
                        onDismissRequest()
                    })
                }
                ToppingPlacementOption(placementName = R.string.placement_none, onClick = {
                    onSetToppingPlacement(null)
                    onDismissRequest()
                })
            }
        }
    }
}

@Composable
fun ToppingPlacementOption(
    @StringRes placementName: Int, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    TextButton(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text(text = stringResource(id = placementName), modifier = Modifier.padding(8.dp))
    }
}

