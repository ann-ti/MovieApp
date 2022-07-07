package com.annti.movieapp.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

@SuppressLint("SimpleDateFormat")
fun dateFormat(oldStringDate: String?): String? {
    if (oldStringDate == null || oldStringDate == "")
        return ""
    val newDate: String?
    val dateFormat = SimpleDateFormat("d/MM/yyyy", Locale(getCountry()))
    newDate = try {
        val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(oldStringDate)
        dateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        oldStringDate
    }
    return newDate
}

private fun getCountry(): String? =
    Locale.getDefault().country.toLowerCase(Locale.ROOT)

