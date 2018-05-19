package fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;
import io.reactivex.Flowable;

/**
 * Created by Asmaa on 5/19/2018.
 */
@Dao
public interface MovieSuggestionDao {
    @Insert
    void insert(MovieSuggestion movieSuggestion);

    @Query("select * from MovieSuggestion")
    Flowable<List<MovieSuggestion>> getAllSuggestions();


}
