package org.example.app.ui.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.launch
import org.example.app.AppContainer
import org.example.app.R
import org.example.app.ui.common.StateView
import com.google.android.material.appbar.MaterialToolbar

/**
 * PUBLIC_INTERFACE
 * Displays a single recipe with image, ingredients, steps and a favorite toggle.
 */
class DetailActivity : AppCompatActivity() {

    private val repo get() = AppContainer.repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val stateView = findViewById<StateView>(R.id.state_view)
        val image = findViewById<ImageView>(R.id.image)
        val title = findViewById<TextView>(R.id.title)
        val description = findViewById<TextView>(R.id.description)
        val category = findViewById<TextView>(R.id.category)
        val ingredients = findViewById<TextView>(R.id.ingredients)
        val steps = findViewById<TextView>(R.id.steps)
        val favToggle = findViewById<ImageButton>(R.id.fav_toggle)

        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        if (id.isBlank()) {
            finish()
            return
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        lifecycleScope.launch {
            repo.getRecipeById(id).collect { recipe ->
                if (recipe == null) {
                    stateView.displayEmpty("Recipe not found")
                } else {
                    stateView.displayContent()
                    image.load(recipe.imageUrl)
                    title.text = recipe.title
                    description.text = recipe.description
                    category.text = recipe.category

                    ingredients.text = recipe.ingredients.joinToString("\n") { "• ${it.quantity} ${it.name}" }
                    steps.text = recipe.steps.joinToString("\n") { "${it.index}. ${it.instruction}" }

                    lifecycleScope.launch {
                        repo.isFavorite(recipe.id).collect { fav ->
                            favToggle.setImageResource(
                                if (fav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                            )
                        }
                    }

                    favToggle.setOnClickListener {
                        lifecycleScope.launch {
                            repo.toggleFavorite(recipe)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_ID = "extra_id"

        // PUBLIC_INTERFACE
        fun intent(context: Context, id: String): Intent =
            Intent(context, DetailActivity::class.java).putExtra(EXTRA_ID, id)
    }
}
