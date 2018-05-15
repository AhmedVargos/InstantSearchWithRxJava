
package fallenleafapps.com.instantsearchdemowithrxjava.model.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponseModule {

    @SerializedName("page")
    @Expose
    private final Integer page;
    @SerializedName("total_results")
    @Expose
    private final Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private final Integer totalPages;
    @SerializedName("results")
    @Expose
    private final List<Movie> movies;

    public SearchResponseModule(Integer page, Integer totalResults, Integer totalPages, List<Movie> movies) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movies = movies;
    }


    public Integer getPage() {
        return page;
    }


    public Integer getTotalResults() {
        return totalResults;
    }


    public Integer getTotalPages() {
        return totalPages;
    }


    public List<Movie> getMovies() {
        return movies;
    }


}
