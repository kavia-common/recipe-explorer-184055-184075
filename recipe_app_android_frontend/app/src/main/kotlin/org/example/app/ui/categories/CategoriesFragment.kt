package org.example.app.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import org.example.app.AppContainer
import org.example.app.R
import org.example.app.data.model.Categories
import org.example.app.ui.common.StateView
import org.example.app.ui.home.RecipeListAdapter
import org.example.app.ui.recipe.DetailActivity

class CategoriesFragment : Fragment() {

    private val repo get() = AppContainer.repository
    private lateinit var adapter: RecipeListAdapter
    private var selectedCategory: String = ""

    private lateinit var stateView: StateView
    private lateinit var chipGroup: ChipGroup
    private lateinit var recycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stateView = view.findViewById(R.id.state_view)
        chipGroup = view.findViewById(R.id.categories_chip_group)
        recycler = view.findViewById(R.id.recipes_recycler)

        adapter = RecipeListAdapter { recipe ->
            startActivity(DetailActivity.intent(requireContext(), recipe.id))
        }
        recycler.adapter = adapter
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        val chips = Categories.all
        chipGroup.removeAllViews()
        for (cat in chips) {
            val chip = layoutInflater.inflate(
                R.layout.view_filter_chip, chipGroup, false
            ) as Chip
            chip.text = cat
            chip.isCheckable = true
            chip.setOnClickListener {
                selectedCategory = cat
                loadCategory(cat)
            }
            chipGroup.addView(chip)
        }

        loadCategory("")
    }

    private fun loadCategory(category: String) {
        stateView.displayLoading()
        viewLifecycleOwner.lifecycleScope.launch {
            repo.getRecipesByCategory(category).collect { list ->
                if (list.isEmpty()) {
                    stateView.displayEmpty("No recipes in $category")
                    adapter.submitList(emptyList())
                } else {
                    stateView.displayContent()
                    adapter.submitList(list)
                }
            }
        }
    }
}
