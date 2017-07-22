package xyz.venfo.apps.multidex.pokemon

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject
import xyz.venfo.apps.multidex.R
import java.io.InputStream

/**
 * The Pokemon's Type information
 *
 * This class contains the specific type along with the type's weakness and effectiveness comparisons.
 *
 * @param type Type The primary type of the Pokemon
 * @param halfDmg A list of Types that receive half damage
 * @param noDmg A list of Types that receive no damage
 * @param normalDmg A list of Types that receive 1x damage
 * @param doubleDmg A list of Types that receive 2x damage
 * @see PokeTypeId
 */
open class PokeType (
    @PrimaryKey var id: Long = 0,
    var type: String = "",
    var halfDmg: RealmList<PokeTypeId> = RealmList(),
    var noDmg: RealmList<PokeTypeId> = RealmList(),
    var normalDmg: RealmList<PokeTypeId> = RealmList(),
    var doubleDmg: RealmList<PokeTypeId> = RealmList()
): RealmObject() {
  companion object {
    fun initPokeTypes(context: Application, realmInstance: Realm) {
//      val gson: Gson = Gson()
//      gson.toJson(1)
//      val values = intArrayOf(1, 2)
      realmInstance.executeTransactionAsync ({ realm ->
        // Initialize PokeTypes
        val inputStream: InputStream = context.resources.openRawResource(R.raw.type_effects)
//        val jsonArray: JSONArray =
        realm.createAllFromJson(PokeType::class.java, inputStream)
      }, {
        Log.i("Realm: ", "Successfully created PokeTypes.")
      }, { error ->
        Log.wtf("Realm: ", "Unable to create PokeTypes. " + error.localizedMessage)
      })
    }
  }
}
