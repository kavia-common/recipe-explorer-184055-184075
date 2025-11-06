package org.example.app.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.app.AppContainer
import org.example.app.R
import org.example.app.ui.common.StateView
import org.example.app.ui.recipe.DetailActivity

class HomeFragment : Fragment() {

    private val repo get() = AppContainer.repository

    private lateinit var adapter: RecipeListAdapter
    private var searchJob: Job? = null

    private lateinit var stateView: StateView
    private lateinit var recycler: RecyclerView
    private lateinit var searchInput: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stateView = view.findViewById(R.id.state_view)
        recycler = view.findViewById(R.id.recycler)
        searchInput = view.findViewById(R.id.search_input)

        adapter = RecipeListAdapter { recipe ->
            startActivity(DetailActivity.intent(requireContext(), recipe.id))
        }
        recycler.adapter = adapter
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    kotlinx.coroutines.delay(250)
                    loadData(s?.toString().orEmpty())
                }
            }
        })

        loadData("")
    }

    private fun loadData(query: String) {
        stateView.displayLoading()
        viewLifecycleOwner.lifecycleScope.launch {
            repo.searchRecipes(query).collect { list ->
                if (list.isEmpty()) {
                    stateView.displayEmpty("No recipes found")
                    adapter.submitList(emptyList())
                } else {
                    stateView.displayContent()
                    adapter.submitList(list)
                }
            }
        }
    }
}
