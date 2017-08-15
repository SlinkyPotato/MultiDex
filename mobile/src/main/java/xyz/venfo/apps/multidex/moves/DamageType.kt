package xyz.venfo.apps.multidex.moves

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import java.io.InputStream

/**
 * The PokeMove's Category
 *
 * @param id {Int}
 * @param category {String}
 */
open class DamageType(
    @PrimaryKey var id: Int = -1,
    var category: String = ""
): RealmObject()
