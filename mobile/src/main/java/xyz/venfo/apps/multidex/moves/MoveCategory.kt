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
open class MoveCategory(
    @PrimaryKey var id: Int = -1,
    var category: String = ""
): RealmObject() {

  companion object {
    val TYPES: Map<Int, String> = mapOf(
        0 to "Physical",
        1 to "Special",
        2 to "Status"
    )

    fun initCategories(context: Application, realm: Realm) {
      var inputStream: InputStream? = null
      try {
        // Initialize Poke Categories
        inputStream = context.resources.openRawResource(R.raw.move_category)
        realm.createAllFromJson(MoveCategory::class.java, inputStream)
        Log.i("Realm", " Successfully created MoveCategories.")
      } catch (error: Exception) {
        Log.wtf("Realm", " Unable to create MoveCategories. " + error.localizedMessage)
      } finally {
        if (inputStream != null) inputStream.close()
      }
    }
  }
}