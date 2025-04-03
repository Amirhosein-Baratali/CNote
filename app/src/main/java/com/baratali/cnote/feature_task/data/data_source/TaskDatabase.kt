package com.baratali.cnote.feature_task.data.data_source

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.baratali.cnote.core.di.ApplicationScope
import com.baratali.cnote.feature_task.data.data_source.model.CategoryIcon
import com.baratali.cnote.feature_task.data.data_source.model.Task
import com.baratali.cnote.feature_task.data.data_source.model.TaskCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Task::class, TaskCategory::class], version = 2, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "tasks_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Ensures data consistency after modifying the CategoryIcon enum.
                // Previously, CategoryIcon stored only ImageVector references. Now, it supports both ImageVector and drawable resource IDs.
                // Since Room stores the icon as a string (enum name), this update ensures that all existing values are still valid.
                // Any outdated or non-existent enum values will be replaced with 'DEFAULT' to prevent potential crashes.
                database.execSQL(
                    "UPDATE taskcategory SET icon = 'DEFAULT' WHERE icon NOT IN (${
                        CategoryIcon.values().joinToString { "'${it.name}'" }
                    })")
            }
        }
    }

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch {
                val categoryDao = database.get().taskDao
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Work",
                        icon = CategoryIcon.WORK,
                        color = Color(0xFFFFA07A).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Grocery",
                        icon = CategoryIcon.GROCERY,
                        color = Color(0xFFADFF2F).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Sport",
                        icon = CategoryIcon.SPORT,
                        color = Color(0xFF00FFFF).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Design",
                        icon = CategoryIcon.DESIGN,
                        color = Color(0xFF98FB98).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "University",
                        icon = CategoryIcon.UNIVERSITY,
                        color = Color(0xFF6495ED).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Social",
                        icon = CategoryIcon.SOCIAL,
                        color = Color(0xFFFF69B4).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Music",
                        icon = CategoryIcon.MUSIC,
                        color = Color(0xFFDA70D6).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Health",
                        icon = CategoryIcon.HEALTH,
                        color = Color(0xFF7FFF00).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Movie",
                        icon = CategoryIcon.MOVIE,
                        color = Color(0xFF87CEFA).toArgb().toLong()
                    )
                )
                categoryDao.insertCategory(
                    TaskCategory(
                        name = "Home",
                        icon = CategoryIcon.HOME,
                        color = Color(0xFFFFD700).toArgb().toLong()
                    )
                )
            }
        }
    }
}