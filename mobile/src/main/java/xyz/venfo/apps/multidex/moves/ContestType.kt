package xyz.venfo.apps.multidex.moves

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * PokeMove's Contest Type
 * @param id {Int}
 * @param type {String}
 */
open class ContestType(
    @PrimaryKey var id: Int = 0,
    @Required var type: String = ""
) : RealmObject()
