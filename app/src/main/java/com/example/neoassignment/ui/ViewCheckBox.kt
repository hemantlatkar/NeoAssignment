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
import com.example.neoassignment.model.Option
import com.example.neoassignment.model.ResponseDataItem

@Composable
fun ViewCheckBox(currentQuestion: ResponseDataItem?, onCheckboxListener: (Boolean, Option) -> Unit) {
    Column {
        var selectedCheckboxItem by remember { mutableStateOf<String?>(null) }

        currentQuestion?.option?.forEach { option ->
            val isChecked = option.option_value == selectedCheckboxItem
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,4.dp,0.dp,0.dp)
                    .clickable {
                    if (isChecked) {
                        selectedCheckboxItem = option.option_value
                        onCheckboxListener(isChecked, option)
                    }
                }
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        if (it) {
                            selectedCheckboxItem = option.option_value
                            onCheckboxListener(it, option)
                        }
                    },
                    colors = CheckboxDefaults.colors(Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = option.option_value)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}