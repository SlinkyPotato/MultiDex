package xyz.venfo.apps.multidex.pokemon

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import java.io.InputStream

/**
 * PokeType Id Class
 *
 * The class must be open.
 * @param id Int The type id
 * @param type String
 */
open class PokeTypeId(
    @PrimaryKey var id: Int = 0, // must have default value to init default constructor
    var type: String = ""
) : RealmObject() {
  // Kotlin compiler generates standard getters and setters. Realm will overload them and code inside them is ignored.

  companion object {
    fun initPokeTypeIds(context: Application, realmInstance: Realm) {
      var typeIdsStream: InputStream? = null
      try {
        // Initialize PokeTypeIds
        typeIdsStream = context.resources.openRawResource(R.raw.type_ids)
        realmInstance.createAllFromJson(PokeTypeId::class.java, typeIdsStream)
        Log.i("Realm", " Successfully created PokeTypeIds.")
      } catch (error: Exception) {
        Log.wtf("Realm", " Unable to create PokeTypeIds. " + error.localizedMessage)
      } finally {
        if (typeIdsStream != null) typeIdsStream.close()
      }
    }
  }
}