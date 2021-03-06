package ru.wearemad.base.activity.contracts

import android.net.Uri

sealed class ContractSelectResult {

    object Cancelled : ContractSelectResult()

    data class Selected(val uri: Uri?) : ContractSelectResult()
}