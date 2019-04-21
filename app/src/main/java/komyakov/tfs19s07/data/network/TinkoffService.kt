package komyakov.tfs19s07.data.network

import io.reactivex.Single
import komyakov.tfs19s07.dto.NewsHeader
import retrofit2.http.GET

public interface TinkoffService {

    @GET("v1/news")
    fun news(): Single<ApiResponse<List<NewsHeader>>>

}