package komyakov.tfs19s07.data

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import komyakov.tfs19s07.data.db.NewsDatabase
import komyakov.tfs19s07.data.network.TinkoffClient
import komyakov.tfs19s07.dto.NewsHeader

class Repo(
    private val api: TinkoffClient,
    private val db: NewsDatabase
) {
    fun loadAll(): Flowable<List<NewsHeader>> {
        api.loadNewsHeaders()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { db.insertHeaders(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})

        return db.loadNewsHeaders()
    }

    fun loadFavorite(): Flowable<List<NewsHeader>> {
        return db.loadFavoriteHeaders()
    }

    fun markFavorite(id: String): Completable {
        return db.markFavorite(id)
    }

    fun unmarkFavorite(id: String): Completable {
        return db.unmarkFavorite(id)
    }

}