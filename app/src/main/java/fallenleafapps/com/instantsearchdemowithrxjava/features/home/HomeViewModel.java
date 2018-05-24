package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.Application;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.DataSource;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database.MovieRoomDatabase;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;


public class HomeViewModel extends ViewModel {

    final BehaviorSubject<List<Movie>> movieList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<Boolean> loading = BehaviorSubject.createDefault(false);
    final BehaviorSubject<List<MovieSuggestion>> searchHistoryList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<String> searchMovies = BehaviorSubject.createDefault("");

    private final CompositeDisposable disposables = new CompositeDisposable();
    private DataSource dataSource;
    private final Scheduler scheduler;
    Context context;

    @SuppressLint("RestrictedApi")
    public HomeViewModel()
    {
        this(Application.getContext(), new DataSource(), Schedulers.computation());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    HomeViewModel(Context context, DataSource dataSource, Scheduler computation)
    {
        this.context = context;
        this.dataSource = dataSource;
        this.scheduler = computation;
        disposables.add(searchMovies.share()
            .subscribe(ignoredValue -> getHistorySearch()));
        startRequestMovies();
    }

    public void startRequestMovies()
    {
        disposables.add(requestMoviesFromDataSource());
    }

    private Disposable requestMoviesFromDataSource()
    {
        loading.onNext(true);

        return (dataSource.getMoviesResult(searchMovies.getValue()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnError(Throwable::printStackTrace)
                .doFinally(() -> loading.onNext(false))
                .subscribe(movieList::onNext);

    }

    public void getHistorySearch()
    {
        disposables.add(getHistorySearchFromDataSource());
    }

    private Disposable getHistorySearchFromDataSource()
    {
        if (!searchMovies.getValue().equals(""))
            return getSpecificSuggestions();
        else {
            return getAllSuggestions();
        }
    }

    private Disposable getAllSuggestions()
    {
        return MovieRoomDatabase.getDatabase(context)
                .getMovieSuggestions()
                .getAllSuggestions()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(searchHistoryList::onNext);

    }

    private Disposable getSpecificSuggestions()
    {
        return MovieRoomDatabase.getDatabase(context)
                .getMovieSuggestions()
                .query(searchMovies.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(searchHistoryList::onNext);
    }

    public void insertSearchResult()
    {
        disposables.add(insertSearchResultToDB());
    }

    private Disposable insertSearchResultToDB()
    {
        return Observable.just(searchMovies.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .filter(val -> !val.isEmpty())
                .doFinally(() -> searchMovies.onNext(""))
                .subscribe(this::makeNewSuggestion);

        //Cannot Return null because disposable does not take nulls
        /*
        if (!searchMovies.getValue().equals("")) {


        }else {
            //return Disposable;
        }
        return null;
        */
    }

    private void makeNewSuggestion(String word){
        MovieRoomDatabase.getDatabase(context)
                .getMovieSuggestions()
                .insert(new MovieSuggestion(String.valueOf(Math.random()),word));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        movieList.onComplete();
        searchMovies.onComplete();
        loading.onComplete();
        searchHistoryList.onComplete();
        super.onCleared();
    }
}
