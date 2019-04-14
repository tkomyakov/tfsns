package komyakov.tfs19s07.di

import io.reactivex.Completable
import io.reactivex.Flowable
import komyakov.tfs19s07.dataProviders.IDataProvider
import komyakov.tfs19s07.dto.ConsolidatedNewsItem
import java.util.concurrent.TimeUnit

interface DataManager {
    fun loadAll(): Flowable<List<ConsolidatedNewsItem>>
    fun loadFavorite(): Flowable<List<ConsolidatedNewsItem>>
    fun markFavorite(id: String): Completable
    fun unmarkFavorite(id: String): Completable
}

class DataManagerImpl(private val dataProvider: IDataProvider) : DataManager {

    override fun unmarkFavorite(id: String): Completable {
        return dataProvider.unmarkFavorite(id)
    }

    override fun markFavorite(id: String): Completable {
        return dataProvider.markFavorite(id)
    }

    override fun loadAll(): Flowable<List<ConsolidatedNewsItem>> {
        return dataProvider.loadAllNews()
            //TODO: remove - долгий запрос
            .delay(5, TimeUnit.SECONDS)
    }

    override fun loadFavorite(): Flowable<List<ConsolidatedNewsItem>> {
        return dataProvider.loadFavoriteNews()
            //TODO: remove - долгий запрос
            .delay(5, TimeUnit.SECONDS)
    }

}