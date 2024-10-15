package com.example.accutane.ui.feature.details

import com.example.accutane.domain.model.AccutaneCourseModel

/**
 * Contract for details
 */
class AccutaneCourseDetailsContract {

    /**
     * State for details
     */
    data class State(
        val item: AccutaneCourseModel?
    )
}