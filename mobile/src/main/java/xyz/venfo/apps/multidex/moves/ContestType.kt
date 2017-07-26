package xyz.venfo.apps.multidex.moves

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import java.io.InputStream

/**
 * PokeMove's Contest Type
 * @param id {Int}
 * @param type {String}
 */
open class ContestType(
    @PrimaryKey var id: Int = -1,
    var type: String = ""
) : RealmObject() {

  companion object {
    val TYPES: Map<Int, String> = mapOf(
        0 to "Cool",
        1 to "Beautiful",
        2 to "Cute",
        3 to "Clever",
        4 to "Tough"
    )

    fun initContestTypes(context: Application, realm: Realm) {
      var inputStream: InputStream? = null
      try {
        // Initialize Poke Categories
        inputStream = context.resources.openRawResource(R.raw.contest_types)
        realm.createAllFromJson(ContestType::class.java, inputStream)
        Log.i("Realm", " Successfully created ContestTypes.")
      } catch (error: Exception) {
        Log.wtf("Realm", " Unable to create ContestTypes. " + error.localizedMessage)
      } finally {
        if (inputStream != null) inputStream.close()
      }
    }
  }
}