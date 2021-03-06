package ru.wearemad.mad_base.collection

fun <T> MutableList<T>.copyListAndReplaceItem(
    predicate: (T) -> Boolean,
    duplicator: (T) -> T,
    action: (T) -> Unit = {}
): MutableList<T> {
    val newList = this.toMutableList()
    val itemPosition = newList.indexOfFirst(predicate)
    if (itemPosition != -1) {
        val copiedItem = duplicator(newList[itemPosition])
        action(copiedItem)
        newList.removeAt(itemPosition)
        newList.add(itemPosition, copiedItem)
    }
    return newList
}

fun <T> MutableList<T>.replaceItem(
    predicate: (T) -> Boolean,
    duplicator: (T) -> T,
    action: (T) -> Unit = {}
) {
    val itemPosition = indexOfFirst(predicate)
    if (itemPosition != -1) {
        val copiedItem = duplicator(get(itemPosition))
        action(copiedItem)
        removeAt(itemPosition)
        add(itemPosition, copiedItem)
    }
}

fun <T> MutableList<T>.replaceItemByPosition(
    itemPosition: Int,
    duplicator: (T) -> T
) {
    val copiedItem = duplicator(get(itemPosition))
    removeAt(itemPosition)
    add(itemPosition, copiedItem)
}

fun <T> MutableList<T>.replaceItemFromOneListToAnother(
    source: List<T>,
    targetPredicate: (T) -> Boolean,
    sourcePredicate: (T) -> Boolean = targetPredicate
) {
    val targetPosition = indexOfFirst(targetPredicate)
    val sourcePosition = source.indexOfFirst(sourcePredicate)
    if (targetPosition != -1 && sourcePosition != -1) {
        removeAt(targetPosition)
        add(targetPosition, source[sourcePosition])
    }
}