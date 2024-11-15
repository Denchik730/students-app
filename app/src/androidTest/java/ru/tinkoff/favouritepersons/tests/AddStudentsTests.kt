package ru.tinkoff.favouritepersons.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.favouritepersons.DatabaseHelper
import ru.tinkoff.favouritepersons.PreferenceRule
import ru.tinkoff.favouritepersons.kaspressoBuilder
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.screens.AddPersonScreen
import ru.tinkoff.favouritepersons.screens.MainScreen
import ru.tinkoff.favouritepersons.WireMockHelper

class AddStudentsTests: TestCase(kaspressoBuilder) {

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
    fun successAddPersonTest() = run {
        val mainScreen = MainScreen()
        val addScreen = AddPersonScreen()

        with(mainScreen) {
            step("Нажать на кнопку добавления пользователя") {
                clickButtonAddSelectExpand()
            }

            step("Нажать на кнопку добавления пользователя вручную (рука)") {
                addPersonManually()
            }
        }

        with(addScreen) {
            step("На открывшемся экране заполнить поля валидными данными (не забудь про URL картинки)") {
                enterNewPersonFullData(
                    "Иван",
                    "Иванов",
                    "М",
                    "1953-12-13",
                    "ivan@ivan.ru",
                    "+79196446900",
                    "Улица Пушкина, дом Колотушкина",
                    "https://img02.rl0.ru/afisha/e780x-i/daily.afisha.ru/uploads/images/9/c8/9c8dbd93078c4276a741b47c3fe1502b.jpg",
                    "55"
                )
            }

            step("Нажать “Сохранить”") {
                clickSubmitButton()
            }
        }

        step("Проверить что открывается главный экран, на котором появился добавленный пользователь с корректно введенными данными и правильно подсчитанным возрастом") {
            mainScreen.checkAgeFirstPerson(70)
        }
    }

    @Test
    fun hiddenTextEmptyPersonsList() = run {
        WireMock.stubFor(
            WireMock.get("/api/")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withBody(WireMockHelper.fileToString("success_download_person_response.json"))
                )
        )

        val mainScreen = MainScreen()

        with(mainScreen) {
            step("Нажать на кнопку добавления нового студента") {
                clickButtonAddSelectExpand()
            }

            step("Нажать на кнопку загрузки студента из интернета (облачко)") {
                clickButtonDownloadFromNetwork()
            }

            step("Проверить что сообщение об отсутствии студентов в списке более не отображается") {
                isTextEmptyListHidden()
            }
        }
    }

    @Test
    fun errorGenderInput() = run {
        val mainScreen = MainScreen()
        val addScreen = AddPersonScreen()

        with(mainScreen) {
            step("Нажать на кнопку добавления пользователя") {
                clickButtonAddSelectExpand()
            }

            step("Нажать на кнопку добавления пользователя вручную (рука)") {
                addPersonManually()
            }
        }

        with(addScreen) {
            step("Нажать “Сохранить”") {
                clickSubmitButton()
            }

            step("Проверить что Рядом с полем \"Пол\" отображается сообщение об ошибке “Поле должно быть заполнено буквами М или Ж”") {
                checkErrorGenderInput()
            }
        }
    }

    @Test
    fun hiddenErrorGenderInput() = run {
        val mainScreen = MainScreen()
        val addScreen = AddPersonScreen()

        with(mainScreen) {
            step("Нажать на кнопку добавления пользователя") {
                clickButtonAddSelectExpand()
            }

            step("Нажать на кнопку добавления пользователя вручную (рука)") {
                addPersonManually()
            }
        }


        with(addScreen) {
            step("Для удобства ввести корректные данные во все поля, кроме одного (например, “Пол”)") {
                enterNewPersonFullData(
                    "Иван",
                    "Иванов",
                    "3",
                    "1953-12-13",
                    "ivan@ivan.ru",
                    "+79196446900",
                    "Улица Пушкина, дом Колотушкина",
                    "https://img02.rl0.ru/afisha/e780x-i/daily.afisha.ru/uploads/images/9/c8/9c8dbd93078c4276a741b47c3fe1502b.jpg",
                    "55"
                )
            }

            step("Нажать “Сохранить”") {
                clickSubmitButton()
            }

            step("Убедиться в отображении сообщения об ошибке для этого поля") {
                checkErrorGenderInput()
            }

            step("Нажать на это поле") {
                clickGenderInput()
            }

            step("Удалить ранее введенные некорректные данные в поле") {
                clearTextInGenderInput()
            }

            step("Проверить что сообщение об ошибке пропало") {
                checkNoErrorGenderInput()
            }
        }
    }
}