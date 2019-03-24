package komyakov.tfs19s05.di

import io.reactivex.Flowable
import komyakov.tfs19s05.dataProviders.IDataProvider
import komyakov.tfs19s05.dto.ConsolidatedNewsItem

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