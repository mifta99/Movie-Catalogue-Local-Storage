package com.mifta.project.id.dicodingsubmission4.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingsubmission4.R;
import com.mifta.project.id.dicodingsubmission4.activity.TvShowDetailActivity;
import com.mifta.project.id.dicodingsubmission4.adapter.CardViewTvShowAdapter;
import com.mifta.project.id.dicodingsubmission4.database.TvShowHelper;
import com.mifta.project.id.dicodingsubmission4.model.MoviesItems;

import java.util.ArrayList;


public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView rvTvShow;
    private TvShowHelper tvShowHelper;

    private static CardViewTvShowAdapter cardViewTvShowAdapter;
    private ArrayList<MoviesItems> listMovies;

    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShow = view.findViewById(R.id.rv_tvshow);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        listMovies = new ArrayList<>();
        cardViewTvShowAdapter = new CardViewTvShowAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        tvShowHelper.open();
        listMovies.clear();
        listMovies.addAll(tvShowHelper.getAllTv());
        cardViewTvShowAdapter.setData(listMovies);
        cardViewTvShowAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTvShow.setLayoutManager(layoutManager);
        rvTvShow.setAdapter(cardViewTvShowAdapter);
        cardViewTvShowAdapter.setOnItemClickCallback(new CardViewTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }
        });
        tvShowHelper.close();
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), TvShowDetailActivity.class);
        moveWithObjectActivity.putExtra(TvShowDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }
}

