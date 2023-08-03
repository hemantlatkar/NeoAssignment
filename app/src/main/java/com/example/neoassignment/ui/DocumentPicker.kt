import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.neoassignment.model.Option
import com.example.neoassignment.utils.FileNameFromUri

@Composable
fun DocumentPicker(context : Context, onDocumentPickerListener: (String) -> Unit) {

    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        pickedImageUri = it.data?.data
    }
    Column {
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    .apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                    }
                launcher.launch(intent)
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .width(IntrinsicSize.Min)
                .indication(
                    indication = rememberRipple(bounded = true, radius = 8.dp),
                    interactionSource = remember { MutableInteractionSource() }
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Text("Select")
        }

        if(pickedImageUri != null){
            onDocumentPickerListener(pickedImageUri.toString())
            val contentResolver = context.contentResolver
            val uri: Uri = pickedImageUri as Uri
            val fileName = FileNameFromUri.getFileNameFromUri(contentResolver, uri)
            if (fileName != null) {
                // Use the file name as needed
                Text("File Name : $fileName")
            }
        }

    }
}
