package com.example.accutane.ui.feature.course

import android.app.TimePickerDialog
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.accutane.R
import com.example.accutane.domain.model.AccutaneCourseModel
import com.example.accutane.ui.feature.AccutaneButton
import com.example.accutane.ui.feature.AccutaneErrorAlertDialog
import com.example.accutane.ui.feature.AccutaneInputField
import com.example.accutane.ui.feature.LoadingBar
import java.util.Calendar
import java.util.Locale

@Composable
fun AccutaneCourseScreen(
    onBackClicked: () -> Unit,
    viewModel: AccutaneCourseViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.firstLoad()
    }
    AccutaneCourseScreenContent(
        state = viewModel.state,
        loadingState = viewModel.loadingState,
        errorMessageState = viewModel.errorMessageState,
        onBackClicked = onBackClicked,
        onClearError = {
            viewModel.clearError()
        },
        onClickSave = { model ->
            viewModel.saveModel(model)
        }
    )
}

@Composable
fun AccutaneCourseScreenContent(
    state: AccutaneCourseContract.State,
    loadingState: Boolean,
    errorMessageState: String?,
    onBackClicked: () -> Unit,
    onClearError: () -> Unit,
    onClickSave: (AccutaneCourseModel) -> Unit
) {
    val model: AccutaneCourseModel = state.model
    val (titleId, btnTextId) = when {
        state.isAdding -> R.string.accutane_course_top_bar_add to R.string.add_course_text_btn
        else -> R.string.accutane_course_top_bar_edit to R.string.edit_course_text_btn
    }
    var newModel = AccutaneCourseModel(
        id = model.id,
        name = model.name,
        dailyDose = model.dailyDose,
        totalTargetDose = model.totalTargetDose,
        accumulatedCourseDose = model.accumulatedCourseDose,
        appointmentReminderTime = model.appointmentReminderTime,
        createDate = model.createDate,
        terminated = false,
        treatmentDay = model.treatmentDay,
        remainingDays = model.remainingDays,
        percentage = model.percentage
    )
    Scaffold(
        topBar = {
            AccutaneCourseTopBar(titleId = titleId)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    AccutaneCourseEditField(
                        placeholderId = R.string.name_placeholder,
                        value = model.name,
                        onValueChanged = { value ->
                            newModel = newModel.copy(
                                name = value
                            )
                        }
                    )
                    AccutaneCourseDoubleField(
                        placeholderId = R.string.daily_dose_placeholder,
                        value = model.dailyDose.toString(),
                        onValueChanged = { value ->
                            newModel = newModel.copy(
                                dailyDose = value.toDouble()
                            )
                        }
                    )
                    AccutaneCourseDoubleField(
                        placeholderId = R.string.total_target_dose_placeholder,
                        value = model.totalTargetDose.toString(),
                        onValueChanged = { value ->
                            newModel = newModel.copy(
                                totalTargetDose = value.toDouble()
                            )
                        }
                    )
                    AccutaneCourseDoubleField(
                        placeholderId = R.string.accumulated_course_dose_placeholder,
                        value = model.accumulatedCourseDose.toString(),
                        onValueChanged = { value ->
                            newModel = newModel.copy(
                                accumulatedCourseDose = value.toDouble()
                            )
                        }
                    )
                    AccutaneCourseTimeField(
                        placeholderId = R.string.appointment_reminder_time_placeholder,
                        value = model.appointmentReminderTime,
                        onValueChanged = { value ->
                            newModel = newModel.copy(
                                appointmentReminderTime = value
                            )
                        }
                    )
                }
                AccutaneButton(
                    textId = btnTextId,
                    onClick = {
                        onClickSave(newModel)
                        onBackClicked()
                    }
                )
            }
            if (loadingState) {
                LoadingBar()
            }
        }
    }
    errorMessageState?.let { errorMessage ->
        AccutaneErrorAlertDialog(
            text = errorMessage,
            onDismiss = onClearError
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccutaneCourseTopBar(
    @StringRes titleId: Int,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(stringResource(id = titleId))
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun AccutaneCourseEditField(
    @StringRes placeholderId: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AccutaneInputField(
        placeholderId = placeholderId,
        errorMessage = R.string.invalid_value,
        value = value,
        onValueChanged = onValueChanged,
        validate = { validateValue ->
            validateValue.isNotEmpty() && validateValue.length < 40
        },
        modifier = modifier
    )
}

@Composable
fun AccutaneCourseDoubleField(
    @StringRes placeholderId: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AccutaneInputField(
        placeholderId = placeholderId,
        errorMessage = R.string.invalid_number_format,
        value = value,
        onValueChanged = onValueChanged,
        validate = { validateValue ->
            try {
                if (validateValue.isEmpty()) {
                    return@AccutaneInputField false
                }
                validateValue.toDouble()
                return@AccutaneInputField true
            } catch (e: NumberFormatException) {
                return@AccutaneInputField false
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        ),
        modifier = modifier
    )
}

@Composable
fun AccutaneCourseTimeField(
    @StringRes placeholderId: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var time by rememberSaveable { mutableStateOf(value) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    AccutaneInputField(
        placeholderId = placeholderId,
        errorMessage = R.string.invalid_time_format,
        value = time,
        onValueChanged = onValueChanged,
        validate = { validateValue ->
            val regex = Regex("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")
            regex.matches(validateValue)
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_today_24px),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        showDialog = true
                    }
            )
        },
        modifier = modifier
    )
    if (showDialog) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(
            LocalContext.current,
            { _, selectedHour, selectedMinute ->
                time = String.format(Locale("ru"), "%02d:%02d", selectedHour, selectedMinute)
                showDialog = false
            },
            hour,
            minute,
            true
        ).show()
    }
}