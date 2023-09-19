package com.example.zoomdatabase.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.zoomdatabase.Model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note:Note)

    @Update
    suspend fun update(note:Note)

     @Delete
    suspend fun delete(note:Note)


    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes():Flow<List<Note>>


//    fun getAllNotes(): Flow<List<Note>>:
//    Định nghĩa phương thức getAllNotes() trả
//    về một luồng (Flow) của danh sách các đối
//    tượng Note. Luồng này cho phép theo dõi thay
//    đổi trong dữ liệu và tự động cung cấp các kết q
//    uả mới khi dữ liệu trong bảng "note_table" thay đổi.
}