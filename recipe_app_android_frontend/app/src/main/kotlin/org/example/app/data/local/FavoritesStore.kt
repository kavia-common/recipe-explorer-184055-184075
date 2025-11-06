package org.example.app.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.app.data.model.Recipe

/**
 * PUBLIC_INTERFACE
 * Simple K/V local store for favorite recipe IDs using SharedPreferences.
 * Suitable for environments without adding annotation processing.
 */
class FavoritesStore(context: Context) {

    private val prefs = context.getSharedPreferences("favorites_store", Context.MODE_PRIVATE)
    private val favoritesFlow = MutableStateFlow(currentIds())

    private fun currentIds(): Set<String> = prefs.getStringSet(KEY_IDS, emptySet()) ?: emptySet()

    fun favorites(): Flow<Set<String>> = favoritesFlow.asStateFlow()

    fun isFavorite(id: String): Flow<Boolean> =
        favoritesFlow.asStateFlow().mapToBoolean(id)

    fun toggle(recipe: Recipe) {
        val ids = currentIds().toMutableSet()
        if (ids.contains(recipe.id)) {
            ids.remove(recipe.id)
        } else {
            ids.add(recipe.id)
        }
        prefs.edit().putStringSet(KEY_IDS, ids).apply()
        favoritesFlow.value = ids
    }

    private fun Flow<Set<String>>.mapToBoolean(id: String): Flow<Boolean> =
        kotlinx.coroutines.flow.map { set -> set.contains(id) }

    companion object {
        private const val KEY_IDS = "ids"
    }
}
