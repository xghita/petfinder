package com.ghitai.petfinder.core.base

interface EventViewStateMapper<K, V> {
    fun toEventState(input: K): V
}