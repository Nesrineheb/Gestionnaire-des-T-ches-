package com.example.gestionnairedetches

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var taches: MutableList<Tache>
    lateinit var adapter: TacheAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tache_list)
        taches= mutableListOf<Tache>()
        taches.add(Tache("Note 1", "Blablabla"))
        taches.add(Tache("Mémo Bob", "Grand joueur de basket"))
        taches.add(Tache("Mémo Bobette", "Future championne de la NBA"))
        taches.add(Tache("Pourquoi Kotlin ?", "Parce-que Java !"))
        adapter=TacheAdapter(taches, this)

        val fab: View = findViewById(R.id.create_tache_fab)
        fab.setOnClickListener {
            Log.i("messaqe","hiiii")

        }

        val recyclerView = findViewById(R.id.taches_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter




    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i("messaqe","hello")
        }
        else {
            when (view.id) {
                R.id.create_tache_fab -> createNewTache()
            }
        }
    }

   fun  createNewTache() {

        Log.i("message","hi 2")
    }


}