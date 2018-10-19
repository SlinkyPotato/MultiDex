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
      realmParser.parseJsonRealm("PokeTypeStrings", PokeTypeStrings::class.java, R.raw.poke_types)
//      realmParser.parseJsonRealm("PokeTypes", ::readPokeType, R.raw.poke_type_stats)
//      realmParser.parseJsonRealm("ContestTypes", ContestType::class.java, R.raw.contest_types)
//      realmParser.parseJsonRealm("DamageTypes", DamageType::class.java, R.raw.damage_types)
//      realmParser.parseJsonRealm("PokeMoves", ::readPokeMove, R.raw.moves, R.raw.move_stats)
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
