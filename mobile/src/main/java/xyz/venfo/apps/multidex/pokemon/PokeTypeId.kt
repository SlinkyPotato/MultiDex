package xyz.venfo.apps.multidex.pokemon

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * PokeType Id Class
 *
 * The class must be open.
 * @param id Int The type id
 * @param type String
 */
open class PokeTypeId(
    @PrimaryKey var id: Long = 0, // must have default value to init default constructor
    var type: String = ""
): RealmObject() {
  // Kotlin compiler generates standard getters and setters. Realm will overload them and code inside them is ignored.
}