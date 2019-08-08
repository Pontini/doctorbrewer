package pontini.systems.room

import androidx.room.*

@Entity(indices = [Index(value = arrayOf("id"), unique = true)])
data class Mash(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id" )
        val id: Long=0,
        @ColumnInfo(name = "description" )
        val name: String,
        @ColumnInfo(name = "notes" )
        val note:String){}
