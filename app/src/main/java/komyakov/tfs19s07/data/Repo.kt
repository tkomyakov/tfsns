package komyakov.tfs19s07.data

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import komyakov.tfs19s07.data.db.NewsDatabase
import komyakov.tfs19s07.data.network.TinkoffClient
import komyakov.tfs19s07.dto.Article
import komyakov.tfs19s07.dto.NewsHeader

class Repo(
    private val api: TinkoffClient,
    private val db: NewsDatabase
) {
    fun loadAll(): Flowable<List<NewsHeader>> {
        return Flowable.mergeDelayError(
            api.loadNewsHeaders()
                .map {
                    it.sortedByDescending { elm -> elm.publicationDate.mills }
                        .take(100)
                }
                .onErrorReturnItem(emptyList())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        db.insertHeaders(it)
                    }
                },
            db.loadNewsHeaders()
        )
    }

    fun loadFavorite(): Flowable<List<NewsHeader>> {
        return db.loadFavoriteHeaders()
    }

    fun loadArticle(id: String): Single<Article> {
        return db.loadArticle(id)
            .onErrorResumeNext {
                api.loaArticle(id)
                    .doOnSuccess { article ->
                        db.insertArticle(Article(id, article.text))
                    }
            }
    }

    fun markFavorite(id: String): Completable {
        return db.markFavorite(id)
    }

    fun unmarkFavorite(id: String): Completable {
        return db.unmarkFavorite(id)
    }

    fun getFavoriteStatus(newsItemId: String): Single<Boolean> {
        return db.getFavoriteStatus(newsItemId)
    }

}