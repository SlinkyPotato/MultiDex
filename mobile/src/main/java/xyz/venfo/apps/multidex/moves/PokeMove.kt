package xyz.venfo.apps.multidex.moves

import android.app.Application
import android.util.JsonReader
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import xyz.venfo.apps.multidex.pokemon.PokeType
import java.io.InputStream
import java.io.InputStreamReader

/**
 * The Pokemon's Move Information
 *
 * This class contains specific move information
 *
 * Notes:
 *  * Karate Chop (2) is normal in gen 1
 *
 * @param id {String}
 * @param name {PokeType}
 * @param type {PokeType}
 * @param category {Int}
 * @param contestType {Int}
 * @param powerPoint {Int}
 * @param power {Int}
 * @param accuracy {Int}
 * @param generation {Int}
 */
open class PokeMove(
    @PrimaryKey var id: Int = -1,
    var name: String = "",
    var type: PokeType = PokeType(),
    var category: MoveCategory = MoveCategory(),
    var contestType: ContestType = ContestType(),
    var powerPoint: Int = -1,
    var power: Int = -1,
    var accuracy: Int = -1,
    var generation: Int = 0, // Gen 1 starts at 1
    var description: String = "",
    var effect: String = "",
    var notes: String = ""
) : RealmObject() {
  companion object {
    fun initPokeMoves(context: Application, realm: Realm) {
      var jsonReader: JsonReader? = null
      try {
        val moveStream: InputStream = context.resources.openRawResource(R.raw.poke_moves)
        jsonReader = JsonReader(InputStreamReader(moveStream, "UTF-8"))
        jsonReader.beginArray()
        while (jsonReader.hasNext()) {
          val pokeMove: PokeMove = readPokeMove(jsonReader, realm)
          realm.copyToRealm(pokeMove)
        }
        jsonReader.endArray()
        Log.i("Realm", " Successfully created PokeMoves.")
      } catch (error: Exception) {
        Log.wtf("Realm", " Unable to create PokeMoves. " + error.localizedMessage)
      } finally {
        if (jsonReader != null) jsonReader.close()
      }
    }

    fun readPokeMove(reader: JsonReader, realm: Realm): PokeMove {
      val pokeMove: PokeMove = PokeMove()
      reader.beginObject()
      while (reader.hasNext()) {
        val field: String = reader.nextName()
        when (field) {
          "id" -> pokeMove.id = reader.nextInt()
          "name" -> pokeMove.name = reader.nextString()
          "type" -> pokeMove.type = readPokeType(reader.nextInt(), realm)
          "categoryId" -> pokeMove.category = readCategory(reader.nextInt(), realm)
          "contestId" -> pokeMove.contestType = readContestType(reader.nextInt(), realm)
          "powerPoint" -> pokeMove.powerPoint = reader.nextInt()
          "power" -> pokeMove.power = reader.nextInt()
          "accuracy" -> pokeMove.accuracy = reader.nextInt()
          "generation" -> pokeMove.generation = reader.nextInt()
          "description" -> pokeMove.description = reader.nextString()
          "effect" -> pokeMove.effect = reader.nextString()
          "notes" -> pokeMove.notes = reader.nextString()
          else -> {
            Log.wtf("JSON", " Unknown read in poke_moves.json")
            reader.skipValue()
          }
        }
      }
      reader.endObject()
      return pokeMove
    }

    fun readPokeType(pokeTypeId: Int, realm: Realm): PokeType {
      return realm.where(PokeType::class.java).equalTo("id", pokeTypeId).findFirst()
    }

    fun readCategory(categoryId: Int, realm: Realm): MoveCategory {
      return realm.where(MoveCategory::class.java).equalTo("id", categoryId).findFirst()
    }

    fun readContestType(typeId: Int, realm: Realm): ContestType {
      return realm.where(ContestType::class.java).equalTo("id", typeId).findFirst()
    }
  }
}