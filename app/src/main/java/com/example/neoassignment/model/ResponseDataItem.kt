package com.example.neoassignment.model

data class ResponseDataItem(
    val id: Int,
    val image: String,
    val income_finish_flag: String,
    val input_type: String,
    val is_mandatory: String,
    val next_question_id: String,
    val on_skip_next_question: String,
    val option: List<Option>,
    val question_category: Any,
    val question_english: String,
    val question_group: String,
    val question_gujarati: Any,
    val question_hindi: Any,
    val question_kannada: Any,
    val question_marathi: Any,
    val question_telugu: Any,
    val status: String
)