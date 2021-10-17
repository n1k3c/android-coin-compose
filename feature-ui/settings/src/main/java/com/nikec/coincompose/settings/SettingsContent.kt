package com.nikec.coincompose.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nikec.coincompose.data.model.Currency

@Composable
fun SettingsContent(onCurrencyChange: (Currency) -> Unit, currency: Currency?) {
    Column(modifier = Modifier.fillMaxSize()) {
        PriceDropdown(onCurrencyChange = onCurrencyChange, currency = currency)
    }
}

@Composable
private fun PriceDropdown(onCurrencyChange: (Currency) -> Unit, currency: Currency?) {
    if (currency == null) return

    var expanded by remember { mutableStateOf(false) }
    val currencies = Currency.values()

    val icon = when {
        expanded -> Icons.Filled.KeyboardArrowUp
        else -> Icons.Filled.KeyboardArrowDown
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.currency),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = currency.name,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                imageVector = icon,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(onClick = {
                    onCurrencyChange(currency)
                    expanded = false
                }) {
                    Text(
                        text = currency.name,
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}
