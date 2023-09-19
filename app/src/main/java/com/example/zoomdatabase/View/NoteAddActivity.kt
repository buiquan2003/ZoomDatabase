package com.example.zoomdatabase.View

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zoomdatabase.R
import com.example.zoomdatabase.Repository.NoteRepository
import com.example.zoomdatabase.ViewModel.NoteViewModel

class NoteAddActivity : AppCompatActivity() {
    lateinit var txtTitle:EditText
    lateinit var txtDesc:EditText
    lateinit var btnSave:Button
    lateinit var btnCancel:Button
    lateinit var noteViewModel: NoteViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        txtTitle=findViewById(R.id.editTextTextAddTitle)
        txtDesc=findViewById(R.id.editTextadddesc)
        btnSave=findViewById(R.id.btn_Add_Ok)
        btnCancel=findViewById(R.id.btn_Add_Cancel)

        supportActionBar?.title="Note Add "

        btnSave.setOnClickListener {
            onSave()
            finish()

        }
        btnCancel.setOnClickListener {
           finish()
        }

    }



    private fun onSave() {
        val noteTitle:String=txtTitle.text.toString()
        val noteDesc:String=txtDesc.text.toString()

        val intent= Intent()
        intent.putExtra("Title",noteTitle)
        intent.putExtra("Desc",noteDesc)
        setResult(RESULT_OK,intent)

    }


}