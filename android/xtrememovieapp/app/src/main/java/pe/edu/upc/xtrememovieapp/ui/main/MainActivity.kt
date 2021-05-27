package pe.edu.upc.xtrememovieapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import pe.edu.upc.xtrememovieapp.R
import pe.edu.upc.xtrememovieapp.ui.favorites.FavoriteFragment
import pe.edu.upc.xtrememovieapp.ui.movies.MovieFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bnMain: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupListeners()
    }

    private fun setupListeners() {
        bnMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemMovie -> {
                    replaceFragment(MovieFragment())
                    true
                }
                R.id.itemFavorite -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                else -> true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fcContent, fragment)
        transaction.commit()
    }

    private fun setupViews() {
        bnMain = findViewById(R.id.bnMain)
    }
}