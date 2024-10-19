package com.example.accutane.ui.feature.courses

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.accutane.R
import com.example.accutane.domain.model.AccutaneCourseFilterModel
import com.example.accutane.domain.model.AccutaneCourseModel
import com.example.accutane.getCurrentRusDate
import com.example.accutane.getOrDefault
import com.example.accutane.set
import com.example.accutane.ui.feature.AccutaneButton
import com.example.accutane.ui.feature.AccutaneErrorAlertDialog
import com.example.accutane.ui.feature.LoadingBar
import com.example.accutane.ui.theme.Black80
import com.example.accutane.ui.theme.Gray80
import kotlinx.coroutines.launch

@Composable
fun AccutaneCoursesScreen(
    viewModel: AccutaneCoursesViewModel = hiltViewModel(),
    onAddItem: () -> Unit,
    onItemClicked: (id: Long?) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.firstLoad()
    }
    AccutaneCoursesContent(
        accutaneCoursesState = viewModel.state,
        loadingState = viewModel.loadingState,
        errorMessageState = viewModel.errorMessageState,
        onItemClicked = onItemClicked,
        onAddItem = onAddItem,
        onSetFilter = { key: String, value: Boolean -> viewModel.setFilterValue(key, value) },
        onDeleteItem = { id -> viewModel.deleteAccutaneCourse(id) },
        onFilterItems = { viewModel.filtersItems() },
        onSearch = { searchQuery -> viewModel.setSearchQuery(searchQuery) },
        onClearError = { viewModel.clearError() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccutaneCoursesContent(
    accutaneCoursesState: AccutaneCoursesContract.State,
    loadingState: Boolean,
    errorMessageState: String?,
    onAddItem: () -> Unit,
    onItemClicked: (id: Long?) -> Unit,
    onSetFilter: (key: String, value: Boolean) -> Unit,
    onDeleteItem: (id: Long?) -> Unit,
    onFilterItems: () -> Unit,
    onSearch: (searchQuery: String) -> Unit,
    onClearError: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val onShowBottomSheet = { showBottomSheet = true }
    Scaffold(
        topBar = {
            AccutaneCoursesAppBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                SearchBar(
                    onShowBottomSheet = onShowBottomSheet,
                    searchQuery = accutaneCoursesState.searchQuery,
                    onSearch = onSearch
                )
                AccutaneCourseList(
                    courseItems = accutaneCoursesState.filteredItems,
                    onItemClicked = onItemClicked,
                    onDeleteItem = onDeleteItem,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                )
                AccutaneButton(
                    textId = R.string.add_course_text_btn,
                    onClick = onAddItem
                )
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    ModalBottomSheetContent(
                        filters = accutaneCoursesState.filters,
                        onSetFilter = onSetFilter,
                        onFilterItems = onFilterItems,
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
    onItemClicked: (id: Long?) -> Unit,
    onDeleteItem: (id: Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (courseItems.isEmpty()) {
        NoDataPlug(
            text = stringResource(id = R.string.no_data_plug)
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 10.dp
            ),
            modifier = modifier
        ) {
            itemsIndexed(courseItems) { index, item ->
                AccutaneCourseItem(
                    id = item.id,
                    title = item.name,
                    subtitle = item.createDate.getCurrentRusDate(),
                    onItemClicked = onItemClicked,
                    onDeleteItem = onDeleteItem
                )
                if (index < courseItems.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NoDataPlug(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 0.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pill),
            contentDescription = "Нет курсов",
            modifier = Modifier.size(100.dp)
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AccutaneCourseItem(
    id: Long?,
    title: String,
    subtitle: String,
    onItemClicked: (id: Long?) -> Unit,
    onDeleteItem: (id: Long?) -> Unit,
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
                        onDeleteItem(id)
                    }
            )
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onShowBottomSheet: () -> Unit,
    onSearch: (searchQuery: String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchQuery,
        onValueChange = {
            onSearch(it)
        },
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
                        onShowBottomSheet()
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
fun ModalBottomSheetContent(
    filters: List<AccutaneCourseFilterModel>,
    onSetFilter: (key: String, value: Boolean) -> Unit,
    onFilterItems: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        LazyColumn(
            modifier = Modifier
                .height(144.dp)
        ) {
            items(filters) { filter ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        checked = filter.value,
                        onCheckedChange = {
                            onSetFilter(filter.key, it)
                        }
                    )
                    Text(text = filter.key)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        AccutaneButton(
            textId = R.string.filters_text_btn,
            onClick = {
                onFilterItems()
                onDismiss()
            }
        )
    }
}