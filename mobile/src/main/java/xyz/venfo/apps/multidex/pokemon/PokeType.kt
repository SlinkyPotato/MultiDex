package xyz.venfo.apps.multidex.pokemon

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * All of the Pokemon Types as Enums
 */
class Type {

  companion object {
    val NORMAL = "Normal"
    val FIRE = "Fire"
    val WATER = "Water"
    val ELECTRIC = "Electric"
    val GRASS = "Grass"
    val ICE = "Ice"
    val FIGHTING = "Fighting"
    val POISON = "Poison"
    val GROUND = "Ground"
    val FLYING = "Flying"
    val PSYCHIC = "Psychic"
    val BUG = "Bug"
    val ROCK = "Rock"
    val GHOST = "Ghost"
    val DRAGON = "Dragon"
    val DARK = "Dark"
    val STEEL = "Steel"
    val FAIRY = "Fairy"
  }
}

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
 * @see Type
 */
data class PokeType(
    @PrimaryKey val type: Int,
    val halfDmg: List<Type>,
    val noDmg: List<Type>,
    val normalDmg: List<Type>,
    val doubleDmg: List<Type>) {
  companion object {
    fun initAll() {
      val myVals = Type.NORMAL
      System.out.println(Type.NORMAL)
      /*val realm: Realm = Realm.getDefaultInstance()
      realm.beginTransaction()
      realm.commitTransaction()*/
    }
  }
}
