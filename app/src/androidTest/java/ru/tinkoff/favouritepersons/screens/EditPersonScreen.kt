package ru.tinkoff.favouritepersons.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import ru.tinkoff.favouritepersons.R

class EditPersonScreen: KScreen<EditPersonScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    private val namePersonInput = KTextInputLayout { withId(R.id.til_name) }
    private val surnamePersonInput = KTextInputLayout { withId(R.id.til_surname) }
    private val dateOfBithPersonInput = KTextInputLayout { withId(R.id.til_birthdate) }
    private val genderPersonInput = KTextInputLayout { withId(R.id.til_gender) }

    private val submitButton = KButton { withId(R.id.submit_button) }

    fun checkDataAboutPerson(name: String, surname: String, gender: String, birthday: String) {
        namePersonInput.edit.hasText(name)
        surnamePersonInput.edit.hasText(surname)
        genderPersonInput.edit.hasText(gender)
        dateOfBithPersonInput.edit.hasText(birthday)
    }

    fun editNamePerson(name: String) {
        namePersonInput.edit.replaceText(name)
    }

    fun clickSubmitButton () {
        submitButton.click()
    }
}