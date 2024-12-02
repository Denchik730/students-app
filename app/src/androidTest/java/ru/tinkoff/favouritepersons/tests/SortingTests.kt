package ru.tinkoff.favouritepersons.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.favouritepersons.DatabaseHelper
import ru.tinkoff.favouritepersons.PreferenceRule
import ru.tinkoff.favouritepersons.kaspressoBuilder
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.screens.MainScreen
import ru.tinkoff.favouritepersons.WireMockHelper

class SortingTests: TestCase(kaspressoBuilder) {

    @Before
    fun createDb() {
        DatabaseHelper.init(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun clearDB() {
        DatabaseHelper.clear()
    }

    @get:Rule(order = 1)
    val preferenceRule = PreferenceRule(useMock = true)

    @get:Rule(order = 2)
    val mock = WireMockRule(5000)

    @get:Rule(order = 3)
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkedSortByDefault() = run {
        val mainScreen = MainScreen()

        with(mainScreen) {
            step("Нажать на иконку сортировки") {
                clickButtonSelectedSort()
            }

            device.uiDevice.waitForIdle()

            step(
                "Проверить что на экране выбора варианта сортировки выбран (checked) вариант “По умолчанию (в порядке добавления)”"
            ) {
                mainScreen.checkIsCheckedSortingByDefault()
            }
        }
    }

    @Test
    fun successSortByAgeTest() = run {
        WireMock.stubFor(
            WireMock.get("/api/")
                .inScenario("add persons")
                .whenScenarioStateIs(STARTED)
                .willSetStateTo("Step 1")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withBody(WireMockHelper.fileToString("success_download_person_response.json"))
                )
        )

        WireMock.stubFor(
            WireMock.get("/api/")
                .inScenario("add persons")
                .whenScenarioStateIs("Step 1")
                .willSetStateTo("Step 2")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withBody(WireMockHelper.fileToString("success_download_person_response_second.json"))
                )
        )

        WireMock.stubFor(
            WireMock.get("/api/")
                .inScenario("add persons")
                .whenScenarioStateIs("Step 2")
                .willSetStateTo("Step 3")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withBody(WireMockHelper.fileToString("success_download_person_response_third.json"))
                )
        )

        val mainScreen = MainScreen()


        with(mainScreen) {
            step("Добавить 3 пользователя") {
                clickButtonAddSelectExpand()
                repeat(3) {
                    clickButtonDownloadFromNetwork()
                }
            }

            step("Нажать на иконку сортировки") {
                clickButtonSelectedSort()
            }

            device.uiDevice.waitForIdle()

            step("Выбрать вариант “По возрасту”") {
                sortByAge()
            }

            step("Проверить что экран выбора сортировки закрылся, элементы в списке отображаются в порядке убывания возраста (от большего к меньшему)") {
                checkAgeFirstPerson(51)
                checkAgeLastPerson(22)
            }
        }
    }
}