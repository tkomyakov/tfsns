package komyakov.tfs19s07.dataProviders

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import komyakov.tfs19s07.dto.ConsolidatedNewsItem
import komyakov.tfs19s07.dto.NewsItem

interface IDataProvider {

    fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>>
    fun loadAllNewsRaw(): Single<List<NewsItem>> {
        throw UnsupportedOperationException("Raw not supported by provider")
    }
    fun loadFavoriteNews(): Flowable<List<ConsolidatedNewsItem>>
    fun insertNews(newsList: List<NewsItem>): Completable
    fun markFavorite(id: String): Completable
    fun unmarkFavorite(id: String): Completable
}