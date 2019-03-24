package komyakov.tfs19s05

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import komyakov.tfs19s05.dataProviders.db.NewsDatabase
import komyakov.tfs19s05.dataProviders.mock.NewsItemMock
import komyakov.tfs19s05.di.AppComponent
import komyakov.tfs19s05.di.DataManager
import komyakov.tfs19s05.di.DataManagerImpl


class ComponentImpl(context: Context) : AppComponent,
    DataManager by DataManagerImpl(NewsDatabase.getInstance(context, NewsItemMock.getInstance()))

class App : Application() {
    val component: AppComponent by lazy { ComponentImpl(this) }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        initializeLeakDetection(this)
    }

    private fun initializeLeakDetection(appContext: Application) {
        if (BuildConfig.DEBUG) LeakCanary.install(appContext)
    }
}