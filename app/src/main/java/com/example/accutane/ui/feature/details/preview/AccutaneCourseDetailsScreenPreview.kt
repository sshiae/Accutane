package com.example.accutane.ui.feature.details.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.accutane.ui.feature.AccutaneErrorAlertDialog
import com.example.accutane.ui.feature.courses.NoDataPlug
import com.example.accutane.ui.feature.details.AccutaneCourseDetailsContract
import com.example.accutane.ui.feature.details.AccutaneCourseDetailsScreenContent
import com.example.accutane.ui.feature.details.CongratulationsDialog
import com.example.accutane.ui.theme.AccutaneTheme

@Preview
@Composable
private fun AccutaneCourseDetailsScreenContentPreview() {
    AccutaneTheme {
        AccutaneCourseDetailsScreenContent(
            state = AccutaneCourseDetailsContract.State(
                item = null
            ),
            loadingState = false,
            terminateCourse = {},
            resumeCourse = {},
            onClearError = {},
            errorMessageState = null,
            onEditItem = {},
            closeTerminateDialog = {},
            closeResumeDialog = {},
            showTerminateDialog = false,
            showResumeDialog = false
        )
    }
}

@Preview
@Composable
private fun CongratulationsDialogPreview() {
    AccutaneTheme {
        CongratulationsDialog(
            onDismiss = {}
        )
    }
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    AccutaneTheme {
        AccutaneErrorAlertDialog(
            title = "Ошибка возобновления курса",
            text = "Курс закончен!\nВозобновление невозможно",
            onDismiss = {}
        )
    }
}

@Preview
@Composable
private fun EmptyCourseListMessagePreview() {
    AccutaneTheme {
        NoDataPlug(
            text = "Необходимо добавить курс"
        )
    }
}