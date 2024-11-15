package ru.tinkoff.favouritepersons

import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams

val kaspressoBuilder = Kaspresso.Builder.simple(
    customize = {
        flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 10_000, intervalMs = 250)
    }
)