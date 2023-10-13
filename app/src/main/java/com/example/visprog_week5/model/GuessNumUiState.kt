package com.example.visprog_week5.model

data class GuessNumUiState(
    val numberToGuess: Int = 0,
    val numberOfGuesses: Int = 0,
    val score: Int = 0,
    val isGameOver: Boolean = false
)
