package xyz.venfo.apps.multidex.pokemon

import io.realm.Realm
import io.realm.RealmObject

/**
 * All of the Pokemon Types as Enums
 */
enum class Type {
  NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLY, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK,
  STEEL, FAIRY
}

/**
 * The Pokemon's Type information
 *
 * This class contains the specific type along with the type's weakness and effectiveness comparisons.
 *
 * @param primaryType Type The primary type of the Pokemon
 * @param secondaryType The secondary type of the Pokemon if it has one
 * @param halfDmg A list of Types that receive half damage
 * @param noDmg A list of Types that receive no damage
 * @param normalDmg A list of Types that receive 1x damage
 * @param doubleDmg A list of Types that receive 2x damage
 * @see Type
 */
data class PokeType(val primaryType: Type,
                    val secondaryType: Type,
                    val halfDmg: List<Type>,
                    val noDmg: List<Type>,
                    val normalDmg: List<Type>,
                    val doubleDmg: List<Type>): RealmObject() {

  companion object {
    fun initAll() {
      /*val realm: Realm = Realm.getDefaultInstance()
      realm.beginTransaction()
      realm.commitTransaction()*/
    }
  }
}
