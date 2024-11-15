package ru.tinkoff.favouritepersons

import android.content.Context
import ru.tinkoff.favouritepersons.room.PersonDataBase

object DatabaseHelper {
    private lateinit var db: PersonDataBase

    fun init(context: Context) {
        db = PersonDataBase.getDBClient(context)
    }

    fun clear() {
        db.personsDao().clearTable()
    }
}