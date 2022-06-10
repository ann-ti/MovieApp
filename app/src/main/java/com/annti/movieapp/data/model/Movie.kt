package com.annti.movieapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Results>,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class Results(
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDetails(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "budget")
    val budget: Int,
    @Json(name = "genres")
    val genres: List<Genres>,
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imdb_id")
    val imdb_id: String?,
    @Json(name = "original_language")
    val original_language: String,
    @Json(name = "original_title")
    val original_title: String,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val poster_path: String?,
    @Json(name = "production_companies")
    val production_companies: List<ProductionCompanies>,
    @Json(name = "production_countries")
    val production_countries: List<ProductionCountries>,
    @Json(name = "release_date")
    val release_date: String,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "spoken_languages")
    val spoken_languages: List<SpokenLanguages>,
    @Json(name = "status")
    val status: String,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Genres(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCompanies(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "logo_path")
    val logo_path: String?,
    @Json(name = "origin_country")
    val origin_country: String,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class SpokenLanguages(
    @Json(name = "iso_639_1")
    val iso: String,
    @Json(name = "name")
    val name: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCountries(
    @Json(name = "iso_3166_1")
    val iso: String,
    @Json(name = "name")
    val name: String
) : Parcelable