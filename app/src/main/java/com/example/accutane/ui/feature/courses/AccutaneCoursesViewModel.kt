package com.example.accutane.ui.feature.courses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.accutane.base.ui.BaseViewModel
import com.example.accutane.domain.interactor.AccutaneInteractor
import com.example.accutane.domain.model.AccutaneCourseFilterModel
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
) : BaseViewModel() {

    var state by mutableStateOf(
        AccutaneCoursesContract.State(
            items = listOf(),
            filteredItems = listOf()
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
                val courses: List<AccutaneCourseModel> = interactor.getAccutaneCourses()
                state = state.copy(items = courses, filteredItems = courses)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * Used for filters the items
     */
    fun filterItems(filters: List<AccutaneCourseFilterModel>) {
        viewModelScope.launch {
            try {
                showLoading()
                val appliedFilters: List<String> = filters.filter { it.value }.map { it.key }
                val items: List<AccutaneCourseModel> = state.items
                val filteredItems: List<AccutaneCourseModel> = items
                    .filter { appliedFilters.contains(it.name) }
                state = state.copy(filteredItems = filteredItems)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * Returns a list of unique course names
     */
    fun getUniqueNames(): List<String> {
        return state.items.map { it.name }.distinct()
    }

    /**
     * Delete a course
     */
    fun deleteAccutaneCourse(id: Long) {
        viewModelScope.launch {
            try {
                showLoading()
                interactor.deleteAccutaneCourse(id)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }
}