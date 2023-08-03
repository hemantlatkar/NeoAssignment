package com.example.neoassignment.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.neoassignment.model.Option
import com.example.neoassignment.model.ResponseDataItem

@Composable
fun ViewRadioButton(currentQuestion: ResponseDataItem?, onRadioButtonListener: (Boolean, Option) -> Unit) {
    Column {
        var selectRadioBoxItem by remember { mutableStateOf<String?>(null) }

        currentQuestion?.option?.forEach { option ->
            val context = LocalContext.current
            val isChecked = option.option_value == selectRadioBoxItem
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,4.dp,0.dp,0.dp)
                    .clickable {
                    if (isChecked) {
                        selectRadioBoxItem = option.option_value
                        onRadioButtonListener(isChecked, option)
                    }
                }
            ) {
               RadioButton(
                    selected = isChecked,
                    onClick = {
                        selectRadioBoxItem = option.option_value
                        onRadioButtonListener(true, option)
                    },
                   colors = RadioButtonDefaults.colors(Color.Blue)

               )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = option.option_value)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}