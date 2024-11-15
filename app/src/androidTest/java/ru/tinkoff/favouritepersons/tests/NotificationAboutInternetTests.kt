package ru.tinkoff.favouritepersons.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.favouritepersons.DatabaseHelper
import ru.tinkoff.favouritepersons.kaspressoBuilder
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.screens.MainScreen

class NotificationAboutInternetTests: TestCase(kaspressoBuilder) {

    @Before
    fun createDb() {
        DatabaseHelper.init(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun clearDB() {
        DatabaseHelper.clear()
    }

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun appearanceNotificationUnableInternet() = run {
        device.network.disable()

        val mainScreen = MainScreen()

        with(mainScreen) {
            step("Нажать на кнопку добавления пользователя") {
                clickButtonAddSelectExpand()
            }

            step("Нажать на кнопку добавления пользователя через интернет") {
                clickButtonDownloadFromNetwork()
            }

            step("Появляется уведомление с текстом “Internet error! Check your connection”") {
                mainScreen.checkSnackbarNoInternet()
            }
        }

    }
}