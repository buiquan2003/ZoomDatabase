package com.example.zoomdatabase.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.zoomdatabase.Model.Note
import com.example.zoomdatabase.R
import com.example.zoomdatabase.View.MainActivity
import com.example.zoomdatabase.View.updateActivity

class NoteAdapter(val activity: MainActivity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var notes:List<Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textviewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        val textviewDesc:TextView = itemView.findViewById(R.id.textView2Desc)
        val cardView:CardView = itemView.findViewById(R.id.CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return NoteViewHolder(view)


    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        var currentNote:Note= notes[position]

        holder.textviewTitle.text=currentNote.title.toString()
        holder.textviewDesc.text=currentNote.description.toString()
        holder.cardView.setOnClickListener {
            val intent = Intent(activity,updateActivity::class.java)
            intent.putExtra("currrentTitle",currentNote.title)
            intent.putExtra("currrentDesc",currentNote.description)
            intent.putExtra("currrentId",currentNote.id)
            activity.updateActivityResultLauncher.launch(intent)
        }
    }
    override fun getItemCount(): Int {
       return notes.size
    }

    fun setNote(myNotes : List<Note>){
        this.notes=myNotes
        notifyDataSetChanged()
    }

    fun getNote(position: Int):Note{
        return notes[position]


    }

}