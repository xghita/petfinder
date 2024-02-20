package com.ghitai.petfinder.core.base

interface ViewStateMapper<K, V> {
    fun toViewState(payload: K): V
}