package xyz.venfo.apps.multidex.pokemon

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * The PokemonModel's Type information
 *
 * This class contains the specific name along with the name's weakness and effectiveness comparisons.
 *
 * @param type The Poke Type strings
 * @param genId The genId id
 * @param halfDmg A list of Types that receive half damage
 * @param noDmg A list of Types that receive no damage
 * @param normalDmg A list of Types that receive 1x damage
 * @param doubleDmg A list of Types that receive 2x damage
 * @see PokeTypeStrings
 */
open class PokeTypeModel(
    @PrimaryKey var id: Int = 0,
    var type: PokeTypeStrings? = PokeTypeStrings(),
    var genId: Int = 0,
    var halfDmg: RealmList<PokeTypeStrings> = RealmList(),
    var noDmg: RealmList<PokeTypeStrings> = RealmList(),
    var normalDmg: RealmList<PokeTypeStrings> = RealmList(),
    var doubleDmg: RealmList<PokeTypeStrings> = RealmList()
): RealmObject() {
  companion object {
    fun readPokeType(realm: Realm, reader: JsonReader, notused: JsonReader? = null): PokeTypeModel {
      val pokeType: PokeTypeModel = PokeTypeModel()
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
          "pokeTypeId" -> pokeType.id = reader.nextInt()
          "genId" -> pokeType.genId = reader.nextInt()
          "doubleDmg" -> pokeType.doubleDmg = readDmg(reader, realm)
          "halfDmg" -> pokeType.halfDmg = readDmg(reader, realm)
          "noDmg" -> pokeType.noDmg = readDmg(reader, realm)
          "normalDmg" -> pokeType.normalDmg = readDmg(reader, realm)
          else -> {
            Log.wtf("JSON", " Skipping unknown read in poke_type_effects.json.")
            reader.skipValue()
          }
        }
      }
      reader.endObject()
      // Retrieve PokeTypeModel Name
      val pokeTypeStrings: PokeTypeStrings? = realm.where(PokeTypeStrings::class.java).equalTo("id", pokeType.id).findFirst()
      pokeTypeStrings?.let { it ->
        pokeType.type = it
      }
      return pokeType
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
        val pkTypeObj: PokeTypeStrings? = realm
            .where(PokeTypeStrings::class.java)
            .equalTo("id", pokeTypeId)
            .findFirst()
        pkTypeObj?.let { it -> dmgEffect.add(pkTypeObj) }
      }
      reader.endArray()
      val realmList: RealmList<PokeTypeStrings> = RealmList()
      realmList.addAll(dmgEffect.toList())
      return realmList
    }
  }
}
