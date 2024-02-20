package com.ghitai.petfinder.core.events

open class ErrorUI(
    open val description: String?
) : EventUI {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ErrorUI) return false

        return description == other.description
    }

    override fun hashCode(): Int {
        return description.hashCode()
    }
}