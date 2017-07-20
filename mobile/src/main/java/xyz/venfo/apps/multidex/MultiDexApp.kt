package xyz.venfo.apps.multidex

import android.app.Application
import android.content.res.Configuration
import io.realm.Realm
import io.realm.RealmConfiguration
import xyz.venfo.apps.multidex.pokemon.PokeTypeId
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.javaClass

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
    val realm = Realm.getDefaultInstance()

    // Initialize Pokemon Database
    realm.executeTransaction({
      try {
        // Obtain Poke type ids
        val inputStream: InputStream = resources.openRawResource(R.raw.type_ids)
        realm.createAllFromJson(PokeTypeId::class.java, inputStream)
      } catch (e: IOException) {
        throw RuntimeException(e)
      }
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
