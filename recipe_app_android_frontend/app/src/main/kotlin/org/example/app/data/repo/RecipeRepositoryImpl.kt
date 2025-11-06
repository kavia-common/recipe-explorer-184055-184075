package org.example.app.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.example.app.data.local.FavoritesStore
import org.example.app.data.model.Categories
import org.example.app.data.model.Ingredient
import org.example.app.data.model.Recipe
import org.example.app.data.model.Step
import java.util.UUID

class RecipeRepositoryImpl(
    private val favoritesStore: FavoritesStore
) : RecipeRepository {

    // Mock dataset
    private val mockRecipes: List<Recipe> = buildMock()

    override fun getAllRecipes(): Flow<List<Recipe>> = flow {
        emit(mockRecipes)
    }.flowOn(Dispatchers.Default)

    override fun searchRecipes(query: String): Flow<List<Recipe>> = flow {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            emit(mockRecipes)
        } else {
            emit(
                mockRecipes.filter {
                    it.title.lowercase().contains(q) ||
                            it.description.lowercase().contains(q) ||
                            it.ingredients.any { ing -> ing.name.lowercase().contains(q) }
                }
            )
        }
    }.flowOn(Dispatchers.Default)

    override fun getRecipesByCategory(category: String): Flow<List<Recipe>> = flow {
        if (category.isBlank()) {
            emit(mockRecipes)
        } else {
            emit(mockRecipes.filter { it.category == category })
        }
    }.flowOn(Dispatchers.Default)

    override fun getRecipeById(id: String): Flow<Recipe?> = flow {
        emit(mockRecipes.firstOrNull { it.id == id })
    }.flowOn(Dispatchers.Default)

    override fun getFavorites(): Flow<List<Recipe>> =
        favoritesStore.favorites().map { ids ->
            mockRecipes.filter { it.id in ids }
        }

    override fun isFavorite(id: String): Flow<Boolean> = favoritesStore.isFavorite(id)

    override suspend fun toggleFavorite(recipe: Recipe) {
        favoritesStore.toggle(recipe)
    }

    private fun buildMock(): List<Recipe> {
        val imgs = listOf(
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=1200&auto=format&fit=crop",
            "https://images.unsplash.com/photo-1498579150354-977475b7ea0b?q=80&w=1200&auto=format&fit=crop",
            "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?q=80&w=1200&auto=format&fit=crop",
            "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?q=80&w=1200&auto=format&fit=crop"
        )
        val list = mutableListOf<Recipe>()
        val cats = Categories.all
        repeat(20) { idx ->
            val id = UUID.randomUUID().toString()
            val cat = cats[idx % cats.size]
            list.add(
                Recipe(
                    id = id,
                    title = "Recipe ${idx + 1}",
                    description = "A delicious $cat dish with modern flavors and a clean presentation.",
                    category = cat,
                    imageUrl = imgs[idx % imgs.size],
                    ingredients = listOf(
                        Ingredient("Ingredient A", "1 cup"),
                        Ingredient("Ingredient B", "2 tbsp"),
                        Ingredient("Salt", "to taste")
                    ),
                    steps = listOf(
                        Step(1, "Prepare all the ingredients."),
                        Step(2, "Cook over medium heat until fragrant."),
                        Step(3, "Plate and serve with garnish.")
                    )
                )
            )
        }
        return list
    }
}
