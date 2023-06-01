package com.shop.eagleway.data

import android.content.Context
import androidx.room.*

@Database(entities = [ProductPreview::class, ProductInfo::class, ProductImage::class, MeasuringUnit::class, Category::class, Currency::class], version = 16, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EaglewayDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

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