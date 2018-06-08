package fallenleafapps.com.instantsearchdemowithrxjava.home.test;



import org.junit.Test;

import java.util.List;

import fallenleafapps.com.instantsearchdemowithrxjava.Application;
import fallenleafapps.com.instantsearchdemowithrxjava.features.home.HomeViewModel;
import fallenleafapps.com.instantsearchdemowithrxjava.home.test.Mock.DataSourceGenerator;
import fallenleafapps.com.instantsearchdemowithrxjava.model.domain.DataSource;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Hanaa on 6/2/2018.
 */


public class HomeViewModelTest {
    HomeViewModel homeViewModel;
    DataSource dataSource;
    DataSourceGenerator dataSourceGenerator;
    TestScheduler scheduler;
    @Test
    public void startRequestMoviesFromActualDataSourceWithoutSearch() throws Exception{
        HomeViewModel homeViewModel=getHomeViewModel(false,false);
        homeViewModel.searchMovies.onNext("");
        homeViewModel.startRequestMovies();
        scheduler.triggerActions();
        assertEquals(20, homeViewModel.movieList.getValue().size());
    }
    @Test
    public void startRequestMoviesFromActualDataSourceWithSearch() throws Exception{
        HomeViewModel homeViewModel=getHomeViewModel(false,false);
        homeViewModel.searchMovies.onNext("str");
        homeViewModel.startRequestMovies();
       scheduler.triggerActions();

        assertEquals(0, homeViewModel.movieList.getValue().size());
    }
    public HomeViewModel getHomeViewModel(boolean error,boolean dataSourceFlag){
        scheduler=new TestScheduler();
        dataSourceGenerator=new DataSourceGenerator(false);
        dataSource=dataSourceFlag? new DataSource():new DataSourceGenerator(error);
        return new HomeViewModel(Application.getContext(),dataSource,scheduler);}

    @Test
    public  void getHistorySearchTest(){
        HomeViewModel homeViewModel=getHomeViewModel(false,false);
        homeViewModel.searchMovies.onNext("q");
        homeViewModel.getHistorySearch();
        scheduler.triggerActions();
        assertEquals(1,homeViewModel.searchHistoryList.getValue().size());
    }
}
