package com.example.businesslogic.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

internal class DateTypeConverters {
    @TypeConverter
    public fun fromTimestamp(value: Long?): Date? {
        return if (value != null) Date(value) else null
    }

    @TypeConverter
    public fun toTimestamp(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    public fun stringListToDb(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    public fun stringListFromDb(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}