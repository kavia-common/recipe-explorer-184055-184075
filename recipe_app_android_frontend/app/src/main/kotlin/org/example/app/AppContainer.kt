package org.example.app

import android.content.Context
import org.example.app.data.local.FavoritesStore
import org.example.app.data.repo.RecipeRepository
import org.example.app.data.repo.RecipeRepositoryImpl

/**
 * Simple service locator for the app singletons.
 */
object AppContainer {
    lateinit var favoritesStore: FavoritesStore
        private set
    lateinit var repository: RecipeRepository
        private set

    fun initialize(context: Context) {
        if (!::repository.isInitialized) {
            favoritesStore = FavoritesStore(context.applicationContext)
            repository = RecipeRepositoryImpl(favoritesStore)
        }
    }
}
