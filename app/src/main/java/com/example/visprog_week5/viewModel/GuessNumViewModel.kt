package com.example.visprog_week5.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.visprog_week5.model.GuessNumUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuessNumViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GuessNumUiState())
    val uiState: StateFlow<GuessNumUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }
    fun updateUserGuess(guessedNumber: String){
        userGuess = guessedNumber
    }

    private fun updateGameState(updatedScore: Int, updatedNoG: Int, numbertogues: Int){
        if (updatedNoG >= 3){
            _uiState.update {currentState ->
                currentState.copy(
                    isGameOver = true,
                    score = updatedScore,
                    numberOfGuesses = updatedNoG
                )
            }
        }
        else{
            _uiState.update {currentState ->
                currentState.copy(
                    score = updatedScore,
                    numberToGuess = numbertogues,
                    numberOfGuesses = updatedNoG
                )
            }
        }
    }

    fun checkGuesses(){
        if(userGuess.toInt() == _uiState.value.numberToGuess){
            val updatedScore = _uiState.value.score +1
            val updatedNoG = _uiState.value.numberOfGuesses +1
            val numbertogues= generateRandomNumber()
            updateGameState(updatedScore,updatedNoG,numbertogues)
        }
        else {
            val updatedNoG = _uiState.value.numberOfGuesses + 1
            val updatedScore = _uiState.value.score
            val numbertogues = _uiState.value.numberToGuess
            updateGameState(updatedScore,updatedNoG,numbertogues)
        }
        updateUserGuess("")
    }

    fun resetGame(){
        _uiState.value = GuessNumUiState(numberToGuess = generateRandomNumber(), numberOfGuesses = 0, score = 0, isGameOver = false)
    }
    private fun generateRandomNumber(): Int {
        return (1..10).random()
    }
}