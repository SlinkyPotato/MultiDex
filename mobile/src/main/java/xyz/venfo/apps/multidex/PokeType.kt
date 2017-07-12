package xyz.venfo.apps.multidex

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
 * @param type Type The Pokemon's Type Enum
 * @param typeEffective A 2D List containing 6 entries being 1/4, 1/2, 0x, 1x, 2x, 4x Type lists of effectiveness.
 * @see Type
 */
data class PokeType(val type: Type,
                    val typeEffective: List<List<Type>>) {

    val quarterEff: List<Type> = this.typeEffective[0]
    val halfEff: List<Type> = this.typeEffective[1]
    val notEff: List<Type> = this.typeEffective[2]
    val normalEff: List<Type> = this.typeEffective[3]
    val doubleEff: List<Type> = this.typeEffective[4]
    val quadEff: List<Type> = this.typeEffective[5]
}
