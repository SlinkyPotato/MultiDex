package xyz.venfo.apps.multidex.pokemon

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * PokeTypeModel Id Class
 *
 * The class must be open.
 * @param id Int The name id
 * @param name String
 */
open class PokeTypeStrings(
    @PrimaryKey var id: Int = 0, // must have default value to init default constructor
    @Required var name: String = "",
    var description: String? = null,
    var notes: String? = null
) : RealmObject() {
  // Kotlin compiler generates standard getters and setters. Realm will overload them and code inside them is ignored.
}
