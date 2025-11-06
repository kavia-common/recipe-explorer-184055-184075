package org.example.app.data.repo

import kotlinx.coroutines.flow.Flow
import org.example.app.data.model.Recipe

/**
 * PUBLIC_INTERFACE
 * Data access interface for recipes. Implementations may use
 * remote REST or local data sources. This version uses mock data
 * plus Room for favorites persistence.
 */
interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun searchRecipes(query: String): Flow<List<Recipe>>
    fun getRecipesByCategory(category: String): Flow<List<Recipe>>
    fun getRecipeById(id: String): Flow<Recipe?>
    fun getFavorites(): Flow<List<Recipe>>
    fun isFavorite(id: String): Flow<Boolean>
    suspend fun toggleFavorite(recipe: Recipe)
}
