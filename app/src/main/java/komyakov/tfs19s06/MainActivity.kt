package komyakov.tfs19s06

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s06.base.baselist.IBaseFragmentListItemCallback
import komyakov.tfs19s06.base.baselist.IBaseListItemModel
import komyakov.tfs19s06.di.DataManager
import komyakov.tfs19s06.news.NewsItemFragment
import komyakov.tfs19s06.tabs.CommonListItemModel

class MainActivity : AppCompatActivity(), NewsItemFragment.Callback, IBaseFragmentListItemCallback {

    private val component: DataManager by lazy {
        (application as App).component
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), false)
            return
        }
    }

    override fun listItemCLicked(listItem: IBaseListItemModel) {
        replaceFragment(NewsItemFragment.newInstance(listItem as CommonListItemModel), true)
    }

    override fun favoriteClicked(id: String, value: Boolean) {

        if (value) {
            //почему обёрнуто?
            //добавим .delay(60, TimeUnit.SECONDS) - как будто запрос выполняется долго
            //(на самом деле это надо добавить перед удалением, в Dao)
            //в итоге имеем долгий запрос, на который подписались тут и ещё взяли this для тоста
            //нажмем дизлайк и покрутим устройство, понажимаем назад, включая выход- течём
            compositeDisposable.add(
                component.markFavorite(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { Toast.makeText(this, R.string.news_like, Toast.LENGTH_SHORT).show() }
            )
            return
        }

        compositeDisposable.add(
            component.unmarkFavorite(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Toast.makeText(this, R.string.news_dislike, Toast.LENGTH_SHORT).show() }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                fragment
            )

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
