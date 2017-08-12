package xyz.venfo.apps.multidex.pokemon

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import xyz.venfo.apps.multidex.R

/**
 * A simple [Fragment] subclass.
 */
class PokemonFragment : ListFragment() {

  /**
   * Called when the fragment has been associated with the activity
   */
  override fun onAttach(context: Context?) {
    super.onAttach(context)
  }

  /**
   * Essential components this fragment uses are initialized here.
   */
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
  }

  /**
   * Called when it's time to draw user interface for the first time.
   */
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_pokemon, container, false) // draw the fragment view
  }

  /**
   * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
   */
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    val myList: List<String> = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o")
    listAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, myList)
    super.onActivityCreated(savedInstanceState)
  }

  /**
   * System calls this function as the user is first leaving the fragment
   */
  override fun onPause() {
    super.onPause()
  }

  /**
   * Handle tapping an entry
   */
  override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
    super.onListItemClick(l, v, position, id)
  }

}// Required empty public constructor
