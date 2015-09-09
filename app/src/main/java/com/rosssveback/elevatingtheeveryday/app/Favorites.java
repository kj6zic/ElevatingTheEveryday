package com.rosssveback.elevatingtheeveryday.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.declangao.wordpressreader.R;

/**
 * Created by a1121661 on 9/4/15.
 */
public class Favorites extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public Favorites(){
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter, container, false);
        //TODO: implement list view for favorite brands


        return rootView;
    }

    //refresh the view
    public void onRefresh(){

    }
}
