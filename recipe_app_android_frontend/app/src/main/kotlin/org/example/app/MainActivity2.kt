package org.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.example.app.ui.categories.CategoriesFragment
import org.example.app.ui.favorites.FavoritesFragment
import org.example.app.ui.home.HomeFragment

/**
 * PUBLIC_INTERFACE
 * MainActivity2 is the entry point for the Recipe app. It hosts the bottom navigation
 * and swaps fragments for Home, Categories, and Favorites.
 */
class MainActivity2 : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val categoriesFragment = CategoriesFragment()
    private val favoritesFragment = FavoritesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as? RecipeApp) ?: run { /* ensures app class loaded */ }
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> switchFragment(homeFragment)
                R.id.menu_categories -> switchFragment(categoriesFragment)
                R.id.menu_favorites -> switchFragment(favoritesFragment)
                else -> false
            }
        }

        if (savedInstanceState == null) {
            switchFragment(homeFragment)
        }
    }

    private fun switchFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }
}
