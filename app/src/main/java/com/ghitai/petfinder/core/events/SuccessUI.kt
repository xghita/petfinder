package com.ghitai.petfinder.core.events

open class  SuccessUI(
    open val message: String? = null
) : EventUI {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SuccessUI) return false

        return message == other.message
    }

    override fun hashCode(): Int {
        return message?.hashCode() ?: 0
    }
}