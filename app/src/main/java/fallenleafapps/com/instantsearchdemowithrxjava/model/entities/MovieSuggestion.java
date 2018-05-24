package fallenleafapps.com.instantsearchdemowithrxjava.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Created by Asmaa on 5/19/2018.
 */
@Entity
public class MovieSuggestion {
        @PrimaryKey
        @NonNull
        private final String uuid; //new Primary key Ahmed

        private final String word; //Cannot be the primary key because the unique constraint Ahmed

        public MovieSuggestion(@NonNull String uuid, String word) {
                this.uuid = uuid;
                this.word = word;
        }

        public String getWord(){return this.word;}

        @NonNull
        public String getUuid() {
                return uuid;
        }

        @Override
        public String toString() {
                return this.word;
        }
}

