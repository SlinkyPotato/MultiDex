package xyz.venfo.apps.multidex

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import xyz.venfo.apps.multidex.moves.ContestType
import xyz.venfo.apps.multidex.moves.DamageType
import xyz.venfo.apps.multidex.moves.PokeMoveModel.Companion.readPokeMove
import xyz.venfo.apps.multidex.pokemon.PokeTypeModel.Companion.readPokeType
import xyz.venfo.apps.multidex.pokemon.PokeTypeStrings

class MultiDexApp: Application() {
  /**
   * Called when the application is starting, before any other application objects have been created.
   */
  override fun onCreate() {
    super.onCreate()
    // Initialization Realm DB
    Realm.init(this)
    val config: RealmConfiguration = RealmConfiguration.Builder().build()
    Realm.setDefaultConfiguration(config)
    val realmInstance = Realm.getDefaultInstance()

    // Initialize database
    realmInstance.executeTransactionAsync ({ realm ->
      realm.deleteAll()
      val realmParser: RealmJsonParser = RealmJsonParser(this, realm)
      realmParser.parseJsonRealm("PokeTypeStrings", R.raw.poke_types, PokeTypeStrings::class.java)
      realmParser.parseJsonRealm("PokeTypes", R.raw.poke_type_stats, ::readPokeType)
      realmParser.parseJsonRealm("ContestTypes", R.raw.contest_types, ContestType::class.java)
      realmParser.parseJsonRealm("DamageTypes", R.raw.damage_types, DamageType::class.java)
//      realmParser.parseJsonRealm("PokeMoves", R.raw.poke_moves, ::readPokeMove)
    }, {
      Log.i("Realm: ", "Successfully initialized database.")
    }, { error ->
      Log.wtf("Realm: ", "Unable to initialize database. " + error.localizedMessage)
    })
  }

  /**
   * Called when device configuration changes while component is running.
   */
  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
  }

  /**
   * Called when overall system is running low on memory.
   */
  override fun onLowMemory() {
    super.onLowMemory()
  }
}
