package komyakov.tfs19s05.dataProviders

import io.reactivex.Flowable
import komyakov.tfs19s05.dto.ConsolidatedNewsItem
import komyakov.tfs19s05.dto.NewsItem

interface IDataProvider {

    fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>>
    fun loadAllNewsRaw(): Flowable<List<NewsItem>> {
        throw UnsupportedOperationException("Readonly provider")
    }
    fun loadFavoriteNews(): Flowable<List<ConsolidatedNewsItem>>
    fun insertNews(newsList: List<NewsItem>)
    fun markFavorite(id: String)
    fun unmarkFavorite(id: String)
}