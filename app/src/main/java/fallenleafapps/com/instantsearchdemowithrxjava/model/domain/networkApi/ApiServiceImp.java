package fallenleafapps.com.instantsearchdemowithrxjava.model.domain.networkApi;

import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchResponseModule;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ApiServiceImp {
    private static final String KEY = "30b6cd3d9e4cad94e10055d0eb5cdbcc";
    private static final String LANG = "en-US";
    private static final String SORT_TYPE = "popularity.desc";
    private  ApiService apiService;

    public ApiServiceImp() {
        apiService = ApiClient.getInstance().create(ApiService.class);
    }

    public Single<SearchResponseModule> queryMovies(String name){
        return apiService.getSearchResults(KEY,LANG, name)
                .subscribeOn(Schedulers.io());
    }

    public Single<SearchResponseModule> getAllPopularMovies(){
        return apiService.getAllPopular(KEY,LANG, SORT_TYPE)
                .subscribeOn(Schedulers.io());
    }

}
