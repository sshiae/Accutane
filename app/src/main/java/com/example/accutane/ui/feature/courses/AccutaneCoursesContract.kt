package com.example.accutane.ui.feature.courses

import com.example.accutane.domain.model.AccutaneCourseModel

/**
 * Contract for a list of courses
 */
class AccutaneCoursesContract {

    /**
     * State for the list of courses
     */
    data class State(
        val items: List<AccutaneCourseModel> = listOf(),
        val filteredItems: List<AccutaneCourseModel> = listOf()
    )
}