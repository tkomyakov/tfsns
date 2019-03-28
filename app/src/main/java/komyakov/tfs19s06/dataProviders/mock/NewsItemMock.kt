package komyakov.tfs19s06.dataProviders.mock

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import komyakov.tfs19s06.dataProviders.IDataProvider
import komyakov.tfs19s06.dto.ConsolidatedNewsItem
import komyakov.tfs19s06.dto.NewsItem
import komyakov.tfs19s06.utils.formatFromServer
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import kotlin.random.Random

class NewsItemMock : IDataProvider {
    override fun markFavorite(id: String): Completable {
        throw UnsupportedOperationException("Readonly provider")
    }

    override fun unmarkFavorite(id: String): Completable {
        throw UnsupportedOperationException("Readonly provider")
    }

    override fun loadFavoriteNews(): Flowable<List<ConsolidatedNewsItem>> {
        return Flowable.just(emptyList())
    }

    override fun insertNews(newsList: List<NewsItem>): Completable {
        throw UnsupportedOperationException("Readonly provider")
    }

    override fun loadAllNews(): Flowable<List<ConsolidatedNewsItem>> {
        throw UnsupportedOperationException("Readonly provider")
    }

    override fun loadAllNewsRaw(): Single<List<NewsItem>> {
        return Single.fromCallable {
            val list = ArrayList<NewsItem>()
            val now = ZonedDateTime.now(ZoneId.systemDefault())
            (1..45).forEach {
                list.add(
                    NewsItem(
                        id = it.toString(),
                        title = "Lorem ipsum dolor sit amet",
                        description = "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident",
                        timestamp = now.minusDays(Random.nextLong(0, 31)).format(formatFromServer)
                    )
                )
            }

            list.sortedByDescending { it.timestamp }.toList()
        }
    }

    companion object {
        fun getInstance(): NewsItemMock {
            return NewsItemMock()
        }
    }
}