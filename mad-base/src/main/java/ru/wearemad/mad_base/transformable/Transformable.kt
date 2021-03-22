package ru.wearemad.mad_base.transformable

interface Transformable<T : Any> {

    fun transform(): T
}