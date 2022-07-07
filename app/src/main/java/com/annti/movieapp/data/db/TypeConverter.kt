package com.annti.movieapp.data.db

import androidx.room.TypeConverter
import com.annti.movieapp.data.model.Genres
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {
    @TypeConverter
    fun fromCountryLangList(genres: List<Int?>?): String? {
        if (genres == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toCountryLangList(genresString: String?): List<Int>? {
        if (genresString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson<List<Int>>(genresString, type)
    }
}