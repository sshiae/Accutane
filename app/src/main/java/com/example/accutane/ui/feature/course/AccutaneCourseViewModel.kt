package com.example.accutane.ui.feature.course

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
import java.util.Date
import javax.inject.Inject

/**
 * View Model for [AccutaneCourseScreen]
 */
@HiltViewModel
class AccutaneCourseViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val interactor: AccutaneInteractor
) : BaseViewModel() {

    var state by mutableStateOf(
        AccutaneCourseContract.State(
            model = AccutaneCourseModel(
                id = null,
                name = "",
                dailyDose = 0.0,
                totalTargetDose = 0.0,
                accumulatedCourseDose = 0.0,
                appointmentReminderTime = "",
                createDate = Date(),
                terminated = false,
                remainingDays = 0,
                treatmentDay = 0L,
                percentage = 0F
            ),
            isAdding = true
        )
    )
        private set

    /**
     * Updates state
     */
    fun updateState() {
        viewModelScope.launch {
            try {
                showLoading()
                val courseId = stateHandle.get<Long>(NavigationKeys.Arg.ACCUTANE_COURSE_ID)
                if (courseId != 0L) {
                    val foundModel: AccutaneCourseModel =
                        interactor.getAccutaneCourseById(courseId)
                    state = state.copy(model = foundModel, isAdding = false)
                }
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    /**
     * It is used to save the model
     */
    fun saveModel(model: AccutaneCourseModel) {
        viewModelScope.launch {
            try {
                showLoading()
                checkModel(model)
                interactor.saveAccutaneCourse(model)
            } catch (e: Exception) {
                showErrorMessage(e.message)
            } finally {
                hideLoading()
            }
        }
    }

    private fun checkModel(model: AccutaneCourseModel) {
        if (model.totalTargetDose < model.dailyDose) {
            throw Exception(ERROR_TOTAL_DOSE_LESS_DAILY)
        }

        if (model.accumulatedCourseDose > model.totalTargetDose) {
            throw Exception(ERROR_ACCUMULATED_DOSE_GREATER_TOTAL)
        }
    }

    companion object {
        private const val ERROR_TOTAL_DOSE_LESS_DAILY = "Общая целевая доза не может быть меньше дневной дозы"
        private const val ERROR_ACCUMULATED_DOSE_GREATER_TOTAL = "Набранная доза не может быть больше общей целевой доза"
    }
}