package com.example.zoomdatabase.View

import android.app.Instrumentation.ActivityResult
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.example.zoomdatabase.Adapter.NoteAdapter
import com.example.zoomdatabase.NoteApplication
import com.example.zoomdatabase.R
import com.example.zoomdatabase.ViewModel.NoteViewModel
import com.example.zoomdatabase.ViewModel.NoteViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    val noteAdapter = NoteAdapter(this)
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.reCvid)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = noteAdapter

       registerActivityResultLauncher()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this, Observer { notes ->
            //update
            noteAdapter.setNote(notes)
            val userCount = notes.size
            Log.d("Login", "Total users in database: $userCount")
            Log.d("bbb", "" + notes)

        })

    }

    fun registerActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultAddNote ->

                    val resultCode = resultAddNote.resultCode
                    val data = resultAddNote.data
                    if (resultCode == RESULT_OK && data != null) {
                        val noteTitle: String = data.getStringExtra("Title").toString()
                        val noteDesc: String = data.getStringExtra("Desc").toString()

                        val note = com.example.zoomdatabase.Model.Note(noteTitle, noteDesc)
                        noteViewModel.insert(note)

                    }
                })
//
        updateActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultUpdateNote ->

                    val resultCode = resultUpdateNote.resultCode
                    val data = resultUpdateNote.data
                    if (resultCode == RESULT_OK && data != null) {


                        val updateTitle: String = data.getStringExtra("updateTitle").toString()
                        val updateDesc: String = data.getStringExtra("updateDesc").toString()
                        val noteId = data.getIntExtra("updateid", -1)
                        val note = com.example.zoomdatabase.Model.Note(updateTitle, updateDesc)
                        note.id = noteId

                        noteViewModel.update(note)


                    }
                })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or
                ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)



        }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.note_add_icon  ->{
                val intent = Intent(this,NoteAddActivity::class.java)
               activityResultLauncher.launch(intent)
            }

            R.id.delete_icon ->{
                showDigLogDelete()
            }
        }
        return true
    }
    private fun showDigLogDelete() {

        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("delete All Notes")
        dialogMessage.setMessage("If click Yes all Notes will delete"+
                "If you want to delete a specific note , please left of right.")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener{
                dialog,which->
            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener{
                dialog,which->
            noteViewModel.deleteAllNotes()
        })
        dialogMessage.create().show()
    }


}