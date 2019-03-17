package komyakov.tfs19s04.di

import komyakov.tfs19s04.dto.NewsItem
import komyakov.tfs19s04.utils.formatFromServer
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*
import kotlin.random.Random

interface DataManager {
    fun loadAll(): List<NewsItem>
    fun loadFavorite(): List<NewsItem>
    fun getItemById(id: Int): NewsItem
}

class DataManagerImpl : DataManager {
    override fun loadAll(): List<NewsItem> {
        val list = ArrayList<NewsItem>()
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        (1..45).forEach {

            list.add(
                NewsItem(
                    "Lorem ipsum dolor sit amet",
                    "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident",
                    now.minusDays(Random.nextLong(0, 31)).format(formatFromServer),
                    false
                )
            )
        }

        return list
    }

    override fun loadFavorite(): List<NewsItem> {
        //ну тут что-то, можно fav проставить ещё true
        return loadAll().take(5)
    }

    override fun getItemById(id: Int): NewsItem {

        return NewsItem(
            "Lorem ipsum dolor sit amet",
            "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident",
            "2019-03-09",
            false
        )
    }

}