package com.example.accutane.ui.feature.details

import android.content.res.Resources.NotFoundException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.accutane.base.ui.BaseViewModel
import com.example.accutane.domain.interactor.AccutaneInteractor
import com.example.accutane.domain.model.AccutaneCourseModel
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

    var showTerminateDialog by mutableStateOf(false)
        private set

    var showResumeDialog by mutableStateOf(false)
        private set

    /**
     * Updates state
     */
    fun firstLoad() {
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

    private fun requireItem(): AccutaneCourseModel {
        return state.item ?: throw NotFoundException()
    }

    /**
     * Used to terminate the course
     */
    fun terminateCourse() {
        viewModelScope.launch {
            try {
                showLoading()
                val item: AccutaneCourseModel = requireItem().let {
                    it.copy(
                        remainingDays = it.calculateRemainingDays(),
                        treatmentDay = it.calculateTreatmentDay(),
                        percentage = it.calculatePercentage(),
                        terminated = true
                    )
                }
                interactor.saveAccutaneCourse(item)
                state = state.copy(item = item)
                showTerminateDialog = true
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * Used to resume the course
     */
    fun resumeCourse() {
        viewModelScope.launch {
            try {
                showLoading()
                val originalItem: AccutaneCourseModel = requireItem()
                if (originalItem.percentage == 100f) {
                    showResumeDialog = true
                    return@launch
                }
                val item: AccutaneCourseModel = requireItem().copy(terminated = false)
                interactor.saveAccutaneCourse(item)
                state = state.copy(item = item)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * It is used to close the dialog when the course is terminated.
     */
    fun closeTerminateDialog() {
        viewModelScope.launch {
            try {
                showLoading()
                showTerminateDialog = false
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * Used to close the dialog when resuming the course
     */
    fun closeResumeDialog() {
        viewModelScope.launch {
            try {
                showLoading()
                showResumeDialog = false
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }
}