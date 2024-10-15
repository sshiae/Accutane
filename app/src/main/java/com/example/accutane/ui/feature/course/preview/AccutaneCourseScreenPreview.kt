package com.example.accutane.ui.feature.course.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.accutane.R
import com.example.accutane.domain.model.AccutaneCourseModel
import com.example.accutane.ui.feature.course.AccutaneCourseContract
import com.example.accutane.ui.feature.course.AccutaneCourseEditField
import com.example.accutane.ui.feature.course.AccutaneCourseScreenContent
import com.example.accutane.ui.feature.course.AccutaneCourseTimeField
import com.example.accutane.ui.theme.AccutaneTheme
import java.util.Date

@Preview
@Composable
private fun AccutaneCourseScreenContentPreview() {
    AccutaneTheme {
        AccutaneCourseScreenContent(
            state = AccutaneCourseContract.State(
                model = AccutaneCourseModel(
                    id = 1L,
                    name = "Ваш курс Аккутана",
                    dailyDose = 0.5,
                    totalTargetDose = 40.0,
                    accumulatedCourseDose = 10.0,
                    appointmentReminderTime = "9:00 AM",
                    createDate = Date(),
                    terminated = true
                ),
                isAdding = true
            ),
            loadingState = false,
            errorMessageState = null,
            onClearError = {},
            onClickSave = {},
            onBackClicked = {}
        )
    }
}

@Preview
@Composable
private fun AccutaneCourseScreenContentLoadingPreview() {
    AccutaneTheme {
        AccutaneCourseScreenContent(
            state = AccutaneCourseContract.State(
                model = AccutaneCourseModel(
                    id = 1L,
                    name = "Ваш курс Аккутана",
                    dailyDose = 0.5,
                    totalTargetDose = 40.0,
                    accumulatedCourseDose = 10.0,
                    appointmentReminderTime = "9:00 AM",
                    createDate = Date(),
                    terminated = true
                ),
                isAdding = true
            ),
            loadingState = true,
            errorMessageState = null,
            onClearError = {},
            onClickSave = {},
            onBackClicked = {}
        )
    }
}

@Preview
@Composable
private fun AccutaneCourseScreenContentWithErrorPreview() {
    AccutaneTheme {
        AccutaneCourseScreenContent(
            state = AccutaneCourseContract.State(
                model = AccutaneCourseModel(
                    id = 1L,
                    name = "Ваш курс Аккутана",
                    dailyDose = 0.5,
                    totalTargetDose = 40.0,
                    accumulatedCourseDose = 10.0,
                    appointmentReminderTime = "9:00 AM",
                    createDate = Date(),
                    terminated = true
                ),
                isAdding = true
            ),
            loadingState = false,
            errorMessageState = "Ошибка произошла",
            onClearError = {},
            onClickSave = {},
            onBackClicked = {}
        )
    }
}

@Preview
@Composable
private fun AccutaneCourseEditFieldPreview() {
    AccutaneTheme {
        AccutaneCourseEditField(
            placeholderId = R.string.name_placeholder,
            value = "Test",
            onValueChanged = {}
        )
    }
}

@Preview
@Composable
private fun AccutaneCourseTimeFieldPreview() {
    AccutaneTheme {
        AccutaneCourseTimeField(
            placeholderId = R.string.name_placeholder,
            value = "22:55",
            onValueChanged = {}
        )
    }
}