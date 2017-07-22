package xyz.venfo.apps.multidex

import android.app.Application
import android.content.res.Configuration
import android.util.JsonReader
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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

    // Initialize database
    realmInstance.executeTransactionAsync ({ realm ->
      realm.deleteAll()
//      PokeTypeId.initPokeTypeIds(this, realm)
//      PokeType.initPokeTypes(this, realm)
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
