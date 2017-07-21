package xyz.venfo.apps.multidex

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import xyz.venfo.apps.multidex.pokemon.PokeType
import xyz.venfo.apps.multidex.pokemon.PokeTypeId
import java.io.*

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

    // Delete the database
    realmInstance.beginTransaction()
    realmInstance.deleteAll()
    realmInstance.commitTransaction()

    // Initialize database
    PokeTypeId.initPokeTypeIds(this, realmInstance)
    PokeType.initPokeTypes(this, realmInstance)
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
