package com.test.recipeapp.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class RecipeMigration {

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `MealDetails` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `time` TEXT NOT NULL, `calorie` TEXT NOT NULL, `serving` TEXT NOT NULL, `ingredients` TEXT NOT NULL, `measurements` TEXT NOT NULL, `instructions` TEXT NOT NULL, `images` TEXT NOT NULL)")
            }
        }
    }
}