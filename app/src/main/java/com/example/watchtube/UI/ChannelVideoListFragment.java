package com.example.watchtube.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watchtube.ChannelVideoListCustomAdapter;
import com.example.watchtube.ChannelVideoListPresenter;
import com.example.watchtube.Contract;
import com.example.watchtube.R;
import com.example.watchtube.model.data.ChannelVideoPreviewData;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;

/**
 * Created by Nikita on 06.09.2018.
 */

public class ChannelVideoListFragment extends Fragment implements Contract.View {

    private ChannelVideoListPresenter mPresenter;
    private GoogleAccountCredential mCredential;
    private String mChannelId;
    private RecyclerView mRecyclerView;
    private ChannelVideoListCustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void setCredentials(GoogleAccountCredential credential){
        mCredential = credential;
    }

    public void setChannelId(String channelId){
        mChannelId = channelId;
        mPresenter = new ChannelVideoListPresenter(this, mCredential, mChannelId);
        mPresenter.onStart();
        mAdapter.clearList(); //всеровно экземпляр не пересоздается. Сделать так чтобы не пересоздавало заново, а тупо обращалось к старому
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("VideoList", "fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CreateFragment", "created");
        View v = inflater.inflate(R.layout.fragment_video_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChannelVideoListCustomAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    public void addVideosToList(ArrayList<ChannelVideoPreviewData> data){
        mAdapter.addVideosToList(data);
    }

    public void fetchVideoListData(){
        mPresenter.fetchVideoList();
    }

    @Override
    public void onStop(){
        super.onStop();
        mPresenter.onStop();
    }
}