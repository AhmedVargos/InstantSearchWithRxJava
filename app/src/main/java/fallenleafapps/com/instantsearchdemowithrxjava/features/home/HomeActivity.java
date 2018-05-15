package fallenleafapps.com.instantsearchdemowithrxjava.features.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import fallenleafapps.com.instantsearchdemowithrxjava.R;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.search_movies)
    AppCompatImageButton searchMovies;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_progress)
    ProgressBar searchProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }
}
