package com.shop.eagleway.data

import android.content.Context
import androidx.room.*

@Database(entities = [ProductPreview::class, ProductInfo::class, ProductImage::class, MeasuringUnit::class, Category::class, Currency::class, Subscription::class], version = 18, exportSchema = false)
@TypeConverters(Converters::class, ListConverters::class)
abstract class EaglewayDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun subDao(): SubscriptionDao

    companion object {
        @Volatile
        private var Instance: EaglewayDatabase? = null

        fun getDatabase(context: Context): EaglewayDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EaglewayDatabase::class.java, "eagleway_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}