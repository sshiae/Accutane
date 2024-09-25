package com.example.accutane.ui.feature.courses.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.accutane.model.AccutaneCourseItem
import com.example.accutane.ui.feature.courses.AccutaneCourseItem
import com.example.accutane.ui.feature.courses.AccutaneCourseList
import com.example.accutane.ui.feature.courses.AccutaneCoursesContract
import com.example.accutane.ui.feature.courses.AccutaneCoursesScreen
import com.example.accutane.ui.feature.courses.LoadingBar
import com.example.accutane.ui.feature.courses.ModalBottomSheetContent
import com.example.accutane.ui.feature.courses.SearchBar
import com.example.accutane.ui.theme.AccutaneTheme

@Preview
@Composable
private fun SearchBarPreview() {
    AccutaneTheme {
        SearchBar()
    }
}

@Preview
@Composable
private fun AccutaneCourseItemPreview() {
    AccutaneTheme {
        AccutaneCourseItem(
            title = "Акнекутан",
            subtitle = "23 ноября 2023"
        )
    }
}

@Preview
@Composable
private fun AccutaneCourseListPreview() {
    AccutaneTheme {
        AccutaneCourseList(
            courseItems = getPreviewCourseList()
        )
    }
}

@Preview
@Composable
private fun AccutaneCoursesScreenPreview() {
    AccutaneTheme(
        dynamicColor = false
    ) {
        AccutaneCoursesScreen(
            state = AccutaneCoursesContract.State(
                items = getPreviewCourseList(),
                isLoading = false
            )
        )
    }
}

@Preview
@Composable
private fun LoadingBarPreview() {
    AccutaneTheme {
        LoadingBar()
    }
}

@Preview
@Composable
private fun BottomSheetContentPreview() {
    AccutaneTheme {
        ModalBottomSheetContent(
            names = listOf("Аккутан", "Роаккутан", "Сотрет"),
            onDismiss = {}
        )
    }
}

private fun getPreviewCourseList(): List<AccutaneCourseItem> {
    val previewData = listOf(
        AccutaneCourseItem(
            id = "1",
            name = "Курс 1",
            dailyDose = 20.0,
            totalTargetDose = 1200.0,
            accumulatedCourseDose = 200.0,
            appointmentReminderTime = "2023-10-01T09:00:00",
            createDate = "23 ноября 2023"
        ),
        AccutaneCourseItem(
            id = "2",
            name = "Курс 2",
            dailyDose = 30.0,
            totalTargetDose = 1500.0,
            accumulatedCourseDose = 600.0,
            appointmentReminderTime = "2023-10-02T10:30:00",
            createDate = "23 ноября 2023"
        ),
        AccutaneCourseItem(
            id = "3",
            name = "Курс 3",
            dailyDose = 25.0,
            totalTargetDose = 1000.0,
            accumulatedCourseDose = 300.0,
            appointmentReminderTime = "2023-10-03T11:15:00",
            createDate = "23 ноября 2023"
        ),
        AccutaneCourseItem(
            id = "4",
            name = "Курс 4",
            dailyDose = 15.0,
            totalTargetDose = 800.0,
            accumulatedCourseDose = 150.0,
            appointmentReminderTime = "2023-10-04T14:00:00",
            createDate = "23 ноября 2023"
        ),
        AccutaneCourseItem(
            id = "5",
            name = "Курс 5",
            dailyDose = 40.0,
            totalTargetDose = 2000.0,
            accumulatedCourseDose = 1000.0,
            appointmentReminderTime = "2023-10-05T16:45:00",
            createDate = "23 ноября 2023"
        )
    )
    return previewData
}