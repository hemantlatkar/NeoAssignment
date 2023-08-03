package com.example.neoassignment.ui

import DocumentPicker
import EditableTextFieldWithHint
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.neoassignment.ui.theme.NeoAssignmentTheme
import com.example.neoassignment.viewmodels.DataState
import com.example.neoassignment.viewmodels.MainViewModel
import com.example.neoassignment.viewmodels.MainViewModelFactory
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.platform.LocalContext
import com.example.neoassignment.DemoApplication
import com.example.neoassignment.ui.theme.ErrorScreen
import com.example.neoassignment.utils.ToastUtils
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeoAssignmentTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Neo Assignment") })
                    },
                    content = {
                        val repository = (application as DemoApplication).questionRepository
                        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
                        MainContent(mainViewModel)
                    }
                )
            }
        }
    }
}

@Composable
fun MainContent(mainViewModel: MainViewModel) {

    val dataState by mainViewModel.dataState.collectAsState()
    when (dataState) {
        is DataState.Loading -> {
            // Show loading UI
            CircularProgressBar()
        }
        is DataState.Success -> {
            // Show success UI with the data
            val data = (dataState as DataState.Success).data
            Log.d("TAG_QUESTION","${data?.size}")
            data[0].let { mainViewModel.selectCurrentQuestion(it.id) }
        }
        is DataState.Error -> {
            // Show error UI with the error message
            val errorMessage = (dataState as DataState.Error).message
            ErrorScreen(errorMessage = errorMessage)
        }
    }
    ShowQuestions(mainViewModel)
}

@Composable
fun ShowQuestions(mainViewModel: MainViewModel) {

    val currentQuestion by mainViewModel.currentQuestion.collectAsState()
    Log.d("TAG_QUESTION","${currentQuestion?.id} : ${currentQuestion?.question_english}")
    
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        //Parent view
        Text(text = "Questions: ${currentQuestion?.question_english}")
        Spacer(modifier = Modifier.height(16.dp))

        var nextQuestionId : Int? = null
        var previousQuestionId : Int? = null
        if (currentQuestion?.input_type == "option") {
            //CheckBox view
            Text(text = "Answer: ")
            previousQuestionId = currentQuestion?.id?.minus(1)
            ViewCheckBox(currentQuestion) { isChecked, option ->
                nextQuestionId = if (isChecked) option.next_question_id.toInt() else nextQuestionId
            }
            Spacer(modifier = Modifier.height(16.dp))

        } else if (currentQuestion?.input_type == "radio") {
            //RadioButton view
            Text(text = "Answer: ")
            previousQuestionId = currentQuestion?.id?.minus(1)
            ViewRadioButton(currentQuestion) { isChecked, option ->
                nextQuestionId = if (isChecked) option.next_question_id.toInt() else nextQuestionId
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else if (currentQuestion?.input_type == "number"
            || currentQuestion?.input_type == "business_profiles"
            || currentQuestion?.input_type == "question_municipal_master"
            || currentQuestion?.input_type == "question_sub_zone_master") {

            //EditeText view
            Text(text = "Answer: ")
            previousQuestionId = currentQuestion?.id?.minus(1)
            EditableTextFieldWithHint{
                if(!TextUtils.isEmpty(it)){
                    nextQuestionId = currentQuestion?.next_question_id?.toInt() ?: 1
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else if (currentQuestion?.input_type == "file") {

            //Document upload view
            previousQuestionId = currentQuestion?.id?.minus(1)
            val context = LocalContext.current
            DocumentPicker(context){
                if(!TextUtils.isEmpty(it)){
                    nextQuestionId = 0
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        //Previous-Next buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            val context = LocalContext.current
            when (currentQuestion?.input_type) {
                "option", "radio", "number" -> {
                    if(currentQuestion?.id != 1){
                        Button(
                            onClick = {
                                if(previousQuestionId != null){
                                    currentQuestion?.let { mainViewModel.selectCurrentQuestion(previousQuestionId!!) }
                                }
                            },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .width(IntrinsicSize.Min)
                        ) {
                            Text("Previous")
                        }
                    }
                    Button(
                        onClick = {
                            if(nextQuestionId != null){
                                currentQuestion?.let { mainViewModel.selectCurrentQuestion(nextQuestionId!!) }
                            }else{
                                ToastUtils.showToast(context,"Please provide answer")
                            }
                        },
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        Text("Next")
                    }
                }
                else -> {
                    if(currentQuestion?.id != 1){
                        Button(
                            onClick = {
                                if(previousQuestionId != null){
                                    currentQuestion?.let { mainViewModel.selectCurrentQuestion(previousQuestionId) }
                                }
                            },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .width(IntrinsicSize.Min)
                        ) {
                            Text("Previous")
                        }
                    }
                    if(TextUtils.isEmpty(currentQuestion?.next_question_id)){
                        Button(
                            onClick = {
                                if(nextQuestionId != null){
                                    ToastUtils.showToast(context,"You have successfully given all the answers.")
                                }else{
                                    ToastUtils.showToast(context,"Please provide answer")
                                }
                            },
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                        ) {
                            Text("Submit")
                        }
                    }
                }
            }
        }
    }
}