package com.example.magicplacefinder.viewModelTests;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.magicplacefinder.models.SearchState;
import com.example.magicplacefinder.viewmodel.PlacesViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PlacesVMTest {


    private PlacesViewModel placesViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init(){
        placesViewModel = new PlacesViewModel();
    }

    @Test
    public void observeSearchState_whenPosted_returnTrue() throws Exception {
        SearchState searchState = SearchState.NO_RESULTS;
        LiveDataTestUtils<SearchState> liveDataTestUtil = new LiveDataTestUtils<>();

        placesViewModel.postSearchState(searchState);
        SearchState observedSearchState = liveDataTestUtil.getValue(placesViewModel.getSearchState());

        Assert.assertEquals(searchState, observedSearchState);
    }
}
