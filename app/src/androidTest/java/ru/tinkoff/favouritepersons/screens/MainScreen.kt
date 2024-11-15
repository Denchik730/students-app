package ru.tinkoff.favouritepersons.screens

import android.view.View
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.tinkoff.favouritepersons.R

class MainScreen: KScreen<MainScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    private val buttonAddSelectExpand = KButton { withId(R.id.fab_add_person) }
    private val buttonAddPersonFromNetwork = KButton { withId(R.id.fab_add_person_by_network) }
    private val buttonAddPersonManually = KButton { withId(R.id.fab_add_person_manually) }
    private val textEmptyListPersons = KTextView { withId(R.id.tw_no_persons) }

    private val buttonSelectedSort = KButton { withId(R.id.action_item_sort) }

    private val radioButtonSortByAge = KButton { withId(R.id.bsd_rb_age) }
    private val radioButtonSortByDefault = KView { withId(R.id.bsd_rb_default) }

    private val snackbarNoInternet = KTextView { withText("Internet error! Check your connection") }


    private val personsList = KRecyclerView(
        builder = { withId(R.id.rv_person_list) },
        itemTypeBuilder = { itemType(::PersonCard) }
    )

    fun clickButtonAddSelectExpand() {
        buttonAddSelectExpand.click()
    }

    fun clickButtonDownloadFromNetwork() {
        buttonAddPersonFromNetwork.click()
    }

    fun addPersonManually() {
        buttonAddPersonManually.click()
    }

    fun isTextEmptyListHidden() {
        textEmptyListPersons.isNotCompletelyDisplayed()
    }

    fun deleteSelectedPerson(position: Int) {
        personsList.childAt<PersonCard>(position) {
            view.perform(ViewActions.swipeLeft())
        }
    }

    fun checkSizePersonsList(size: Int) {
        personsList.hasSize(size)
    }

    fun checkNameSelectedCatInList(position: Int, expectedName: String) {
        personsList.childAt<PersonCard>(position) {
            this.personName.containsText(expectedName)
        }
    }

    fun clickButtonSelectedSort() {
        buttonSelectedSort.click()
    }

    fun sortByAge() {
        radioButtonSortByAge.click()
    }

    fun checkAgeFirstPerson(age: Int) {
        personsList.firstChild<PersonCard> {
            this.personGenderAndAge.containsText(age.toString())
        }
    }

    fun checkAgeLastPerson(age: Int) {
        personsList.lastChild<PersonCard> {
            this.personGenderAndAge.containsText(age.toString())
        }
    }

    fun clickSelectedPerson(position: Int) {
        personsList.childAt<PersonCard>(position) {
            this.personName.click()
        }
    }

    fun checkSnackbarNoInternet() {
        snackbarNoInternet.isDisplayed()
    }

    fun checkIsCheckedSortingByDefault() {
        radioButtonSortByDefault.matches { ViewMatchers.isChecked() }
    }
}

private class PersonCard(matcher: Matcher<View>) : KRecyclerItem<PersonCard>(matcher) {
    val personName = KTextView(matcher) { withId(R.id.person_name) }
    val personGenderAndAge = KTextView(matcher) { withId(R.id.person_private_info) }
}