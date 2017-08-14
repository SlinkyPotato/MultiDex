package xyz.venfo.apps.multidex.pokemon

import android.app.Application
import android.util.JsonReader
import android.util.Log
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import java.io.InputStream
import java.io.InputStreamReader

/**
 * The PokemonModel's Type information
 *
 * This class contains the specific name along with the name's weakness and effectiveness comparisons.
 *
 * @param name {Type} The primary name of the PokemonModel
 * @param halfDmg A list of Types that receive half damage
 * @param noDmg A list of Types that receive no damage
 * @param normalDmg A list of Types that receive 1x damage
 * @param doubleDmg A list of Types that receive 2x damage
 * @see PokeTypeId
 */
open class PokeType (
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var halfDmg: RealmList<PokeTypeId> = RealmList(),
    var noDmg: RealmList<PokeTypeId> = RealmList(),
    var normalDmg: RealmList<PokeTypeId> = RealmList(),
    var doubleDmg: RealmList<PokeTypeId> = RealmList(),
    var generation: Int = 0
): RealmObject() {
  companion object {
    /*val TYPES: Map<Int, String> = mapOf(
        0 to "Normal",
        1 to "Fire",
        2 to "Water",
        3 to "Electric",
        4 to "Grass",
        5 to "Ice",
        6 to "Fighting",
        7 to "Poison",
        8 to "Ground",
        9 to "Flying",
        10 to "Psychic",
        11 to "Bug",
        12 to "Rock",
        13 to "Ghost",
        14 to "Dragon",
        15 to "Dark",
        16 to "Steel",
        17 to "Fairy",
        18 to "???"
    )*/

    fun initPokeTypes(context: Application, realm: Realm) {
      var jsonReader: JsonReader? = null
      try {
        // Initialize PokeTypes
        val pokeTypesStream: InputStream = context.resources.openRawResource(R.raw.poke_type_effects)
        jsonReader = JsonReader(InputStreamReader(pokeTypesStream, "UTF-8"))
        // Read the poke types
        jsonReader.beginArray()
        while (jsonReader.hasNext()) {
          val pokeType: PokeType = readPokeType(jsonReader, realm)

          // Insert PokeType into database
          realm.copyToRealm(pokeType)
        }
        jsonReader.endArray()
        // Add Poketypes to database
        Log.i("Realm", " Successfully created PokeTypes.")
      } catch(error: Exception) {
        Log.wtf("Realm", " Unable to create PokeTypes. " + error.localizedMessage)
        throw Exception()
      } finally {
        if (jsonReader != null ) jsonReader.close() // only reader needs to be closed
      }
    }

    /**
     * Read PokeType Object
     */
    fun readPokeType(reader: JsonReader, realm: Realm): PokeType {
      var id: Int = -1
      val type: String
      var halfDmg: RealmList<PokeTypeId> = RealmList()
      var noDmg: RealmList<PokeTypeId> = RealmList()
      var normalDmg: RealmList<PokeTypeId> = RealmList()
      var doubleDmg: RealmList<PokeTypeId> = RealmList()
      // Read the fields
      reader.beginObject()
      while(reader.hasNext()) {
        val field: String = reader.nextName()
        // Handle various field types
        when (field) {
          "id" -> id = reader.nextInt()
          "halfDmg" -> halfDmg = readDmg(reader, realm)
          "noDmg" -> noDmg = readDmg(reader, realm)
          "normalDmg" -> normalDmg = readDmg(reader, realm)
          "doubleDmg" -> doubleDmg = readDmg(reader, realm)
          else -> {
            Log.wtf("JSON", " Unknown read in poke_type_effects.json.json.")
            reader.skipValue()
          }
        }
      }
      reader.endObject()
      // Retrieve PokeType Name
      val typeIdObj: PokeTypeId = realm.where(PokeTypeId::class.java).equalTo("id", id).findFirst()
      type = typeIdObj.name
      return PokeType(id, type, halfDmg, noDmg, normalDmg, doubleDmg)
    }

    /**
     * Read damage array and convert to PokeTypeId objects array
     * @param reader
     * @return RealmList<PokeTypeId>
     */
    fun readDmg(reader: JsonReader, realm: Realm): RealmList<PokeTypeId> {
      val dmgEffect: MutableList<PokeTypeId> = mutableListOf()
      reader.beginArray()
      while (reader.hasNext()) {
        val pokeTypeId: Int = reader.nextInt()
        val pkTypeObj: PokeTypeId = realm
            .where(PokeTypeId::class.java)
            .equalTo("id", pokeTypeId)
            .findFirst()
        dmgEffect.add(pkTypeObj)
      }
      reader.endArray()
      val realmList: RealmList<PokeTypeId> = RealmList()
      realmList.addAll(dmgEffect.toList())
      return realmList
    }
  }
}
