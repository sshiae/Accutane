package com.example.accutane.ui.feature.details.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.accutane.ui.feature.ErrorDialog
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
            resumeCourse = {}
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
        ErrorDialog(
            title = "Ошибка возобновления курса",
            text = "Курс закончен!\u2028Возобновление невозможно",
            onDismiss = {}
        )
    }
}