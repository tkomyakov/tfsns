package komyakov.tfs19s07

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import komyakov.tfs19s07.data.Repo
import komyakov.tfs19s07.data.db.NewsDatabase
import komyakov.tfs19s07.data.network.TinkoffClient


class App : Application() {
    companion object {
        lateinit var repo: Repo
    }

    override fun onCreate() {
        super.onCreate()
        //Stetho.initializeWithDefaults(this);
        repo = Repo(TinkoffClient, NewsDatabase.getInstance(this))
        AndroidThreeTen.init(this)
        initializeLeakDetection(this)
    }

    private fun initializeLeakDetection(appContext: Application) {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(appContext)
        }
    }
}