package fallenleafapps.com.instantsearchdemowithrxjava.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

/**
 * Created by Asmaa on 5/19/2018.
 */
@Entity
public class MovieSuggestion {
        @PrimaryKey
        @NonNull
        private String word;

        public MovieSuggestion(@NonNull String word) {this.word = word;}

        public String getWord(){return this.word;}

}
