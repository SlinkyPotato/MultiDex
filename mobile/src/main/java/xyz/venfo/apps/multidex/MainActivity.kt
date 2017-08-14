package xyz.venfo.apps.multidex

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import io.realm.Realm
import xyz.venfo.apps.multidex.moves.PokeMovesFragment
import xyz.venfo.apps.multidex.pokemon.PokemonRVFragment
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  companion object {
    val ACTIVITY_KEY: String = "xyz.venfo.multidex.PASSED_MSG"
  }

  private var realm: Realm by Delegates.notNull()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nav) // activity gets set

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    initializeFragmentLayout(savedInstanceState) // init fragments

//    val helloText: TextView = findViewById(R.id.testText)
//    val helloBtn: Button = findViewById(R.id.helloBtn)
//    val pokeTypeBtn: Button = findViewById(R.id.pokeTypeBtn)
//    val openPokeMoves: Button = findViewById(R.id.showPokeMovesBtn)

    realm = Realm.getDefaultInstance()

    // Test buttons
    /*helloBtn.setOnClickListener {
      val realmQuery: RealmQuery<PokeTypeId> = realm.where(PokeTypeId::class.java)
      realmQuery.equalTo("id", 15)
      val result: PokeTypeId = realmQuery.findFirst()
      helloText.text = result.name
    }

    pokeTypeBtn.setOnClickListener {
      val realmQuery: RealmQuery<PokeType> = realm.where(PokeType::class.java)
      realmQuery.equalTo("id", 0)
      val normalType: PokeType = realmQuery.findFirst()
      pokeTypeBtn.text = normalType.name
    }

    openPokeMoves.setOnClickListener { view: View ->
      // Working with fragments
      val intent: Intent = Intent(this, PokeMovesActivity::class.java)
      intent.putExtra(ACTIVITY_KEY, pokeTypeBtn.text)
      startActivity(intent)
    }*/

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

  /**
   * When the main activity has resumed. Fragments interaction can freely occur here.
   */
  override fun onResume() {
    super.onResume()
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

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    val id = item.itemId
    // Working with Fragments
    if (findViewById<FrameLayout>(R.id.fragment_container) == null) return true
    val frag: FragmentTransaction = supportFragmentManager.beginTransaction()
    when (id) {
      // Navigate to the various fragment
      R.id.nav_pokemon -> {
        val pokemonRVFragment: PokemonRVFragment = PokemonRVFragment()
        pokemonRVFragment.arguments = intent.extras
        frag.replace(R.id.fragment_container, pokemonRVFragment)
      }
      R.id.nav_moves -> { // handle gallery action
        val pokeMovesFragment: PokeMovesFragment = PokeMovesFragment()
        pokeMovesFragment.arguments = intent.extras
        frag.replace(R.id.fragment_container, pokeMovesFragment)
      }
      R.id.nav_types -> {

      }
      R.id.nav_abilities -> {

      }
      R.id.nav_natures -> {

      }
      // Items
      R.id.nav_items -> {

      }
      R.id.nav_medicine -> {

      }
      R.id.nav_tms_hms -> {

      }
      R.id.nav_berries -> {

      }
      R.id.nav_key_items -> {

      }
      // Settings
      R.id.nav_settings -> {

      }
      R.id.nav_help -> {

      }
      R.id.nav_changelog -> {

      }
      R.id.nav_about -> {

      }
    }
    val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
    drawer.closeDrawer(GravityCompat.START) // closes the drawer
    frag.commit()
    return true
  }

  /**
   * Initialize the main pokemon tab fragment
   */
  fun initializeFragmentLayout(savedInstanceState: Bundle?) {
    if (findViewById<FrameLayout>(R.id.fragment_container) == null) return // check the activity is using framelayout
    if (savedInstanceState != null) return // do not init fragment if previous state exits
    val pokemonRVFragment: PokemonRVFragment = PokemonRVFragment()
    pokemonRVFragment.arguments = intent.extras // pass activity intents as argument
    supportFragmentManager.beginTransaction().add(R.id.fragment_container, pokemonRVFragment).commit()
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
