package com.thepascal.todolist.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.thepascal.todolist.ui.viewmodels.MainViewModel
import com.thepascal.todolist.R
import com.thepascal.todolist.ui.fragments.gallery.GalleryFragment
import com.thepascal.todolist.ui.fragments.home.HomeFragment
import com.thepascal.todolist.ui.fragments.slideshow.SlideshowFragment
import com.thepascal.todolist.ui.fragments.tasks.TasksFragment

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, ToDoActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState != null){
            mainViewModel.navDrawerDisplaySelection = MutableLiveData(savedInstanceState.getInt(mainViewModel.navDrawerDisplaySelectionName))
        }
        mainViewModel.navDrawerDisplaySelection.observe(this, Observer {
            handleDisplaySelection(it)
        })

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        /*val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        //handleDisplaySelection(mainViewModel.navDrawerDisplaySelection)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainViewModel.navDrawerDisplaySelection.value?.let {
            outState.putInt(mainViewModel.navDrawerDisplaySelectionName, it)
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment).commit()

        selectNavigationMenuItem(fragment)
    }

    //Let's ensure the right nav menu is selected when
    // menu is selected
    private fun selectNavigationMenuItem(fragment: Fragment) {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val menu = navigationView.menu
        //val id:Int = R.id.nav_home
        when (fragment) {
            is HomeFragment -> menu.findItem(R.id.nav_home).isChecked = true
            is GalleryFragment -> menu.findItem(R.id.nav_gallery).isChecked = true
            is SlideshowFragment -> menu.findItem(R.id.nav_slideshow).isChecked = true
            is TasksFragment -> menu.findItem(R.id.nav_tasks).isChecked = true
        }
    }

    private fun handleDisplaySelection(itemId: Int) {
        when (itemId) {
            R.id.nav_home -> {
                displayFragment(HomeFragment())
            }
            R.id.nav_gallery -> {
                displayFragment(GalleryFragment())
            }
            R.id.nav_slideshow -> {
                displayFragment(SlideshowFragment())
            }
            R.id.nav_tasks -> {
                displayFragment(TasksFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        handleDisplaySelection(item.itemId)
        mainViewModel.navDrawerDisplaySelection = MutableLiveData(item.itemId)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
