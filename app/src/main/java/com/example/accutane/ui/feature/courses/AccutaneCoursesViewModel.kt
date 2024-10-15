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
     * Used for set a filters
     */
    fun setFilters(filters: List<AccutaneCourseFilterModel>) {
        viewModelScope.launch {
            try {
                showLoading()
                state = state.copy(filters = filters)
                makeItemsForContent()
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * Used for set a search query
     */
    fun setSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            try {
                showLoading()
                state = state.copy(searchQuery = searchQuery)
                makeItemsForContent()
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    private fun makeItemsForContent() {
        var items: List<AccutaneCourseModel> = state.items
        val filters: List<AccutaneCourseFilterModel> = state.filters
        if (filters.isNotEmpty()) {
            items = applyFiltersForItems(items)
        }
        val searchQuery: String = state.searchQuery
        if (searchQuery.isNotEmpty()) {
            items = applySearchForItems(items)
        }
        state = state.copy(filteredItems = items)
    }

    private fun applyFiltersForItems(
        items: List<AccutaneCourseModel>
    ): List<AccutaneCourseModel> {
        val filters: List<AccutaneCourseFilterModel> = state.filters
        val appliedFilters: List<String> = filters.filter { it.value }.map { it.key }
        val filteredItems: List<AccutaneCourseModel> = items
            .filter { appliedFilters.contains(it.name) }
        return filteredItems
    }

    private fun applySearchForItems(
        items: List<AccutaneCourseModel>
    ): List<AccutaneCourseModel> {
        val searchQuery: String = state.searchQuery
        val filteredItems: List<AccutaneCourseModel> = items
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
        return filteredItems
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
    fun deleteAccutaneCourse(id: Long?) {
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