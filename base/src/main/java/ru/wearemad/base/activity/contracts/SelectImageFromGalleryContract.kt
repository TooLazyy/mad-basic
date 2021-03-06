package ru.wearemad.base.activity.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SelectImageFromGalleryContract : ActivityResultContract<Unit, ContractSelectResult>() {

    override fun createIntent(context: Context, input: Unit?): Intent =
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

    override fun parseResult(resultCode: Int, intent: Intent?): ContractSelectResult =
        if (resultCode == Activity.RESULT_OK) {
            ContractSelectResult.Selected(intent?.data)
        } else {
            ContractSelectResult.Cancelled
        }
}