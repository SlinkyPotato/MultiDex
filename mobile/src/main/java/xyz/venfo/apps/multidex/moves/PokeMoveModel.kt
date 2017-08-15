package xyz.venfo.apps.multidex.moves

import android.app.Application
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.venfo.apps.multidex.R
import xyz.venfo.apps.multidex.pokemon.PokeTypeModel
import java.io.InputStream
import java.io.InputStreamReader

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
 * @param generation {Int}
 */
open class PokeMoveModel(
    @PrimaryKey var id: Int = -1,
    var name: String = "",
    var type: PokeTypeModel = PokeTypeModel(),
    var category: DamageType = DamageType(),
    var contestType: ContestType = ContestType(),
    var powerPoint: Int = -1,
    var power: Int = -1,
    var accuracy: Int = -1,
    var generation: Int = 0, // Gen 1 starts at 1
    var description: String = "",
    var effect: String = "",
    var notes: String? = ""
) : RealmObject() {
  companion object {
    fun initPokeMoves(context: Application, realm: Realm) {
      var jsonReader: JsonReader? = null
      try {
        val moveStream: InputStream = context.resources.openRawResource(R.raw.poke_moves)
        jsonReader = JsonReader(InputStreamReader(moveStream, "UTF-8"))
        jsonReader.beginArray()
        while (jsonReader.hasNext()) {
          val pokeMove: PokeMoveModel = readPokeMove(jsonReader, realm)
          realm.copyToRealm(pokeMove)
        }
        jsonReader.endArray()
        Log.i("Realm", " Successfully created PokeMoves.")
      } catch (error: Exception) {
        Log.wtf("Realm", " Unable to create PokeMoves. " + error.localizedMessage)
        throw Exception()
      } finally {
        if (jsonReader != null) jsonReader.close()
      }
    }

    fun readPokeMove(reader: JsonReader, realm: Realm): PokeMoveModel {
      val pokeMove: PokeMoveModel = PokeMoveModel()
      reader.beginObject()

      // Anon functions to obtain data from realm
      val readPokeTypeFun = fun (pokeTypeId: Int): PokeTypeModel {
        return realm.where(PokeTypeModel::class.java).equalTo("id", pokeTypeId).findFirst()
      }
      val readCategory = fun (categoryId: Int): DamageType {
        return realm.where(DamageType::class.java).equalTo("id", categoryId).findFirst()
      }
      val readContestType = fun (typeId: Int): ContestType {
        return realm.where(ContestType::class.java).equalTo("id", typeId).findFirst()
      }

      // Process the json file
      while (reader.hasNext()) {
        val field: String = reader.nextName()
        if (reader.peek() == JsonToken.NULL) {
          reader.skipValue()
        }
        when (field) {
          "id" -> pokeMove.id = reader.nextInt()
          "name" -> pokeMove.name = reader.nextString()
          "pokeTypeId" -> pokeMove.type = readPokeTypeFun(reader.nextInt())
          "damageTypeId" -> pokeMove.category = readCategory(reader.nextInt())
          "contestTypeId" -> pokeMove.contestType = readContestType(reader.nextInt())
          "powerPoint" -> pokeMove.powerPoint = reader.nextInt()
          "power" -> pokeMove.power = reader.nextInt()
          "accuracy" -> pokeMove.accuracy = reader.nextInt()
          "generation" -> pokeMove.generation = reader.nextInt()
          "description" -> pokeMove.description = reader.nextString()
          "effect" -> pokeMove.effect = reader.nextString()
          "notes" -> reader.nextString()
          else -> {
            Log.wtf("JSON", " Unknown read in poke_moves.json")
            reader.skipValue()
          }
        }
      }
      reader.endObject()
      return pokeMove
    }
  }
}