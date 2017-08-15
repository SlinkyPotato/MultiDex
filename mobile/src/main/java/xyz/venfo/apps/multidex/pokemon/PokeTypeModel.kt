package xyz.venfo.apps.multidex.pokemon

import android.app.Application
import android.util.JsonReader
import android.util.JsonToken
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
 * @param type The Poke Type strings
 * @param genId The generation id
 * @param halfDmg A list of Types that receive half damage
 * @param noDmg A list of Types that receive no damage
 * @param normalDmg A list of Types that receive 1x damage
 * @param doubleDmg A list of Types that receive 2x damage
 * @see PokeTypeStrings
 */
open class PokeTypeModel(
    @PrimaryKey var id: Int = 0,
    var type: PokeTypeStrings = PokeTypeStrings(),
    var genId: Int = 0,
    var halfDmg: RealmList<PokeTypeStrings> = RealmList(),
    var noDmg: RealmList<PokeTypeStrings> = RealmList(),
    var normalDmg: RealmList<PokeTypeStrings> = RealmList(),
    var doubleDmg: RealmList<PokeTypeStrings> = RealmList()
): RealmObject() {
  companion object {
    fun readPokeType(reader: JsonReader, realm: Realm): PokeTypeModel {
      var id: Int = 0
      var genId: Int = 0
      var doubleDmg: RealmList<PokeTypeStrings> = RealmList()
      var halfDmg: RealmList<PokeTypeStrings> = RealmList()
      var noDmg: RealmList<PokeTypeStrings> = RealmList()
      var normalDmg: RealmList<PokeTypeStrings> = RealmList()
      // Read the fields
      reader.beginObject()
      while(reader.hasNext()) {
        val field: String = reader.nextName()
        if (reader.peek() == JsonToken.NULL) {
          reader.skipValue()
          continue
        }
        // Handle various field types
        when (field) {
          "pokeTypeId" -> id = reader.nextInt()
          "genId" -> genId = reader.nextInt()
          "doubleDmg" -> doubleDmg = readDmg(reader, realm)
          "halfDmg" -> halfDmg = readDmg(reader, realm)
          "noDmg" -> noDmg = readDmg(reader, realm)
          "normalDmg" -> normalDmg = readDmg(reader, realm)
          else -> {
            Log.wtf("JSON", " Skipping unknown read in poke_type_effects.json.")
            reader.skipValue()
          }
        }
      }
      reader.endObject()
      // Retrieve PokeTypeModel Name
      val typeStringsObj: PokeTypeStrings = realm.where(PokeTypeStrings::class.java).equalTo("id", id).findFirst()
      return PokeTypeModel(id, typeStringsObj, genId, halfDmg, noDmg, normalDmg, doubleDmg)
    }

    /**
     * Read damage array and convert to PokeTypeStrings objects array
     * @param reader
     * @return RealmList<PokeTypeStrings>
     */
    fun readDmg(reader: JsonReader, realm: Realm): RealmList<PokeTypeStrings> {
      val dmgEffect: MutableList<PokeTypeStrings> = mutableListOf()
      reader.beginArray()
      while (reader.hasNext()) {
        val pokeTypeId: Int = reader.nextInt()
        val pkTypeObj: PokeTypeStrings = realm
            .where(PokeTypeStrings::class.java)
            .equalTo("id", pokeTypeId)
            .findFirst()
        dmgEffect.add(pkTypeObj)
      }
      reader.endArray()
      val realmList: RealmList<PokeTypeStrings> = RealmList()
      realmList.addAll(dmgEffect.toList())
      return realmList
    }
  }
}
