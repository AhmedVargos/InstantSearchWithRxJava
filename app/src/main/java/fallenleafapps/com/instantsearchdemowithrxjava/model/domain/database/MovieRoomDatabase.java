package fallenleafapps.com.instantsearchdemowithrxjava.model.domain.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fallenleafapps.com.instantsearchdemowithrxjava.model.entities.MovieSuggestion;

/**
 * Created by Asmaa on 5/19/2018.
 */
@Database(entities = {MovieSuggestion.class}, version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase{

    public abstract MovieSuggestionDao getMovieSuggestions();

    private static MovieRoomDatabase INSTANCE;


    static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "MovieSuggestionDB")
                            .build();

                }
            }
        }
        return INSTANCE;

    }


}
