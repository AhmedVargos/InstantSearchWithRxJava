package fallenleafapps.com.instantsearchdemowithrxjava.model.domain;

import android.content.ComponentName;
import android.content.Context;

import java.util.List;
import java.util.Observable;

import fallenleafapps.com.instantsearchdemowithrxjava.Application;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database.MovieRoomDatabase;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database.MovieSuggestionDao;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.networkApi.ApiServiceImp;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchResponseModule;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataSource {
    private ApiServiceImp apiServiceImp;
    Context context;
    public DataSource(){
        apiServiceImp = new ApiServiceImp();
        context= Application.getContext();
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
    public Flowable<List<MovieSuggestion>> getSuggestionFromDB(String key){
        return  MovieRoomDatabase.getDatabase(context)
                .getMovieSuggestions()
                .query("%" + key+ "%");
    }
    public void addNewSuggestion(String suggestion){

        MovieRoomDatabase.getDatabase(context)
                .getMovieSuggestions()
                .insert(new MovieSuggestion(String.valueOf(Math.random()),suggestion));
    }
}
