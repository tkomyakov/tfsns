package komyakov.tfs19s07

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import komyakov.tfs19s07.dataProviders.db.NewsDatabase
import komyakov.tfs19s07.dataProviders.mock.NewsItemMock
import komyakov.tfs19s07.di.AppComponent
import komyakov.tfs19s07.di.DataManager
import komyakov.tfs19s07.di.DataManagerImpl


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
        if (BuildConfig.DEBUG) {
            LeakCanary.install(appContext)
        }
    }
}