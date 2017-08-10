package xyz.venfo.apps.multidex

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmQuery
import xyz.venfo.apps.multidex.pokemon.PokeType
import xyz.venfo.apps.multidex.pokemon.PokeTypeId
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  companion object {
    val ACTIVITY_KEY: String = "xyz.venfo.multidex.PASSED_MSG"
  }

  private var realm: Realm by Delegates.notNull()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nav)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val helloText: TextView = findViewById(R.id.testText)
    val helloBtn: Button = findViewById(R.id.helloBtn)
    val pokeTypeBtn: Button = findViewById(R.id.pokeTypeBtn)
    val openPokeMoves: Button = findViewById(R.id.showPokeMovesBtn)

    realm = Realm.getDefaultInstance()

    helloBtn.setOnClickListener {
      val realmQuery: RealmQuery<PokeTypeId> = realm.where(PokeTypeId::class.java)
      realmQuery.equalTo("id", 15)
      val result: PokeTypeId = realmQuery.findFirst()
      helloText.text = result.type
    }

    pokeTypeBtn.setOnClickListener {
      val realmQuery: RealmQuery<PokeType> = realm.where(PokeType::class.java)
      realmQuery.equalTo("id", 0)
      val normalType: PokeType = realmQuery.findFirst()
      pokeTypeBtn.text = normalType.type
    }

    openPokeMoves.setOnClickListener { view: View ->
      val intent: Intent = Intent(this, PokeMovesActivity::class.java)
      intent.putExtra(ACTIVITY_KEY, pokeTypeBtn.text)
      startActivity(intent)
    }

    val fab = findViewById<FloatingActionButton>(R.id.fab)
    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
    }

    val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
    val toggle = ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    val navigationView: NavigationView = findViewById(R.id.nav_view)
    navigationView.setNavigationItemSelectedListener(this)
  }

  override fun onBackPressed() {
    val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
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

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    val id = item.itemId
    when (id) {
      R.id.nav_pokemon -> { // handle the camera action
      }
      R.id.nav_moves -> { // handle gallery action
      }
      R.id.nav_items -> {
      }
    }
    val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
    drawer.closeDrawer(GravityCompat.START)
    return true
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
