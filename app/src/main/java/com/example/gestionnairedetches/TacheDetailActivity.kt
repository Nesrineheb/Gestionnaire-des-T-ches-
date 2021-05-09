package com.example.gestionnairedetches

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class TacheDetailActivity : AppCompatActivity() {


    companion object {
        val REQUEST_EDIT_Tache = 1

        val EXTRA_Tache = "tache"
        val EXTRA_Tache_INDEX = "tacheIndex"



    }
    lateinit var tache: Tache
    var tacheIndex:Int = -1
    lateinit var nomView: TextView
    lateinit var dateView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tache_detail)

        tache = intent.getParcelableExtra<Tache>(EXTRA_Tache)
        tacheIndex = intent.getIntExtra(EXTRA_Tache_INDEX, -1)



       nomView = findViewById(R.id.nom) as TextView
        dateView = findViewById(R.id.date) as TextView
        nomView.text = tache.nom
      dateView.text = tache.date
    }
    fun saveTache() {
        tache.nom= nomView.text.toString()
        tache.date = dateView.text.toString()

        intent = Intent()
        intent.putExtra(EXTRA_Tache, tache as Parcelable)
        intent.putExtra(EXTRA_Tache_INDEX, tacheIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_tache_detail,menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save ->{ return true  }
            else ->{ return super.onOptionsItemSelected(item)


            }
           }
          }

}