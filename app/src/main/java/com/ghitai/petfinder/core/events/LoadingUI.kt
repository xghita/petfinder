package com.ghitai.petfinder.core.events

open class LoadingUI(
    open val message: String? = null
) : EventUI {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LoadingUI) return false

        return message == other.message
    }

    override fun hashCode(): Int {
        return message?.hashCode() ?: 0
    }
}