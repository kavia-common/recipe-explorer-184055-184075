package org.example.app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.example.app.AppContainer
import org.example.app.R
import org.example.app.ui.common.StateView
import org.example.app.ui.home.RecipeListAdapter
import org.example.app.ui.recipe.DetailActivity

class FavoritesFragment : Fragment() {

    private val repo get() = AppContainer.repository
    private lateinit var adapter: RecipeListAdapter

    private lateinit var stateView: StateView
    private lateinit var recycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stateView = view.findViewById(R.id.state_view)
        recycler = view.findViewById(R.id.recycler)

        adapter = RecipeListAdapter { recipe ->
            startActivity(DetailActivity.intent(requireContext(), recipe.id))
        }
        recycler.adapter = adapter
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        loadFavorites()
    }

    private fun loadFavorites() {
        stateView.displayLoading()
        viewLifecycleOwner.lifecycleScope.launch {
            repo.getFavorites().collect { list ->
                if (list.isEmpty()) {
                    stateView.displayEmpty("No favorites yet")
                    adapter.submitList(emptyList())
                } else {
                    stateView.displayContent()
                    adapter.submitList(list)
                }
            }
        }
    }
}
