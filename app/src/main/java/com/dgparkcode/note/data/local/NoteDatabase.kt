package com.dgparkcode.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dgparkcode.note.data.local.dao.NoteEntityDao
import com.dgparkcode.note.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteEntityDao: NoteEntityDao
}