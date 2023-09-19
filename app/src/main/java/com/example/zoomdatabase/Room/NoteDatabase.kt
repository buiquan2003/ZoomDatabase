package com.example.zoomdatabase.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.zoomdatabase.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase:RoomDatabase() {

 abstract fun getNoteDao():NoteDao

 companion object{
       @Volatile
     private var INSTANCE :NoteDatabase? = null
         fun getDatabase(context :Context,scope: CoroutineScope) : NoteDatabase{
              return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(context.applicationContext,
            NoteDatabase::class.java,"note_table")
                .addCallback(NoteDatabaseCallBack(scope))
             .build()
               INSTANCE=instance
               instance
        }
    }
     private class NoteDatabaseCallBack(private val  scope: CoroutineScope):RoomDatabase.Callback(){

         override fun onCreate(db: SupportSQLiteDatabase) {
             super.onCreate(db)

             INSTANCE?.let { database->

                 scope.launch {
                     val noteDao =database.getNoteDao()
                     noteDao.insert(Note("1","tantien"))
                     noteDao.insert(Note("2","abc"))
                     noteDao.insert(Note("3","def"))

                 }

             }
         }

     }
 }
//    ây là một abstract class NoteDatabase trong Room, một thư viện hỗ trợ cho
//    việc lưu trữ và quản lý cơ sở dữ liệu trên thiết bị Android.
//
//    Class này kế thừa RoomDatabase, và định nghĩa một phương thức
//    trừu tượng getNoteDao(). Phương thức này sẽ được cài đặt trong lớp
//    triển khai của lớp này để trả về đối tượng DAO (Data Access Object)
//    cho lớp thực thể Note.
//
//    Lớp NoteDatabase còn có một đối tượng đồng bộ (@Volatile)
//    là INSTANCE để đảm bảo rằng việc truy cập đến nó là an toàn từ nhiều luồng.
//
//    Class này cũng có một hàm companion object getDatabase()
//    để tạo ra một thể hiện duy nhất của NoteDatabase và trả về nó.
//    Hàm này sử dụng synchronized để đảm bảo rằng chỉ có một luồng có thể truy cập vào INSTANCE tại một thời điểm.
//
//    NoteDatabaseCallBack là một lớp lồng được sử dụng để
//    thực hiện các hành động trên cơ sở dữ liệu khi nó được tạo
//    ra hoặc mở lại. Lớp này triển khai RoomDatabase.Callback(),
//    và trong hàm onCreate(), nó tạo ra và lưu trữ một đối tượng DAO,
//    và sau đó thực hiện các thao tác trên đối tượng DAO đó bằng cách
//    sử dụng một CoroutineScope. Các thao tác này thêm ba ghi chú vào
//    cơ sở dữ liệu mỗi khi cơ sở dữ liệu được tạo ra.

//    Đoạn code trên giúp triển khai kiến trúc singleton pattern
//    (một lớp chỉ có thể có một đối tượng duy nhất) để đảm
//    bảo rằng chỉ có một phiên bản của cơ sở dữ liệu được tạo
//    ra và sử dụng trong toàn bộ ứng dụng.
}