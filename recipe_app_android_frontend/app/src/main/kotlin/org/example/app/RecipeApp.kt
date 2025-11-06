package org.example.app

import android.app.Application

/**
 * PUBLIC_INTERFACE
 * RecipeApp is the Application class used to initialize singletons such as
 * the database and repository at app startup.
 */
class RecipeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(this)
    }
}
