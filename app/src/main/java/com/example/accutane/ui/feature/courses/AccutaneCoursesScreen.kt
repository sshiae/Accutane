package com.example.accutane.ui.feature.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.accutane.R
import com.example.accutane.domain.model.AccutaneCourseModel
import com.example.accutane.ui.theme.Black80
import com.example.accutane.ui.theme.Gray80
import com.example.accutane.ui.theme.White80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccutaneCoursesScreen(
    state: AccutaneCoursesContract.State,
    onNavigationRequested: (itemId: Long) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            AccutaneCoursesAppBar()
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Column {
                SearchBar()
                Box {
                    AccutaneCourseList(
                        courseItems = state.items,
                        onItemClicked = onNavigationRequested
                    )
                    if (state.isLoading) {
                        LoadingBar()
                    }
                }
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp,
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.add_course_text_btn),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = White80
                    ),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                ModalBottomSheetContent(
                    names = emptyList(),
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccutaneCoursesAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.accutane_courses_top_bar))
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
fun AccutaneCourseList(
    courseItems: List<AccutaneCourseModel>,
    onItemClicked: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 30.dp,
            bottom = 30.dp
        ),
        modifier = modifier
    ) {
        itemsIndexed(courseItems) { index, item ->
            AccutaneCourseItem(
                id = item.id,
                title = item.name,
                subtitle = item.createDate,
                onItemClicked = onItemClicked
            )
            if (index < courseItems.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AccutaneCourseItem(
    id: Long,
    title: String,
    subtitle: String,
    onItemClicked: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .height(58.dp)
            .fillMaxWidth()
            .clickable {
                onItemClicked(id)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Black80
                    ),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Gray80
                    ),
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_24px),
                contentDescription = stringResource(id = R.string.accutane_course_item_delete_desc),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(24.dp)
                    .clickable {
                        //TODO
                    }
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_list_24px),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        //TODO
                    }
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.placeholder_search)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ModalBottomSheetContent(
    names: List<String>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val checkedState = remember {
        mutableStateListOf(false, false, false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        names.forEachIndexed { index, name ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = checkedState[index], 
                    onCheckedChange = {
                        checkedState[index] = it
                    }
                )
                Text(text = name)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.filters_text_btn),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = White80
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}