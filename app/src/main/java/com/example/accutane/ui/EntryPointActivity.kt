package com.example.accutane.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.accutane.ui.NavigationKeys.Arg.ACCUTANE_COURSE_ID
import com.example.accutane.ui.feature.course.AccutaneCourseScreen
import com.example.accutane.ui.feature.courses.AccutaneCoursesScreen
import com.example.accutane.ui.feature.details.AccutaneCourseDetailsScreen
import com.example.accutane.ui.theme.AccutaneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccutaneTheme {
                AccutaneApp()
            }
        }
    }
}

@Composable
private fun AccutaneApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationKeys.Route.ACCUTANE_COURSES_LIST
    ) {
        composable(route = NavigationKeys.Route.ACCUTANE_COURSES_LIST) {
            AccutaneCoursesDestination(navController)
        }
        composable(
            route = "${NavigationKeys.Route.ACCUTANE_COURSE}/{$ACCUTANE_COURSE_ID}",
            arguments = listOf(
                navArgument(ACCUTANE_COURSE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            AccutaneCourseDestination(navController)
        }
        composable(
            route = "${NavigationKeys.Route.ACCUTANE_COURSES_LIST}/{$ACCUTANE_COURSE_ID}",
            arguments = listOf(
                navArgument(ACCUTANE_COURSE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            AccutaneCourseDetailsDestination(navController)
        }
    }
}

@Composable
private fun AccutaneCoursesDestination(
    navController: NavHostController
) {
    AccutaneCoursesScreen(
        onAddItem = {
            navController.navigate("${NavigationKeys.Route.ACCUTANE_COURSE}/0")
        },
        onItemClicked = { itemId ->
            navController.navigate("${NavigationKeys.Route.ACCUTANE_COURSES_LIST}/${itemId}")
        }
    )
}

@Composable
private fun AccutaneCourseDestination(
    navController: NavHostController
) {
    AccutaneCourseScreen(
        onBackClicked = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun AccutaneCourseDetailsDestination(
    navController: NavHostController
) {
    AccutaneCourseDetailsScreen(
        onEditItem = { itemId ->
            navController.navigate("${NavigationKeys.Route.ACCUTANE_COURSE}/$itemId")
        }
    )
}

object NavigationKeys {
    object Arg {
        const val ACCUTANE_COURSE_ID = "accutaneCourseId"
    }

    object Route {
        const val ACCUTANE_COURSES_LIST = "accutane_courses_list"
        const val ACCUTANE_COURSE = "accutane_course"
    }
}