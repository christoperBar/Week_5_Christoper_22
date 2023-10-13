package com.example.visprog_week5.ui.theme.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visprog_week5.ui.theme.view.ui.theme.VisProg_Week5Theme
import com.example.visprog_week5.viewModel.GuessNumViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign


@Composable
fun Soal1(GuessNumViewModel: GuessNumViewModel = viewModel()){
    val gameUiState by GuessNumViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Guess The Number",
            style = MaterialTheme.typography.titleLarge,
        )

        LayoutGame(
            onUserGuessChanged = {GuessNumViewModel.updateUserGuess(it)},
            onKeyboardDone = {GuessNumViewModel.checkGuesses()},
            userGuess = GuessNumViewModel.userGuess,
            numberOfGuess = gameUiState.numberOfGuesses,
            numberToGuess = gameUiState.numberToGuess,
            score =gameUiState.score,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            onClick = { GuessNumViewModel.checkGuesses() }
        ) {
            Text("Submit")
        }
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { GuessNumViewModel.resetGame() }
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutGame(
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    userGuess: String,
    numberOfGuess: Int,
    numberToGuess: Int,
    score: Int,
    modifier: Modifier = Modifier
){
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ){
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(modifier = Modifier
                .padding(end = 20.dp, top = 20.dp)
                .align(alignment = Alignment.End)){
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceTint)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    text = "Number Of Guesses: $numberOfGuess",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                text = "$numberToGuess",
                textAlign = TextAlign.Center,
                fontSize = 45.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "From 1 to 10 Guess the Number",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Score: $score",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                onValueChange = onUserGuessChanged,
                label = {Text("enter your number") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}

@Composable
fun FinalScoreDialog (
    onPlayAgain: () -> Unit,
    score: Int,
    modifier: Modifier = Modifier
){
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = {

        },
        title = { Text("Welp") },
        text = { Text("You scored: $score") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text= "exit")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = "Play again")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Soal1Preview() {
    VisProg_Week5Theme {
        Soal1()
    }
}