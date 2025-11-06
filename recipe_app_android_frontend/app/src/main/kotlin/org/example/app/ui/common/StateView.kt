package org.example.app.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * StateView is a small helper to present loading/empty/content states consistently.
 */
class StateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val contentContainer: FrameLayout
    private val progress: ProgressBar
    private val emptyText: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_state, this, true)
        progress = findViewById(R.id.progress)
        emptyText = findViewById(R.id.empty_text)
        contentContainer = findViewById(R.id.content_container)
    }

    fun displayLoading() {
        progress.visibility = VISIBLE
        emptyText.visibility = GONE
        contentContainer.visibility = GONE
    }

    fun displayEmpty(message: String) {
        progress.visibility = GONE
        emptyText.visibility = VISIBLE
        emptyText.text = message
        contentContainer.visibility = GONE
    }

    fun displayContent() {
        progress.visibility = GONE
        emptyText.visibility = GONE
        contentContainer.visibility = VISIBLE
    }

    // PUBLIC_INTERFACE
    fun contentContainer(): FrameLayout = contentContainer
}
