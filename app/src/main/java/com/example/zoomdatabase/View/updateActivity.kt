package com.example.zoomdatabase.View

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.zoomdatabase.R
import com.example.zoomdatabase.ViewModel.NoteViewModel

class updateActivity : AppCompatActivity() {
    lateinit var txtTitle: EditText
    lateinit var txtDesc: EditText
    lateinit var btnSave: Button
    lateinit var btnCancel: Button
    lateinit var noteViewModel: NoteViewModel
    var currenId = -1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        txtTitle=findViewById(R.id.editTextTextAddTitleUpdate)
        txtDesc=findViewById(R.id.editTextadddescUpdate)
        btnSave=findViewById(R.id.btn_Add_OkUpdate)
        btnCancel=findViewById(R.id.btn_Add_CancelUpdate)

        updateNotes()
        supportActionBar?.title="Note Update "

        btnSave.setOnClickListener {
//            onUpdate()
            finish()

        }
        btnCancel.setOnClickListener {

            finish()
        }

    }



    fun updateNotes(){

        //get
        val currenTitle = intent.getStringExtra("currrentTitle")
        val currenDesc = intent.getStringExtra("currrentDesc")
         currenId = intent.getIntExtra("currrentId",-1)
        //set
        txtTitle.setText(currenTitle)
        txtDesc.setText(currenDesc)




    }

}