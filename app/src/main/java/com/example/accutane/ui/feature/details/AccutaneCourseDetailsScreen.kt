package com.example.accutane.ui.feature.details

import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.accutane.R
import com.example.accutane.domain.model.AccutaneCourseModel
import com.example.accutane.getCurrentRusDate
import com.example.accutane.ui.feature.AccutaneButton
import com.example.accutane.ui.feature.AccutaneErrorAlertDialog
import com.example.accutane.ui.feature.DividerLine
import com.example.accutane.ui.feature.LoadingBar
import com.example.accutane.ui.feature.TwoTextInRow
import com.example.accutane.ui.theme.Black80
import com.example.accutane.ui.theme.Blue40
import com.example.accutane.ui.theme.Blue80
import com.example.accutane.ui.theme.GrayForPieChart

@Composable
fun AccutaneCourseDetailsScreen(
    viewModel: AccutaneCourseDetailsViewModel = hiltViewModel()
) {
    AccutaneCourseDetailsScreenContent(
        state = viewModel.state,
        loadingState = viewModel.loadingState,
        errorMessageState = viewModel.errorMessageState,
        terminateCourse = { },
        resumeCourse = { },
        onClearError = {
            viewModel.clearError()
        }
    )
}

@Composable
fun AccutaneCourseDetailsScreenContent(
    state: AccutaneCourseDetailsContract.State,
    loadingState: Boolean,
    errorMessageState: String?,
    terminateCourse: () -> Unit,
    resumeCourse: () -> Unit,
    onClearError: () -> Unit
) {
    state.item?.let { item ->
        val remainingDays: Int = item.getRemainingDays()
        val treatmentDay: Long = item.getTreatmentDay()
        val percentage: Float =
            Math.round(((item.accumulatedCourseDose / item.totalTargetDose) * 100)).toFloat()
        Scaffold { innerPadding ->
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    PieChart(
                        percentage = percentage,
                        modifier = Modifier
                            .padding(0.dp, 30.dp, 0.dp, 16.dp)
                    )
                    Text(
                        text = "Сегодня ${treatmentDay}-й день лечения",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Black80,
                            fontWeight = FontWeight.Normal
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.createDate.getCurrentRusDate(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Black80,
                            fontWeight = FontWeight.Normal
                        ),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    )
                    DividerLine()
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TwoTextInRow(
                                title = stringResource(id = R.string.daily_dose),
                                value = "${item.dailyDose} мг/кг"
                            )
                            TwoTextInRow(
                                title = stringResource(id = R.string.cumulative_dose),
                                value = "${item.accumulatedCourseDose} мг"
                            )
                            TwoTextInRow(
                                title = stringResource(id = R.string.target_dose),
                                value = "${item.totalTargetDose} мг"
                            )
                            TwoTextInRow(
                                title = stringResource(id = R.string.days_left),
                                value = "$remainingDays дней"
                            )
                        }
                        if (item.terminated) {
                            AccutaneButton(
                                textId = R.string.resume_course_text_btn,
                                onClick = resumeCourse
                            )
                        } else {
                            AccutaneButton(
                                textId = R.string.terminate_course_text_btn,
                                onClick = terminateCourse
                            )
                        }
                    }
                }
                FloatingActionButton(
                    contentColor = Color.White,
                    containerColor = Blue80,
                    shape = CircleShape,
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.add_course_btn_description)
                    )
                }
                if (loadingState) {
                    LoadingBar()
                }
            }
        }
    } ?: run {
        LoadingBar()
    }
    errorMessageState?.let { errorMessage ->
        AccutaneErrorAlertDialog(
            text = errorMessage,
            onDismiss = onClearError
        )
    }
}

@Composable
fun PieChart(
    percentage: Float,
    modifier: Modifier = Modifier
) {
    val sweepAngle = 360 * (percentage / 100)
    val text = "${percentage.toInt()}%"
    val filledColor = if (isSystemInDarkTheme()) Blue40 else Blue80
    Box(
        modifier = modifier.size(179.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = GrayForPieChart,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )

            drawArc(
                color = filledColor,
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )
        }
        Text(
            text = text,
            color = filledColor,
            fontSize = 40.sp,
            style = TextStyle(fontWeight = FontWeight.Normal),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun CongratulationsDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = null,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pill),
                    contentDescription = "Поздравление",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Поздравляем! Вы завершили курс.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        confirmButton = {
            AccutaneButton(
                textId = R.string.close_btn_text,
                onClick = { /*TODO*/ }
            )
        },
        dismissButton = null
    )
}