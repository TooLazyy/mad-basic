package ru.wearemad.mad_base.vibration

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * Утилита для работы с вибрацией.
 *
 * В модуле, в котором используется вибрация, необходимо прописать
 * разрешение <uses-permission android:name="android.permission.VIBRATE" />
 */
object VibratorUtils {

    private const val DEFAULT_VIBRATION_DURATION = 100L
    private const val DEFAULT_AMPLITUDE_VALUE = 50

    fun vibrateOnce(context: Context?) = vibrate(context)

    fun vibrateOnceWeak(context: Context?) = vibrate(context, 10L, 1)

    fun vibrateCustom(
        context: Context?,
        milliseconds: Long,
        amplitude: Int
    ) = vibrate(context, milliseconds, amplitude)

    /**
     * Однократная вибрация.
     *
     * @param context контекст вызова функции
     * @param amplitude сила вибрации
     * @param milliseconds продолжительность вибрации по времени
     */
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "NewApi")
    private fun vibrate(
        context: Context?,
        milliseconds: Long = DEFAULT_VIBRATION_DURATION,
        amplitude: Int = DEFAULT_AMPLITUDE_VALUE
    ) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, amplitude))
        } else {
            vibrator.vibrate(milliseconds)
        }
    }
}