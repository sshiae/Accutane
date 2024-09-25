package com.example.accutane.ui.feature.courses

import com.example.accutane.model.AccutaneCourseItem

/**
 * Contract for a list of courses
 */
class AccutaneCoursesContract {

    /**
     * State for the list of courses
     */
    data class State(
        val items: List<AccutaneCourseItem> = listOf(),
        val isLoading: Boolean = false
    )
}