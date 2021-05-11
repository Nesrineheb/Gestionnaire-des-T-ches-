package com.example.gestionnairedetches

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var taches: MutableList<Tache>
    lateinit var adapter: TacheAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
       
        setContentView(R.layout.activity_tache_list)
        taches= mutableListOf<Tache>()
        taches.add(Tache("Tache 1", "20/03/2020"))
        taches.add(Tache("Tache 2", "04/05/2021"))
        taches.add(Tache("Tache 3", "09/09/2021"))
        taches.add(Tache("Tache 4", "14/11/2021"))
        adapter=TacheAdapter(taches, this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //findViewById(R.id.create_tache_fab).setOnClickListener (this)
        super.onCreate(savedInstanceState)

        val fab: View = findViewById(R.id.create_tache_fab)
        fab.setOnClickListener {
           createNewTache()

        }




        val recyclerView = findViewById(R.id.taches_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            showDetailActivity(view.tag as Int)
            Log.i("messaqe","hello")
        }
        else {
            when (view.id) {
                R.id.create_tache_fab -> createNewTache()
            }
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!=Activity.RESULT_OK ||  data==null){
            return
        }
        when (requestCode){
            TacheDetailActivity.REQUEST_EDIT_Tache -> processEditTacheResult(data)
        }
    }


    private fun processEditTacheResult(data: Intent) {
        val tacheIndex = data.getIntExtra(TacheDetailActivity.EXTRA_Tache_INDEX, -1)
        val tache = data.getParcelableExtra<Tache>(TacheDetailActivity.EXTRA_Tache)
        when(data.action) {
            TacheDetailActivity.ACTION_SAVE_TACHE-> {

                val note = data.getParcelableExtra<Tache>(TacheDetailActivity.EXTRA_Tache)
                saveTache(note, tacheIndex)
            }
            TacheDetailActivity.ACTION_DELETE_TACHE -> deleteTache(tacheIndex)
        }
        
    }


    fun deleteTache(tacheIndex: Int) {
        if (tacheIndex < 0) {
            return
        }
       taches.removeAt(tacheIndex)
        adapter.notifyDataSetChanged()


    }

     fun saveTache(tache: Tache, tacheIndex: Int) {
         if (tacheIndex<0){
             taches.add(0,tache)
         }else{
             taches[tacheIndex] = tache
         }

        adapter.notifyDataSetChanged()
    }

    fun  createNewTache() {

        Log.i("message","hi creat")
       showDetailActivity(-1)
    }

    fun showDetailActivity(tacheIndex: Int) {
        val tache = if (tacheIndex < 0) Tache() else taches[tacheIndex]

        val intent = Intent(this, TacheDetailActivity::class.java)
        intent.putExtra(TacheDetailActivity.EXTRA_Tache, tache as Parcelable)
        intent.putExtra(TacheDetailActivity.EXTRA_Tache_INDEX, tacheIndex)
        startActivityForResult(intent, TacheDetailActivity.REQUEST_EDIT_Tache)
    }

}
