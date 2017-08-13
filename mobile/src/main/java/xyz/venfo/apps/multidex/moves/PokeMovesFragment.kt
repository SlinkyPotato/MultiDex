package xyz.venfo.apps.multidex.moves


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.venfo.apps.multidex.R
import xyz.venfo.apps.multidex.databinding.FragmentMovesBinding
import xyz.venfo.apps.multidex.databinding.FragmentPokemonBinding


/**
 * A simple [Fragment] subclass.
 */
class PokeMovesFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
//    val textView = TextView(activity)
//    textView.setText(R.string.hello_blank_fragment)
//    return textView
//    return inflater.inflate(R.layout.fragment_moves, container, false)
    val binding: FragmentMovesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_moves, container, false)
    val view: View = binding.root
    binding.pokeMove = PokeMove(1, "Hello binding!")
    return view
  }

}// Required empty public constructor
