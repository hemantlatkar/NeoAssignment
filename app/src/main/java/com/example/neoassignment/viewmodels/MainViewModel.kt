    package com.example.neoassignment.viewmodels

    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.neoassignment.model.ResponseData
    import com.example.neoassignment.model.ResponseDataItem
    import com.example.neoassignment.repository.QuestionRepository
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.SharingStarted
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.stateIn
    import kotlinx.coroutines.launch

    class MainViewModel(private val repository: QuestionRepository) : ViewModel() {


        private val _dataState: MutableStateFlow<DataState?> = MutableStateFlow(null)
        val dataState: StateFlow<DataState?>
            get() = _dataState

        init {
            fetchData()
        }

        val questions : MutableLiveData<ResponseData>
        get() = repository.questions

        fun fetchData() {
            viewModelScope.launch {
                _dataState.value = DataState.Loading
                try {
                    val data = repository.getQuestions()
                    _dataState.value = data?.let { DataState.Success(it) }
                } catch (e: Exception) {
                    _dataState.value = DataState.Error(e.message ?: "Unknown error")
                }
            }
        }

        private val _currentQuestion: MutableStateFlow<ResponseDataItem?> = MutableStateFlow(null)
        val currentQuestion: StateFlow<ResponseDataItem?>
            get() = _currentQuestion.stateIn(viewModelScope, SharingStarted.Eagerly, null)

        fun selectCurrentQuestion(id: Int) {
            var tempId = id
            if(tempId == 0) tempId = 1
            if(!(_dataState.value as DataState.Success).data.isEmpty()){
                val currentQuestion = (_dataState.value as DataState.Success).data.find { it.id == tempId }
                _currentQuestion.value = currentQuestion
            }
        }
    }