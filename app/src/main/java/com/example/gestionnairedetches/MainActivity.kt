package com.example.gestionnairedetches

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    lateinit var taches: MutableList<Tache>
    lateinit var taches_f: MutableList<Tache>
    lateinit var adapter: TacheAdapter
    lateinit var option: Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
       
        setContentView(R.layout.activity_tache_list)

        taches= mutableListOf<Tache>()

        taches.add(Tache("Tache n°1", "14-mai-2021"))
        taches.add(Tache("Tache n°2", "15-mai-2021"))
        taches.add(Tache("Tache n°3", "25-mai-2021"))
        taches.add(Tache("Tache n°4", "26-mai-2021"))
        taches.add(Tache("Tache n°5", "27-mai-2021"))




        //ctypeOption.adapter = ctypeOptionAdapter

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            taches_f=taches
            option=findViewById(R.id.spinner) as Spinner
            val options=arrayOf("Tout","5 Derniers Jours","Aujourd'hui")
            option.adapter=ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options)




            option.onItemSelectedListener= object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


        }

        taches_f=taches

        adapter=TacheAdapter(taches_f, this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)




        val recyclerView = findViewById(R.id.taches_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        super.onCreate(savedInstanceState)

        val fab: View = findViewById(R.id.create_tache_fab)
        fab.setOnClickListener {
            createNewTache()

        }
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

        when(data.action) {
            TacheDetailActivity.ACTION_SAVE_TACHE-> {

                val tache = data.getParcelableExtra<Tache>(TacheDetailActivity.EXTRA_Tache)
                saveTache(tache, tacheIndex)
            }
            TacheDetailActivity.ACTION_DELETE_TACHE -> deleteTache(tacheIndex)
        }

    }


    fun deleteTache(tacheIndex: Int) {
        if (tacheIndex < 0) {
            return
        }
       taches_f.removeAt(tacheIndex)
        adapter.notifyDataSetChanged()


    }



     fun saveTache(tache: Tache, tacheIndex: Int) {
         if (tacheIndex<0){
             taches_f.add(0,tache)
         }else{
             taches_f[tacheIndex] = tache
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
