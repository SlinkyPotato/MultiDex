package xyz.venfo.apps.multidex.pokemon

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import xyz.venfo.apps.multidex.R

class PokemonCardViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
  var cardView: CardView = itemView.findViewById(R.id.cv_pokemon)
  var helloText: TextView = itemView.findViewById(R.id.hello_text_view)
}
