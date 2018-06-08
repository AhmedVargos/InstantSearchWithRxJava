package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import fallenleafapps.com.instantsearchdemowithrxjava.R;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.input_search)
    AutoCompleteTextView inputSearch;
    @BindView(R.id.search_movies)
    AppCompatImageButton searchMovies;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_progress)
    ProgressBar searchProgress;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.startRequestMovies();
        disposables.add(bindSearchMovieTextView(viewModel.searchMovies));
        disposables.add(bindAutoCompleteHistory(viewModel.searchHistoryList));
        disposables.add(bindProgressView(viewModel.loading));
        disposables.add(bindRecyclerView(viewModel.movieList));
        //Need to make the click work
        disposables.add(bindSearchButton(viewModel));
    }

    private Disposable bindAutoCompleteHistory(BehaviorSubject<List<MovieSuggestion>> searchHistoryList) {
/*        ArrayAdapter<MovieSuggestion> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                searchHistoryList.getValue());
*/
        List<MovieSuggestion> temp = new ArrayList<>();
        temp.add(new MovieSuggestion("2232","first"));
        temp.add(new MovieSuggestion("22312","third"));
        temp.add(new MovieSuggestion("22324","second"));
        SearchFieldAdapter adapter = new SearchFieldAdapter(this, android.R.layout.simple_dropdown_item_1line,
                searchHistoryList);

        /*String[] test = {"first","second","third"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                test);
        */

        inputSearch.setAdapter(adapter);
        inputSearch.setThreshold(1);
        return adapter.getDisposable();
    }


    private Disposable bindSearchButton(HomeViewModel viewModel) {

        return RxView.clicks(searchMovies)
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(ignoredValue -> addSuggestionToHistoryAndSearch(viewModel));
    }

    private void addSuggestionToHistoryAndSearch(HomeViewModel viewModel) {
        viewModel.insertSearchResult();
        viewModel.startRequestMovies();
    }


    private Disposable bindRecyclerView(BehaviorSubject<List<Movie>> movieList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MoviesRecyclerAdapter adapter = new MoviesRecyclerAdapter(movieList);
        recyclerView.setAdapter(adapter);
        disposables.add(movieList.share()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ignoredValue -> recyclerView.scrollToPosition(0)));

        return adapter.getDisposable();
    }

    private Disposable bindProgressView(BehaviorSubject<Boolean> loading) {
        return loading.share()
                .observeOn(AndroidSchedulers.mainThread())
                .map(state -> state ? View.VISIBLE : View.GONE)
                .subscribe(searchProgress::setVisibility);
    }

    private Disposable bindSearchMovieTextView(BehaviorSubject<String> searchMovies) {
        return RxTextView.textChangeEvents(inputSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MICROSECONDS)
                .map(TextViewTextChangeEvent::text)
                .map(String::valueOf)
                .subscribe(searchMovies::onNext);
    }
}
