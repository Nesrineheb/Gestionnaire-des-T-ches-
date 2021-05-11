package com.example.gestionnairedetches

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class TacheDetailActivity : AppCompatActivity() {


    companion object {
        val REQUEST_EDIT_Tache = 1
        val EXTRA_Tache = "tache"
        val EXTRA_Tache_INDEX = "tacheIndex"
        val ACTION_DELETE_TACHE = "com.example.gestionnairedetches.actions.ACTION_DELETE_TACHE"
        val ACTION_SAVE_TACHE= "com.example.gestionnairedetches.actions.ACTION_SAVE_TACHE"
    }


    lateinit var tache: Tache
    var tacheIndex:Int = -1
    lateinit var nomView: TextView
    lateinit var dateView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tache_detail)

        tache = intent.getParcelableExtra<Tache>(EXTRA_Tache)
        tacheIndex = intent.getIntExtra(EXTRA_Tache_INDEX, -1)


val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       nomView = findViewById(R.id.nom) as TextView
        dateView = findViewById(R.id.date) as TextView
        nomView.text = tache.nom
      dateView.text = tache.date
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_tache_detail,menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_save ->{
                saveTache()
                return true  }
            R.id.action_delete ->{
                Log.i("meeeesage debug","delete action")
                showConfirmDeleteDialogue()
                return true
            }
            else ->{ return super.onOptionsItemSelected(item) }
           }
          }

    private fun showConfirmDeleteDialogue() {
        val confirmFragment = ConfirmDeleteDialogueFragment()
        confirmFragment.listener = object :ConfirmDeleteDialogueFragment.ConfirmDeleteListener {
            override fun onDialogPositiveClick() {
                deleteTache()
            }
            override fun onDialogNegativeClick() {

            } }
        confirmFragment.show(supportFragmentManager, "delete confirm")
    }

    fun saveTache() {
        tache.nom= nomView.text.toString()
        tache.date = dateView.text.toString()

        intent = Intent(ACTION_SAVE_TACHE)
        intent.putExtra(EXTRA_Tache, tache as Parcelable)
        intent.putExtra(EXTRA_Tache_INDEX, tacheIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun deleteTache() {
        intent = Intent(ACTION_DELETE_TACHE)
        intent.putExtra(EXTRA_Tache_INDEX, tacheIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
