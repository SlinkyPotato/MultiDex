package xyz.venfo.apps.multidex

import android.app.Application
import android.util.JsonReader
import android.util.Log
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import xyz.venfo.apps.multidex.pokemon.PokeTypeModel
import java.io.InputStream
import java.io.InputStreamReader

class RealmJsonParser(
    val context: Application,
    val realm: Realm
) {

  // TODO: Create base parser where other parsers make calls to it
  /**
   * Create and store a realm object directly from a json file
   * @param tableName
   * @param fileResource
   * @param classType
   */
  fun <RealmType: RealmModel> parseJsonRealm(tableName: String,
                     fileResource: Int,
                     classType: Class<RealmType>) {
    val resStream: InputStream = context.resources.openRawResource(fileResource)
    try {
      realm.createAllFromJson(classType, resStream)
      Log.i("Realm", " Successfully created " + tableName)
    } catch (error: Exception) {
      Log.wtf("Realm", " Unable to create " + tableName)
      Log.wtf("Realm", " " + error.localizedMessage)
      throw Exception()
    } finally {
      resStream.close()
    }
  }

  /**
   * Process a json file field by field using a specified function
   */
  fun <RealmType : RealmModel> parseJsonRealm(tableName: String,
                                              fileResource: Int,
                                              jsonReadFun: (JsonReader, Realm) -> RealmType) {
    val resStream: InputStream = context.resources.openRawResource(fileResource)
    val jsonReader: JsonReader = JsonReader(InputStreamReader(resStream, "UTF-8"))
    try {
      jsonReader.beginArray()
      while (jsonReader.hasNext()) {
        val genObj: RealmType = jsonReadFun(jsonReader, realm)
        realm.copyToRealm(genObj)
      }
      jsonReader.endArray()
      Log.i("Realm", " Successfully created " + tableName)
    } catch (error: Exception) {
      Log.wtf("Realm", " Unable to create " + tableName)
      Log.wtf("Realm", " " + error.localizedMessage)
      throw Exception()
    } finally {
      jsonReader.close()
    }
  }

  // TODO: Create third parser that combines two (and maybe more) json files

}
