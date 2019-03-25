package komyakov.tfs19s06.di

import io.reactivex.Flowable
import komyakov.tfs19s06.dataProviders.IDataProvider
import komyakov.tfs19s06.dto.ConsolidatedNewsItem

interface DataManager {
    fun loadAll(): Flowable<List<ConsolidatedNewsItem>>
    fun loadFavorite(): Flowable<List<ConsolidatedNewsItem>>
    fun markFavorite(id: String)
    fun unmarkFavorite(id: String)
}

class DataManagerImpl(private val dataProvider: IDataProvider) : DataManager {

    override fun unmarkFavorite(id: String) {
        dataProvider.unmarkFavorite(id)
    }

    override fun markFavorite(id: String) {
        dataProvider.markFavorite(id)
    }

    override fun loadAll(): Flowable<List<ConsolidatedNewsItem>> {
        return dataProvider.loadAllNews()
    }

    override fun loadFavorite(): Flowable<List<ConsolidatedNewsItem>> {
        return dataProvider.loadFavoriteNews()
    }

}