package com.example.zoomdatabase.ViewModel

import androidx.lifecycle.*
import com.example.zoomdatabase.Model.Note
import com.example.zoomdatabase.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository):ViewModel() {


    val myAllNotes:LiveData<List<Note>> = repository.myAllNotes.asLiveData()


    fun insert(note: Note) = viewModelScope.launch (Dispatchers.IO ) {
        repository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch (Dispatchers.IO ) {
        repository.update(note)
    }
    fun delete(note: Note) = viewModelScope.launch (Dispatchers.IO ) {
        repository.delete(note)
    }
    fun deleteAllNotes() = viewModelScope.launch (Dispatchers.IO ) {
        repository.deleteAllNotes()
    }
}

class NoteViewModelFactory(private var repository: NoteRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
          return NoteViewModel(repository) as T
      }else{
          throw java.lang.IllegalArgumentException("unknown View Model")
      }
    }

    //    Đoạn mã này triển khai một lớp NoteViewModel sử dụng kiến trúc MVVM (Model-View-ViewModel) để quản lý
//    và hiển thị danh sách ghi chú. Lớp này là một phần của phần mềm Android và kế thừa từ lớp ViewModel.
//    ViewModel là một lớp bảo mật dữ liệu của ứng dụng và giúp giữ cho dữ liệu sống sót trong suốt quá trình hoạt
//    động của ứng dụng, ngay cả khi các thành phần UI được khởi tạo lại.
//
//    Lớp NoteViewModel chịu trách nhiệm tương tác với NoteRepository để lấy và lưu trữ dữ liệu ghi chú.
//    NoteRepository là một lớp trung gian, được sử dụng để tương tác với cơ sở dữ liệu Room, và nó cho
//    phép các phương thức để thêm, sửa, xóa và truy vấn các ghi chú.
//
//    LiveData được sử dụng để cung cấp một phiên bản không thay đổi của danh sách ghi chú, được cập nhật
//    tự động khi dữ liệu thay đổi trong cơ sở dữ liệu.
//
//    Phương thức insert(), update(), delete() và deleteAllNotes() được triển khai để thực hiện các hoạt
//    động CRUD trên cơ sở dữ liệu ghi chú. Tất cả các phương thức này đều được gọi bằng cách sử dụng viewModelScope.
//    launch (Dispatchers.IO), nó giúp tạo ra một coroutine trên nền tảng không chặn, vì vậy các hoạt động trên cơ sở dữ liệu
//    không ảnh hưởng đến luồng chính của ứng dụng.
//
//    NoteViewModelFactory là một lớp triển khai của ViewModelProvider.Factory, nó cung cấp một instance
//    của NoteViewModel. ViewModelProvider.Factory là một cách để cung cấp các dependencies cho lớp ViewModel.
//    Nếu lớp ViewModel được đưa ra không tương thích với NoteViewModel, nó sẽ ném ra một ngoại lệ.

}