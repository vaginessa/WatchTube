package com.example.watchtube;

import com.example.watchtube.Contract;
import com.example.watchtube.UI.ChannelPlaylistListFragment;
import com.example.watchtube.model.APIUtils.YouTubeAPIUtils;
import com.example.watchtube.model.data.ChannelPlaylistPreviewData;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 13.09.2018.
 */

public class ChannelPlaylistListPresenter implements Contract.Presenter {

    private String mChannelId;
    private ChannelPlaylistListFragment mFragment;
    private CompositeDisposable mDisposable;
    private GoogleAccountCredential mCredential;
    private YouTubeAPIUtils mYouTubeAPIUtils;

    public ChannelPlaylistListPresenter(ChannelPlaylistListFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void onStart() {
        mDisposable = new CompositeDisposable();
    }

    public void setCredential(GoogleAccountCredential credential){
        mCredential = credential;
    }

    public void setChannelId(String channelId){
        mChannelId = channelId;
        prepareYouTubeUtils();
    }

    private void prepareYouTubeUtils(){
        mYouTubeAPIUtils = new YouTubeAPIUtils(mFragment.getContext(), this);
        mYouTubeAPIUtils.setupCredential(mCredential);
        mYouTubeAPIUtils.setupChannelId(mChannelId);
    }

    public void fetchPlaylistList(){
        Disposable disposable = mYouTubeAPIUtils.getChannelPlaylistPreviewData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<ChannelPlaylistPreviewData>>() {
                    @Override
                    public void onSuccess(ArrayList<ChannelPlaylistPreviewData> channelPlaylistPreviewData) {
                        mFragment.addPlaylistsToList(channelPlaylistPreviewData);
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                });
        mDisposable.add(disposable);
    }

    @Override
    public void onStop() {
        mDisposable.clear();
    }
}
