package xyz.venfo.apps.multidex.moves

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.pokemon.PokeTypeModel

/**
 * The PokemonModel's Move Information
 *
 * This class contains specific move information
 *
 * Notes:
 *  * Karate Chop (2) is normal in gen 1
 *
 * @param id {String}
 * @param name {PokeTypeModel}
 * @param type {PokeTypeModel}
 * @param category {Int}
 * @param contestType {Int}
 * @param powerPoint {Int}
 * @param power {Int}
 * @param accuracy {Int}
 * @param genId {Int}
 */
open class PokeMoveModel(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var type: PokeTypeModel = PokeTypeModel(),
    var category: DamageType = DamageType(),
    var contestType: ContestType = ContestType(),
    var powerPoint: Int = 0,
    var power: Int? = null,
    var accuracy: Int? = 0,
    var genId: Int = 0, // Gen 1 starts at 1
    var description: String = "",
    var effect: String = "",
    var notes: String? = null
) : RealmObject() {
  companion object {
    fun readPokeMove(realm: Realm, reader: JsonReader, otherReader: JsonReader?): PokeMoveModel {
      val pokeMove: PokeMoveModel = PokeMoveModel()
      reader.beginObject()
      otherReader?.beginObject() // skip if null

      // Anon functions to obtain data from realm
      val readPokeTypeFun = fun(pokeTypeId: Int): PokeTypeModel {
        return realm.where(PokeTypeModel::class.java).equalTo("id", pokeTypeId).findFirst()
      }

      val readCategory = fun(categoryId: Int): DamageType {
        return realm.where(DamageType::class.java).equalTo("id", categoryId).findFirst()
      }

      val readContestType = fun(typeId: Int): ContestType {
        return realm.where(ContestType::class.java).equalTo("id", typeId).findFirst()
      }

      // Process the first Json file
      while (reader.hasNext()) {
        val field: String = reader.nextName()
        if (reader.peek() == JsonToken.NULL) {
          reader.skipValue()
          continue
        }
        when (field) {
          "id" -> pokeMove.id = reader.nextInt()
          "name" -> pokeMove.name = reader.nextString()
          "description" -> pokeMove.description = reader.nextString()
          "effect" -> pokeMove.effect = reader.nextString()
          "notes" -> reader.nextString()
          else -> {
            Log.wtf("JSON", " Unknown read in poke_moves.json")
            throw Exception("Failed to read poke_moves.json")
          }
        }
      }
      reader.endObject()
      if (otherReader != null) {
        // Process the second json file
        while (otherReader.hasNext()) {
          val field = otherReader.nextName()
          if (otherReader.peek() == JsonToken.NULL) {
            otherReader.skipValue()
            continue
          }
          when (field) {
            "pokeMoveId" -> if (pokeMove.id != otherReader.nextInt()) throw Exception("PokeMove ID does not match.")
            "pokeTypeId" -> pokeMove.type = readPokeTypeFun(otherReader.nextInt())
            "damageTypeId" -> pokeMove.category = readCategory(otherReader.nextInt())
            "contestTypeId" -> pokeMove.contestType = readContestType(otherReader.nextInt())
            "powerPoint" -> pokeMove.powerPoint = otherReader.nextInt()
            "power" -> pokeMove.power = otherReader.nextInt()
            "accuracy" -> pokeMove.accuracy = otherReader.nextInt()
            "genId" -> pokeMove.genId = otherReader.nextInt()
            else -> {
              Log.wtf("JSON", " Unknown read in poke_moves.json")
              throw Exception("Failed to read poke_moves.json")
            }
          }
        }
        otherReader.endObject()
      }
      return pokeMove
    }
  }
}