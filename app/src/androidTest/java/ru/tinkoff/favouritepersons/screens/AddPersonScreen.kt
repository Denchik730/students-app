package ru.tinkoff.favouritepersons.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import ru.tinkoff.favouritepersons.R

class AddPersonScreen: KScreen<EditPersonScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    private val namePersonInput = KTextInputLayout { withId(R.id.til_name) }
    private val surnamePersonInput = KTextInputLayout { withId(R.id.til_surname) }
    private val genderPersonInput = KTextInputLayout { withId(R.id.til_gender) }
    private val dateOfBithPersonInput = KTextInputLayout { withId(R.id.til_birthdate) }
    private val mailPersonInput = KTextInputLayout { withId(R.id.til_email) }
    private val phonePersonInput = KTextInputLayout { withId(R.id.til_phone) }
    private val addressPersonInput = KTextInputLayout { withId(R.id.til_address) }
    private val photoUrlPersonInput = KTextInputLayout { withId(R.id.til_image_link) }
    private val finalScorePersonInput = KTextInputLayout { withId(R.id.til_score) }

    private val submitButton = KButton { withId(R.id.submit_button) }

    fun enterNewPersonFullData(
        name: String,
        surname: String,
        gender: String,
        birthday: String,
        mail: String,
        phone: String,
        address: String,
        photo: String,
        finalScore: String
    ) {
        namePersonInput.edit.replaceText(name)
        surnamePersonInput.edit.replaceText(surname)
        genderPersonInput.edit.replaceText(gender)
        dateOfBithPersonInput.edit.replaceText(birthday)
        mailPersonInput.edit.replaceText(mail)
        phonePersonInput.edit.replaceText(phone)
        addressPersonInput.edit.replaceText(address)
        photoUrlPersonInput.edit.replaceText(photo)
        finalScorePersonInput.edit.replaceText(finalScore)
    }

    fun clickGenderInput() {
        genderPersonInput.edit.click()
    }

    fun clearTextInGenderInput() {
        genderPersonInput.edit.clearText()
    }

    fun clickSubmitButton() {
        submitButton.click()
    }

    fun checkErrorGenderInput() {
        genderPersonInput.hasError("Поле должно быть заполнено буквами М или Ж")
    }

    fun checkNoErrorGenderInput() {
        genderPersonInput.hasNoError()
    }
}