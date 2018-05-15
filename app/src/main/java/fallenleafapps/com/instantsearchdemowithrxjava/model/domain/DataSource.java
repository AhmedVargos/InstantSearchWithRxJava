package fallenleafapps.com.instantsearchdemowithrxjava.model.domain;

import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.networkApi.ApiServiceImp;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchResponseModule;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataSource {
    private ApiServiceImp apiServiceImp;

    public DataSource(){
        apiServiceImp = new ApiServiceImp();
    }

    public Single<List<Movie>> getMoviesResult(String query){
        if (query.isEmpty()){
            return apiServiceImp.getAllPopularMovies()
                    .observeOn(Schedulers.io())
                    .map(SearchResponseModule::getMovies);
        }else {
            return apiServiceImp.queryMovies(query)
                    .observeOn(Schedulers.io())
                    .map(SearchResponseModule::getMovies);
        }

    }
}
