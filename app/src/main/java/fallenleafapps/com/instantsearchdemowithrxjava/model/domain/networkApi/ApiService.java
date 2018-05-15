package fallenleafapps.com.instantsearchdemowithrxjava.model.domain.networkApi;

import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchResponseModule;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/movie")
    Single<SearchResponseModule> getSearchResults(@Query("api_key") String key, @Query("language") String lang, @Query("query") String query);

    @GET("discover/movie")
    Single<SearchResponseModule> getAllPopular(@Query("api_key") String key, @Query("language") String lang, @Query("sort_by") String sortBy);
}
