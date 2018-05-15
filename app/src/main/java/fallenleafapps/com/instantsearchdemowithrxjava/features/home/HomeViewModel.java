package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.annotation.SuppressLint;
import android.support.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.DataSource;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.SearchItem;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class HomeViewModel {

    final BehaviorSubject<List<Movie>> movieList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<Boolean> loading = BehaviorSubject.createDefault(false);
    final BehaviorSubject<List<SearchItem>> searchHistoryList = BehaviorSubject.createDefault(new ArrayList<>());
    final BehaviorSubject<String> searchInput = BehaviorSubject.createDefault("");

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final DataSource dataSource;
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
}
