package com.example.accutane.ui.feature.course

import com.example.accutane.domain.model.AccutaneCourseModel

/**
 * Contract for an accutane
 */
class AccutaneCourseContract {

    /**
     * State for an accutane
     */
    data class State(
        val model: AccutaneCourseModel,
        val isAdding: Boolean
    )
}