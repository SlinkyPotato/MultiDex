package xyz.venfo.apps.multidex.pokemon

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.venfo.apps.multidex.databinding.FragmentPokemonBinding

/**
 * A simple [Fragment] subclass.
 */
class PokemonRVFragment : Fragment() {
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
   * Data binding initialization must be in here
   */
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Obtain binding
    val binding: FragmentPokemonBinding = FragmentPokemonBinding.inflate(inflater, container, false)

    // Obtain view
    val view: View = binding.root

    // Prepare data for binding
    val pokeSet: MutableList<PokemonModel> = mutableListOf(PokemonModel("Hello!"))

    // Attach adapter
    binding.rvPokemon.adapter = PokemonRVAdapter(pokeSet)
    return view
  }

  /**
   * Non-data binding initialization must be in here
   */
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
  }

  /**
   * System calls this function as the user is first leaving the fragment
   */
  override fun onPause() {
    super.onPause()
  }

}
