package ru.wearemad.mad_base.data_wrapper

/**
 * Extension-функции для [DataWrapperInterface]
 */

fun <T> Collection<DataWrapperInterface<T>>.getData(): Collection<T> =
    this.map { it.data }

fun <T> List<DataWrapperInterface<T>>.getData(): List<T> =
    this.map { it.data }

fun <T> Set<DataWrapperInterface<T>>.getData(): Set<T> =
    this.map { it.data }.toSet()

/**
 * Найти объект(ы) в коллекции по filterPredicate
 * и изменить в соответствии с applyConsumer
 */
fun <T, E : DataWrapperInterface<T>> filterAndApply(
    collection: Collection<E>,
    filterPredicate: (T) -> Boolean,
    applyConsumer: (E) -> Unit
) {
    collection
        .filter { filterPredicate(it.data) }
        .forEach { applyConsumer(it) }
}

inline fun <reified T> List<*>.isListOfType(): Boolean {
    return all { it is T }
}