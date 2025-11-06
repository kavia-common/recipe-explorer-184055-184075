package org.example.app.data.model

/**
 * Recipe domain model used across UI and data layers.
 */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>
)

/**
 * Ingredient model for a recipe.
 */
data class Ingredient(
    val name: String,
    val quantity: String
)

/**
 * Step model for a recipe instruction step.
 */
data class Step(
    val index: Int,
    val instruction: String
)

/**
 * PUBLIC_INTERFACE
 * Category enumeration. Using simple strings allows easy REST mapping later.
 */
object Categories {
    val all = listOf(
        "Breakfast",
        "Lunch",
        "Dinner",
        "Dessert",
        "Vegetarian",
        "Vegan",
        "Seafood",
        "Poultry",
        "Beef",
        "Pasta"
    )
}
