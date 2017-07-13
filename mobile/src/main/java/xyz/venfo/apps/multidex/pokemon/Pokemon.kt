package xyz.venfo.apps.multidex.pokemon

import io.realm.RealmObject

class Pokemon(name: String, type: PokeType) : RealmObject() {

    init {

    }

    companion object {
        fun initAll() {

        }
    }

}