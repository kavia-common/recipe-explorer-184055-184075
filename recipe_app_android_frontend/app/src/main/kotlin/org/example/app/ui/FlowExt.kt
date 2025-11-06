package org.example.app.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * PUBLIC_INTERFACE
 * Collects a Flow<T> in a lifecycle-aware manner tied to the given owner.
 */
fun <T> Flow<T>.collectLatestIn(owner: LifecycleOwner, minActiveState: Lifecycle.State = Lifecycle.State.STARTED, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(minActiveState) {
            collect { block(it) }
        }
    }
}
