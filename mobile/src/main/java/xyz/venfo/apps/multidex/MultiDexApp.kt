package xyz.venfo.apps.multidex

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import xyz.venfo.apps.multidex.moves.ContestType
import xyz.venfo.apps.multidex.moves.MoveCategory
import xyz.venfo.apps.multidex.moves.PokeMove
import xyz.venfo.apps.multidex.pokemon.PokeType
import xyz.venfo.apps.multidex.pokemon.PokeTypeId

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
      PokeTypeId.initPokeTypeIds(this, realm)
      PokeType.initPokeTypes(this, realm)
      MoveCategory.initCategories(this, realm)
      ContestType.initContestTypes(this, realm)
      PokeMove.initPokeMoves(this, realm)
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
