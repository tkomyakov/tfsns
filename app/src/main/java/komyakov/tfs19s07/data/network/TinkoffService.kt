package komyakov.tfs19s07.data.network

import io.reactivex.Single
import komyakov.tfs19s07.dto.Article
import komyakov.tfs19s07.dto.NewsHeader
import retrofit2.http.GET
import retrofit2.http.Query

interface TinkoffService {

    @GET("v1/news")
    fun news(): Single<ApiResponse<List<NewsHeader>>>

    @GET("v1/news_content")
    fun article(
        @Query("id") id: String
    ): Single<ApiResponse<Article>>

}