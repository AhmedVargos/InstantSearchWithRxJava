package fallenleafapps.com.instantsearchdemowithrxjava.home.test.Mock;

import android.arch.persistence.room.Index;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.DataSource;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database.MovieRoomDatabase;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database.MovieSuggestionDao;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchResponseModule;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hanaa on 6/3/2018.
 */

public class DataSourceGenerator extends DataSource {
    boolean error;
    public DataSourceGenerator(Boolean error){
        this.error=error;
    }

    @Override
    public Single<List<Movie>> getMoviesResult(String query){
        if (error) {
            return Single.error(new UnsupportedOperationException("---error---"));
        }


        List<Movie> movies=generateMovies();
        if(query.isEmpty()) {
            return Single.just(movies);
            /* to convert observable to single
            singleOrError
                firstOrError
                lastOrError
                elementAtOrError
             */
        }else {

         return Observable.fromIterable(movies)
                 .filter(movies1 -> movies1.getTitle().contains(query))
                 .toList()
                 .doFinally(()->{});

        }
    }
    @Override
    public Flowable<List<MovieSuggestion>> getSuggestionFromDB(String key){
        if (error) {
            return Flowable.error(new UnsupportedOperationException("---error---"));
        }
        List<MovieSuggestion>movieSuggestions=generateMovieSuggestion();
       /* List<MovieSuggestion>acceptedMovieSuggestion=new ArrayList<>();
        movieSuggestions.forEach((movie)->{if(movie.getWord().equals(key))
                                            acceptedMovieSuggestion.add(movie);
        });*/

        return  Flowable.fromIterable(movieSuggestions)
                        .filter((movieSuggestions1 -> movieSuggestions1.getWord().contains(key)))
                .toList().toFlowable();
    }
    @Override
    public void addNewSuggestion(String suggestion){


    }
    private List<MovieSuggestion> generateMovieSuggestion(){
        List<MovieSuggestion> movieSuggestions=new ArrayList<>();
        MovieSuggestion movieSuggestion=new MovieSuggestion("0","movie1");
        movieSuggestions.add(movieSuggestion);
        movieSuggestion=new MovieSuggestion("1","movie2");
        movieSuggestions.add(movieSuggestion);
        movieSuggestion=new MovieSuggestion("2","g");
        movieSuggestions.add(movieSuggestion);
        movieSuggestion=new MovieSuggestion("3","noOne");
        movieSuggestions.add(movieSuggestion);
        return movieSuggestions;
    }
    private List<Movie> generateMovies(){
        List<Movie> movies=new ArrayList<>();
        Movie movie=new Movie(1,2,true,1.2,"string1",5.6,"path","string","string",generateListOfInt(),"object",true,"string","15-9-2001");
        movies.add(movie);
        movie=new Movie(1,2,true,1.2,"string1",5.6,"path","string","string",generateListOfInt(),"object",true,"string","15-9-2001");
        movies.add(movie);
        movie=new Movie(1,2,true,1.2,"string1",5.6,"path","string","string",generateListOfInt(),"object",true,"string","15-9-2001");
        movies.add(movie);
        movie=new Movie(1,2,true,1.2,"string1",5.6,"path","string","string",generateListOfInt(),"object",true,"string","15-9-2001");
        movies.add(movie);
        return movies;
    }
    private List<Integer>generateListOfInt(){
        ArrayList<Integer> integers=new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        return integers;
    }
}
