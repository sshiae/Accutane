package com.example.accutane.ui.feature.courses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accutane.domain.interactor.AccutaneInteractor
import com.example.accutane.domain.model.AccutaneCourseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model for [AccutaneCoursesScreen]
 */
@HiltViewModel
class AccutaneCoursesViewModel @Inject constructor(
    private val interactor: AccutaneInteractor
) : ViewModel() {

    var state by mutableStateOf(
        AccutaneCoursesContract.State(
            items = listOf(),
            isLoading = true
        )
    )
        private set

    init {
        firstLoad()
    }

    private fun firstLoad() {
        viewModelScope.launch {
            val courses: List<AccutaneCourseModel> = interactor.getAccutaneCourses()
            state = state.copy(
                items = courses,
                isLoading = false
            )
        }
    }
}