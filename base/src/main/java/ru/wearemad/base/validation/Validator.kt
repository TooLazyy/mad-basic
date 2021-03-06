package ru.wearemad.base.validation

interface Validator<in P : Any?> {

    fun validate(param: P): Boolean
}

interface StringValidator : Validator<String>