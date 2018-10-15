package xyz.venfo.apps.multidex

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
  companion object {
    val ACTIVITY_KEY: String = "xyz.venfo.multidex.PASSED_MSG"
  }

  private var realm: Realm by Delegates.notNull()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nav) // activity gets set
  }

  /**
   * Close the drawer on back pressed
   */
  override fun onBackPressed() {
    val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.nav, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    val id = item.itemId

    if (id == R.id.action_settings) {
      return true
    }

    return super.onOptionsItemSelected(item)
  }
}

fun thisIsMyFirstFun(): Int {
  System.out.println("hello?")
  return 5
}

fun describe(obj: Any): String =
    when (obj) {
      1 -> "one"
      "hello" -> "ok"
      is Long -> "Long"
      !is String -> "Not a string"
      else -> "Not sure buddy"
    }
