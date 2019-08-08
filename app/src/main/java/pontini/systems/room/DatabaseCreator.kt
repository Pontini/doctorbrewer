package pontinisistemas.doctorbrewer.room;

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean


object DatabaseCreator {

    val isDatabaseCreated = MutableLiveData<Boolean>()

    lateinit var database: RoomDatabase

    lateinit var dataBaseName: String

    private val mInitializing = AtomicBoolean(true)

    fun createDb(context: Context, classeRoom: Class<out RoomDatabase>, roomDatabaseName: String) {
        if (mInitializing.compareAndSet(true, false).not()) {
            return
        }

        isDatabaseCreated.value = false

        Completable.fromAction {

            dataBaseName = roomDatabaseName
            database = Room.databaseBuilder(context, classeRoom, roomDatabaseName).fallbackToDestructiveMigration().
                    //addMigrations(MIGRATION_1_2).
                    build()
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isDatabaseCreated.value = true }, { it.printStackTrace() })
    }

//
//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, " + "`name` TEXT, PRIMARY KEY(`id`))")
//        }
//    }


    fun criarCopiaBancoPara(context: Context, pastaDestino: File): File? {
        try {
            if (pastaDestino.canWrite()) {
                val currentDBPath = context.getDatabasePath(dataBaseName).absolutePath
                val backupDBPath = dataBaseName
                val currentDB = File(currentDBPath)
                val backupDB = File(pastaDestino, backupDBPath)
                val src = FileInputStream(currentDB).channel
                val dst = FileOutputStream(backupDB).channel
                dst.transferFrom(src, 0L, src.size())
                src.close()
                dst.close()
                return backupDB
            }
        } catch (var9: Exception) {
            var9.printStackTrace()
        }
        return null
    }
}