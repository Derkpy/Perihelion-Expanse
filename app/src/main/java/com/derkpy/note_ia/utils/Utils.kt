package com.derkpy.note_ia.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun Long.toMexicanDate(): String {
        val date = Date(this)
        val localeMX = Locale.forLanguageTag("es-MX")

        val format = SimpleDateFormat("dd/MM/yyyy", localeMX)
        return format.format(date)
    }




}