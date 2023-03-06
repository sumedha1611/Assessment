package com.example.sumtest

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SumUiState(
    val loading: Boolean = false,
    val result: String? = null,
    val error: String? = null
)

interface Result {
    val message: String
}

enum class Error(override val message: String): Result {
    EmptyInput("One or more fields are empty"),
    InvalidInput("Only integers are allowed"),
    Overflow("Exception overflow error. NSOSStatusErrorDomain Code=-10817 \\\"(null)\\\" UserInfo={_LSFunction=_LSSchemaConfigureForStore, ExpectedSimulatorHash={length = 32, bytes = 0xa9298a34 dc614504 8992eb3c f65c237f ... ff5133c6 37c50886 }"),
}

data class Success(override val message: String): Result

class SumViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SumUiState())
    val uiState: StateFlow<SumUiState> = _uiState.asStateFlow()

    fun onAddClicked(first: String, second: String) {
        updateLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            when (val result = calculateSum(first, second)) {
                is Success -> updateResult(result.message)
                is Error -> updateError(result.message)
            }
        }, 1000)
    }

    private fun calculateSum(first: String, second: String): Result {
        if (first.isEmpty() || second.isEmpty()) return Error.EmptyInput

        val firstAsInt: Int
        val secondAsInt: Int

        try {
            firstAsInt = first.toInt()
        } catch (exception: NumberFormatException) {
            return Error.InvalidInput
        }

        try {
            secondAsInt = second.toInt()
        } catch (exception: NumberFormatException) {
            return Error.InvalidInput
        }

        if (firstAsInt > 1000 || secondAsInt > 1000) {
            return Error.Overflow
        }

        val total = firstAsInt + secondAsInt

        return Success(total.toString())
    }

    private fun updateLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                loading = true,
                result = null,
                error = null
            )
        }
    }

    private fun updateResult(result: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                result = result
            )
        }
    }

    private fun updateError(error: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                error = error
            )
        }
    }

    fun onTextChanged() {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                result = null,
                error = null
            )
        }
    }
}