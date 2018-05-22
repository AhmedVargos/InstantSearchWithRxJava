package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.RestrictTo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.DataSource;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchItem;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;


public class HomeViewModel extends ViewModel{

    final BehaviorSubject<List<Movie>> movieList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<Boolean> loading = BehaviorSubject.createDefault(false);
    final BehaviorSubject<List<SearchItem>> searchHistoryList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<String> searchMovies = BehaviorSubject.createDefault("");

    private final CompositeDisposable disposables = new CompositeDisposable();
    private  DataSource dataSource;
    private final Scheduler scheduler;

    @SuppressLint("RestrictedApi")
    public HomeViewModel(){
        this(new DataSource(), Schedulers.computation());
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    HomeViewModel(DataSource dataSource, Scheduler computation) {
        this.dataSource = dataSource;
        this.scheduler = computation;
    }

    public Disposable startRequestMovies() {
        loading.onNext(true);

        return (dataSource.getMoviesResult(""))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnError(Throwable::printStackTrace)
                .doFinally(()->loading.onNext(false))
                .subscribe(movieList::onNext);

        

    }
   /* public  Disposable getHistorySearchList(){
        return Observable.fromCallable()
    }*/
    @Override
    protected void onCleared() {
        disposables.clear();
        movieList.onComplete();
        searchMovies.onComplete();
        loading.onComplete();
        searchHistoryList.onComplete();
        super.onCleared();
    }
  /*  private Disposable startRequestMovies() {

        return Observable.just(movieInput.getValue())
                .observeOn(scheduler)
                .filter(text -> !"".equals(text))
                .filter(ignoredValue -> !loading.getValue())
                .doOnNext(ignoredValue -> loading.onNext(true))
                .flatMapSingle(dataRepository::getMoviesResult)
                .doOnError(Throwable::printStackTrace)
                .onErrorReturnItem(new ArrayList<>())
                .doFinally(() ->loading.onNext(false))
                .subscribe(movies::onNext);
    }*/
}
