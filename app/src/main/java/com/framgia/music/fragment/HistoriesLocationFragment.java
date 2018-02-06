package com.framgia.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music.DBManager;
import com.framgia.music.R;
import com.framgia.music.adapter.HistoriesAdapter;
import com.framgia.music.model.HistoriesLocation;
import java.util.Collections;
import java.util.List;

/**
 * Created by trong_tai on 28/02/2018.
 */

public class HistoriesLocationFragment extends Fragment {

    private RecyclerView mRecyclerViewHistoriesLocation;
    HistoriesAdapter mHistoriesAdapter;
    List<HistoriesLocation> historiesLocationList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histories_location, container, false);

        DBManager dbManager = new DBManager(getContext());
        historiesLocationList = dbManager.GetAllHistoriesLoaction();
        Collections.reverse(historiesLocationList);
        mRecyclerViewHistoriesLocation = view.findViewById(R.id.recycler_histories_location);
        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerViewHistoriesLocation.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewHistoriesLocation.addItemDecoration(mDividerItemDecoration);
        mHistoriesAdapter = new HistoriesAdapter(getContext(),historiesLocationList);
        mRecyclerViewHistoriesLocation.setAdapter(mHistoriesAdapter);
        return view;
    }
}
