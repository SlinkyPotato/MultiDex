package xyz.venfo.apps.multidex.pokemon

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.venfo.apps.multidex.R
import xyz.venfo.apps.multidex.moves.PokeMove

class PokemonRVAdapter(
    var pokemons: MutableList<PokeMove>
): RecyclerView.Adapter<PokemonCardViewHolder>() {

  /**
   * Set the card view and display the contents
   */
  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PokemonCardViewHolder {
    val itemView: View = LayoutInflater.from(parent?.context).inflate(R.layout.content_pokemon_card, parent, false)
    return PokemonCardViewHolder(itemView)
  }

  /**
   * Binds array data to view holder data
   */
  override fun onBindViewHolder(holder: PokemonCardViewHolder?, position: Int) {
    val helloText: PokeMove = pokemons[position]
    holder?.helloText?.text = helloText.name
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
    super.onAttachedToRecyclerView(recyclerView)
  }

  /**
   * Return the number of pokemon
   */
  override fun getItemCount(): Int {
    return pokemons.size
  }
}
