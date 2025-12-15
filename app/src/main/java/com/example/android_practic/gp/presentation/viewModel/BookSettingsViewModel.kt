package com.example.android_practic.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practic.gp.domain.interactor.BookInteractor
import com.example.android_practic.gp.presentation.model.BookSettingsState
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookSettingsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: BookInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(BookSettingsState())
    val viewState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.observeNewFirstSettings().collect { newFirst ->
                mutableState.update { it.copy(newFirst = newFirst) }
            }
        }
    }

    fun onBookFirstCheckedChange(isChecked: Boolean) {
        mutableState.update { it.copy(newFirst = isChecked) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }

    fun onSaveClicked(){
        viewModelScope.launch {
            interactor.setNewFirstSettings(viewState.value.newFirst)
            onBack()
        }

    }
}