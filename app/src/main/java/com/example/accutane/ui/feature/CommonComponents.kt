package com.example.accutane.ui.feature

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accutane.R
import com.example.accutane.ui.theme.Blue80
import com.example.accutane.ui.theme.GrayForDivider
import com.example.accutane.ui.theme.White80

@Composable
fun AccutaneInputField(
    @StringRes placeholderId: Int,
    @StringRes errorMessage: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    validate: (String) -> Boolean,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var valueState by rememberSaveable { mutableStateOf(value) }
    var isValidState by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(value) {
        valueState = value
    }

    Column {
        OutlinedTextField(
            value = valueState,
            onValueChange = {
                valueState = it
                isValidState = validate(it)
                if (isValidState) {
                    onValueChanged(it)
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            label = {
                Text(text = stringResource(id = placeholderId))
            },
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            isError = isValidState.not(),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )

        if (isValidState.not()) {
            Text(
                text = stringResource(id = errorMessage),
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AccutaneErrorAlertDialog(
    text: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Ошибка"
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Ошибка",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        confirmButton = {
            AccutaneButton(
                textId = R.string.close_btn_text,
                onClick = { }
            )
        },
        dismissButton = null
    )
}

@Composable
fun AccutaneButton(
    @StringRes textId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = textId),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = White80
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(GrayForDivider)
    )
}

@Composable
fun TwoTextInRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal
            ),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Blue80,
                fontWeight = FontWeight.Normal
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}