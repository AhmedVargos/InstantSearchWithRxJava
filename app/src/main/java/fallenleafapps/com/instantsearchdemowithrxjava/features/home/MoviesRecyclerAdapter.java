package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.Application;
import fallenleafapps.com.instantsearchdemowithrxjava.R;
import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.Movie;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder>{

    private final BehaviorSubject<List<Movie>> dataSource;
    private final Disposable disposable;

    MoviesRecyclerAdapter(BehaviorSubject<List<Movie>> dataSource) {
        this.dataSource = dataSource;
        disposable = this.dataSource.share()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> notifyDataSetChanged());

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(view(parent));
    }

    private View view(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieTitle.setText(dataSource.getValue().get(position).getTitle());
        holder.movieDate.setText(dataSource.getValue().get(position).getReleaseDate());
        Glide.with(Application.getContext())
                .load("https://image.tmdb.org/t/p/w500" + dataSource.getValue().get(position).getPosterPath())
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return dataSource.getValue().size();
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView movieTitle;
        private final TextView movieDate;
        private final ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieDate = itemView.findViewById(R.id.movie_date);
            movieImage = itemView.findViewById(R.id.movie_image);
        }
    }
}
