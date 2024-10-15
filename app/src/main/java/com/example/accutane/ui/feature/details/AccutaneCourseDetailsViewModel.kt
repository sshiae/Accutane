package com.example.accutane.ui.feature.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.accutane.base.ui.BaseViewModel
import com.example.accutane.domain.interactor.AccutaneInteractor
import com.example.accutane.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Viewmodel for [AccutaneCourseDetailsScreen]
 */
@HiltViewModel
class AccutaneCourseDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val interactor: AccutaneInteractor
) : BaseViewModel() {

    var state by mutableStateOf(
        AccutaneCourseDetailsContract.State(
            item = null
        )
    )
        private set

    init {
        firstLoad()
    }

    private fun firstLoad() {
        viewModelScope.launch {
            try {
                showLoading()
                val itemId = stateHandle.get<Long>(NavigationKeys.Arg.ACCUTANE_COURSE_ID)
                    ?: throw IllegalArgumentException("No course id was passed to destination.")
                val accutaneCourse = interactor.getAccutaneCourseById(itemId)
                state = state.copy(item = accutaneCourse)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }
}