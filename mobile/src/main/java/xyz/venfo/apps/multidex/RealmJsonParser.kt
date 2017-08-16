package xyz.venfo.apps.multidex

import android.app.Application
import android.util.JsonReader
import android.util.Log
import io.realm.Realm
import io.realm.RealmModel
import java.io.InputStream
import java.io.InputStreamReader

class RealmJsonParser(
    val context: Application,
    val realm: Realm
) {

  private fun baseParseJsonRealm(
      tableName: String,
      operation: (InputStream, InputStream?) -> Unit,
      fileResource: Int,
      otherFileResource: Int? = null
  ) {
    try {
      val fileStream: InputStream = context.resources.openRawResource(fileResource)
      val otherStream: InputStream? = if (otherFileResource != null) context.resources.openRawResource(otherFileResource) else null
      fileStream.use { operation(fileStream, otherStream) }
      Log.i("Realm", " Successfully created " + tableName)
    } catch (error: Exception) {
      Log.wtf("Realm", " Unable to create " + tableName)
      Log.wtf("Realm", " " + error.localizedMessage)
      throw Exception()
    }
  }

  /**
   * Create and store a realm object directly from a json file
   * @param tableName
   * @param fileResource
   * @param classType
   */
  fun <RealmType : RealmModel> parseJsonRealm(
      tableName: String,
      classType: Class<RealmType>,
      fileResource: Int
  ) {
    baseParseJsonRealm(tableName, { stream: InputStream, _: InputStream? ->
      realm.createAllFromJson(classType, stream)
    }, fileResource)
  }

  /**
   * Process a json file field by field using a specified function
   */
  fun <RealmType : RealmModel> parseJsonRealm(
      tableName: String,
      jsonReadFun: (Realm, JsonReader, JsonReader?) -> RealmType,
      fileResource: Int,
      otherFileResource: Int? = null
  ) {
    baseParseJsonRealm(tableName, { stream: InputStream, otherStream: InputStream? ->
      val jsonReader: JsonReader = JsonReader(InputStreamReader(stream, "UTF-8"))
      val otherJsonReader: JsonReader? = if (otherStream != null) JsonReader(InputStreamReader(otherStream, "UTF-8")) else null
      jsonReader.use {
        otherJsonReader.use {
          jsonReader.beginArray()
          otherJsonReader?.beginArray()
          while (jsonReader.hasNext()) {
            val genObj: RealmType = jsonReadFun(realm, jsonReader, otherJsonReader)
            realm.copyToRealm(genObj)
          }
          jsonReader.endArray()
          otherJsonReader?.endArray()
        }
      }
    }, fileResource, otherFileResource)
  }
}
