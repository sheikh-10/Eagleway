package com.shop.eagleway.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

class ListConverters {

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun fromPlanList(value: List<Subscription.SubscriptionPlan>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromDetailList(value: List<Subscription.SubscriptionDetail>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPlanList(value: String): List<Subscription.SubscriptionPlan> {
        return try {
            Gson().fromJson<List<Subscription.SubscriptionPlan>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }

    @TypeConverter
    fun toDetailList(value: String): List<Subscription.SubscriptionDetail> {
        return try {
            Gson().fromJson<List<Subscription.SubscriptionDetail>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }
}