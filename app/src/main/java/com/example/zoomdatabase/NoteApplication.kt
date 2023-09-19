package com.example.zoomdatabase

import android.app.Application
import com.example.zoomdatabase.Repository.NoteRepository
import com.example.zoomdatabase.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application(){
        val applicationScope= CoroutineScope(SupervisorJob())
        val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }
        val repository by lazy {  NoteRepository(database.getNoteDao())}
}
//1.applicationScope - Một CoroutineScope được khởi tạo với một SupervisorJob(),
// để quản lý các coroutine được khởi chạy trong phạm vi ứng dụng.
// Nó cho phép quản lý các coroutine con trong phạm vi này một cách riêng biệt và đảm bảo rằng một lỗi
// trong một coroutine con không ảnh hưởng đến các coroutine khác trong cùng phạm vi.

//2.database - Được khởi tạo bằng cách sử dụng by lazy để trì hoãn việc khởi tạo đến khi được yêu
// cầu lần đầu tiên. database là một đối tượng NoteDatabase được truy cập thông qua phương thức tĩnh
// getDatabase() của lớp NoteDatabase và được truyền vào một CoroutineScope để quản lý các thao tác trên cơ sở dữ liệu.

//3.repository - Được khởi tạo bằng cách sử dụng by lazy để trì hoãn việc khởi tạo đến
// khi được yêu cầu lần đầu tiên. repository là một đối tượng NoteRepository được truy
// cập thông qua constructor của lớp NoteRepository và truyền vào một DAO (NoteDao) từ đối tượng
// database. NoteRepository được sử dụng để truy cập và thực hiện các thao tác trên cơ sở dữ liệu.

//4.Vì các thành phần này được khởi tạo trong constructor của NoteApplication,
// chúng có thể được sử dụng trong toàn bộ ứng dụng thông qua đối tượng NoteApplication.
// Các thành phần này được khởi tạo một lần duy nhất trong vòng đời của ứng dụng, và chỉ có một đối
// tượng NoteApplication duy nhất được tạo ra trong toàn bộ ứng dụng (do đó triển khai kiến trúc singleton pattern).
