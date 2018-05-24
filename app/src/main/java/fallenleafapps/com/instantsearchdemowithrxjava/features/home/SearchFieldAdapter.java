package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.R;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class SearchFieldAdapter extends ArrayAdapter<MovieSuggestion> {

    private BehaviorSubject<List<MovieSuggestion>> searchHistoryList;
    private int layoutResourceId;
    private final Disposable disposable;

    public SearchFieldAdapter(@NonNull Context context, int resource, @NonNull BehaviorSubject<List<MovieSuggestion>> searchHistoryList) {
        super(context, resource);
        this.searchHistoryList = searchHistoryList;
        this.layoutResourceId = resource;
        disposable = this.searchHistoryList.share()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suggestionList -> {notifyDataSetInvalidated();
                                                notifyDataSetChanged();});
    }

    @Override
    public int getCount() {
        return searchHistoryList.getValue().size();
    }

    @Nullable
    @Override
    public MovieSuggestion getItem(int position) {
        if(position < searchHistoryList.getValue().size()) {
            return searchHistoryList.getValue().get(position);
        }else {
            return super.getItem(position);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layoutResourceId, null);
        }
        if(position < searchHistoryList.getValue().size()){
            MovieSuggestion movieSuggestion = searchHistoryList.getValue().get(position);
            if (movieSuggestion != null) {
                TextView customerNameLabel = (TextView) v.findViewById(android.R.id.text1);
                if (customerNameLabel != null) {
                    customerNameLabel.setText(String.valueOf(movieSuggestion.getWord()));
                }
            }
        }
        return v;
    }

    public Disposable getDisposable() {
        return disposable;
    }
}
