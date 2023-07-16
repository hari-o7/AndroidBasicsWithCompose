package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    //    game ui state
    //    holds the current UI state of the game
    //    MutableStateFlow allows us to emit and update the state value.
    private val _uiState = MutableStateFlow(GameUiState())

    //StateFlow provides a read-only access to the current state value,
    // which can be observed by other components.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //hold the current unscrambled word for the game.
    private lateinit var currentWord: String

    // Set of words already used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else
            usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)

    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
//        In simpler terms,
//        _uiState.value allows you to read the current UI state,
//        and when you assign a new value to it,
//        it updates the UI state and notifies any components
//        observing the uiState state flow about the state change,
//        so they can update the UI based on the new state.
    }

    init {
        resetGame()
    }

}