package xyz.venfo.apps.multidex.pokemon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.venfo.apps.multidex.R

/**
 * A simple [Fragment] subclass.
 */
class PokemonFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
//    val textView = TextView(activity)
//    textView.setText(R.string.hello_blank_fragment)
//    return textView
    return inflater.inflate(R.layout.fragment_pokemon, container, false)
  }

}// Required empty public constructor
