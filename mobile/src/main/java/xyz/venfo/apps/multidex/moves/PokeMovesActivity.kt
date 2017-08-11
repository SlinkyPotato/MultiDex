package xyz.venfo.apps.multidex.moves

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_poke_moves.*
import xyz.venfo.apps.multidex.R

class PokeMovesActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_poke_moves)
    setSupportActionBar(toolbar)

    // Get the sent poke move
//    val pokeMove: String = intent.getStringExtra(MainActivity.ACTIVITY_KEY)

//    textView5.text = pokeMove

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
    }
  }
}
