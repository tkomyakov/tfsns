package komyakov.tfs19s04

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import komyakov.tfs19s04.di.AppComponent
import komyakov.tfs19s04.di.DataManager
import komyakov.tfs19s04.di.DataManagerImpl

class ComponentImpl : AppComponent,
    DataManager by DataManagerImpl()


class App : Application() {
    val component: AppComponent by lazy { ComponentImpl() }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}