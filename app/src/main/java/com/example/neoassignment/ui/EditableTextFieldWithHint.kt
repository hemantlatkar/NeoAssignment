import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.neoassignment.model.Option

@Composable
fun EditableTextFieldWithHint(onEditTextListener: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,4.dp,0.dp,0.dp)
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
        ) {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onEditTextListener(textFieldValue.text)
                },
                textStyle = TextStyle.Default.copy(color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            if (textFieldValue.text.isEmpty()) {
                Text(
                    text = "Write here...",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 20.dp, top = 8.dp)
                )
            }
        }
    }
}
