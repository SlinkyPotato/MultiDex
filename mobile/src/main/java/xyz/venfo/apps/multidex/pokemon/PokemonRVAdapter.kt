package xyz.venfo.apps.multidex.pokemon

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.venfo.apps.multidex.BR
import xyz.venfo.apps.multidex.R
import xyz.venfo.apps.multidex.RecyclerViewHolder

class PokemonRVAdapter(
    var pokemons: MutableList<PokemonModel>
): RecyclerView.Adapter<RecyclerViewHolder>() {

  /**
   * Set the card view and display the contents
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
    val itemViewBinding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.content_pokemon_card, parent, false)
    return RecyclerViewHolder(itemViewBinding)
  }

  /**
   * Binds array data to view holder data
   */
  override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    val viewDataBinding: ViewDataBinding = holder.itemViewBinding
    viewDataBinding.setVariable(BR.pokemon, pokemons[position])
  }

  /**
   * Return the number of pokemon
   */
  override fun getItemCount(): Int {
    return pokemons.size
  }
}
