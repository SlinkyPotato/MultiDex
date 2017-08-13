package xyz.venfo.apps.multidex

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class RecyclerViewHolder(
    var itemViewBinding: ViewDataBinding
) : RecyclerView.ViewHolder(itemViewBinding.root) {
  init {
    // Forces the bindings to run immediately instead of delaying until the next frame
    itemViewBinding.executePendingBindings() // important
  }
}
